package com.netease.myShoppingMall.core.service.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.netease.myShoppingMall.base.service.impl.BaseService;
import com.netease.myShoppingMall.base.util.FileUploadUtil;
import com.netease.myShoppingMall.core.dao.CartMapper;
import com.netease.myShoppingMall.core.dao.GoodsImageMapper;
import com.netease.myShoppingMall.core.dao.GoodsMapper;
import com.netease.myShoppingMall.core.dao.OrderMapper;
import com.netease.myShoppingMall.core.domain.Goods;
import com.netease.myShoppingMall.core.domain.GoodsImage;
import com.netease.myShoppingMall.core.service.face.ISellerService;

@Service
public class SellerService implements ISellerService{
	
	@Resource
	private GoodsMapper goodsMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource 
	private CartMapper cartMapper;
	
	@Resource
	private GoodsImageMapper goodsImageMapper;
	
	private @Value("${goods.limit}") int GOODS_LIMIT ;
	
	private @Value("${goods.sizeLimit}") long UPLOAD_IMAGE_SIZE ;
		
	@Override
	@Transactional
	public Map<String, Object> releaseOrEditGoods(Goods goodsInfo, String status) {	
		Map<String, Object> res = new HashMap<String, Object>();
		if("release".equals(status)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sellerId", goodsInfo.getSellerId());
			int count = goodsMapper.count(params);
			if(count + 1 > GOODS_LIMIT) {
				res.put("result", "fail");
				res.put("msg", "发布商品数量不得超过" + GOODS_LIMIT);
			} else {
				if (goodsMapper.insert(goodsInfo) > 0 ) {
					res.put("result", "success");				
					res.put("msg", "发布成功！");
					res.put("goodsId", goodsInfo.getGoodsId());
				} else {
					res.put("result", "fail");
					res.put("msg", "发布失败！");
				}
			}
			
		} else {
			if (goodsMapper.update(goodsInfo) > 0) {
				res.put("result", "success");
				res.put("msg", "保存成功！");
				res.put("goodsId", goodsInfo.getGoodsId());
			} else {
				res.put("result", "fail");
				res.put("msg", "保存失败！");
			}
		}
		return res;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteMyGoods(Goods goods) {
		Map<String, Object> res = new HashMap<String, Object>();
		//检查商品是否已被存在于订单中
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodsId", goods.getGoodsId());
		int exist = orderMapper.count(params) + cartMapper.count(params);
		if(exist > 0) {
			//商品设为无效
			goods.setIsValid("0");
			if(goodsMapper.update(goods) > 0) {
				res.put("status", "success");
				res.put("msg", "商品已存在于订单或购物车中，已被标记为无效！" );
				return res;
			}
		} else {
			if(goodsMapper.deleteOne(goods) > 0){
				res.put("status", "success");
				res.put("msg", "商品删除成功！");
				return res;
			}
		}
		
		res.put("status", "fail");
		return res;
	}

	@Override
	@Transactional
	public String uploadGoodsImage(MultipartFile goodsImage, String pathToSave, Integer goodsId) {
		try {
			//判断图片大小，若不符合，返回
			if(goodsImage.getSize() > UPLOAD_IMAGE_SIZE ) {
				return "{\"status:\"fail\",\"msg:\"文件大小超过标准！\"}";
			} else {
				//用商品ID和时间作为文件名
				String newFileName = goodsId + "_" + new Date().getTime();
				String savedImgPath = FileUploadUtil.uploadFile(goodsImage, pathToSave, newFileName);
				String jsonObject = "";
				if(savedImgPath != null) {
					jsonObject = "{\"status\":\"success\"}";
				} else {
					jsonObject = "{\"status\":\"fail\"}";
				} 
				if(savedImgPath != null) {
					GoodsImage image = new GoodsImage();
					image.setGoodsId(goodsId);
					image.setImgSrc(savedImgPath);
					goodsImageMapper.insert(image);
				}
				return jsonObject;
			}	
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}	

}
