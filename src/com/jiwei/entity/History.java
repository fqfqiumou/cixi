package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * History entity. @author MyEclipse Persistence Tools
 */

public class History implements java.io.Serializable {

	// Fields

	private Integer id;
	private Useradmin useradmin;
	private String title;
	private Timestamp date;
	private String content;

	// Constructors

	/** default constructor */
	public History() {
	}

	/** full constructor */
	public History(Useradmin useradmin, String title, Timestamp date, String content) {
		this.useradmin = useradmin;
		this.title = title;
		this.date = date;
		this.content = content;
	}
	public History( String title) {
		this.title = title;
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