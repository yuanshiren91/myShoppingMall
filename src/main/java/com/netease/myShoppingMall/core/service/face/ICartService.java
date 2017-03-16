package com.netease.myShoppingMall.core.service.face;

import java.util.Map;

import com.netease.myShoppingMall.base.service.face.IBaseService;

public interface ICartService extends IBaseService<Map<String, Object>>{
	//结算购物车中选中商品
	public int checkout(Map<String, Object> params);

	public String addToCart(Map<String, Object> params);
}
