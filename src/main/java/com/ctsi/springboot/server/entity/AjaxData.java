package com.ctsi.springboot.server.entity;

import java.util.UUID;

/**
 * 
 * @author lb
 *
 * @since 2018年7月9日
 *
 *
 */
public class AjaxData {
	
	private String uid;
	// code 0 代表没有错误
	private int code = 0;
	private String message;
	private String token;
	
	public AjaxData(int code, String message) {
		this.code = code;
		this.message = message;
		
		uid = UUID.randomUUID().toString().replace("-", "");
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
