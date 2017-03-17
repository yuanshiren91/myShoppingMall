package com.netease.myShoppingMall.core.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.netease.myShoppingMall.base.service.impl.BaseService;
import com.netease.myShoppingMall.core.dao.CartMapper;
import com.netease.myShoppingMall.core.dao.GoodsMapper;
import com.netease.myShoppingMall.core.dao.OrderMapper;
import com.netease.myShoppingMall.core.domain.Goods;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.service.face.ICartService;

@Service
public class CartService extends BaseService<Map<String, Object>> implements ICartService{
	
	@Resource
	private CartMapper cartMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private GoodsMapper goodsMapper;
	
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(cartMapper);
	}

	@Override
	@Transactional
	public int checkout(Map<String, Object> params) {
		orderMapper.checkout(params);
		goodsMapper.updateAfterPurchased(params);
		throw new RuntimeException("test");
		//return cartMapper.deletes(params);		
	    
	}

	@Override
	@Transactional
	public String addToCart(Map<String, Object> params) {
		try{
			//首先判断商品是否还有库存
			Goods goods = goodsMapper.findOne(params);
			if(goods.getAmount() < Integer.parseInt(params.get("purchasedAmount").toString())) {
				return "{\"status\":\"fail\",\"msg\":\"商品库存不足！\"}";
			}
			//判断商品是否已经加入购物车
			boolean exist = exist(params);	
			if(exist) {				
				if(cartMapper.update(params) > 0) {
					return "{\"status\":\"success\",\"msg\":\"添加购物车成功！\"}";
				}
			} else {
				if(cartMapper.insert(params) > 0) {
					return "{\"status\":\"success\",\"msg\":\"添加购物车成功！\"}";
				}
			}
			return "{\"status\":\"fail\",\"msg\":\"添加购物车失败！\"}";
		} catch(Exception e) {
			e.printStackTrace();
			return "{\"status\":\"fail\",\"msg\":\"添加购物车失败！\"}";
		} 
	}
	
}
