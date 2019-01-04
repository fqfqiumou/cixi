package com.jiwei.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jiwei.dao.entityDao;
import com.jiwei.service.entityService; 
@Service
public class entityServiceImpl implements entityService {
	@Autowired
	private entityDao ed;

	@Override
	public boolean caozuoObj(Object object, Integer c) throws Exception {
		// TODO Auto-generated method stub
		return ed.caozuoObj(object, c);
	}

	@Override
	public List getObject(Object object) throws Exception {
		// TODO Auto-generated method stub
		List<Object> list = ed.getObject(object);
		if (list != null & list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public boolean plcaozuoObj(List<Object> object, Integer c) throws Exception {
		// TODO Auto-generated method stub
		return ed.plcaozuoObj(object, c);
	}

	
	@Override
	public List getObjectAllByTy(Class Object, Boolean cache, Integer start, Integer size, List<String> ziduan,
			List<Object> zhi, List<Object> sqlzd, List<Integer> type, String odb,List<String> gl,List<Class> c) {
		// TODO Auto-generated method stub
		return ed.getObjectAllByTy(Object, cache, start, size, ziduan, zhi, sqlzd, type, odb,gl,c);
	}

	@Override
	public Integer getObjectAllByTyCount(Class Object, Boolean cache, List<String> ziduan, List<Object> zhi,
			List<Integer> type, List<String> gl, List<Class> c) {
		// TODO Auto-generated method stub
		return ed.getObjectAllByTyCount(Object, cache, ziduan, zhi, type, gl, c);
	}

	@Override
	public Object getObjectbyId(Class Object, Integer id, Integer i) throws Exception {
		// TODO Auto-generated method stub
		return ed.getObjectbyId(Object, id, i);
	}

	@Override
	public Object getObjectbyZd(Class Object, String ziduan, Object i, Boolean cache) throws Exception {
		// TODO Auto-generated method stub
		return ed.getObjectbyZd(Object, ziduan, i, cache);
	}

	@Override
	public List getObjectAll(Class Object, Boolean cache, Map<String, Integer> limit, Map<String, Object> map,
			List<Integer> type, String odb, List<String> gl, List<Class> c, String... sqlzd)  {
		// TODO Auto-generated method stub
		return ed.getObjectAll(Object, cache, limit, map, type, odb, gl, c, sqlzd);
	}

}
