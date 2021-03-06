package com.jiwei.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiwei.entity.Affairs;

import com.jiwei.entity.Record;
import com.jiwei.entity.Useradmin;
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
@Api(tags = "党务公开")
public class AffairsAction extends BaseController {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(AffairsAction.class);

	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	@ResponseBody
	@RequestMapping(value = "admin/getAffairsall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	public Object admingetAffairsall(Integer start, Integer size, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("id");
			sqlzd.add("date");
			List<Affairs> List = es.getObjectAllByTy(Affairs.class, true, start, size, null, null, sqlzd, null, "id",
					null, null);
			if (List != null) {
				json = JSONArray.fromObject(List, jsonConfig);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "无数据", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "数据上传出错", null)).toString();
		}
	}

	@RequestMapping(value = "getAffairsall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getAffairsall(Integer start, Integer size, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			Integer number = 0;
			Map<String, Integer> limit = new HashMap<String, Integer>();
			limit.put("start", start);
			limit.put("size", size);
			List<Affairs> List = es.getObjectAll(Affairs.class, true, limit, null, null, "id", null, null, "title",
					"id", "date");
			/*
			 * List<Affairs> List = es.getObjectAllByTy(Affairs.class, true,
			 * start, size, null, null, sqlzd, null, "id", null, null);
			 */
			if (List != null) {
				number = es.getObjectAllByTyCount(Affairs.class, true, null, null, null, null, null);
				// json = JSONArray.fromObject(List, jsonConfig);
				for (int i = 0; i < List.size(); i++) {
					List.get(i).setTitle(StringEscapeUtils.unescapeHtml4(List.get(i).getTitle()));
				}
				/*
				 * return JSONArray.fromObject(new JsonResult("200", "成功",
				 * JSON.toJSONString(List,
				 * SerializerFeature.WriteDateUseDateFormat),
				 * number)).toString();
				 */
				return JSON.toJSONString(renderSuccess(List), SerializerFeature.WriteDateUseDateFormat);
			} else {
				return JSONArray.fromObject(new JsonResult("400", "无数据", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "数据上传出错", null)).toString();
		}
	}

	/*
	 * @RequestMapping(value = "getAffairsall.do", method = RequestMethod.GET,
	 * produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	 * 
	 * @ResponseBody public String getAffairsall(Integer start, Integer size,
	 * HttpSession session, HttpServletRequest request, HttpServletResponse
	 * response) throws Exception { getJson1(); if (start != null && size !=
	 * null) { List<Object> sqlzd = new ArrayList<Object>(); sqlzd.add("title");
	 * sqlzd.add("id"); sqlzd.add("date"); sqlzd.add("useradmin.contact");
	 * Integer number = 0; List<String> gl = new ArrayList<String>();
	 * gl.add("useradmin"); List<Class> c = new ArrayList<Class>();
	 * c.add(Affairs.class); List<Affairs> List =
	 * es.getObjectAllByTy(Affairs.class, true, start, size, null, null, sqlzd,
	 * null, "id", gl, c); number = es.getObjectAllByTyCount(Affairs.class,
	 * true, null, null, null, null, null); if (List != null) { // json =
	 * JSONArray.fromObject(List, jsonConfig); for (int i = 0; i < List.size();
	 * i++) {
	 * 
	 * } return StringEscapeUtils.unescapeHtml4(JSONArray.fromObject(new
	 * JsonResult("200", "成功", JSON.toJSONString(List,
	 * SerializerFeature.WriteDateUseDateFormat), number)).toString()); } else {
	 * return JSONArray.fromObject(new JsonResult("400", "无数据",
	 * null)).toString(); } } else { return JSONArray.fromObject(new
	 * JsonResult("400", "数据上传出错", null)).toString(); } }
	 */

	@RequestMapping(value = "findAffairsById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findAffairsById(Integer Id, Integer updown, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			// List<String> ziduan = new ArrayList<String>();
			List<Integer> type = new ArrayList<Integer>();
			List<Object> zhi = new ArrayList<Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", Id);
			/*
			 * ziduan.add("id"); zhi.add(Id);
			 */
			List<Affairs> List;
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
			// List = es.getObjectAllByTy(Affairs.class, true, null, null,
			// ziduan, zhi, sqlzd, type, odb, null, null);
			List = es.getObjectAll(Affairs.class, true, null, map, type, odb, null, null, "title", "date", "content",
					"id");
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

	@RequestMapping(value = "admin/delAffairs.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delAffairs(Integer id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (id != null) {
			synchronized (this) {
				Affairs obj = (Affairs) es.getObjectbyId(Affairs.class, id, 0);
				if (obj != null) {
					es.caozuoObj(obj, 2);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				} else {
					return JSONArray.fromObject(new JsonResult("200", "不存在", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "id未提交", null)).toString();
		}
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

	@RequestMapping(value = "admin/addAffairs.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addAffairs(@ModelAttribute Affairs obj, HttpSession session) throws Exception {
		getJson1();
		if (obj != null && obj.getTitle() != null) {
			synchronized (this) {
				// System.out.println(Useradmins.toString());
				if (es.getObject(new Affairs(obj.getTitle())) != null) {
					return JSONArray.fromObject(new JsonResult("200", "标题已存在", null)).toString();
				} else {
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

	public static void main(String[] args) {
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

		System.out.println(strD + " dateX=" + strX);
	}

	@RequestMapping(value = "admin/updateAffairs.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateAffairs(@ModelAttribute Affairs obj, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (obj != null && obj.getId() != null && obj.getTitle() != null) {
			Affairs obj1 = null;
			synchronized (this) {
				obj1 = (Affairs) es.getObjectbyZd(Affairs.class, "title", obj.getTitle(), true);
				if (obj1 != null && !obj1.getId().equals(obj.getId())) {
					return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
				} else {
					Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
					obj.setUseradmin(useradmin);
					obj1 = (Affairs) es.getObjectbyId(Affairs.class, obj.getId(), 0);
					obj1 = (Affairs) BeanUtilIgnoreNull.copyPropertiesIgnoreNull(obj, obj1);
					es.caozuoObj(obj1, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/findAffairsById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String adminfindAffairsById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Affairs obj1 = (Affairs) es.getObjectbyId(Affairs.class, Id, 0);
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
}
