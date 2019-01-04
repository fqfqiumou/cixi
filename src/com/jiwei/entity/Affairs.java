package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * Affairs entity. @author MyEclipse Persistence Tools
 */

public class Affairs implements java.io.Serializable {

	// Fields

	private Integer id;
	private Useradmin useradmin;
	private String title;
	private Timestamp date;
	private String content;

	// Constructors

	/** default constructor */
	public Affairs() {
	}

	/** full constructor */
	public Affairs(Useradmin useradmin, String title, Timestamp date, String content) {
		this.useradmin = useradmin;
		this.title = title;
		this.date = date;
		this.content = content;
	}

	// Property accessors

	public Affairs(String title) {
		// TODO Auto-generated constructor stub
		this.title = title;
	}

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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}