package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * Dynote entity. @author MyEclipse Persistence Tools
 */

public class Dynote implements java.io.Serializable {

	// Fields

	private Integer id;
	private Useradmin useradmin;
	private Dynamic dynamic;
	private String content;
	private String title;
	private Timestamp date;
	private Integer status;
	private String opinion;

	// Constructors

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** default constructor */
	public Dynote() {
	}

	/** full constructor */
	public Dynote(Useradmin useradmin, Dynamic dynamic, String content, String title, Timestamp date) {
		this.useradmin = useradmin;
		this.dynamic = dynamic;
		this.content = content;
		this.title = title;
		this.date = date;
	}

	// Property accessors

	public Dynote(Useradmin useradmin, String title) {
		this.title = title;
		this.useradmin = useradmin;
		// TODO Auto-generated constructor stub
	}
	public Dynote(Dynamic dynamic, String title) {
		this.title = title;
		this.dynamic = dynamic;
		// TODO Auto-generated constructor stub
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

	public Dynamic getDynamic() {
		return this.dynamic;
	}

	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

}