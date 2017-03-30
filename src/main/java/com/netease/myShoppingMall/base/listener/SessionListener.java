package com.netease.myShoppingMall.base.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.netease.myShoppingMall.base.domain.UserInfo;

public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	
	//保存当前登录用户
	public static Map<String, HttpSession> loginUser = new HashMap<String, HttpSession>(); 
	
	//session中保存用户信息关键字
	public static String SESSION_LOGIN_KEY = "userInfo";

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		//若session中添加的属性名是用户信息
		if(se.getName().equals(SESSION_LOGIN_KEY)) {
			UserInfo userInfo = (UserInfo)se.getValue();
			HttpSession session = loginUser.remove(userInfo.getUsername());
			//若登录列表中已存在该用户
			if(session != null) {
				session.removeAttribute(SESSION_LOGIN_KEY);
			}
			loginUser.put(userInfo.getUsername(), se.getSession());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		//若移除的属性为用户信息，则从map中删除该用户
		if(se.getName().equals(SESSION_LOGIN_KEY)) {
			UserInfo userInfo = (UserInfo)se.getValue();
			loginUser.remove(userInfo.getUsername());
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		if(se.getName().equals(SESSION_LOGIN_KEY)) {
			
			UserInfo userInfo = (UserInfo)se.getValue();
			HttpSession session = loginUser.remove(userInfo.getUsername());
			
			loginUser.put(userInfo.getUsername(), session);
		}

	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		UserInfo userInfo = (UserInfo)se.getSession().getAttribute("userInfo");
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//若session超时或注销，则从map中删除该用户
		UserInfo userInfo = (UserInfo)se.getSession().getAttribute("userInfo");
		loginUser.remove(userInfo.getUsername());
	}

}
