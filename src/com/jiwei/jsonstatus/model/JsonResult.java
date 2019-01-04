package com.jiwei.jsonstatus.model;

public class JsonResult {
	private String code;
	private String message;
	private Object data;
	private Integer number;

	public JsonResult(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public JsonResult(String code, String message, Object data,Integer number) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.number = number;
	}
	public JsonResult() {
	}



	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
