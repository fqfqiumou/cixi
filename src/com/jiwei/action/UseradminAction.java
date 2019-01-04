package com.jiwei.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.ls.LSInput;

//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
import com.jiwei.entity.Affairs;
import com.jiwei.entity.Useradmin;
import com.jiwei.jsonstatus.model.JsonResult;
import com.jiwei.service.entityService;

import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import redis.clients.jedis.Jedis;

@CrossOrigin(origins = "*")
@Controller
@Scope("prototype")
@Api(tags = "用户管理") 
public class UseradminAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(UseradminAction.class);

	public void getJson2(HttpServletResponse response) throws Exception {
		response.getWriter().flush();
		response.getWriter().close();
	}
	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		/*
		 * response.setHeader("Access-Control-Allow-Origin", "*");
		 * response.setHeader("Access-Control-Allow-Methods",
		 * "POST,GET,OPTIONS,DELETE");
		 * response.setHeader("Access-Control-Max-Age", "3600");
		 * response.setHeader("Access-Control-Allow-Headers",
		 * "Origin, X-Requested-With, Content-Type, Accept");
		 * response.setContentType("text/html");
		 * response.setCharacterEncoding("utf-8");
		 */
	}

	@RequestMapping(value = "login.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String login(@ModelAttribute Useradmin useradmin, HttpSession session) throws Exception {
		getJson1();
		if (useradmin != null && useradmin.getName() != null && useradmin.getPassword() != null) {
			Useradmin Useradmin = null;
			useradmin.setPassword(getSHA256Str(encrypByMd5Jar(useradmin.getPassword())));
			List<Useradmin> u = es.getObject(useradmin);
			System.out.println(useradmin.getName());
			if (u != null && u.size() > 0) {
				Useradmin = u.get(0);
				if(Useradmin.getPower()>0){
				session.setAttribute("useradmin", Useradmin);
				/*
				 * Jedis jedis = new Jedis("172.17.0.1",6379); String
				 * admin=getSHA256Str("useradmin" + useradmin.getId()); byte[]
				 * b=admin.getBytes(); jedis.set(b,
				 * SerializeUtil.serialize(Useradmin)); jedis.expire(b, 60*30);
				 */
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}else{
					return JSONArray.fromObject(new JsonResult("200", "帐号已被停用", null)).toString();
				}
			} else {
				return JSONArray.fromObject(new JsonResult("200", "用户名或密码错误", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "数据为空", null)).toString();
		}
	}

	@RequestMapping(value = "logout.do", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String logout(HttpSession session, HttpServletResponse response) throws Exception {
		session.removeAttribute("useradmin");
		session.invalidate();
		return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
	}

	@RequestMapping(value = "superadmin/addUseradmin.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addUseradmin(@ModelAttribute Useradmin useradmin, Integer power, HttpSession session)
			throws Exception {
		Useradmin admin = (Useradmin) session.getAttribute("useradmin");
		getJson1();
		if (useradmin != null && useradmin.getPhone() != null 
				&& useradmin.getName() != null) {
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			synchronized (this) {
				ziduan.add("name");
				ziduan.add("phone");
				zhi.add(useradmin.getName());
				zhi.add(useradmin.getPhone());
				type.add(1);
				type.add(1);
				List<Useradmin> Useradmins = es.getObjectAllByTy(Useradmin.class, true, 0, 1, ziduan, zhi, null, type,
						null, null, null);
				// System.out.println(Useradmins.toString());
				if (Useradmins != null && Useradmins.size() > 0) {
					return JSONArray.fromObject(new JsonResult("200", "用户名或手机号已存在", null)).toString();
				} else {
					if (admin.getPower() != 413) {
						return JSONArray.fromObject(new JsonResult("200", "没有权限添加用户", null)).toString();
					} else {
						useradmin.setPower(power);
						useradmin.setPassword(getSHA256Str(encrypByMd5Jar("111111")));
					}
					if (es.caozuoObj(useradmin, 0)) {
						return JSONArray.fromObject(new JsonResult("200", "成功,默认密码为111111", null)).toString();
					} else {
						return JSONArray.fromObject(new JsonResult("200", "失败", null)).toString();
					}
				}
			}
		} else {
			return "请检查信息是否为空";
		}

	}

	@RequestMapping(value = "superadmin/updateUseradmin.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateUseradmin(@ModelAttribute Useradmin useradmin, Integer roleId, HttpSession session)
			throws Exception {
		Useradmin admin = (Useradmin) session.getAttribute("useradmin");
		getJson1();
		if (useradmin != null && useradmin.getId() != null) {
			synchronized (this) {
				Useradmin admin1 = (Useradmin) es.getObjectbyId(Useradmin.class, useradmin.getId(), 0);
				if (admin1 != null) {
					List<String> ziduan = new ArrayList<String>();
					List<Object> zhi = new ArrayList<Object>();
					List<Integer> type = new ArrayList<Integer>();
					if (useradmin.getName() != null) {
						ziduan.add("name");
						zhi.add(useradmin.getName());
						type.add(1);
					}
					if (useradmin.getPhone() != null) {
						ziduan.add("phone");
						zhi.add(useradmin.getPhone());
						type.add(1);
					}
					List<Useradmin> Useradmins = null;
					if (ziduan.size() > 0) {
						Useradmins = es.getObjectAllByTy(Useradmin.class, true, 0, 1, ziduan, zhi, null, type, null,
								null, null);
					}
					if (Useradmins != null && Useradmins.size() > 0 && Useradmins.get(0).getId() != useradmin.getId()) {
						return JSONArray.fromObject(new JsonResult("200", "用户名或手机号已存在", null)).toString();
					} else {
						if (useradmin.getPassword() != null) {
							admin1.setPassword(useradmin.getPassword());
						}
						if (roleId != null) {
							if (roleId == 1 && admin.getPower() == 413) {
								admin1.setPower(413);
							} else {
								admin1.setPower(1);
							}
						}
						if (useradmin.getName() != null) {
							admin1.setName(useradmin.getName());
						}
						if (useradmin.getPhone() != null) {
							admin1.setPhone(useradmin.getPhone());
						}
						if (useradmin.getContact() != null) {
							admin1.setContact(useradmin.getContact());
						}
						if (es.caozuoObj(admin1, 1)) {
							return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
						} else {
							return JSONArray.fromObject(new JsonResult("200", "失败", null)).toString();
						}
					}
				} else {
					return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}

	}

	@RequestMapping(value = "superadmin/delUseradmin.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delUseradmin(Integer userAdminId, HttpSession session) throws Exception {
		Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
		getJson1();
		if (userAdminId != null) {
			synchronized (this) {
				Useradmin Useradmin = (Useradmin) es.getObjectbyId(Useradmin.class, userAdminId, 0);
				if (Useradmin != null && Useradmin.getPower() < useradmin.getPower()) {
					if(Useradmin.getPower()>0){
					Useradmin.setPower(0);
					}else{
						Useradmin.setPower(1);
					}
					if (es.caozuoObj(Useradmin, 1)) {
						return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
					} else {
						return JSONArray.fromObject(new JsonResult("200", "失败", null)).toString();
					}
				} else {
					return JSONArray.fromObject(new JsonResult("200", "管理员不存在,或者你的权限不够", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}

	}

	@RequestMapping(value = "superadmin/rePassword.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String rePassword(Integer userAdminId, HttpSession session) throws Exception {
		Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
		getJson1();
		if (userAdminId != null) {
			Useradmin Useradmin = (Useradmin) es.getObjectbyId(Useradmin.class, userAdminId, 0);
			if (Useradmin != null && Useradmin.getPower() < useradmin.getPower()) {
				Useradmin.setPassword("65249994cf11a8d417f7113406da386ebdf22bb90f55f950df6c70e8ddfb996e");
				if (es.caozuoObj(Useradmin, 1)) {
					return JSONArray.fromObject(new JsonResult("200", "成功默认密码为111111", null)).toString();
				} else {
					return JSONArray.fromObject(new JsonResult("200", "失败", null)).toString();
				}
			} else {
				return JSONArray.fromObject(new JsonResult("200", "管理员不存在,或者你的权限不够", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}

	}

	@RequestMapping(value = "superadmin/getUserAdminAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getUserAdminAll(Integer start, Integer size, HttpSession session) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("id");
			sqlzd.add("phone");
			sqlzd.add("contact");
			sqlzd.add("name");
			sqlzd.add("power");
			List<Class> c=new ArrayList<Class>();
			c.add(Useradmin.class);
			List<Useradmin> List = es.getObjectAllByTy(Useradmin.class, true, start, size, null, null, sqlzd, null,
					"id", null, c);
			if (List != null) {
				json = JSONArray.fromObject(List, jsonConfig);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "无数据", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查数据是否为空", null)).toString();
		}
	}
	@RequestMapping(value = "superadmin/findUseradminById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findUseradminById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Useradmin obj1 = (Useradmin) es.getObjectbyId(Useradmin.class, Id, 0);
			if (obj1 != null) {
				JSONObject json = JSONObject.fromObject(obj1);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}
	// 获取申请记录
	// 修改密码,重置密码,发送验证码
	@RequestMapping(value = "modifyPwd.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String modifyPwd(String phone, Integer xiugai, String renewpwd, String newpwd, String yzm,
			HttpSession session) throws Exception {
		getJson1();
		if (phone != null && xiugai != null) {
			Useradmin useradmin = (Useradmin) es.getObjectbyZd(Useradmin.class, "phone", phone, true);
			if (useradmin != null) {
				if (xiugai == 0) {
					renewpwd = getSHA256Str(encrypByMd5Jar(renewpwd));
					System.out.println("renewpwd=" + renewpwd + "旧密码:" + useradmin.getPassword().trim());
					if (useradmin.getPassword().trim().equals(renewpwd.trim())) {
						newpwd = getSHA256Str(encrypByMd5Jar(newpwd));
						useradmin.setPassword(newpwd);
						es.caozuoObj(useradmin, 1);
						session.removeAttribute("useradmin");
						return "修改成功请重新登陆";
					} else {
						return "密码不匹配";
					}
				} else if (xiugai == 1) {
					 Jedis jedis = new Jedis("172.17.0.1", 6379);
				//	Jedis jedis = new Jedis();
					String phone1 = jedis.get(phone);
					Integer a;
					if (phone1 == null) {
						a = (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
						 //sendSms(phone, a);
						jedis.set(phone, a.toString());
						jedis.expire(phone, 55);
						jedis.close();
						phone = null;
						System.out.println(a);
						return "成功";
					} else {
						jedis.close();
						phone = null;
						return "成功";
					}
				} else {
					 Jedis jedis = new Jedis("172.17.0.1", 6379);
				//	Jedis jedis = new Jedis();
					String phone1 = jedis.get(useradmin.getPhone());
					jedis.close();
					if (phone1 == null || yzm == null) {
						return "验证码不正确";
					}
					if (phone1.equals(yzm)) {
						useradmin.setPassword("65249994cf11a8d417f7113406da386ebdf22bb90f55f950df6c70e8ddfb996e");
						es.caozuoObj(useradmin, 1);
						return "重置成功请重新登陆";
					} else {
						return "验证码不正确";
					}
				}
			} else {
				return "该用户不存在";
			}
		} else {
			return "请输入手机号";
		}

	}

	public String encrypByMd5Jar(String context) {
		System.out.println(context);
		return DigestUtils.md5Hex(context);

	}

	/***
	 * 利用Apache的工具类实现SHA-256加密
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 */
	public String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		str = null;
		return encdeStr;
	}

	/*public SendSmsResponse sendSms(String phone, Integer a) throws ClientException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		final String accessKeyId = "";// 你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = "";// 你的accessKeySecret，参考本文档步骤2
		// 初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("极位");
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode("SMS_128550215");
		// 随机验证码
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${ name},您的验证码为${code}"时,此处的值为
		// request.setTemplateParam("{\"number\":\"123\"}");
		request.setTemplateParam("{\"code\":\"" + a + "\"}");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");
		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null) {
			System.out.println(sendSmsResponse.getCode());
		}
		return sendSmsResponse;
	}*/

}
