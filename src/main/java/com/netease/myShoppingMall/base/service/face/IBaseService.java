package com.netease.myShoppingMall.base.service.face;

import java.util.List;
import java.util.Map;

import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;

public interface IBaseService<T>{
	public int count(Map<String, Object> params);
	
	public boolean exist(Map<String, Object> params);

	public PageResult<T> findByPage(PageEntity pageEntity);
	
	public T findOne(Map<String, Object> params);
	
	public List<T> findList(Map<String, Object> params);
		
	public int insert(T record);
		
	public int update(T record);
		
	public int deleteOne(T record);
		
	public int deletes(Map<String, Object> params);
}
