package com.jiwei.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.jiwei.entity.Dynamic;
import com.jiwei.entity.Dynote;
import com.jiwei.entity.Record;
import com.jiwei.entity.Zonenote;
import com.jiwei.entity.Zonenote;
import com.jiwei.entity.Useradmin;
import com.jiwei.entity.Zone;
import com.jiwei.jsonstatus.model.JsonResult;
import com.jiwei.service.entityService;

import io.netty.handler.codec.string.StringEncoder;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@CrossOrigin(origins = "*")
@Controller
@Scope("prototype")
@Api(tags = "工作专区子版块帖子")
public class ZonenoteAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(ZonenoteAction.class);

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
	@RequestMapping(value = "admin/findZonenoteId.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findZonenoteId(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Zonenote obj1 = (Zonenote) es.getObjectbyId(Zonenote.class, Id, 0);
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

	@RequestMapping(value = "getZonenoteall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getZonenoteall(Integer start, Integer size, Integer Id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("date");
			sqlzd.add("id");
			List<Zonenote> List = null;
			Integer number=0;
			if (Id != null) {
				List<String> ziduan = new ArrayList<String>();
				List<Object> zhi = new ArrayList<Object>();
				List<Integer> type = new ArrayList<Integer>();
				ziduan.add("zone.id");
				zhi.add(Id);
				type.add(0);
				List = es.getObjectAllByTy(Zonenote.class, true, start, size, ziduan, zhi, sqlzd, type, "id", null, null);
				number=es.getObjectAllByTyCount(Zonenote.class, true, ziduan, zhi, type, null, null);
			} else {
				List = es.getObjectAllByTy(Zonenote.class, true, start, size, null, null, sqlzd, null, "id", null,null);
				number=es.getObjectAllByTyCount(Zonenote.class, true, null, null, null, null, null);
			}
			if (List != null) {
				// json = JSONArray.fromObject(List, jsonConfig);
				for (int i = 0; i < List.size(); i++) {
					List.get(i).setTitle(StringEscapeUtils.unescapeHtml4(List.get(i).getTitle()));
				}
		
				return JSONArray.fromObject(
						new JsonResult("200", "成功", JSON.toJSONString(List, SerializerFeature.WriteDateUseDateFormat),number))
						.toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "暂无数据", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "数据上传出错", null)).toString();
		}
	}

	@RequestMapping(value = "findZonenoteById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findZonenoteById(Integer Id,Integer updown, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			List<String> ziduan = new ArrayList<String>();
			List<Integer> type = new ArrayList<Integer>();
			List<Object> zhi = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("date");
			sqlzd.add("content");
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
			List<Zonenote> List = es.getObjectAllByTy(Zonenote.class, true, null, null, ziduan, zhi, sqlzd, type, odb,
					null, null);
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

	@RequestMapping(value = "admin/addZonenote.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addZonenote(@ModelAttribute Zonenote Zonenote, Integer Id, HttpSession session) throws Exception {
		getJson1();
		if (Zonenote != null && Id != null) {
			synchronized (this) {
				Zone zone = new Zone();
				zone.setId(Id);
				// System.out.println(Useradmins.toString());
				if (es.getObject(new Zonenote(zone, Zonenote.getTitle())) != null) {
					return JSONArray.fromObject(new JsonResult("200", "用户名或手机号已存在", null)).toString();
				} else {
					System.out.println("Zonenoteid:" + Zonenote.getId());
					Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
					Zonenote.setUseradmin(useradmin);
					Zonenote.setZone(zone);
					Zonenote.setDate(new Timestamp(new Date().getTime()));
					if (es.caozuoObj(Zonenote, 0)) {
						addRecord(useradmin);
						return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
					} else {
						return JSONArray.fromObject(new JsonResult("400", "失败", null)).toString();
					}
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/updateZonenote.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateZonenote(@ModelAttribute Zonenote Zonenote, HttpSession session) throws Exception {
		if (Zonenote != null && Zonenote.getId() != null) {
			getJson1();
			// System.out.println(SsUserAdmins.toString());
			Zonenote Zonenote1 = null;
			synchronized (this) {
				Zonenote1 = (Zonenote) es.getObjectbyZd(Zonenote.class, "title", Zonenote.getTitle(), true);
				if (Zonenote1 != null && !Zonenote1.getId().equals(Zonenote.getId())) {
					return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
				} else {
					Zonenote1 = (Zonenote) es.getObjectbyId(Zonenote.class, Zonenote.getId(), 0);
					if (Zonenote.getContent() != null) {
						Zonenote1.setContent(Zonenote.getContent());
					}
					if (Zonenote.getTitle() != null) {
						Zonenote1.setTitle(Zonenote.getTitle());
					}
					if (Zonenote1.getUseradmin() == null) {
						Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
						Zonenote1.setUseradmin(useradmin);
					}
					es.caozuoObj(Zonenote1, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "数据上传失败", null)).toString();
		}
	}

	@RequestMapping(value = "admin/delZonenote.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delZonenote(Integer id, HttpSession session) throws Exception {
		if (id != null) {
			getJson1();
			// System.out.println(SsUserAdmins.toString());
			synchronized (this) {
				Zonenote Zonenote1 = (Zonenote) es.getObjectbyId(Zonenote.class, id, 0);
				if (Zonenote1 != null) {
					es.caozuoObj(Zonenote1, 2);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				} else {
					return JSONArray.fromObject(new JsonResult("200", "不存在该帖子", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "数据上传失败", null)).toString();
		}
	}

	@RequestMapping(value = "admin/getZonenoteAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String admingetZonenoteAll(Integer id, Integer start, Integer size, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Zonenote> List;
			if (id != null) {
				List<String> ziduan = new ArrayList<String>();
				List<Object> zhi = new ArrayList<Object>();
				List<Integer> type = new ArrayList<Integer>();
				ziduan.add("zone.id");
				zhi.add(id);
				type.add(0);
				List<Object> sqlzd = new ArrayList<Object>();
				sqlzd.add("title");
				sqlzd.add("id");
				sqlzd.add("date");
				List = es.getObjectAllByTy(Zonenote.class, true, start, size, ziduan, zhi, sqlzd, type, "id", null,
						null);
			} else {
				List = es.getObjectAllByTy(Zonenote.class, true, start, size, null, null, null, null, "id", null, null);
			}
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
}
