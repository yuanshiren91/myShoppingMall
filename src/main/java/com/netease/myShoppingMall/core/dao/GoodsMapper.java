package com.netease.myShoppingMall.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.netease.myShoppingMall.base.dao.face.BaseMapper;
import com.netease.myShoppingMall.core.domain.Goods;

@Repository
public interface GoodsMapper extends BaseMapper<Goods>{
	//用户购买商品后更新商品数量
	public int updateAfterPurchased(Map<String, Object> params);
}
