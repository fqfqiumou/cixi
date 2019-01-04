package com.jiwei.service;

import java.util.List;
import java.util.Map;  

public interface entityService {
	public List getObject(Object object)throws Exception;
	public boolean caozuoObj(Object object, Integer c) throws Exception ;
	public boolean plcaozuoObj(List<Object> object, Integer c) throws Exception;
	public List getObjectAllByTy(Class Object, Boolean cache, Integer start, Integer size, List<String> ziduan,
			List<Object> zhi, List<Object> sqlzd, List<Integer> type, String odb,List<String> gl,List<Class> c) ;
	public Integer getObjectAllByTyCount(Class Object, Boolean cache, List<String> ziduan, List<Object> zhi,
			List<Integer> type, List<String> gl, List<Class> c);
	public Object getObjectbyId(Class Object, Integer id,Integer i) throws Exception; // 公用标识列查询
	public List getObjectAll(Class Object, Boolean cache, Map<String, Integer> limit, Map<String, Object> map,
			List<Integer> type, String odb, List<String> gl, List<Class> c, String... sqlzd) ;
	public Object getObjectbyZd(Class Object, String ziduan,Object i,Boolean cache) throws Exception; // 公用标识列查询
}
