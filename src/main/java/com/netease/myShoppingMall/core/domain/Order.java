package com.netease.myShoppingMall.core.domain;

import java.util.Date;

public class Order extends OrderKey {
    private Date orderTime;

    private Integer purchasedAmount;

    private Double priceSum;

    private String isCompleted;

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