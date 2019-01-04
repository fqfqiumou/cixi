package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * Affairs entity. @author MyEclipse Persistence Tools
 */

public class Monthreport implements java.io.Serializable {
	// Fields

	private Integer id;
	private Useradmin useradmin;
	private String title;
	private Timestamp date;
	private Integer status;
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	private String summary;
	private String plan;
	
	private String summarytxt;
	public String getSummarytxt() {
		return summarytxt;
	}

	public void setSummarytxt(String summarytxt) {
		this.summarytxt = summarytxt;
	}

	private String plantxt;

	// Constructors

	public String getPlantxt() {
		return plantxt;
	}

	public void setPlantxt(String plantxt) {
		this.plantxt = plantxt;
	}

	/** default constructor */
	public Monthreport() {
	}

	/** full constructor */
	public Monthreport(Useradmin useradmin, String title, Timestamp date, String summary, String plan) {
		this.useradmin = useradmin;
		this.title = title;
		this.date = date;
		this.summary = summary;
		this.plan = plan;
	}

	// Property accessors

	public Monthreport(String title) {
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
}