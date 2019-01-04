package com.jiwei.entity;

import java.sql.Timestamp;

/**
 * Msg entity. @author MyEclipse Persistence Tools
 */

public class Msg implements java.io.Serializable {

	// Fields

	private Integer msgId;
	private String content;
	private String title;
	private Timestamp date;
	private String phone;
	private String reply;
	private Integer status;
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	// Constructors

	/** default constructor */
	public Msg() {
	}

	/** full constructor */
	public Msg(String content, Timestamp date,String title) {
		this.content = content;
		this.date = date;
		this.title = title;
	}
	public Msg(String content) {
		this.content = content;
	}

	// Property accessors

	public Integer getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}