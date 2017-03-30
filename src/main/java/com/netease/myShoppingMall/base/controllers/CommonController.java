package com.netease.myShoppingMall.base.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;

import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;
import com.netease.myShoppingMall.base.domain.UserInfo;

@SuppressWarnings("unchecked")
public class CommonController {
	
	/**
	 * 获取session中的参数，并将其封装为map
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> getSessionParams(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Enumeration<String> keys = session.getAttributeNames();
    	String key;
    	while(keys.hasMoreElements()){
    		key = keys.nextElement();
    		if(session.getAttribute(key) instanceof UserInfo){
    			params.putAll(getUserInfo((UserInfo)session.getAttribute(key)));
    		} else {
    			params.put(key, session.getAttribute(key));
    		}	
    	}
    	return params;
	}
	
	/**
	 * 获取userInfo中的信息，将其封装为Map
	 * @param userInfo
	 * @return
	 */
	public Map<String, Object> getUserInfo(UserInfo userInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userInfo.getUserId());
		params.put("username", userInfo.getUsername());
		params.put("roleId", userInfo.getRoleId());
		params.put("password", userInfo.getPassword());
		
		return params;
	}
	/**
	 * 获取HttpServletRequest中的参数，并将其封装成map
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getRequestParams(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = ((String) names.nextElement()).trim();
            if (name.equalsIgnoreCase("idField") || name.equalsIgnoreCase("nameField")) {
                continue;
            }
            String[] values = request.getParameterValues(name);
            String key = name.replaceAll("\\[([^\\]]*)\\]", "_$1");
            if (values.length == 1) {
                key = key.replaceAll("_$", "");
                String val = values[0].trim();
                if (val.length() > 0) {
                    Date date = parseDate(val);
                    params.put(key, (date == null ? val : date));
                } 
            } else {
                boolean isDate = parseDate(values[0].trim()) == null ? (parseDate(values[1].trim()) == null ? false : true) : true;

                List<Object> vals = new ArrayList<Object>();
                for (int i = 0; i < values.length; i++) {
                    String val = values[i].trim();
                    if (val.length() > 0) {
                        Date date = parseDate(val);
                        vals.add(date == null ? val : date);
                    } else if (isDate) {
                        vals.add("");
                    }
                }
                if (vals.size() > 0) {
                    params.put(key, vals);
                }
            }
        }
        return params;
	}
	
	private static Date parseDate(String input) {
        Date date = null;
        try {
            date = DateUtils.parseDate(input, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"});
        } catch (ParseException e) {
        }
        return date;
    }
	
	public PageEntity generatePageEntity(int currentPage, int itemsOnPage, Map<String, Object> params) {
		PageEntity pageEntity = new PageEntity();
		pageEntity.setCurrentPage(currentPage);
		pageEntity.setItemsOnPage(itemsOnPage);
		pageEntity.setParams(params);
		return pageEntity;
	}
	
}
