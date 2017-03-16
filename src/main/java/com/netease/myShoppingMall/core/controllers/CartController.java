package com.netease.myShoppingMall.core.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.myShoppingMall.base.controllers.CommonController;
import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;
import com.netease.myShoppingMall.core.domain.Goods;
import com.netease.myShoppingMall.core.service.face.ICartService;
import com.netease.myShoppingMall.core.service.face.IOrderService;


@Controller
@RequestMapping("/cart")
public class CartController extends CommonController{
	
	@Resource
	private ICartService cartService;
	
	
	@RequestMapping("/myCart")
    public String myCart() {
    	return "views/cart/myCart";
    }
	
	/**
	 * 获得用户购物车项目数量
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/countCartItems")
	public int countCartItems(@RequestParam(value = "status",required = false) String status, HttpServletRequest request, HttpServletResponse response ) {
		Map<String, Object> params = getSessionParams(request);
		String _userId = params.get("userId") == null ? "" : params.get("userId").toString();
		if(!StringUtils.isEmpty(_userId)) {
			int userId = Integer.valueOf(_userId);
			params.put("userId", userId);
			params.put("status", status);
			int total = cartService.count(params);
			return total;
		} else{
			return 0;
		}	
	}
	
	/**
	 * 获得用户购物车中商品
	 * @param currentPage
	 * @param itemsOnPage
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCartItems")
	public PageResult<Map<String, Object>> getCartItems(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getSessionParams(request);
		PageEntity pageEntity = generatePageEntity(currentPage, itemsOnPage, params);		
		PageResult<Map<String, Object>> pageResult = cartService.findByPage(pageEntity);
		return pageResult;
		
	}
	
	/**
	 * 添加商品入购物车
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String addToCart(@RequestBody Map<String, Object> params, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Integer userId = (Integer)session.getAttribute("userId");
		params.put("userId", userId);
		return cartService.addToCart(params);
	}
	
	/**
	 * 删除购物车中项目
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFromCart" ,method = RequestMethod.POST)
	public Map<String, Object> deleteFromCart(@RequestBody Goods goodsInfo, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodsId", goodsInfo.getGoodsId());
		params.put("userId", request.getSession().getAttribute("userId"));
		if(cartService.deleteOne(params) > 0) {
			res.put("status", "success");
		} else {
			res.put("status", "fail");
		}
		return res;
	}
	
	/**
	 * 结算
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public int checkout(@RequestBody Map<String, Object> goodsInfo, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodsIds", goodsInfo.get("goodsIds"));
		params.put("userId", request.getSession().getAttribute("userId"));
		params.put("status", goodsInfo.get("status"));
		return cartService.checkout(params);
	}
	
	
	
}
