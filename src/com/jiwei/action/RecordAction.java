package com.jiwei.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.jiwei.entity.Affairs;
import com.jiwei.entity.Bulletin;
import com.jiwei.entity.Dynote;
import com.jiwei.entity.Filelibrary;
import com.jiwei.entity.Msg;
import com.jiwei.entity.News;
import com.jiwei.entity.Newsflash;
import com.jiwei.entity.Record;
import com.jiwei.entity.Useradmin;
import com.jiwei.entity.Zonenote;
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
@Component("Task")
@Api(tags = "信息系统以及督办系统")
public class RecordAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	private JsonObject object = new JsonObject();
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(RecordAction.class);

	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}

	@RequestMapping(value = "admin/getRecordAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getRecordAll(String date,HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Jedis jedis = new Jedis("172.17.0.1", 6379);
			getJson1();
	/*		Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			String gtimelast = sdf.format(c.getTime()); // 上月
			logger.info(gtimelast);
			int lastMonthMaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			logger.info(lastMonthMaxDay);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
			// 按格式输出
			String gtime = sdf.format(c.getTime()); // 上月最后一天
			Date date1 = sdf.parse(gtime);// 将字符串转换成时间
			logger.info(gtime);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00");
			String gtime2 = sdf2.format(c.getTime()); // 上月第一天
			Date date2 = sdf2.parse(gtime2);// 将字符串转换成时间
*/
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Object> sqlzd = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			ziduan.add("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			zhi.add(new Date(getMonthBegin(new Date(sdf.parse(date).getTime()))));
			zhi.add(new Date(getMonthEnd(new Date(sdf.parse(date).getTime()))));
			type.add(9);
			sqlzd.add("count");
			sqlzd.add("useradmin.contact");
			List<String> gl = new ArrayList<String>();
			gl.add("useradmin");
			List<Class> c1 = new ArrayList<Class>();
			c1.add(Record.class);
			List<Record> List = es.getObjectAllByTy(Record.class, true, null, null, ziduan, zhi, sqlzd, type, "id", gl,
					c1);
			if (List != null) {
				json = JSONArray.fromObject(List, jsonConfig);
				String b = json.toString().replaceAll("\\.", "");
				return JSONArray.fromObject(new JsonResult("200", "成功", b)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			}
	}

	public static Date nextMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static long printDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		logger.info(sdf.format(date));
		String gtime2 = sdf.format(date);
		Date date1;
		try {
			date1 = sdf.parse(gtime2);
			logger.info(date1.getTime());
			return date1.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	@RequestMapping(value = "admin/getRecordAllByDate.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getRecordAllByDate(Long sdate, Long ldate, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		List<String> ziduan = new ArrayList<String>();
		List<Object> zhi = new ArrayList<Object>();
		List<Object> sqlzd = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		ziduan.add("date");
		zhi.add(new Timestamp(sdate * 1000));
		zhi.add(new Timestamp(ldate * 1000));
		type.add(9);
		sqlzd.add("count");
		sqlzd.add("useradmin.contact");
		List<String> gl = new ArrayList<String>();
		gl.add("useradmin");
		List<Class> c1 = new ArrayList<Class>();
		c1.add(Record.class);
		List<Record> List = es.getObjectAllByTy(Record.class, true, null, null, ziduan, zhi, sqlzd, type, "id", gl, c1);
		if (List != null) {
			json = JSONArray.fromObject(List, jsonConfig);
			return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
		} else {
			return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
		}

	}
	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
		}

		// 获得本月最后一天24点时间
		public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
		}
	@Scheduled
	public void getRecordAllByTask() throws Exception {
		// Jedis jedis = new Jedis("172.17.0.1", 6379);
		logger.info("定时任务开始");
		/*
		 * Jedis jedis = new Jedis(); String a = jedis.get("getRecordAll"); if
		 * (a == null) { getJson1(); Calendar c = Calendar.getInstance();
		 * c.add(Calendar.MONTH, -1); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd  HH:mm:ss"); String gtimelast =
		 * sdf.format(c.getTime()); // 上月 logger.info(gtimelast); int
		 * lastMonthMaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		 * logger.info(lastMonthMaxDay); c.set(c.get(Calendar.YEAR),
		 * c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59); // 按格式输出 String
		 * gtime = sdf.format(c.getTime()); // 上月最后一天 Date date1 =
		 * sdf.parse(gtime);// 将字符串转换成时间 logger.info(gtime); SimpleDateFormat
		 * sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00"); String gtime2 =
		 * sdf2.format(c.getTime()); // 上月第一天 Date date2 = sdf2.parse(gtime2);//
		 * 将字符串转换成时间 logger.info(gtime2); List<String> ziduan = new
		 * ArrayList<String>(); List<Object> zhi = new ArrayList<Object>();
		 * List<Object> sqlzd = new ArrayList<Object>(); List<Integer> type =
		 * new ArrayList<Integer>(); ziduan.add("date"); zhi.add(date2);
		 * zhi.add(date1); type.add(9); sqlzd.add("count");
		 * sqlzd.add("useradmin.contact"); List<String> gl = new
		 * ArrayList<String>(); gl.add("useradmin"); List<Class> c1 = new
		 * ArrayList<Class>(); c1.add(Record.class); List<Record> List =
		 * es.getObjectAllByTy(Record.class, true, null, null, ziduan, zhi,
		 * sqlzd, type, "id", gl, c1); if (List != null) { json =
		 * JSONArray.fromObject(List, jsonConfig); jedis.set("getRecordAll",
		 * JSONArray.fromObject(new JsonResult("200", "成功",
		 * json.toString())).toString()); Date nextMonthFirstDate =
		 * nextMonthFirstDate(); long sj =((printDate(nextMonthFirstDate) - new
		 * Date().getTime())) / 1000; logger.info(printDate(nextMonthFirstDate)
		 * + "_" + new Date().getTime()); jedis.expire("getRecordAll", (int)
		 * sj); } } jedis.close();
		 */
		List<String> ziduan = new ArrayList<String>();
		ziduan.add("date");
		List<Object> zhi = new ArrayList<Object>();
		zhi.add(getTimesMonthmorning());
		zhi.add(getTimesMonthnight());
		List<Integer> type = new ArrayList<Integer>();
		//type.add(0);
		type.add(9);
		List<Record> records = es.getObjectAllByTy(Record.class, true, null, null, ziduan, zhi, null, type, null, null,
				null);
		List<Useradmin> useradmins = es.getObjectAll(Useradmin.class, true, null, null, null, null, null, null, null);
		for (int i = 0; i < useradmins.size(); i++) {
			boolean a = true;
			if (records != null && records.size() > 0) {
				for (int j = 0; j < records.size(); j++) {
					if (records.get(j).getUseradmin().getId().equals(useradmins.get(i).getId())) {
						a = false;
						break;
					}
				}
			}
			if (a) {
				Record record = new Record();
				record.setCount(0);
				record.setUseradmin(useradmins.get(i));
				record.setDate(new Date());
				es.caozuoObj(record, 0);
			}
		}
		logger.info("定时任务结束");
	}
	/**
	 * 获取指定日期所在月份开始的时间戳
	 * @param date 指定日期
	 * @return
	 */
	public static Long getMonthBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		//设置为1号,当前日期既为本月第一天  
		c.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0  
		c.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		c.set(Calendar.MINUTE, 0);  
		//将秒至0  
		c.set(Calendar.SECOND,0);  
		//将毫秒至0  
		c.set(Calendar.MILLISECOND, 0);  
		// 获取本月第一天的时间戳  
		return c.getTimeInMillis();  
	}
	/**
	 * 获取指定日期所在月份结束的时间戳
	 * @param date 指定日期
	 * @return
	 */
	public static Long getMonthEnd(Date date) {
		Calendar c = Calendar.getInstance();  
		c.setTime(date);
		
		//设置为当月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
		//将小时至23
		c.set(Calendar.HOUR_OF_DAY, 23);  
		//将分钟至59
		c.set(Calendar.MINUTE, 59);  
		//将秒至59
		c.set(Calendar.SECOND,59);  
		//将毫秒至999
		c.set(Calendar.MILLISECOND, 999);  
		// 获取本月最后一天的时间戳  
		return c.getTimeInMillis();
	}
	public static void main(String[] args) throws ParseException {
		/*Date currentTime = new Date();
		SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd ");
		String dateString3 = "0000-00-00 00:00:00";
		Date date3 = formatter3.parse(dateString3);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");
		String dateString = formatter.format(currentTime);
		String dateString1 = formatter1.format(currentTime);
		Date date = null;
		Date date2 = null;
		date = formatter.parse(dateString);
		date2 = formatter1.parse(dateString1);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date2);
		calendar.add(calendar.DATE, 1);
		date2 = calendar.getTime();
		logger.info(date2.getTime() + "qqq" + date.getTime());*/
		String string="2015-5-20";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     //   System.out.println(sdf.parse(string).getTime());
        System.out.println(getMonthEnd(new Date(sdf.parse(string).getTime())));
	}

	@RequestMapping(value = "admin/tongji.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String tongji() throws ParseException {
		Date currentTime = new Date();
		SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd ");
		String dateString3 = "0000-00-00 00:00:00";
		Date date3 = formatter3.parse(dateString3);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");
		String dateString = formatter.format(currentTime);
		String dateString1 = formatter1.format(currentTime);
		Date date = null;
		Date date2 = null;
		date = formatter.parse(dateString);
		date2 = formatter1.parse(dateString1);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date2);
		calendar.add(calendar.DATE, 1);
		date2 = calendar.getTime();
		List<String> ziduan = new ArrayList<String>();
		List<Object> zhi = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		ziduan.add("date");
		ziduan.add("date");
		zhi.add(new Timestamp(date.getTime()));
		zhi.add(new Timestamp(date2.getTime()));
		type.add(6);
		type.add(10);
		Integer af = es.getObjectAllByTyCount(Affairs.class, true, ziduan, zhi, type, null, null);
		Integer bl = es.getObjectAllByTyCount(Bulletin.class, true, ziduan, zhi, type, null, null);
		Integer zo = es.getObjectAllByTyCount(Zonenote.class, true, ziduan, zhi, type, null, null);
		Integer dy = es.getObjectAllByTyCount(Dynote.class, true, ziduan, zhi, type, null, null);
		Integer fl = es.getObjectAllByTyCount(Filelibrary.class, true, ziduan, zhi, type, null, null);
		Integer msg = es.getObjectAllByTyCount(Msg.class, true, ziduan, zhi, type, null, null);
		Integer news = es.getObjectAllByTyCount(News.class, true, ziduan, zhi, type, null, null);
		Integer newsflash = es.getObjectAllByTyCount(Newsflash.class, true, ziduan, zhi, type, null, null);
		List<JsonObject> list = new ArrayList<JsonObject>();
		if (af != null) {
			object = new JsonObject();
			object.addProperty("name", "党务公开");
			object.addProperty("count", af);
			list.add(object);
		}
		if (bl != null) {
			object = new JsonObject();
			object.addProperty("name", "公告栏");
			object.addProperty("count", bl);
			list.add(object);
		}
		if (zo != null) {
			object = new JsonObject();
			object.addProperty("name", "工作专区");
			object.addProperty("count", zo);
			list.add(object);
		}
		if (dy != null) {
			object = new JsonObject();
			object.addProperty("name", "基层动态");
			object.addProperty("count", dy);
			list.add(object);
		}
		if (fl != null) {
			object = new JsonObject();
			object.addProperty("name", "文件库");
			object.addProperty("count", fl);
			list.add(object);
		}
		if (msg != null) {
			object = new JsonObject();
			object.addProperty("name", "意见建议");
			object.addProperty("count", msg);
			list.add(object);
		}
		if (news != null) {
			object = new JsonObject();
			object.addProperty("name", "新闻滚动");
			object.addProperty("count", news);
			list.add(object);
		}
		if (newsflash != null) {
			object = new JsonObject();
			object.addProperty("name", "慈团快讯");
			object.addProperty("count", newsflash);
			list.add(object);
		}
		return JSONArray.fromObject(new JsonResult("200", "成功", list.toString())).toString();

	}
	public static Date getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	@RequestMapping(value = "admin/tongjiByDate.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String tongji(String date) throws ParseException {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date1 = format.parse(date+" 00:00:00");
	     Date date2 = format.parse(date+" 23:59:59");
		List<String> ziduan = new ArrayList<String>();
		List<Object> zhi = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		ziduan.add("date");
		ziduan.add("date");
		zhi.add(new Timestamp(date1.getTime()));
		zhi.add(new Timestamp(date2.getTime()));
		type.add(6);
		type.add(10);
		Integer af = es.getObjectAllByTyCount(Affairs.class, true, ziduan, zhi, type, null, null);
		Integer bl = es.getObjectAllByTyCount(Bulletin.class, true, ziduan, zhi, type, null, null);
		Integer zo = es.getObjectAllByTyCount(Zonenote.class, true, ziduan, zhi, type, null, null);
		Integer dy = es.getObjectAllByTyCount(Dynote.class, true, ziduan, zhi, type, null, null);
		Integer fl = es.getObjectAllByTyCount(Filelibrary.class, true, ziduan, zhi, type, null, null);
		Integer msg = es.getObjectAllByTyCount(Msg.class, true, ziduan, zhi, type, null, null);
		Integer news = es.getObjectAllByTyCount(News.class, true, ziduan, zhi, type, null, null);
		Integer newsflash = es.getObjectAllByTyCount(Newsflash.class, true, ziduan, zhi, type, null, null);
		List<JsonObject> list = new ArrayList<JsonObject>();
		if (af != null) {
			object = new JsonObject();
			object.addProperty("name", "党务公开");
			object.addProperty("count", af);
			list.add(object);
		}
		if (bl != null) {
			object = new JsonObject();
			object.addProperty("name", "公告栏");
			object.addProperty("count", bl);
			list.add(object);
		}
		if (zo != null) {
			object = new JsonObject();
			object.addProperty("name", "工作专区");
			object.addProperty("count", zo);
			list.add(object);
		}
		if (dy != null) {
			object = new JsonObject();
			object.addProperty("name", "基层动态");
			object.addProperty("count", dy);
			list.add(object);
		}
		if (fl != null) {
			object = new JsonObject();
			object.addProperty("name", "文件库");
			object.addProperty("count", fl);
			list.add(object);
		}
		if (msg != null) {
			object = new JsonObject();
			object.addProperty("name", "意见建议");
			object.addProperty("count", msg);
			list.add(object);
		}
		if (news != null) {
			object = new JsonObject();
			object.addProperty("name", "新闻滚动");
			object.addProperty("count", news);
			list.add(object);
		}
		if (newsflash != null) {
			object = new JsonObject();
			object.addProperty("name", "慈团快讯");
			object.addProperty("count", newsflash);
			list.add(object);
		}
		return JSONArray.fromObject(new JsonResult("200", "成功", list.toString())).toString();
		
	}
}
