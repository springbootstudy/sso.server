package com.ctsi.springboot.server.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.ctsi.springboot.server.filter.JwtLoginFilter;

@Configuration
@Component
public class FilterConfig {
	
	private static final Logger log = Logger.getLogger(FilterConfig.class);
	
	@Value("${filter.token:true}")
	private boolean filterToken;
	
	@Autowired
	private JwtLoginFilter jwtLoginFilter;
	
	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean() {
		log.info("### " + filterToken);
		
		FilterRegistrationBean<Filter> reg = new FilterRegistrationBean<Filter>();
		reg.setFilter(jwtLoginFilter);
//		reg.addInitParameter("login", "/login");
		
		// 不需要验证的 url
		Map<String, String> initParameters = new HashMap<>();
//		initParameters.put("login", "/login");
		initParameters.put("validateServiceTicket", "/validateServiceTicket");
		initParameters.put("logine", "/logine");
		reg.setInitParameters(initParameters);
		
		reg.setEnabled(filterToken);
		
		return reg;
	}

	public boolean isFilterToken() {
		return filterToken;
	}

	public void setFilterToken(boolean filterToken) {
		this.filterToken = filterToken;
	}

}
