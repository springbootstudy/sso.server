package com.ctsi.springboot.server.util;

import io.jsonwebtoken.Claims;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ctsi.springboot.server.entity.TokenData;

/**
 * 
 * @author lb
 *
 * @since 2018年8月1日
 *
 */
public class TokenDataUtil {
	
	private static final Logger log = Logger.getLogger(TokenDataUtil.class);
	
//	public static String get(HttpServletRequest req, String key) {
//		Claims claims = (Claims) req.getAttribute("tokenData");
//		return (String) claims.get(key);
//	}
	
	public static Optional<TokenData> getData(HttpServletRequest req) {
		Claims claims = (Claims) req.getAttribute("tokenData");
		
		if (null == claims) {
			return Optional.empty();
		}
		
		Optional<TokenData> opt;
		try {
			String json = JacksonUtil.bean2Json(claims.get(Constants.TOKEN_DATA));
//			json = JacksonUtil.bean2Json(claims.get(Constants.USER_USERNAME));
			log.info("## " + json);
			
			TokenData data = JacksonUtil.json2Bean(json, TokenData.class);
			log.info("## " + data);
			
			opt = Optional.ofNullable(data);
		} 
		catch (Exception e) {
			e.printStackTrace();
			opt = Optional.empty();
		}
		
		return opt;
	}

}
