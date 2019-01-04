package com.jiwei.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.jiwei.entity.Dynamic;
import com.jiwei.entity.Dynote;
import com.jiwei.entity.Dynamic;
import com.jiwei.entity.News;
import com.jiwei.entity.Zonenote;
import com.jiwei.jsonstatus.model.JsonResult;
import com.jiwei.service.entityService;

import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@CrossOrigin(origins = "*")
@Controller
@Scope("prototype")
@Api(tags = "基层动态")
public class DynamicAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(DynamicAction.class);

	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	@RequestMapping(value = "admin/findDynamicById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findDynamicById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Dynamic obj1 = (Dynamic) es.getObjectbyId(Dynamic.class, Id, 0);
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

	@RequestMapping(value = "admin/updateDynamic.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateDynamic(@ModelAttribute Dynamic dynamic, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (dynamic != null && dynamic.getId() != null && dynamic.getName() != null) {
			Dynamic dynamic1 = null;
			synchronized (this) {
				dynamic1 = (Dynamic) es.getObjectbyZd(Dynamic.class, "name", dynamic.getName(), true);
				if (dynamic1 != null && !dynamic1.getId().equals(dynamic.getId())) {
					return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
				} else {
					es.caozuoObj(dynamic, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/addDynamic.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addDynamic(@ModelAttribute Dynamic obj, HttpSession session) throws Exception {
		getJson1();
		if (obj != null && obj.getName() != null) {
			synchronized (this) {
				// System.out.println(Useradmins.toString());
				if (es.getObject(new Dynamic(obj.getName())) != null) {
					return JSONArray.fromObject(new JsonResult("200", "名称已存在", null)).toString();
				} else {
					if (es.caozuoObj(obj, 0)) {
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

	@RequestMapping(value = "admin/delDynamic.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delDynamic(Integer id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		synchronized (this) {
			Dynamic obj = (Dynamic) es.getObjectbyId(Dynamic.class, id, 0);
			if (obj != null) {
				List<String> ziduan=new ArrayList<String>();
				ziduan.add("dynamic.id");
				List<Object> zhi=new ArrayList<Object>();
				zhi.add(id);
				List<Integer> type=new ArrayList<Integer>();
				type.add(0);
				List<Dynote> List = es.getObjectAllByTy(Dynote.class, true, null, null, ziduan, zhi, null, type, "id", null,
						null);
				if(List!=null&&List.size()>0){
					List<Object> objs=new ArrayList<Object>();
					for (int i = 0; i < List.size(); i++) {
						Dynote zonenote=new Dynote();
						zonenote=List.get(i);
						zonenote.setDynamic(null);
						objs.add(zonenote);
					}
					if(objs!=null&&objs.size()>0){
						es.plcaozuoObj(objs, 1);
					}
				}
				es.caozuoObj(obj, 2);
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "不存在该分类", null)).toString();
			}
		}
	}

	@RequestMapping(value = "admin/getDynamicAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getDynamicAll(Integer start, Integer size, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Dynamic> List = es.getObjectAllByTy(Dynamic.class, true, start, size, null, null, null, null, "id",
					null, null);
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

	@RequestMapping(value = "getDynamicAllByStart.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getDynamicAllByStart(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getJson1();
		List<Dynamic> List = es.getObjectAllByTy(Dynamic.class, true, null, null, null, null, null, null, null, null,
				null);
		if (List != null) {
			json = JSONArray.fromObject(List, jsonConfig);
			return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
		} else {
			return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
		}
	}

}
