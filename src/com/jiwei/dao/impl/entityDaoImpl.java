package com.jiwei.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.jiwei.dao.entityDao;

public class entityDaoImpl extends HibernateDaoSupport implements entityDao {
	public void cache() {
		this.getHibernateTemplate().setCacheQueries(true);
	}

	@Override
	public boolean caozuoObj(Object object, Integer c) throws Exception {
		// TODO Auto-generated method stub
		cache();
		switch (c) {
		case 0:
			try {
				super.getHibernateTemplate().save(object);
				object = null;
				c = null;
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		case 1:
			try {
				super.getHibernateTemplate().merge(object);
				// super.getHibernateTemplate().update(object);
				object = null;
				c = null;
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		case 2:
			try {
				super.getHibernateTemplate().delete(object);
				object = null;
				c = null;
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		default:
			return false;

		}
	}

	@Override
	public List<Object> getObject(Object object) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getHibernateTemplate().setCacheQueries(true);
			return super.getHibernateTemplate().findByExample(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	// 批量更新,比单独save更快捷,单独save会导致session频繁打开关闭
	public boolean plcaozuoObj(final List<Object> object, final Integer c) throws Exception {
		// TODO Auto-generated method stub
		cache();
		return (boolean) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				try {
					for (int i = 0; i < object.size(); i++) {
						Object obj = new Object();
						obj = object.get(i);
						switch (c) {
						case 0:
							session.save(obj);
							break;
						case 1:
							session.merge(obj);
							break;
						case 2:
							session.delete(obj);
							break;
						default:
							break;
						}
						obj = null;
					}
					/* query.setParameter("value", value); */
					return true;
				} catch (HibernateException e) {
					// TODO: handle exception
					e.printStackTrace();
					return false;
				}

			}
		});
	}

	@Override

	public List<Object> getObjectAllByTy(Class Object, Boolean cache, Integer start, Integer size, List<String> ziduan,
			List<Object> zhi, List<Object> sqlzd, List<Integer> type, String odb, List<String> gl, List<Class> c) {
		// TODO Auto-generated method stub
		if (cache != null) {
			// 是否需要开启缓存
			this.getHibernateTemplate().setCacheQueries(cache);
		} else {
			cache();
		}
		// 需要查询的实体
		DetachedCriteria criteria = DetachedCriteria.forClass(Object);
		// 需要查询的字段名
		if (sqlzd != null && sqlzd.size() > 0) {
			ProjectionList pList = Projections.projectionList();
			for (int j = 0; j < sqlzd.size(); j++) {
				pList.add(Property.forName(String.valueOf(sqlzd.get(j))), String.valueOf(sqlzd.get(j)));
				// pList.add(Projections.property("binding").as("binding"));
			}
			criteria.setProjection(pList);
			 criteria.setResultTransformer(Transformers.aliasToBean(Object));
		}
		// 多表关联查询,对应的实体并且返回格式会成为map
		if (c != null && c.size() > 0) {
			for (int i = 0; i < c.size(); i++) {
				if (!c.get(i).equals(Object)) {
					criteria.setResultTransformer(Transformers.aliasToBean(c.get(i)));
				}
			}
			criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		// 需要关联的表
		if (gl != null && gl.size() > 0) {
			for (int i = 0; i < gl.size(); i++) {
				criteria.createAlias(gl.get(i), gl.get(i));
			}
		}

		// 启用or
		Disjunction disjunction = Restrictions.disjunction();
		Boolean a = false;
		if (ziduan != null && zhi != null) {
			for (int j = 0; j < ziduan.size(); j++) {
				switch (type.get(j)) {
				// 等于
				case 0:
					criteria.add(Restrictions.eq(ziduan.get(j), zhi.get(j)));
					break;
				// or
				case 1:
					a = true;
					disjunction.add(Restrictions.eq(ziduan.get(j), zhi.get(j)));
					break;
				// 空
				case 2:
					criteria.add(Restrictions.isNull(ziduan.get(j)));
					break;
				// 非空
				case 3:
					criteria.add(Restrictions.isNotNull(ziduan.get(j)));
					break;
				// like
				case 4:
					if (zhi.get(j).getClass().getName() == "java.lang.String") {
						criteria.add(Restrictions.like(ziduan.get(j), zhi.get(j).toString(), MatchMode.START));
					} else {
						criteria.add(Restrictions.like(ziduan.get(j), zhi.get(j)));
					}
					break;
				// <=
				case 5:
					criteria.add(Restrictions.le(ziduan.get(j), zhi.get(j)));
					break;
				// >=
				case 6:
					criteria.add(Restrictions.ge(ziduan.get(j), zhi.get(j)));
					break;
				// 不等
				case 7:
					criteria.add(Restrictions.ne(ziduan.get(j), zhi.get(j)));
					break;
				// 大于
				case 8:
					criteria.add(Restrictions.gt(ziduan.get(j), zhi.get(j)));
					break;
				// 小于
				case 10:
					criteria.add(Restrictions.lt(ziduan.get(j), zhi.get(j)));
					break;
				// 区间
				case 9:
					criteria.add(Restrictions.between(ziduan.get(j), zhi.get(j), zhi.get(j + 1)));
					break;
				default:
					break;
				}

			}
		}
		if (a = false) {
			disjunction = null;
		}
		if (disjunction != null) {
			criteria.add(disjunction);
		}
		// 倒叙
		if (odb != null) {
			criteria.addOrder(Order.desc(odb));
		}
		// 是否分页
		if (start != null && size != null) {
			return (List<java.lang.Object>) this.getHibernateTemplate().findByCriteria(criteria, start, size);
		} else {
			return (List<java.lang.Object>) this.getHibernateTemplate().findByCriteria(criteria);
		}
	}

	public List<Object> getObjectAll(Class Object, Boolean cache, Map<String, Integer> limit, Map<String, Object> map,
			List<Integer> type, String odb, List<String> gl, List<Class> c, String... sqlzd) {
		// TODO Auto-generated method stub
		// 是否需要开启缓存
		if (cache != null) {
			this.getHibernateTemplate().setCacheQueries(cache);
		} else {
			cache();
		}
		//查询的实体
		DetachedCriteria criteria = DetachedCriteria.forClass(Object);
		//查询的字段null代表*
		if (sqlzd != null && sqlzd.length > 0) {
			ProjectionList pList = Projections.projectionList();
			for (int j = 0; j < sqlzd.length; j++) {
				pList.add(Property.forName(String.valueOf(sqlzd[j])), String.valueOf(sqlzd[j]));
				// pList.add(Projections.property("binding").as("binding"));
			}
			criteria.setProjection(pList);
			criteria.setResultTransformer(Transformers.aliasToBean(Object));
		}
		//需要关联查询的表
		if (gl != null && gl.size() > 0) {
			for (int i = 0; i < gl.size(); i++) {
				criteria.createAlias(gl.get(i), gl.get(i));
			}
		}
		//封装的实体
		if (c != null && c.size() > 0) {
			for (int i = 0; i < c.size(); i++) {
				if (!c.get(i).equals(Object)) {
					criteria.setResultTransformer(Transformers.aliasToBean(c.get(i)));
				}
			}
			criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		//是否批量or
		Disjunction disjunction = Restrictions.disjunction();
		Boolean a = false;
		if (map != null && map.size() > 0 && type != null & type.size() > 0) {
			for (int i = 0; i < type.size(); i++) {
				String ziduan = getKeyOrNull(map);
				Object zhi = getFirstOrNull(map);
				map.remove(ziduan);
				switch (type.get(i)) {
				// 等于
				case 0:
					criteria.add(Restrictions.eq(ziduan, zhi));
					break;
				// or
				case 1:
					a = true;
					disjunction.add(Restrictions.eq(ziduan, zhi));
					break;
				// 空
				case 2:
					criteria.add(Restrictions.isNull(ziduan));
					break;
				// 非空
				case 3:
					criteria.add(Restrictions.isNotNull(ziduan));
					break;
				// like
				case 4:
					if (zhi.getClass().getName() == "java.lang.String") {
						criteria.add(Restrictions.like(ziduan, zhi.toString(), MatchMode.START));
					} else {
						criteria.add(Restrictions.like(ziduan, zhi));
					}
					break;
				// <=
				case 5:
					criteria.add(Restrictions.le(ziduan, zhi));
					break;
				// >=
				case 6:
					criteria.add(Restrictions.ge(ziduan, zhi));
					break;
				// 不等
				case 7:
					criteria.add(Restrictions.ne(ziduan, zhi));
					break;
				// 大于
				case 8:
					criteria.add(Restrictions.gt(ziduan, zhi));
					break;
				// 小于
				case 10:
					criteria.add(Restrictions.lt(ziduan, zhi));
					break;
				// 区间
				case 9:
					Object zhi2 = getFirstOrNull(map);
					ziduan = getKeyOrNull(map);
					map.remove(ziduan);
					criteria.add(Restrictions.between(ziduan, zhi, zhi2));
					break;
				default:
					break;
				}

			}
		}
		if (a = false) {
			disjunction = null;
		}
		if (disjunction != null) {
			criteria.add(disjunction);
		}
		if (odb != null) {
			criteria.addOrder(Order.desc(odb));
		}
		if (limit != null && limit.size() > 1) {
			return (List<java.lang.Object>) this.getHibernateTemplate().findByCriteria(criteria, limit.get("start"),
					limit.get("size"));
		} else {
			return (List<java.lang.Object>) this.getHibernateTemplate().findByCriteria(criteria);
		}
	}

	public Integer getObjectAllByTyCount(Class Object, Boolean cache, List<String> ziduan, List<Object> zhi,
			List<Integer> type, List<String> gl, List<Class> c) {
		// TODO Auto-generated method stub
		if (cache != null) {
			this.getHibernateTemplate().setCacheQueries(cache);
		} else {
			cache();
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Object);
		if (c != null && c.size() > 0) {
			for (int i = 0; i < c.size(); i++) {
				criteria.setResultTransformer(Transformers.aliasToBean(c.get(i)));
			}
			criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		if (gl != null && gl.size() > 0) {
			for (int i = 0; i < gl.size(); i++) {
				criteria.createAlias(gl.get(i), gl.get(i));
			}
		}
		criteria.setProjection(Projections.rowCount());
		Disjunction disjunction = Restrictions.disjunction();
		Boolean a = false;
		if (ziduan != null && zhi != null) {
			for (int j = 0; j < ziduan.size(); j++) {
				switch (type.get(j)) {
				// 等于
				case 0:
					criteria.add(Restrictions.eq(ziduan.get(j), zhi.get(j)));
					break;
				// or
				case 1:
					if (a == false) {
						a = true;
					}
					disjunction.add(Restrictions.eq(ziduan.get(j), zhi.get(j)));
					break;
				// 空
				case 2:
					criteria.add(Restrictions.isNull(ziduan.get(j)));
					break;
				// 非空
				case 3:
					criteria.add(Restrictions.isNotNull(ziduan.get(j)));
					break;
				// like
				case 4:
					if (zhi.get(j).getClass().getName() == "java.lang.String") {
						criteria.add(Restrictions.like(ziduan.get(j), zhi.get(j).toString(), MatchMode.START));
					} else {
						criteria.add(Restrictions.like(ziduan.get(j), zhi.get(j)));
					}
					break;
				// <=
				case 5:
					criteria.add(Restrictions.le(ziduan.get(j), zhi.get(j)));
					break;
				// >=
				case 6:
					criteria.add(Restrictions.ge(ziduan.get(j), zhi.get(j)));
					break;
				// 不等
				case 7:
					criteria.add(Restrictions.ne(ziduan.get(j), zhi.get(j)));
					break;
				// 大于
				case 8:
					criteria.add(Restrictions.gt(ziduan.get(j), zhi.get(j)));
					break;
				// 小于
				case 10:
					criteria.add(Restrictions.lt(ziduan.get(j), zhi.get(j)));
					break;
				// 区间
				case 9:
					criteria.add(Restrictions.between(ziduan.get(j), zhi.get(j), zhi.get(j + 1)));
					break;
				default:
					break;
				}

			}
		}
		if (a = false) {
			disjunction = null;
		}
		if (disjunction != null) {
			criteria.add(disjunction);
		}
		Number num = (Number) this.getHibernateTemplate().findByCriteria(criteria).get(0);
		return num.intValue();
	}

	@Override
	public Object getObjectbyZd(Class Object, String ziduan, Object i, Boolean cache) throws Exception {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().setCacheQueries(cache);
		DetachedCriteria criteria = DetachedCriteria.forClass(Object);
		criteria.add(Restrictions.eq(ziduan, i));
		if (this.getHibernateTemplate().findByCriteria(criteria).size() > 0) {
			return this.getHibernateTemplate().findByCriteria(criteria).get(0);
		} else {
			return null;
		}
	}

	@Override
	public Object getObjectbyId(Class Object, Integer id, Integer i) throws Exception {
		cache();
		// TODO Auto-generated method stub
		if (i == 0) {
			return this.getHibernateTemplate().get(Object, id);
		} else {
			return this.getHibernateTemplate().load(Object, id);
		}
	}

	/**
	 * 获取map中第一个key值
	 *
	 * @param map
	 *            数据源
	 * @return
	 */
	private static String getKeyOrNull(Map<String, Object> map) {
		String obj = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			obj = entry.getKey();
			if (obj != null) {
				break;
			}
		}
		return obj;
	}

	/**
	 * 获取map中第一个数据值
	 *
	 * @param map
	 *            数据源
	 * @return
	 */
	private static Object getFirstOrNull(Map<String, Object> map) {
		Object obj = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			obj = entry.getValue();
			if (obj != null) {
				break;
			}
		}
		return obj;
	}
}
