package com.jiwei.action;

import java.io.File; 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiwei.entity.Dynamic;
import com.jiwei.entity.Dynote;
import com.jiwei.entity.History;
import com.jiwei.entity.News;
import com.jiwei.entity.Record;
import com.jiwei.entity.Useradmin;
import com.jiwei.entity.News;
import com.jiwei.jsonstatus.model.JsonResult;
import com.jiwei.service.entityService;

import io.swagger.annotations.Api;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@CrossOrigin(origins = "*")
@Controller
@Scope("prototype")
@Api(tags = "新闻滚动")
public class NewsAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(NewsAction.class);

	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}
	public void addRecord(Useradmin useradmin) throws Exception {
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		int maximum = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		int minmum = ca.getActualMinimum(Calendar.DAY_OF_MONTH);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		Calendar cal = (Calendar) ca.clone();
		cal.add(Calendar.DATE, maximum - day);
		Date dateD = cal.getTime();
		String strD = f.format(dateD);
		cal = (Calendar) ca.clone();
		cal.add(Calendar.DATE, minmum - day);
		Date dateX = cal.getTime();
		String strX = f.format(dateX);
		List<String> ziduan = new ArrayList<String>();
		ziduan.add("useradmin.id");
		ziduan.add("date");
		List<Object> zhi = new ArrayList<Object>();
		zhi.add(useradmin.getId());
		zhi.add(dateX);
		zhi.add(dateD);
		List<Integer> type = new ArrayList<Integer>();
		type.add(0);
		type.add(9);
		List<Record> records = es.getObjectAllByTy(Record.class, true, 0, 1, ziduan, zhi, null, type, null, null, null);
		if (records != null && records.size() > 0) {
			Record record = records.get(0);
			record.setCount(record.getCount() + 1);
			es.caozuoObj(record, 1);
		} else {
			Record record = new Record();
			record.setCount(1);
			record.setUseradmin(useradmin);
			record.setDate(new Date());
			es.caozuoObj(record, 0);
		}
	}
	@RequestMapping(value = "admin/getNewsAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String admingetNewsAll(Integer start, Integer size, HttpSession session) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("id");
			sqlzd.add("date");
			List<News> List = es.getObjectAllByTy(News.class, true, start, size, null, null, sqlzd, null, "id", null,
					null);
			if (List != null) {
				json = JSONArray.fromObject(List, jsonConfig);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "数据上传出错", null)).toString();
		}
	}

	@RequestMapping(value = "admin/addNews.do", method = RequestMethod.POST, consumes = "multipart/form-data", produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addNews(@ModelAttribute News obj, @RequestParam(value = "file", required = false) MultipartFile file,
			Integer id, HttpSession session, HttpServletRequest request) throws Exception {
		getJson1();
		if (obj != null && obj.getTitle() != null) {
			synchronized (this) {
				// System.out.println(Useradmins.toString());
				if (es.getObject(new News(obj.getTitle())) != null) {
					return JSONArray.fromObject(new JsonResult("200", "标题已存在", null)).toString();
				} else {
					String a = "";
					String img = null;

					if (file != null && file.getSize() > 0) {
						String realPath = request.getServletContext().getRealPath("/img/news/");
						String suffix = file.getOriginalFilename()
								.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
						img = new Date().getTime() + "_" + UUID.randomUUID().toString() + "." + suffix;
						File targetFile = new File(realPath, img);
						if (!targetFile.exists()) {
							targetFile.mkdirs();
						}
						// 保存
						try {
							// logger.info(realPath);
							if (file.getOriginalFilename().matches(".*\\.jpg")
									|| file.getOriginalFilename().matches(".*\\.png")) {
								file.transferTo(targetFile);
								File input = new File(realPath + img);
								Thumbnails.of(input).scale(1f).outputQuality(0.25f).toFile(targetFile);
								// file.transferTo(targetFile);
								obj.setImg(img);
							} else {
								a = ",只允许上传png或者jpg";
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
					obj.setUseradmin(useradmin);
					obj.setDate(new Timestamp(new Date().getTime()));
					if (es.caozuoObj(obj, 0)) {
						addRecord(useradmin);
						return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
					} else {
						return JSONArray.fromObject(new JsonResult("200", "失败", null)).toString();
					}
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}

	}

	@RequestMapping(value = "admin/delNews.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delNews(Integer id, HttpSession session, HttpServletRequest request) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		synchronized (this) {
			News obj = (News) es.getObjectbyId(News.class, id, 0);
			if (obj != null) {
				es.caozuoObj(obj, 2);
				delFolder(request.getServletContext().getRealPath("/img/news/" + obj.getImg()));
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "不存在该帖子", null)).toString();
			}
		}
	}

	@RequestMapping(value = "admin/updateNews.do", method = RequestMethod.POST, consumes = "multipart/form-data", produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateNews(@ModelAttribute News obj,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session,
			HttpServletRequest request) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (obj != null && obj.getId() != null && obj.getTitle() != null) {
			News obj1 = null;
			synchronized (this) {
				obj1 = (News) es.getObjectbyZd(News.class, "title", obj.getTitle(), true);
				if (obj1 != null && obj1.getTitle() != null && obj1.getTitle().equals(obj.getTitle())
						&& !obj1.getId().equals(obj.getId())) {
					return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
				} else {
					String a = "";
					String img = null;
					obj1 = (News) es.getObjectbyId(News.class, obj.getId(), 0);
					if (file != null && file.getSize() > 0) {
						String realPath = request.getServletContext().getRealPath("/img/news/");
						if (obj1.getImg() == null) {
							String suffix = file.getOriginalFilename()
									.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
							img = new Date().getTime() + "_" + UUID.randomUUID().toString() + "." + suffix;
							obj1.setImg(img);
						} else {
							img = obj1.getImg();
						}
						File targetFile = new File(realPath, img);
						if (!targetFile.exists()) {
							targetFile.mkdirs();
						}
						// 保存
						try {
							// logger.info(realPath);
							if (file.getOriginalFilename().matches(".*\\.jpg")
									|| file.getOriginalFilename().matches(".*\\.png")) {
								file.transferTo(targetFile);
								File input = new File(realPath + img);
								Thumbnails.of(input).scale(1f).outputQuality(0.25f).toFile(targetFile);
								if (obj.getImg() != null) {
									delFolder(request.getServletContext().getRealPath("/img/news/" + obj.getImg()));
								}
							} else {
								a = ",只允许上传png或者jpg";
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (obj1.getUseradmin() == null) {
						Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
						obj1.setUseradmin(useradmin);
					}
					if (obj.getTitle() != null && !obj.getTitle().equals(obj1.getTitle())) {
						obj1.setTitle(obj.getTitle());
					}
					es.caozuoObj(obj1, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功" + a, null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "getNewsall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getNewsall(Integer start, Integer size, HttpSession session) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("img");
			sqlzd.add("id");
			List<News> List = es.getObjectAllByTy(News.class, true, start, size, null, null, sqlzd, null, "id", null,
					null);
			Integer number = es.getObjectAllByTyCount(News.class, true, null, null, null, null, null);
			if (List != null) {
				for (int i = 0; i < List.size(); i++) {
					List.get(i).setTitle(StringEscapeUtils.unescapeHtml4(List.get(i).getTitle()));
				}
				// json = JSONArray.fromObject(List, jsonConfig);
				return JSONArray.fromObject(new JsonResult("200", "成功",
						JSON.toJSONString(List, SerializerFeature.WriteDateUseDateFormat), number)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "无数据", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "数据上传出错", null)).toString();
		}
	}

	@RequestMapping(value = "findNewsById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findNewsById(Integer Id, Integer updown, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			List<String> ziduan = new ArrayList<String>();
			List<Integer> type = new ArrayList<Integer>();
			List<Object> zhi = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("content");
			sqlzd.add("date");
			sqlzd.add("img");
			sqlzd.add("id");
			ziduan.add("id");
			zhi.add(Id);
			String odb = null;
			if (updown != null) {
				if (updown > 0) {
					type.add(8);
				} else {
					type.add(10);
					odb = "id";
				}
			} else {
				type.add(0);
			}
			List<News> List = es.getObjectAllByTy(News.class, true, null, null, ziduan, zhi, sqlzd, type, odb, null,
					null);
			if (List != null && List.size() > 0) {
				List.get(0).setTitle(StringEscapeUtils.unescapeHtml4(List.get(0).getTitle()));
				List.get(0).setContent(StringEscapeUtils.unescapeHtml4(List.get(0).getContent()));
				// JSONObject json = JSONObject.fromObject(List.get(0));
				return JSONArray.fromObject(new JsonResult("200", "成功",
						JSON.toJSONString(List.get(0), SerializerFeature.WriteDateUseDateFormat))).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/findNewsById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String adminfindNewsById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			News obj1 = (News) es.getObjectbyId(News.class, Id, 0);
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

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public void delFolder(String folderPath) {
		try {
			logger.info(folderPath);
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			System.gc();
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
}
