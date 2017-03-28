package com.netease.myShoppingMall.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.netease.myShoppingMall.base.dao.face.BaseMapper;
import com.netease.myShoppingMall.core.domain.GoodsImage;

@Repository
public interface GoodsImageMapper extends BaseMapper<GoodsImage>{
	
	//删除商品所有照片，只保留一张用以页面显示
	public int deletesButOne(Map<String, Object> params);
}
