package com.netease.myShoppingMall.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	@SuppressWarnings("unchecked")
	public String checkout(Map<String, Object> params) {			
		List<Integer> goodsIds = new ArrayList<Integer>();
		for(Object goodsId : (List<Object>)params.get("goodsIds")) {
			goodsIds.add(Integer.parseInt(goodsId.toString()));
		}
		Map<Integer, String> invalidGoodsList = new HashMap<Integer, String>();
		StringBuffer res = new StringBuffer("");
		if(!goodsIds.isEmpty()) {
			//首先检查所选商品库存是否充足
			for(Integer goodsId : goodsIds) {
				params.put("goodsId", goodsId);
				Goods goodsInfo = goodsMapper.findOne(params);
				Map<String, Object> cartItem = cartMapper.findOne(params);
				if(goodsInfo != null) {
					//商品已被商家标为无效
					if(!"1".equals(goodsInfo.getIsValid())) {
						invalidGoodsList.put(goodsId, "商品已失效！");
					} else if(goodsInfo.getAmount() < Integer.parseInt(cartItem.get("purchasedAmount").toString())) {
						invalidGoodsList.put(goodsId, "商品库存不足！");
					}					
				}
			}
			if(invalidGoodsList.isEmpty()) {
				orderMapper.checkout(params);
				goodsMapper.updateAfterPurchased(params);
				cartMapper.deletes(params);	
				res.append("{\"status\":\"success\",\"msg\":\"购买成功！\"}");
			} else {
				res.append("{\"status\":\"fail\"");
				res.append(",\"invalidGoodsList\":[");
				for(Integer invalidGoodsId : invalidGoodsList.keySet()) {
					res.append("{\"goodsId\":" + invalidGoodsId + ",\"msg\":\"" + invalidGoodsList.get(invalidGoodsId) + "\"},");
				}
				res.deleteCharAt(res.length()-1);
				res.append("]}");
			}
			
		} else {
			res.append("{\"status\":\"fail\",\"msg\":\"购买失败！\"}");
		}
		return res.toString();
   
	}

	@Override
	@Transactional
	public String addToCart(Map<String, Object> params) {
		String res = "";
		try{
			//首先判断商品是否还有库存
			Goods goods = goodsMapper.findOne(params);
			if(goods.getAmount() < Integer.parseInt(params.get("purchasedAmount").toString())) {
				res = "{\"status\":\"fail\",\"msg\":\"商品库存不足！\"}";
			} else {
				//判断商品是否已经加入购物车
				Map<String, Object> cartItemExist = cartMapper.findOne(params);		
				int amountToPurchase =  Integer.parseInt(params.get("purchasedAmount").toString());
				if(cartItemExist != null) {
					int amountExist = Integer.parseInt(cartItemExist.get("purchasedAmount").toString());
					//检查购物车中该商品的总数量是否会超过商品库存
					if((amountExist + amountToPurchase) > goods.getAmount()) {
						res = "{\"status\":\"fail\",\"msg\":\"购物车中该商品总数不能超过库存总数！\"}";
					} else if(cartMapper.update(params) > 0) {
						res = "{\"status\":\"success\",\"msg\":\"添加购物车成功！\"}";
					} else {
						res = "{\"status\":\"success\",\"msg\":\"添加购物车失败！\"}";
					}
				} else {	
					if(cartMapper.insert(params) > 0) {
						res = "{\"status\":\"success\",\"msg\":\"添加购物车成功！\"}";
					} else {
						res = "{\"status\":\"fail\",\"msg\":\"添加购物车失败！\"}";
					}
				}
			}
			return res;
		} catch(Exception e) {
			e.printStackTrace();
			return "{\"status\":\"fail\",\"msg\":\"添加购物车失败！\"}";
		} 
	}
	
}
