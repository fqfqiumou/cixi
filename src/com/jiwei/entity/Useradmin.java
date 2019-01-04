package com.jiwei.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Useradmin entity. @author MyEclipse Persistence Tools
 */

public class Useradmin implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String password;
	private Integer power;
	private String phone;
	private String contact;
	/*private Set newsflashs = new HashSet(0);
	private Set bulletins = new HashSet(0);
	private Set dynotes = new HashSet(0);
	private Set zonenotes = new HashSet(0);
	private Set affairses = new HashSet(0);
	private Set newses = new HashSet(0);*/

	// Constructors

	/** default constructor */
	public Useradmin() {
	}

	/** full constructor */
/*	public Useradmin(String name, String password, Integer power, String phone, String contact, Set newsflashs,
			Set bulletins, Set dynotes, Set zonenotes, Set affairses, Set newses) {
		this.name = name;
		this.password = password;
		this.power = power;
		this.phone = phone;
		this.contact = contact;
		this.newsflashs = newsflashs;
		this.bulletins = bulletins;
		this.dynotes = dynotes;
		this.zonenotes = zonenotes;
		this.affairses = affairses;
		this.newses = newses;
	}*/
	public Useradmin(String name, String password, Integer power, String phone, String contact) {
		this.name = name;
		this.password = password;
		this.power = power;
		this.phone = phone;
		this.contact = contact;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPower() {
		return this.power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

/*	public Set getNewsflashs() {
		return this.newsflashs;
	}

	public void setNewsflashs(Set newsflashs) {
		this.newsflashs = newsflashs;
	}

	public Set getBulletins() {
		return this.bulletins;
	}

	public void setBulletins(Set bulletins) {
		this.bulletins = bulletins;
	}

	public Set getDynotes() {
		return this.dynotes;
	}

	public void setDynotes(Set dynotes) {
		this.dynotes = dynotes;
	}

	public Set getZonenotes() {
		return this.zonenotes;
	}

	public void setZonenotes(Set zonenotes) {
		this.zonenotes = zonenotes;
	}

	public Set getAffairses() {
		return this.affairses;
	}

	public void setAffairses(Set affairses) {
		this.affairses = affairses;
	}

	public Set getNewses() {
		return this.newses;
	}

	public void setNewses(Set newses) {
		this.newses = newses;
	}*/

}