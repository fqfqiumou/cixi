package com.jiwei.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

import com.jiwei.entity.Affairs;
import com.jiwei.entity.Msg;
import com.jiwei.entity.Msg;
import com.jiwei.entity.News;
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
@Api(tags = "意见建议") 
public class MsgAction {
	private JsonConfig jsonConfig = new JsonConfig();
	private JSONArray json;
	@Autowired
	private entityService es;
	private static Logger logger = Logger.getLogger(MsgAction.class);
	public void getJson1() throws Exception {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	}
	@RequestMapping(value = "admin/getMsgAll.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getMsgAll(Integer start, Integer size, Integer status, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getJson1();
		if (start != null && size != null) {
			
			List<String> ziduan = new ArrayList<String>();
			List<Object> zhi = new ArrayList<Object>();
			List<Integer> type = new ArrayList<Integer>();
			ziduan.add("status");
			zhi.add(status);
			type.add(0);
			
			
			List<Msg> List = es.getObjectAllByTy(Msg.class, true, start, size, ziduan, zhi, null, type, "id", null,
					null);
			if (List != null) {
				json = JSONArray.fromObject(List, jsonConfig);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "数据上传出错", null)).toString();
		}
	}
	@RequestMapping(value = "admin/delMsg.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String delMsg(Integer id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		synchronized (this) {
			Msg obj = (Msg) es.getObjectbyId(Msg.class, id, 0);
			if (obj != null) {
				es.caozuoObj(obj, 2);
				return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("400", "不存在", null)).toString();
			}
		}
	}
	@RequestMapping(value = "addMsg.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String addMsg(@ModelAttribute Msg obj, HttpSession session) throws Exception {
		getJson1();
		if (obj != null && obj.getContent() != null) {
			synchronized (this) {
				// System.out.println(Useradmins.toString());
				obj.setDate(new Timestamp(new Date().getTime()));
				obj.setStatus(1);
				if (es.getObject(new Msg(obj.getContent())) != null) {
					return JSONArray.fromObject(new JsonResult("400", "该内容已经有了", null)).toString();
				} else {
					obj.setDate(new Timestamp(new Date().getTime()));
					if (es.caozuoObj(obj, 0)) {
						return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
					} else {
						return JSONArray.fromObject(new JsonResult("400", "失败", null)).toString();
					}
				}
			}
		} else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}


	@RequestMapping(value = "admin/findMsgById.do", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String findMsgById(Integer Id, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (Id != null) {
			Msg obj1 = (Msg) es.getObjectbyId(Msg.class, Id, 0);
			if (obj1 != null) {
				JSONObject json = JSONObject.fromObject(obj1);
				return JSONArray.fromObject(new JsonResult("200", "成功", json.toString())).toString();
			} else {
				return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
			}
		} else {
			return JSONArray.fromObject(new JsonResult("400", "请检查信息是否为空", null)).toString();
		}
	}
	
	@RequestMapping(value = "admin/updateMsg.do", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String updateAffairs(@ModelAttribute Msg obj, HttpSession session) throws Exception {
		getJson1();
		// System.out.println(SsUserAdmins.toString());
		if (obj != null && obj.getMsgId() != null) {
			Msg obj1 = null;
			synchronized (this) {
					obj1 = (Msg) es.getObjectbyId(Msg.class, obj.getMsgId(), 0);
					obj1.setReply(obj.getReply());
					obj1.setStatus(2);
					es.caozuoObj(obj1, 1);
					return JSONArray.fromObject(new JsonResult("200", "成功", null)).toString();
				}
		}
		 else {
			return JSONArray.fromObject(new JsonResult("200", "请检查信息是否为空", null)).toString();
		}
	}
}
