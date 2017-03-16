package com.netease.myShoppingMall.core.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.myShoppingMall.base.service.impl.BaseService;
import com.netease.myShoppingMall.core.dao.CartMapper;
import com.netease.myShoppingMall.core.dao.OrderMapper;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.domain.OrderKey;
import com.netease.myShoppingMall.core.service.face.IOrderService;

@Service
public class OrderService extends BaseService<Order> implements IOrderService{
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private CartMapper cartMapper;
	
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(orderMapper);
	}
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(OrderKey key) {
		return orderMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Transactional
    public int insert(Order record) {
    	return orderMapper.insert(record);
    }

	@Override
	@Transactional
    public int insertSelective(Order record) {
    	return orderMapper.insertSelective(record);
    }

	@Override
    public Order selectByPrimaryKey(OrderKey key) {
		return orderMapper.selectByPrimaryKey(key);
	}

	@Override
	@Transactional
    public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
    public int updateByPrimaryKey(Order record) {
		return orderMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getOrderSum(Integer userId) {
		return orderMapper.getOrderSum(userId);
	}

}
