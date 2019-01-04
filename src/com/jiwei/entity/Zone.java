package com.jiwei.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Zone entity. @author MyEclipse Persistence Tools
 */

public class Zone implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
/*	private Set zonenotes = new HashSet(0);*/

	// Constructors

	/** default constructor */
	public Zone() {
	}

	/** full constructor */
	public Zone(String name) {
		this.name = name;
	}
/*	public Zone(String name, Set zonenotes) {
		this.name = name;
		this.zonenotes = zonenotes;
	}
*/
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

	/*public Set getZonenotes() {
		return this.zonenotes;
	}

	public void setZonenotes(Set zonenotes) {
		this.zonenotes = zonenotes;
	}*/

}