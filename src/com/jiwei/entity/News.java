package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * News entity. @author MyEclipse Persistence Tools
 */

public class News implements java.io.Serializable {

	// Fields

	private Integer id;
	private Useradmin useradmin;
	private String title;
	private Timestamp date;
	private String content;
	private String img;

	// Constructors

	/** default constructor */
	public News() {
	}

	/** full constructor */
	public News(Useradmin useradmin, String title, Timestamp date, String img,String content) {
		this.useradmin = useradmin;
		this.title = title;
		this.date = date;
		this.img = img;
		this.content = content;
	}

	// Property accessors

	public News(String title) {
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

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}