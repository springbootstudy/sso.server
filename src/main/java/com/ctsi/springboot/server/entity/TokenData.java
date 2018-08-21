package com.ctsi.springboot.server.entity;

import java.io.Serializable;


/**
 * 
 * @author lb
 *
 * @since 2018年8月1日
 *
 */
public class TokenData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6431933063854113798L;
	
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
