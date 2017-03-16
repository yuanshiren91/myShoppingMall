package com.netease.myShoppingMall.core.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.myShoppingMall.base.controllers.CommonController;
import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.service.face.IOrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends CommonController{
	
	@Resource
	private IOrderService orderService;
	
	@RequestMapping("/myOrder")
    public String myCart(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	String username = session.getAttribute("username").toString();
    	if(StringUtils.isEmpty(username)) {
    		try {
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}    	
    	return "views/order/myOrder";
    }
	
	/**
	 * 获得用户订单数量
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/countOrderItems")
	public int countCartItems(HttpServletRequest request, HttpServletResponse response ) {
		Map<String, Object> params = getSessionParams(request);
		String _userId = params.get("userId") == null ? "" : params.get("userId").toString();
		if(!StringUtils.isEmpty(_userId)) {
			int userId = Integer.valueOf(_userId);
			params.put("userId", userId);
			int total = orderService.count(params);
			return total;
		} else{
			return 0;
		}	
	}
	
	/**
	 * 获得用户所有订单
	 * @param currentPage
	 * @param itemsOnPage
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderItems")
	public PageResult<Order> getOrderItems(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getSessionParams(request);
		PageEntity pageEntity = generatePageEntity(currentPage, itemsOnPage, params);		
		PageResult<Order> pageResult = orderService.findByPage(pageEntity);
		return pageResult;		
	}
	
	/**
	 * 获得用户订单总价
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderSum")
	public double getOrderSum(HttpSession session, HttpServletRequest request) {
		Integer userId = (Integer)session.getAttribute("userId");
		return orderService.getOrderSum(userId);
	}
	
	
	
}
