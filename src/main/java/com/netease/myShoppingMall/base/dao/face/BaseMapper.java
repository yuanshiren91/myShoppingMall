package com.netease.myShoppingMall.base.dao.face;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseMapper<T> extends Serializable {	
	
	/**
	 * 获得符合查询条件的记录个数
	 * @param params
	 * @return
	 */
	public int count(Map<String, Object> params);
	
	/**
	 * 获得符合查询条件的分页记录
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	public List<T> findByPage(Map<String, Object> params);
	
	/**
	 * 获得符合查询条件的一条记录
	 * @param params
	 * @return
	 */
	public T findOne(Map<String, Object> params);
	
	/**
	 * 获得符合查询条件的所有记录
	 * @param params
	 * @return
	 */
	public List<T> findList(Map<String, Object> params);
	
	/**
	 * 插入一条记录
	 * @param record
	 */
	public int insert(T record);
	
	/**
	 * 更新一条记录
	 * @param record
	 */
	public int update(T record);
	
	/**
	 * 删除一条记录
	 * @param record
	 */
	public int deleteOne(T record);
	
	/**
	 * 根据条件删除多条记录
	 * @param params
	 * @return
	 */
	public int deletes(Map<String, Object> params);
	
}
