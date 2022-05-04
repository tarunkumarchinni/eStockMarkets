package com.estockmarket.app.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class serviceMessage {
	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("result")
	private List result;
	public serviceMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public serviceMessage(String code, String message,List result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getResult() {
		return result;
	}
	public void setResult(List result) {
		this.result = result;
	}
	
}
