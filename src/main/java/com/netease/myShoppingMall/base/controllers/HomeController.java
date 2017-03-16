package com.netease.myShoppingMall.base.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页处理
 * @author 陈俊良
 *
 */
@Controller
public class HomeController {

	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/index", "/", ""}, method = RequestMethod.GET)
	public String index(HttpServletRequest request) {				
		return "views/home/index";
	}
		
}

