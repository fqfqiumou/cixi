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

import com.jiwei.entity.Zone;
import com.jiwei.entity.Zonenote;
import com.jiwei.entity.Dynote;
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
@Api(tags = "工作专区")
public class ZoneAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(ZoneAction.class);

	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	@RequestMapping(value = "admin/findZoneById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findZoneById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Zone obj1 = (Zone) es.getObjectbyId(Zone.class, Id, 0);
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


	@RequestMapping(value = "admin/updateZone.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateZone(@ModelAttribute Zone Zone, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Zone != null && Zone.getId() != null && Zone.getName() != null) {
			Zone Zone1 = null;
			synchronized (this) {
				Zone1 = (Zone) es.getObjectbyZd(Zone.class, "name", Zone.getName(), true);
				if (Zone1 != null && !Zone1.getId().equals(Zone.getId())) {
					return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
				} else {
					es.caozuoObj(Zone, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/addZone.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addZone(@ModelAttribute Zone obj, HttpSession session) throws Exception {
		getJson1();
		if (obj != null && obj.getName() != null) {
			synchronized (this) {
				// System.out.println(Useradmins.toString());
				if (es.getObject(new Zone(obj.getName())) != null) {
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

	@RequestMapping(value = "admin/delZone.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delZone(Integer id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		synchronized (this) {
			Zone obj = (Zone) es.getObjectbyId(Zone.class, id, 0);
			if (obj != null) {
				List<String> ziduan=new ArrayList<String>();
				ziduan.add("zone.id");
				List<Object> zhi=new ArrayList<Object>();
				zhi.add(id);
				List<Integer> type=new ArrayList<Integer>();
				type.add(0);
				List<Zonenote> List = es.getObjectAllByTy(Zonenote.class, true, null, null, ziduan, zhi, null, type, "id", null,
						null);
				if(List!=null&&List.size()>0){
					List<Object> objs=new ArrayList<Object>();
					for (int i = 0; i < List.size(); i++) {
						Zonenote zonenote=new Zonenote();
						zonenote=List.get(i);
						zonenote.setZone(null);
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

	@RequestMapping(value = "admin/getZoneAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getZoneAll(Integer start, Integer size, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Zone> List = es.getObjectAllByTy(Zone.class, true, start, size, null, null, null, null, "id", null,
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

	@RequestMapping(value = "getZoneAllByStart.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getZoneAllByStart(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getJson1();
		List<Zone> List = es.getObjectAllByTy(Zone.class, true, null, null, null, null, null, null, "id", null, null);
		if (List != null) {
			json = JSONArray.fromObject(List, jsonConfig);
			return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
		} else {
			return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();

		}
	}

}
