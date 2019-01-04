package com.jiwei.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface entityDao {
	public List<Object> getObject(Object object) throws Exception;
	public Object getObjectbyId(Class Object, Integer id,Integer i) throws Exception; // 公用标识列查询
	public Object getObjectbyZd(Class Object, String ziduan,Object i,Boolean cache) throws Exception; // 公用标识列查询
	public boolean caozuoObj(Object object, Integer c) throws Exception;

	public boolean plcaozuoObj(List<Object> object, Integer c) throws Exception;

	public List<Object> getObjectAllByTy(Class Object, Boolean cache, Integer start, Integer size, List<String> ziduan,
			List<Object> zhi, List<Object> sqlzd, List<Integer> type, String odb, List<String> gl, List<Class> c);// CLASS
	public List<Object> getObjectAll(Class Object, Boolean cache, Map<String, Integer> limit, Map<String, Object> map,
			List<Integer> type, String odb, List<String> gl, List<Class> c, String... sqlzd) ;
	public Integer getObjectAllByTyCount(Class Object, Boolean cache, List<String> ziduan, List<Object> zhi,
			List<Integer> type, List<String> gl, List<Class> c);
}
