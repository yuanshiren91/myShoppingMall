package com.netease.myShoppingMall.core.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.netease.myShoppingMall.base.dao.face.BaseMapper;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.domain.OrderKey;

@Repository
public interface OrderMapper extends BaseMapper<Order>{
    int deleteByPrimaryKey(OrderKey key);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(OrderKey key);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);    
    
    int update(Map<String, Object> params);
    
    int getOrderSum(Integer userId);
	
	public int checkout(Map<String, Object> params);
}