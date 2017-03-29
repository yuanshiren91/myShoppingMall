package com.netease.myShoppingMall.base.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;



/**
 * 用户直接访问url过滤器
 */
public class UrlFilter implements Filter{

		@Override
		public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain filterChain) throws IOException, ServletException {
			if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {  
	            throw new ServletException("UrlFilter just supports HTTP requests");  
	        }  
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			String conString = "";  
	        conString = request.getHeader("REFERER");
	        
	        if("".equals(conString) || null==conString){ 
	            String url = request.getRequestURI();
	            if(url.toLowerCase().indexOf("index".toLowerCase()) > 0 || url.toLowerCase().indexOf("login".toLowerCase()) > 0){ 
	            	filterChain.doFilter(request, response);  
	            } else {  
	            	response.sendRedirect(request.getContextPath()+"/index");   
	            }  
	        } else {  
	        	filterChain.doFilter(request, response);  
	        }  
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			
		}


		@Override
		public void destroy() {
			
		}
		
}
