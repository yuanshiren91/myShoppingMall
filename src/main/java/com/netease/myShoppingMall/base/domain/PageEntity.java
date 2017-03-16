package com.netease.myShoppingMall.base.domain;

import java.util.Map;

public class PageEntity {  
	//当前页
    private int currentPage;
    //每页记录数
    private int itemsOnPage; 
    //查询参数
    private Map<String, Object> params; 
    
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getItemsOnPage() {
		return itemsOnPage;
	}
	public void setItemsOnPage(int itemOnPage) {
		this.itemsOnPage = itemOnPage;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
    
    
}  
