package com.netease.myShoppingMall.core.service.face;

import java.util.Map;

import com.netease.myShoppingMall.base.service.face.IBaseService;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.domain.OrderKey;

public interface IOrderService extends IBaseService<Order>{
	
	public int deleteByPrimaryKey(OrderKey key);

    public int insertSelective(Order record);

    public Order selectByPrimaryKey(OrderKey key);

    public int updateByPrimaryKeySelective(Order record);

    public int updateByPrimaryKey(Order record);
    
    public int getOrderSum(Integer userId);
  
}
