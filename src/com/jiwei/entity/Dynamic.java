package com.jiwei.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Dynamic entity. @author MyEclipse Persistence Tools
 */

public class Dynamic implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
/*	private Set dynotes = new HashSet(0);*/

	// Constructors

	/** default constructor */
	public Dynamic() {
	}

	/** full constructor */
/*	public Dynamic(String name, Set dynotes) {
		this.name = name;
		this.dynotes = dynotes;
	}*/
	// Property accessors

	public Dynamic(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

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

/*	public Set getDynotes() {
		return this.dynotes;
	}

	public void setDynotes(Set dynotes) {
		this.dynotes = dynotes;
	}*/

}