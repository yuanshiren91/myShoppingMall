package com.netease.myShoppingMall.base.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.myShoppingMall.base.domain.User;
import com.netease.myShoppingMall.base.service.face.ILoginService;
import com.netease.myShoppingMall.base.service.impl.LoginService;

@Controller
public class LoginController {
	
	@Resource
	private ILoginService loginService;
	
	@RequestMapping("/login")
	public String login(@RequestParam(value = "returnTo", required = false) String returnTo, ModelMap model) {
		model.addAttribute("returnTo", returnTo);
		return "views/account/login";
	}
	
	/** 
     * 注销
     * @param session Session 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/logout")  
    public String logout(HttpSession session) throws Exception{  
        //清除Session中的用户信息 
        session.invalidate();  
          
        return "views/home/index";  
    }  
	
	 /** 
     * 登录验证 
     * @param session HttpSession 
     * @param username 用户名 
     * @param password 密码 
     * @return 
     */  
	@ResponseBody
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)  
    public Map<String, Object> loginValidation(@RequestBody User loginInfo, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{ 
		Map<String, Object> res = new HashMap<String, Object>();
		String username = loginInfo.getUsername() == null ? "" : loginInfo.getUsername().toString();
		String password = loginInfo.getPassword() == null ? "" : loginInfo.getPassword().toString();
        //验证用户名密码是否合法
    	int errorCode = loginService.doLogin(username, password, session);
    	//登录成功
    	if(errorCode == 0) {   	
    		res.put("result", "success");
    		res.put("errorCode", 0);
    	} else {
    		res.put("result", "fail");
    		res.put("errorCode", errorCode);
    	}
    	return res;
    }  
}
