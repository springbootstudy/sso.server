package com.ctsi.springboot.server.util;

import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 
 * @author lb
 *
 * @since 2018年8月21日
 * 
 * 模拟生成 Service Ticket 
 *
 */
public class ServiceTicketUtil {
	
	private static final Logger log = Logger.getLogger(ServiceTicketUtil.class);
	
	private static HashMap<String, String> stMap = new HashMap<String, String>();
	
	private ServiceTicketUtil() {}
	
	/**
	 * 模拟生成
	 * @return
	 */
	public static String getServiceTicket() {
		
		String st = UUID.randomUUID().toString();
		log.info("生成票据 " + st);
		
		stMap.put(st, st);
		log.info(stMap);
		
		return st;
	}
	
	/**
	 * 模拟验证
	 * @param st
	 * @return
	 */
	public static boolean validate(String st) {
		log.info("验证票据 " + st);
		boolean result = (stMap.containsKey(st) && stMap.containsValue(st));
		if (result) {
			if (stMap.remove(st, st)) {
				log.info("验证成功，删除票据，每个票据只能使用一次！");
			}
		}
		log.info(stMap);
		
		return result;
	}

}
