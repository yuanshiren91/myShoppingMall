package com.netease.myShoppingMall.base.service.face;

import javax.servlet.http.HttpSession;

public interface ILoginService {
	public int doLogin(String username, String password, HttpSession session);
}
