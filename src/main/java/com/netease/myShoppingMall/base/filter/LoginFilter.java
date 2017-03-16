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
 * 登录过滤器
 */
public class LoginFilter implements Filter{

		@Override
		public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain filterChain) throws IOException, ServletException {
			if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {  
	            throw new ServletException("LoginFilter just supports HTTP requests");  
	        }  
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			HttpSession session = request.getSession(true);
			
			String url = request.getRequestURI();  
			
			/*
			 * 访问的路径不为空，访问的不是登录页面，session中没有登录用户
			 * 获得购物车数量
			 */
			
			if(url != null && ! url.equals("") && (url.toLowerCase().indexOf("login") < 0 ) 
					&& url.indexOf("countCartItems") < 0 && session.getAttribute("userId") == null) {
				if(isAjaxRequest(request) && !checkAuthority(request)) {
					ServletOutputStream out = response.getOutputStream();
					out.print("loginRequired");
					out.flush();
					out.close();	
					return;
				} else {
					response.sendRedirect(request.getContextPath()+"/login?returnTo=" + url);   
					return;
				}
			}
			
			filterChain.doFilter(servletRequest, servletResponse);
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			
		}


		@Override
		public void destroy() {
			
		}
		
		/**
		 * 判断请求是否为ajax请求
		 * @param request
		 * @return
		 */
		private boolean isAjaxRequest(HttpServletRequest request) {
			return request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
		}
		
		/**
		 * 权限判断
		 * @param request
		 * @return
		 */
		private boolean checkAuthority(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			String url = request.getRequestURI();  
			if(session == null) {
				return false;
			} else {
				Integer roleId = session.getAttribute("roleId") == null ? 0 : Integer.valueOf(session.getAttribute("roleId").toString());
				if(url.toLowerCase().indexOf("cart") > 0 && !roleId.equals(1)) {
					return false;
				} else if(url.toLowerCase().indexOf("uploadImage") > 0 && !roleId.equals("2") ){
					return true;
				}
			}
			return true;
		}

}
