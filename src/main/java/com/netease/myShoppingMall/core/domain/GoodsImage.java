package com.netease.myShoppingMall.core.domain;

public class GoodsImage {
	private int goodsId;
	private String imgSrc;
	
	public GoodsImage() {
		
	}
	
	public GoodsImage(int goodsId, String imgSrc) {
		super();
		this.goodsId = goodsId;
		this.imgSrc = imgSrc;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
}
