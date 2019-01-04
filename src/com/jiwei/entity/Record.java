package com.jiwei.entity;

import java.util.Date;

/**
 * Record entity. @author MyEclipse Persistence Tools
 */

public class Record implements java.io.Serializable {

	// Fields

	private Integer id;
	private Useradmin useradmin;
	private Integer count;
	private Date date;

	// Constructors

	/** default constructor */
	public Record() {
	}

	/** full constructor */
	public Record(Useradmin useradmin, Integer count, Date date) {
		this.useradmin = useradmin;
		this.count = count;
		this.date = date;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Useradmin getUseradmin() {
		return this.useradmin;
	}

	public void setUseradmin(Useradmin useradmin) {
		this.useradmin = useradmin;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}