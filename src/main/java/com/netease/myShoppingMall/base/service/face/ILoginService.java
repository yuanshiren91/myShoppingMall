package com.netease.myShoppingMall.base.service.face;

import javax.servlet.http.HttpSession;

import com.netease.myShoppingMall.base.domain.UserInfo;

public interface ILoginService {
	public int doLogin(UserInfo loginInfo, HttpSession session);
}
