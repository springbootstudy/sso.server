package com.ctsi.springboot.server.mydefine.service.impl;

import com.ctsi.springboot.server.mydefine.service.MyService;

/**
 * 
 * @author lb
 *
 * @since 2018年8月6日
 *
 */
public class SimpleMyService implements MyService {
	
	public SimpleMyService() {
		System.out.println("自动注入　");
	}

	@Override
	public void doMything() {
		System.out.println("自定义配置执行");
	}

}
