package com.ctsi.springboot.server.mydefine;

import org.springframework.context.annotation.Bean;

import com.ctsi.springboot.server.mydefine.service.MyService;
import com.ctsi.springboot.server.mydefine.service.impl.SimpleMyService;

public class MyConfiguration {
	
	@Bean
	public MyService getMyService() {
		System.out.println("注入配置");
		return new SimpleMyService();
	}

}
