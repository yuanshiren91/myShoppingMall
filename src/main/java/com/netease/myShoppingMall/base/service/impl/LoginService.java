package com.netease.myShoppingMall.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.netease.myShoppingMall.base.dao.face.SpringJdbcInterface;
import com.netease.myShoppingMall.base.dao.impl.SpringJdbcTemplate;
import com.netease.myShoppingMall.base.domain.UserInfo;
import com.netease.myShoppingMall.base.service.face.ILoginService;


/**
 * 登录服务接口，用于验证登录
 */
@Service
public class LoginService implements ILoginService{
	
	@Autowired
    public SpringJdbcInterface springJdbcTemplate;
	
	@Override
	public int doLogin(UserInfo loginInfo, HttpSession session) {
		String username = loginInfo.getUsername() == null ? "" : loginInfo.getUsername().toString();
		String password = loginInfo.getPassword() == null ? "" : loginInfo.getPassword().toString();
		String sql = "select userId, username, password, roleId from netease_userinfo where username='" + username +"'";
		List<Map<String, Object>> userList = springJdbcTemplate.queryForList(sql);
		if(CollectionUtils.isEmpty(userList)) {
			return 1;        //用户不存在
		} else {
			Map<String, Object> user = userList.get(0);
			String passwd = user.get("password").toString();
			if(!passwd.equals(password)) {
				return 2;    //密码错误
			} else {
				//session中保存用户信息
				Integer userId = (Integer)user.get("userId");
				Integer roleId = (Integer)user.get("roleId");
				loginInfo.setUserId(userId);
				loginInfo.setRoleId(roleId);
				
				session.setAttribute("userInfo", loginInfo);
				
//				session.setAttribute("username", username);
//				session.setAttribute("userId", userId);
//				session.setAttribute("roleId", roleId);
				
				return 0;    //验证成功				
			}
		}
	}
}
