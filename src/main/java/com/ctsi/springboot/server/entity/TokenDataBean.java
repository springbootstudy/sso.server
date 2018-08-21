package com.ctsi.springboot.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author lb
 *
 * @since 2018年8月1日
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDataBean {
	
	private String username;
	private int userid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}
