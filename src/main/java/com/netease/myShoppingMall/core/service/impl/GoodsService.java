package com.netease.myShoppingMall.core.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.netease.myShoppingMall.base.service.impl.BaseService;
import com.netease.myShoppingMall.base.util.FileUploadUtil;
import com.netease.myShoppingMall.core.dao.CartMapper;
import com.netease.myShoppingMall.core.dao.GoodsImageMapper;
import com.netease.myShoppingMall.core.dao.GoodsMapper;
import com.netease.myShoppingMall.core.dao.OrderMapper;
import com.netease.myShoppingMall.core.domain.Goods;
import com.netease.myShoppingMall.core.domain.GoodsImage;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.service.face.IGoodsService;

@Service
public class GoodsService extends BaseService<Goods> implements IGoodsService{
	
	@Resource
	private GoodsMapper goodsMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private CartMapper cartMapper;
	
	@Resource
	private GoodsImageMapper goodsImageMapper;
	
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(goodsMapper);
	}

	@Override
	public List<Goods> findMostPopularGoods(Map<String, Object> params) {
		params.put("start", 0);
		params.put("limit", params.get("itemCount"));
		params.put("ifOrderBySelledAmount",true);
		return goodsMapper.findByPage(params);
	}

	@Override
	public List<File> getGoodsImage(Map<String, Object> params) {
		List<GoodsImage> imgSrcList = goodsImageMapper.findList(params);
		List<File> imgList = new ArrayList<File>();
		for(GoodsImage imgSrc : imgSrcList) {
			imgList.add(FileUploadUtil.getFile(imgSrc.getImgSrc().toString()));
		}
		return imgList;
	}

	@Override
	public List<String> getGoodsImgSrc(Map<String, Object> params) {
		List<GoodsImage> goodsImgSrc = goodsImageMapper.findList(params);
		List<String> goodsImgSrcList = new ArrayList<String>();
		for(GoodsImage goodsImage : goodsImgSrc) {
			goodsImgSrcList.add(goodsImage.getImgSrc().toString());
		}
		return goodsImgSrcList;
	}

	@Override
	public Goods getGoodsInfo(Map<String, Object> params) {
		Goods goodsInfo = goodsMapper.findOne(params);
		//判断用户是否已购买,若已购买，显示最近一次购买时价格	
		params.put("ifOrderByTime", true);
		List<Order> orderItems = orderMapper.findList(params);
		if(!orderItems.isEmpty()) {
			goodsInfo.setUnitPrice(orderItems.get(0).getPurchasedUnitPrice());
		}
		return goodsInfo;
	}
	
	

	
}
