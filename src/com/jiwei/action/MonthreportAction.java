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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
@Api(tags = "工作月报")
public class MonthreportAction extends BaseController {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(MonthreportAction.class);

	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	@ResponseBody
	@RequestMapping(value = "admin/getMonthreportall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	public Object admingetMonthreportall(Integer start, Integer size, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			List<Object> sqlzd = new ArrayList<Object>();
			sqlzd.add("title");
			sqlzd.add("id");
			sqlzd.add("date");
			sqlzd.add("status");
			
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			
			Useradmin useradmin = (Useradmin)session.getAttribute("useradmin");
			if(useradmin.getPower() != 413)
			{
				ziduan.add("useradmin.id"); 
				zhi.add(useradmin.getId());
				type.add(0);
			}
			else
			{
				ziduan.add("status"); 
				zhi.add(2);
				type.add(0);
			}
			
			List<Monthreport> List = es.getObjectAllByTy(Monthreport.class, true, start, size, ziduan, zhi, sqlzd, type, "id",
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

	@RequestMapping(value = "getMonthreportall.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getMonthreportall(Integer start, Integer size, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			Integer number = 0;
			Map<String, Integer> limit = new HashMap<String, Integer>();
			limit.put("start", start);
			limit.put("size", size);
			List<Monthreport> List = es.getObjectAll(Monthreport.class, true, limit, null, null, "id", null, null, "title",
					"id", "date");
			/*
			 * List<Affairs> List = es.getObjectAllByTy(Affairs.class, true,
			 * start, size, null, null, sqlzd, null, "id", null, null);
			 */
			if (List != null) {
				number = es.getObjectAllByTyCount(Monthreport.class, true, null, null, null, null, null);
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

	@RequestMapping(value = "findMonthreportById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findMonthreportById(Integer Id, Integer updown, HttpSession session) throws Exception {
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
			List<Monthreport> List;
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
			List = es.getObjectAll(Monthreport.class, true, null, map, type, odb, null, null, "title", "date", "content",
					"id");
			if (List != null && List.size() > 0) {
				List.get(0).setTitle(StringEscapeUtils.unescapeHtml4(List.get(0).getTitle()));
				List.get(0).setSummary(StringEscapeUtils.unescapeHtml4(List.get(0).getSummary()));
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

	@RequestMapping(value = "admin/delMonthreport.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delMonthreport(Integer id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (id != null) {
			synchronized (this) {
				Monthreport obj = (Monthreport) es.getObjectbyId(Monthreport.class, id, 0);
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

	@RequestMapping(value = "admin/addMonthreport.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addMonthreport(@ModelAttribute Monthreport obj, HttpSession session) throws Exception {
		getJson1();
		if (obj != null && obj.getTitle() != null) {
			synchronized (this) {
				// System.out.println(Useradmins.toString());
				if (es.getObject(new Monthreport(obj.getTitle())) != null) {
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

	@RequestMapping(value = "admin/updateMonthreport.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateMonthreport(@ModelAttribute Monthreport obj, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (obj != null && obj.getId() != null && obj.getTitle() != null) {
			Monthreport obj1 = null;
			synchronized (this) {
				obj1 = (Monthreport) es.getObjectbyZd(Monthreport.class, "title", obj.getTitle(), true);
				if (obj1 != null && !obj1.getId().equals(obj.getId())) {
					return JSONArray.fromObject(new JsonResult("200", "已存在同样标题", null)).toString();
				} else {
					Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
					obj.setUseradmin(useradmin);
					obj1 = (Monthreport) es.getObjectbyId(Monthreport.class, obj.getId(), 0);
					obj1 = (Monthreport) BeanUtilIgnoreNull.copyPropertiesIgnoreNull(obj, obj1);
					es.caozuoObj(obj1, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}

	@RequestMapping(value = "admin/findMonthreportById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String adminfindMonthreportById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Monthreport obj1 = (Monthreport) es.getObjectbyId(Monthreport.class, Id, 0);
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
	
	
	@RequestMapping(value = "admin/getMonthreprotAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getRecordAll(String date,HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			getJson1();
	
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Object> sqlzd = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			ziduan.add("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			zhi.add(new Date(RecordAction.getMonthBegin(new Date(sdf.parse(date).getTime()))));
			zhi.add(new Date(RecordAction.getMonthEnd(new Date(sdf.parse(date).getTime()))));
			type.add(9);
			sqlzd.add("id");
			sqlzd.add("useradmin.id");
			sqlzd.add("useradmin.name");
			sqlzd.add("useradmin.contact");
			List<String> gl = new ArrayList<String>();
			gl.add("useradmin");
			List<Class> c1 = new ArrayList<Class>();
			c1.add(Monthreport.class);
			List<Map<String,Object>> List = es.getObjectAllByTy(Monthreport.class, true, null, null, ziduan, zhi, sqlzd, type, "id", gl,
					c1);
			if (List != null) {
				
				Map<Integer,Map<String,Object>> userReportCount = new HashMap<>(); 
				
				for(Map<String,Object> report : List)
				{
					int userId = (int)report.get("useradmin.id");
					
					Map<String,Object> userMap = userReportCount.get(userId);
					if(userMap == null)
					{
						userMap = new HashMap<>();
						userMap.put("userId", userId);
						userMap.put("userName", report.get("useradmin.name"));
						userMap.put("userContact", report.get("useradmin.contact"));
						userMap.put("count", 1);
						
						userReportCount.put(userId, userMap);
					}
					else
					{
						userMap.put("count", (int)userMap.get("count") + 1);
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
						userCount.put("count", 0);	
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
	
	@RequestMapping(value = "admin/exportReport.do", method = RequestMethod.GET, produces="application/octet-stream;charset=UTF-8")
	public ResponseEntity<byte[]> exportReport(@RequestParam("date") String date,HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ResponseEntity<byte[]> result = null;
		
		try {
			
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Object> sqlzd = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			ziduan.add("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			zhi.add(new Date(RecordAction.getMonthBegin(new Date(sdf.parse(date).getTime()))));
			zhi.add(new Date(RecordAction.getMonthEnd(new Date(sdf.parse(date).getTime()))));
			type.add(9);
			sqlzd.add("id");
			sqlzd.add("useradmin.id");
			sqlzd.add("useradmin.name");
			sqlzd.add("useradmin.contact");
			sqlzd.add("summary");
			sqlzd.add("plan");
			List<String> gl = new ArrayList<String>();
			gl.add("useradmin");
			List<Class> c1 = new ArrayList<Class>();
			c1.add(Monthreport.class);
			List<Map<String,Object>> List = es.getObjectAllByTy(Monthreport.class, true, null, null, ziduan, zhi, sqlzd, type, "id", gl,
					c1);
			
			XSSFWorkbook wb = ExcelUtil.createExcel("./data/report/" + "monthreport.xls");
			
			XSSFSheet sheet = wb.createSheet("Sheet1");

			ExcelUtil.insertRows(wb,"Sheet1",1);
			
			XSSFRow row = ExcelUtil.insertRows(wb, "Sheet1", 0);
			row.createCell(0).setCellValue("编号");
			
			row.createCell(1).setCellValue("账号");
			
			row.createCell(2).setCellValue("姓名");
			
			row.createCell(3).setCellValue("本月工作");
			
			row.createCell(4).setCellValue("下月计划");
			
			int rownum = 1;
			
			XSSFCellStyle cellStyle=wb.createCellStyle();     
			cellStyle.setWrapText(true);     
			
			for(Map<String,Object> report : List)
			{
				int userId = (int)report.get("useradmin.id");
				String name = (String)report.get("useradmin.name");
				String contact = (String)report.get("useradmin.contact");
				String summary = (String)report.get("summary");
				String plan = (String)report.get("plan");
				
				XSSFRow row1 = ExcelUtil.insertRows(wb, "Sheet1", rownum);
				
				XSSFCell cell1 = row1.createCell(0);
				cell1.setCellValue(userId);
				
				XSSFCell cell2 = row1.createCell(1);
				cell2.setCellValue(name);
				
				XSSFCell cell3 = row1.createCell(2);
				cell3.setCellValue(contact);
				
				XSSFCell cell4 = row1.createCell(3);
				cell4.setCellValue(summary);
				cell4.setCellStyle(cellStyle);
				
				
				XSSFCell cell5 = row1.createCell(4);
				cell5.setCellValue(plan);
				cell5.setCellStyle(cellStyle);
				
				rownum++;
			}
			
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			
			ExcelUtil.saveExcel(wb, "./data/report/" + "monthreport.xls");
			
			HttpHeaders headers = new HttpHeaders();//http头信息
	        
	        String downloadFileName = new String("monthReport.xls".getBytes("UTF-8"),"iso-8859-1");//设置编码
	        
	        headers.setContentDispositionFormData("attachment", downloadFileName);
	        
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        
	        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
	        result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File("./data/report/monthreport.xls")),headers,HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
