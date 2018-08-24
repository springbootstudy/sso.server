package com.ctsi.springboot.server.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ctsi.springboot.server.util.Constants;
import com.ctsi.springboot.server.util.JwtUtil;

@Component
@WebFilter( urlPatterns = {"/*"}, filterName = "jwtLoginFilter") 
public class JwtLoginFilter implements Filter  {
	
	private static final Logger log = Logger.getLogger(JwtLoginFilter.class);
	
    private List<String> excludedPageList;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludedPageList = new ArrayList<String>();
		Enumeration<String> enums = filterConfig.getInitParameterNames();
		String key;
		String value;
		while ( enums.hasMoreElements() ) {
			key = enums.nextElement();
			value = filterConfig.getInitParameter(key);
			
			log.info("## " + key + ", " + value);
			
			excludedPageList.add(value);
		}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.info("## doFilter ");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		log.info("## " + req.getQueryString() + ", " + req.getMethod() + ", " + req.getServletPath() + ", " + req.getUserPrincipal());
		
//		Enumeration<String> names = req.getAttributeNames();
//		while (names.hasMoreElements()) {
//			String name = names.nextElement();
//			System.out.println(name + ", " + req.getAttribute(name));
//		}
		
		rep.setHeader("Access-Control-Allow-Origin", "*");  
		
		// 判定是否预检请求
		if ("OPTIONS".equals(req.getMethod())) {
			log.info("## 处理预检");
			rep.setStatus(HttpStatus.NO_CONTENT.value());
			//当判定为预检请求后，设定允许请求的头部类型
			rep.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, ssoToken"); 
			//当判定为预检请求后，设定允许请求的方法
			rep.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
			// 单位秒
			rep.addHeader("Access-Control-Max-Age", "60"); 
		}
		
		boolean isExcludedPage = false;
		for (String page : excludedPageList) {

			// 判断当前URL是否与例外页面相同
			if (req.getServletPath().equals(page)) { 
				log.info("## " + page + " , you're excluded.");
				isExcludedPage = true;
				break;
			}
			
		}
		
		if (isExcludedPage) { // 在过滤url之外
			chain.doFilter(request, response);
		} 
		else { // 不在过滤url之外
			response.setContentType("application/json; charset=utf-8");
			
//			AjaxData ajaxData;
			String queryAddress = req.getParameter("qa");
			// 获取全局登录标识
			String gssoc = req.getParameter("gssoc");
			log.info("全局 SSO Cookie " + gssoc);
			/*
			 * 全局登录标识
			 * 为空则说明未登录，跳转到 SSO Server 的登录页面
			 * 不为空则需要检查是否有效，有效则证明已经登录，直接返回 Service Ticket，无效则跳转登录页面
			 */
			if (StringUtils.isEmpty(gssoc)) {
				log.info("## " + queryAddress);
				log.info("## 跳转 sso 登录");
//				req.getRequestDispatcher("http://www.baidu.com").forward(request, response);
				rep.sendRedirect("http://sso.sevenzero.org:8088/#/login?qa=" + queryAddress);
				
//				try ( Writer writer = response.getWriter() ) {
//					ajaxData = new AjaxData(1000, "请登录系统");
//					writer.write(JacksonUtil.bean2Json(ajaxData));
//				}
//				catch (Exception ex) {
//					ex.printStackTrace();
//				}
			}
			else {
				try {
//					JwtUtil.validateToken(token);
					Claims claims = JwtUtil.getClaimsFromToken(gssoc);
					log.info("## " + claims.get("username") + ", " + claims.get("userid") + ", " + claims.get(Constants.TOKEN_DATA));
//					Date date = claims.getExpiration();
//					long tokenTime = date.getTime();
//					log.info("## 获取 GSSOC 的时间 " + tokenTime + " => " + new Date(tokenTime));
//					long curTime = System.currentTimeMillis();
//					log.info("## 当前时间 " + curTime + " => " + new Date(curTime));
					
					log.info("## 通过验证");
					req.setAttribute("tokenData", claims);
					chain.doFilter(request, response);
				}
				catch (ExpiredJwtException ex) {
					log.info("## GSSOC 过期");
					ex.printStackTrace();
					rep.sendRedirect("http://sso.sevenzero.org:8088/#/login?qa=" + queryAddress);
//					try ( Writer writer = response.getWriter() ) {
//						ajaxData = new AjaxData(1001, "GSSOC 过期，请重新获取");
//						writer.write(JacksonUtil.bean2Json(ajaxData));
//					}
//					catch (Exception e) {
//						e.printStackTrace();
//					}
				}
				catch (Exception ex) {
					log.info("## 解析 GSSOC 出错");
					ex.printStackTrace();
					rep.sendRedirect("http://sso.sevenzero.org:8088/#/login?qa=" + queryAddress);
					
//					try ( Writer writer = response.getWriter() ) {
//						ajaxData = new AjaxData(1002, "GSSOC 不正确");
//						writer.write(JacksonUtil.bean2Json(ajaxData));
//					}
//					catch (Exception e) {
//						e.printStackTrace();
//					}
				}
			}
		}
	}

	@Override
	public void destroy() {
		
	}
	
}
