package com.netease.myShoppingMall.base.domain;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {
	//当前页
    private int currentPage;  
    //总记录数
    private int total;  
    //当前页记录
    private List<T> resultList = new ArrayList<T>();  
           
    public int getCurrentPage() {  
        return currentPage;  
    }  
    public void setCurrentPage(int currentPage) {  
        this.currentPage = currentPage;  
    }  
    public int getTotal() {  
        return total;  
    }  
    public void setTotal(int total) {  
        this.total = total;  
    }  
    public List<T> getResultList() {  
        return resultList;  
    }  
    public void setResultList(List<T> resultList) {  
        this.resultList = resultList;  
    }  
}
