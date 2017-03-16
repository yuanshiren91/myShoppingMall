package com.netease.myShoppingMall.core.service.face;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.netease.myShoppingMall.base.service.face.IBaseService;
import com.netease.myShoppingMall.core.domain.Goods;
import com.netease.myShoppingMall.core.domain.GoodsImage;

public interface IGoodsService extends IBaseService<Goods>{
	
	public List<Goods> findMostPopularGoods(Map<String, Object> params);

	public List<File> getGoodsImage(Map<String, Object> params);	
	
	public List<String> getGoodsImgSrc(Map<String, Object> params);
}
