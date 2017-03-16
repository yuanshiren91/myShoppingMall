package com.netease.myShoppingMall.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.netease.myShoppingMall.base.dao.face.BaseMapper;
import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;
import com.netease.myShoppingMall.base.service.face.IBaseService;

@Service
public class BaseService<T> implements IBaseService<T>{
	
	private BaseMapper<T> baseMapper;
	
	public void setBaseMapper(BaseMapper<T> baseMapper) {
		this.baseMapper = baseMapper;
	}
	
	@Override
	public int count(Map<String, Object> params) {
		return baseMapper.count(params);
	}
	
	@Override
	public boolean exist(Map<String, Object> params) {
		T record = baseMapper.findOne(params);
		if(null != record ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int insert(T record) {
		return baseMapper.insert(record);
	}

	@Override
	public int update(T record) {
		return baseMapper.update(record);
	}

	@Override
	public int deleteOne(T record) {
		return baseMapper.deleteOne(record);
	}

	@Override
	public PageResult<T> findByPage(PageEntity pageEntity) {
		try {  
            int currentPage = pageEntity.getCurrentPage() == 0  ? 1 : pageEntity.getCurrentPage(); //默认为第一页  
            int itemsOnPage = pageEntity.getItemsOnPage() ==0  ? 8 : pageEntity.getItemsOnPage(); //默认每页8个  
              
            int start = (currentPage - 1 ) * itemsOnPage;
            int limit = itemsOnPage;
              
            Map<String, Object> params = pageEntity.getParams();  
            params.put("start", start);
            params.put("limit", limit);
            pageEntity.setParams(params);
            System.out.println(pageEntity.getParams());
            
            List<T> resultList = baseMapper.findByPage(params); 
            int total = count(pageEntity.getParams());  
              
            PageResult<T> pageResult = new PageResult<T>();  
            pageResult.setCurrentPage(currentPage);  
            pageResult.setTotal(total);  
            pageResult.setResultList(resultList);  
            return pageResult;
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
	}
	
	@Override
	public T findOne(Map<String, Object> params) {
		return baseMapper.findOne(params);
	}

	@Override
	public List<T> findList(Map<String, Object> params) {
		return baseMapper.findList(params);
	}

	@Override
	public int deletes(Map<String, Object> params) {
		return baseMapper.deletes(params);
	}

	
	
}
