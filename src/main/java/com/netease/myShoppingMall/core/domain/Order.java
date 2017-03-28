package com.netease.myShoppingMall.core.domain;

import java.util.Date;

public class Order extends OrderKey {

	private Integer userId;

    private Integer goodsId;
    
    private Date orderTime;

    private Integer purchasedAmount;
    
    private Double purchasedUnitPrice;   

	private Double priceSum;

    private String isCompleted;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getpurchasedAmount() {
        return purchasedAmount;
    }

    public void setpurchasedAmount(Integer purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }
    
    public Double getPurchasedUnitPrice() {
		return purchasedUnitPrice;
	}

	public void setPurchasedUnitPrice(Double purchasedUnitPrice) {
		this.purchasedUnitPrice = purchasedUnitPrice;
	}

    public Double getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(Double priceSum) {
        this.priceSum = priceSum;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted == null ? null : isCompleted.trim();
    }
}