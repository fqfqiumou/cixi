package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * Zonenote entity. @author MyEclipse Persistence Tools
 */

public class Zonenote implements java.io.Serializable {

	// Fields

	private Integer id;
	private Useradmin useradmin;
	private Zone zone;
	private String content;
	private String title;
	private Timestamp date;

	// Constructors

	/** default constructor */
	public Zonenote() {
	}

	/** full constructor */
	public Zonenote(Useradmin useradmin, Zone zone, String content, String title, Timestamp date) {
		this.useradmin = useradmin;
		this.zone = zone;
		this.content = content;
		this.title = title;
		this.date = date; 
	}
	public Zonenote(Zone zone, String title) {
		this.zone = zone;
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

	public Zone getZone() {
		return this.zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
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