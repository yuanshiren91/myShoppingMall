package com.netease.myShoppingMall.core.domain;

public class Goods {
	private int goodsId;
	private String goodsName;
	private double unitPrice;
	private int sellerId;
	private int amount;
	private String abstractInfo;
	private String description;
	private String imgSrc;
	private int selledAmount;
	private String isValid;
	
	public Goods() {
		
	}
	public Goods(int goodsId, String goodsName, double unitPrice, int sellerId,
			int amount, String abstractInfo, String description, String imgSrc, int selledAmount, String isValid) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.unitPrice = unitPrice;
		this.sellerId = sellerId;
		this.amount = amount;
		this.abstractInfo = abstractInfo;
		this.description = description;
		this.imgSrc = imgSrc;
		this.selledAmount = selledAmount;
		this.isValid = isValid;
	}
			
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getAbstractInfo() {
		return abstractInfo;
	}
	public void setAbstractInfo(String abstractInfo) {
		this.abstractInfo = abstractInfo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public int getSelledAmount() {
		return selledAmount;
	}
	public void setSelledAmount(int selledAmount) {
		this.selledAmount = selledAmount;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

}
