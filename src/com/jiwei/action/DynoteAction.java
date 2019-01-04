package com.jiwei.action;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiwei.entity.Dynamic;
import com.jiwei.entity.Dynote;
import com.jiwei.entity.Monthreport;
import com.jiwei.entity.Record;
import com.jiwei.entity.Useradmin;
import com.jiwei.jsonstatus.model.JsonResult;
import com.jiwei.service.entityService;
import com.jiwei.util.ExcelUtil;

import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@CrossOrigin(origins = "*")
@Controller
@Scope("prototype")
@Api(tags = "基层动态子项目帖子")
public class DynoteAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(DynoteAction.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	@RequestMapping(value = "getDynoteall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getDynoteall(Integer start, Integer size, Integer Id, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("date");
			sqlzd.add("id");
			List<Dynote> List = null;
			Integer number=0;
			if (Id != null) {
				List<String> ziduan = new ArrayList<String>();
				List<Object> zhi = new ArrayList<Object>();
				List<Integer> type = new ArrayList<Integer>();
				ziduan.add("dynamic.id"); 
				zhi.add(Id);
				type.add(0);
				List = es.getObjectAllByTy(Dynote.class, true, start, size, ziduan, zhi, sqlzd, type, "id", null, null);
				number=es.getObjectAllByTyCount(Dynote.class, true, ziduan, zhi, type, null, null);
			} else {
				List = es.getObjectAllByTy(Dynote.class, true, start, size, null, null, sqlzd, null, "id", null, null);
				number=es.getObjectAllByTyCount(Dynote.class, true, null, null, null, null, null);
			}
			if (List != null) {
				//json = JSONArray.fromObject(List, jsonConfig);
				for (int i = 0; i < List.size(); i++) {
					List.get(i).setTitle(StringEscapeUtils.unescapeHtml4(List.get(i).getTitle()));
				}
				return JSONArray.fromObject(new JsonResult("200", "成功", 	JSON.toJSONString(List,SerializerFeature.WriteDateUseDateFormat),number)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "无数据", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "数据上传出错", null)).toString();
		}
	}

	@RequestMapping(value = "findDynoteById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findDynoteById(Integer Id,Integer updown, HttpSession session) throws Exception {
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
			List<Dynote> List = es.getObjectAllByTy(Dynote.class, true, null, null, ziduan, zhi, sqlzd, type, odb,
					null, null);
			if (List != null && List.size() > 0) {
				List.get(0).setTitle(StringEscapeUtils.unescapeHtml4(List.get(0).getTitle()));
				List.get(0).setContent(StringEscapeUtils.unescapeHtml4(List.get(0).getContent()));
				//JSONObject json = JSONObject.fromObject(List.get(0));
				return JSONArray.fromObject(new JsonResult("200", "成功", JSON.toJSONString(List.get(0),SerializerFeature.WriteDateUseDateFormat))).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/findDynoteId.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findDynoteId(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Dynote obj1 = (Dynote) es.getObjectbyId(Dynote.class, Id, 0);
			if (obj1 != null) {
				JSONObject json = JSONObject.fromObject(obj1);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/addDynote.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addDynote(@ModelAttribute Dynote dynote, Integer id, HttpSession session) throws Exception {
		getJson1();
		if (dynote != null && id != null) {
			synchronized (this) {
				Dynamic dynamic = new Dynamic();
				dynamic.setId(id);
				// System.out.println(Useradmins.toString());
				if (es.getObject(new Dynote(dynamic, dynote.getTitle())) != null) {
					return JSONArray.fromObject(new JsonResult("200", "相同标题的文章已存在", null)).toString();
				} else {
					dynote.setDynamic(dynamic);
					Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
					dynote.setUseradmin(useradmin);
					dynote.setDate(new Timestamp(new Date().getTime()));
					dynote.setStatus(1);
					if (es.caozuoObj(dynote, 0)) {
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
	@RequestMapping(value = "admin/updateDynote.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateDynote(@ModelAttribute Dynote dynote, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if(dynote!=null&&dynote.getId()!=null){
		Dynote dynote1 = null;
		synchronized (this) {
			dynote1 = (Dynote) es.getObjectbyZd(Dynote.class, "title", dynote.getTitle(), true);
			if (dynote1 != null && !dynote1.getId().equals(dynote.getId())) {
				return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
			} else {
				dynote1 = (Dynote) es.getObjectbyId(Dynote.class, dynote.getId(), 0);
				if (dynote.getContent() != null) {
					dynote1.setContent(dynote.getContent());
				}
				if (dynote.getTitle() != null) {
					dynote1.setTitle(dynote.getTitle());
				}
				if (dynote1.getUseradmin() == null) {
					Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
					dynote1.setUseradmin(useradmin);
				}
				
				dynote1.setStatus(1);
				es.caozuoObj(dynote1, 1);
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			}
		}
		}else{
			return JSONArray.fromObject(new JsonResult("200", "Id为空", null)).toString();
		}
	}
	
	
	@RequestMapping(value = "admin/reviewDynote.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String reviewDynote(@ModelAttribute Dynote dynote, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if(dynote!=null&&dynote.getId()!=null){
		Dynote dynote1 = null;
		synchronized (this) {
			dynote1 = (Dynote) es.getObjectbyZd(Dynote.class, "title", dynote.getTitle(), true);
			if (dynote1 != null && !dynote1.getId().equals(dynote.getId())) {
				return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
			} else {
				dynote1 = (Dynote) es.getObjectbyId(Dynote.class, dynote.getId(), 0);
				
				//管理员也有可能修改内容和标题
				if (dynote.getContent() != null) {
					dynote1.setContent(dynote.getContent());
				}
				if (dynote.getTitle() != null) {
					dynote1.setTitle(dynote.getTitle());
				}
				if (dynote.getStatus() != null) {
					dynote1.setStatus(dynote.getStatus());
				}
				dynote1=(Dynote) BeanUtilIgnoreNull.copyPropertiesIgnoreNull(dynote, dynote1);
				es.caozuoObj(dynote1, 1);
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			}
		}
		}else{
			return JSONArray.fromObject(new JsonResult("200", "Id为空", null)).toString();
		}
	}
	
	@RequestMapping(value = "admin/batchReviewDynote.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String batchReviewDynote(@RequestBody Map<String,Object> paras, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		
		com.alibaba.fastjson.JSONArray dynotesArray = (com.alibaba.fastjson.JSONArray)paras.get("dynotes");
		
		for (Object jsonObject : dynotesArray)
		{
			int id = (Integer) jsonObject;
			if(id > 0){
				Dynote dynote1 = null;
				synchronized (this) {
					dynote1 = (Dynote) es.getObjectbyId(Dynote.class, id, 0);
					dynote1.setStatus((Integer)paras.get("status"));
					es.caozuoObj(dynote1, 1);
				}
				}else{
					return JSONArray.fromObject(new JsonResult("200", "Id为空", null)).toString();
				}
		}
		
		return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
		
	}

	@RequestMapping(value = "admin/delDynote.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delDynote(Integer id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		synchronized (this) {
			Dynote dynote1 = (Dynote) es.getObjectbyId(Dynote.class, id, 0);
			if (dynote1 != null) {
				es.caozuoObj(dynote1, 2);
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "不存在该帖子", null)).toString();
			}
		}
	}

	@RequestMapping(value = "admin/getDynoteAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String admingetDynoteAll(Integer id, Integer status, Integer start, Integer size, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Dynote> List = null;
			if (id != null) {
				List<String> ziduan = new ArrayList<String>();
				List<Object> zhi = new ArrayList<Object>();
				List<Integer> type = new ArrayList<Integer>();
				ziduan.add("dynamic.id");
				zhi.add(id);
				type.add(0);
				
				ziduan.add("status"); 
				zhi.add(status);
				type.add(0);
				
				Useradmin useradmin = (Useradmin)session.getAttribute("useradmin");
				if(useradmin.getPower() != 413)
				{
					ziduan.add("useradmin.id"); 
					zhi.add(useradmin.getId());
					type.add(0);
				}
				
				
				List<Object> sqlzd = new ArrayList<Object>();
				sqlzd.add("title");
				sqlzd.add("id");
				sqlzd.add("date");
				sqlzd.add("status");
				List = es.getObjectAllByTy(Dynote.class, false, start, size, ziduan, zhi, sqlzd, type, "id", null, null);
			} else {
				List = es.getObjectAllByTy(Dynote.class, true, start, size, null, null, null, null, "id", null, null);
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
	
	@RequestMapping(value = "admin/statDynote.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String statDynote(String dateStart, String dateEnd, HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			getJson1();
	
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Object> sqlzd = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			ziduan.add("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			
			Date startDate = new Date(RecordAction.getMonthBegin(new Date(sdf.parse(dateStart).getTime())));
			Date endDate = new Date(RecordAction.getMonthEnd(new Date(sdf.parse(dateEnd).getTime())));
			
			zhi.add(startDate);
			zhi.add(endDate);
			
			List<String> monthes = new ArrayList<>();
			int index = 0;
			while(startDate.getTime() <= endDate.getTime())
			{
				monthes.add(sdf2.format(startDate));
				startDate = DateUtils.addMonths(startDate, 1);
				index++;
			}
			
			type.add(9);
			sqlzd.add("id");
			sqlzd.add("date");
			sqlzd.add("useradmin.id");
			sqlzd.add("useradmin.name");
			sqlzd.add("useradmin.contact");
			List<String> gl = new ArrayList<String>();
			gl.add("useradmin");
			List<Class> c1 = new ArrayList<Class>();
			c1.add(Dynote.class);
			List<Map<String,Object>> List = es.getObjectAllByTy(Dynote.class, true, null, null, ziduan, zhi, sqlzd, type, "id", gl,
					c1);
			if (List != null) {
				
				Map<Integer,Map<String,Object>> userReportCount = new HashMap<>(); 
				
				for(Map<String,Object> report : List)
				{
					int userId = (int)report.get("useradmin.id");
					Date date = (Date)report.get("date");
					String dateStr = sdf2.format(date);
					
					Map<String,Object> userMap = userReportCount.get(userId);
					if(userMap == null)
					{
						userMap = new HashMap<>();
						userMap.put("userId", userId);
						userMap.put("userName", report.get("useradmin.name"));
						userMap.put("userContact", report.get("useradmin.contact"));
						
						//各个月份发表的基层动态文章数量，先都设置为零
						int totalCount = 0;
						for(String month:monthes)
						{
							if(month.equals(dateStr))
							{
								userMap.put(month, 1);
								totalCount = 1;
							}else
							{
								userMap.put(month, 0);
							}
						}
						
						userMap.put("total", totalCount);
						
						userReportCount.put(userId, userMap);
					}
					else
					{
						userMap.put(dateStr, (int)userMap.get(dateStr) + 1);
						userMap.put("total", (int)userMap.get("total") + 1);
					}
				}
				
				List<Map<String,Object>> resultList = new ArrayList<>();
				
				sqlzd = new ArrayList<Object>();
				sqlzd.add("id");
				sqlzd.add("phone");
				sqlzd.add("contact");
				sqlzd.add("name");
				sqlzd.add("power");
				
//				zhi = new ArrayList<Object>();
//				sqlzd = new ArrayList<Object>();
//				type = new ArrayList<Integer>();
//				ziduan.add("power");
				
				List<Class> c=new ArrayList<Class>();
				c.add(Useradmin.class);
				List<Map<String,Object>> userList = es.getObjectAllByTy(Useradmin.class, true, null, null, null, null, sqlzd, null,
						"id", null, c);
				
				
				for(Map<String,Object> user:userList)
				{
					int userId = (int)user.get("id");
					Map userCount = userReportCount.get(userId);
					if(userCount == null)
					{
						userCount = new HashMap<>();
						userCount.put("userId", userId);
						userCount.put("userName", user.get("name"));
						userCount.put("userContact", user.get("contact"));
						for(String month:monthes)
						{
							userCount.put(month, 0);
						}
						
						userCount.put("total", 0);
					}
					
					resultList.add(userCount);
				}
				
				json = JSONArray.fromObject(resultList, jsonConfig);
				String b = json.toString().replaceAll("\\.", "");
				return JSONArray.fromObject(new JsonResult("200", "成功", b)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			}
	}
	
	@RequestMapping(value = "admin/exportDynoteStat.do", method = RequestMethod.GET, produces="application/octet-stream;charset=UTF-8")
	public ResponseEntity<byte[]> exportReport(@RequestParam("dateStart") String dateStart,@RequestParam("dateEnd") String dateEnd,HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ResponseEntity<byte[]> result = null;
		
		try {
			getJson1();
			
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Object> sqlzd = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			ziduan.add("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			
			Date startDate = new Date(RecordAction.getMonthBegin(new Date(sdf.parse(dateStart).getTime())));
			Date endDate = new Date(RecordAction.getMonthEnd(new Date(sdf.parse(dateEnd).getTime())));
			
			zhi.add(startDate);
			zhi.add(endDate);
			
			List<String> monthes = new ArrayList<>();
			while(startDate.getTime() <= endDate.getTime())
			{
				monthes.add(sdf2.format(startDate));
				startDate = DateUtils.addMonths(startDate, 1);
			}
			
			type.add(9);
			sqlzd.add("id");
			sqlzd.add("date");
			sqlzd.add("useradmin.id");
			sqlzd.add("useradmin.name");
			sqlzd.add("useradmin.contact");
			List<String> gl = new ArrayList<String>();
			gl.add("useradmin");
			List<Class> c1 = new ArrayList<Class>();
			c1.add(Dynote.class);
			List<Map<String,Object>> List = es.getObjectAllByTy(Dynote.class, true, null, null, ziduan, zhi, sqlzd, type, "id", gl,
					c1);
			if (List != null) {
				
				Map<Integer,Map<String,Object>> userReportCount = new HashMap<>(); 
				
				for(Map<String,Object> report : List)
				{
					int userId = (int)report.get("useradmin.id");
					Date date = (Date)report.get("date");
					String dateStr = sdf2.format(date);
					
					Map<String,Object> userMap = userReportCount.get(userId);
					if(userMap == null)
					{
						userMap = new HashMap<>();
						userMap.put("userId", userId);
						userMap.put("userName", report.get("useradmin.name"));
						userMap.put("userContact", report.get("useradmin.contact"));
						
						//各个月份发表的基层动态文章数量，先都设置为零
						int totalCount = 0;
						for(String month:monthes)
						{
							if(month.equals(dateStr))
							{
								userMap.put(month, 1);
								totalCount = 1;
							}else
							{
								userMap.put(month, 0);
							}
						}
						
						userMap.put("total", totalCount);
						
						userReportCount.put(userId, userMap);
					}
					else
					{
						userMap.put(dateStr, (int)userMap.get(dateStr) + 1);
						userMap.put("total", (int)userMap.get("total") + 1);
					}
				}
				
				List<Map<String,Object>> resultList = new ArrayList<>();
				
				sqlzd = new ArrayList<Object>();
				sqlzd.add("id");
				sqlzd.add("phone");
				sqlzd.add("contact");
				sqlzd.add("name");
				sqlzd.add("power");
				
//				zhi = new ArrayList<Object>();
//				sqlzd = new ArrayList<Object>();
//				type = new ArrayList<Integer>();
//				ziduan.add("power");
				
				List<Class> c=new ArrayList<Class>();
				c.add(Useradmin.class);
				List<Map<String,Object>> userList = es.getObjectAllByTy(Useradmin.class, true, null, null, null, null, sqlzd, null,
						"id", null, c);
				
				
				for(Map<String,Object> user:userList)
				{
					int userId = (int)user.get("id");
					Map<String,Object> userCount = userReportCount.get(userId);
					if(userCount == null)
					{
						userCount = new HashMap<>();
						userCount.put("userId", userId);
						userCount.put("userName", user.get("name"));
						userCount.put("userContact", user.get("contact"));
						for(String month:monthes)
						{
							userCount.put(month, 0);
						}
						
						userCount.put("total", 0);
					}
					
					resultList.add(userCount);
				}
			
				XSSFWorkbook wb = ExcelUtil.createExcel("./data/report/" + "monthreport.xls");
				
				XSSFSheet sheet = wb.createSheet("Sheet1");
	
				ExcelUtil.insertRows(wb,"Sheet1",1);
				
				XSSFRow row = ExcelUtil.insertRows(wb, "Sheet1", 0);
				row.createCell(0).setCellValue("编号");
				
				row.createCell(1).setCellValue("账号");
				
				row.createCell(2).setCellValue("姓名");

				int i = 3;
				for(String month:monthes)
				{
					row.createCell(i).setCellValue(month);
					i++;
				}
				
				row.createCell(i).setCellValue("总计");
				
				int rownum = 1;
				
				XSSFCellStyle cellStyle=wb.createCellStyle();     
				cellStyle.setWrapText(true);     
				
				for(Map<String,Object> usercount : resultList)
				{
					int userId = (int)usercount.get("userId");
					String name = (String)usercount.get("userName");
					String contact = (String)usercount.get("userContact");
					
					XSSFRow row1 = ExcelUtil.insertRows(wb, "Sheet1", rownum);
					
					XSSFCell cell1 = row1.createCell(0);
					cell1.setCellValue(userId);
					
					XSSFCell cell2 = row1.createCell(1);
					cell2.setCellValue(name);
					
					XSSFCell cell3 = row1.createCell(2);
					cell3.setCellValue(contact);
					
					int j = 3;
					for(String month:monthes)
					{
						XSSFCell cell = row1.createCell(j);
						cell.setCellValue(Integer.toString((Integer)usercount.get(month)));
						j++;
					}
					
					XSSFCell cell = row1.createCell(j);
					cell.setCellValue(Integer.toString((Integer)usercount.get("total")));
					
					rownum++;
				}
				
				ExcelUtil.saveExcel(wb, "./data/report/" + "stat.xls");
				
				HttpHeaders headers = new HttpHeaders();//http头信息
		        
		        String downloadFileName = new String("stat.xls".getBytes("UTF-8"),"iso-8859-1");//设置编码
		        
		        headers.setContentDispositionFormData("attachment", downloadFileName);
		        
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		        
		        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
		        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File("./data/report/stat.xls")),headers,HttpStatus.CREATED);
			}	
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
		
	}

}
