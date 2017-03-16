package com.netease.myShoppingMall.core.service.face;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.netease.myShoppingMall.base.service.face.IBaseService;
import com.netease.myShoppingMall.core.domain.Goods;

public interface ISellerService {
	
	public Map<String, Object> releaseOrEditGoods(Goods goodsInfo, String status);
	
	public Map<String, Object> deleteMyGoods(Goods goods);
	
	public String uploadGoodsImage(MultipartFile goodsImage, String pathToSave, Integer goodsId);

}
