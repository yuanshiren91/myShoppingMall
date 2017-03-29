package com.netease.myShoppingMall.core.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.netease.myShoppingMall.base.controllers.CommonController;
import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;
import com.netease.myShoppingMall.base.util.FileUploadUtil;
import com.netease.myShoppingMall.core.domain.Goods;
import com.netease.myShoppingMall.core.domain.GoodsImage;
import com.netease.myShoppingMall.core.domain.Order;
import com.netease.myShoppingMall.core.domain.OrderKey;
import com.netease.myShoppingMall.core.service.face.ICartService;
import com.netease.myShoppingMall.core.service.face.IGoodsService;
import com.netease.myShoppingMall.core.service.face.IOrderService;

@Controller
@RequestMapping("/goods")
public class GoodsController extends CommonController{

	@Resource
	private IGoodsService goodsService;
	
	@Resource
	private IOrderService orderService;
		
	/**
	 * 获得所有商品数量
	 * @param itemsOnPage
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countAllGoods")
	public int countGoods(@RequestParam("status") String status, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getSessionParams(request);
		params.put("status", status);
		//只统计有效商品数量
		params.put("isValid", "1");
		int total = goodsService.count(params);
		return total;
	}
	
	/**
	 * 获取所有商品
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllGoods") 
	public PageResult<Goods> getAllGoods(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage, 
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> params = getRequestParams(request);
		params.putAll(getSessionParams(request));
		if(params.get("keywords") != null) {
			params.put("keywords", URLDecoder.decode(params.get("keywords").toString(),"UTF-8"));
		}
		//只显示有效的商品
		params.put("isValid", "1");
		params.put("isCompleted", "1");
		PageEntity pageEntity = generatePageEntity(currentPage, itemsOnPage, params);
		PageResult<Goods> pageResult = goodsService.findByPage(pageEntity);
		return pageResult;
	}
	
	/**
	 * 获取主页最受欢迎商品列表
	 * @param itemsCount
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMostPopular/{itemsCount}")
	public List<Goods> getMostPopularGoods(@PathVariable("itemsCount") Integer itemsCount, HttpServletRequest request) {
		Map<String, Object> params = getSessionParams(request);
		params.put("itemCount", itemsCount);
		//只显示有效商品
		params.put("isValid", "1");
		List<Goods> resultList = goodsService.findMostPopularGoods(params);
		return resultList;
	}
	
	/**
	 * 获得商品信息页面
	 * @param goodsid
	 * @param model
	 * @return
	 */
	@RequestMapping("/showGoodsInfo")
	public String ShowGoodsInfo(@RequestParam("goodsId") Integer goodsId, HttpSession session, HttpServletRequest request, ModelMap model) {
		Map<String, Object> params = getRequestParams(request);
		params.put("goodsId", goodsId);		
		model.addAttribute("goodsId", goodsId);
		if(session.getAttribute("userId") != null && "1".equals(session.getAttribute("roleId"))) {
			params.put("userId", session.getAttribute("userId"));
			model.addAttribute("isPurchased", orderService.exist(params));
		}
		return "views/goods/goodsInfo";
	}
	
	/**
	 * 显示商品信息
	 * @param goodsid
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGoodsInfo")
	public Goods getGoodsInfo(@RequestParam("goodsId") Integer goodsId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getSessionParams(request);
		params.put("goodsId", goodsId);
		Goods goods = goodsService.getGoodsInfo(params);
		return goods;
	}
		
	/**
	 * 根据商品ID获取首页图片
	 * @param imgSrc
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showGoodsImage/{goodsId}/{status}/{index}")
	public List<String> showGoodsImage(@PathVariable("goodsId") Integer goodsId, @PathVariable("status") String status, @PathVariable("index") Integer index,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodsId", goodsId);
		List<String> imgSrcList = goodsService.getGoodsImgSrc(params);
		response.setContentType("image/*");
		FileInputStream fis = null;
		OutputStream os = null;
		try{ 
			if("single".equals(status)) {
				String imgSrc = "";
				if(!imgSrcList.isEmpty()) {
					int maxIndex = imgSrcList.size() - 1;
					imgSrc = imgSrcList.get(Math.min(index, maxIndex));
					File img = FileUploadUtil.getFile(imgSrc);
					if(!img.isDirectory()){
						fis = new FileInputStream(img);
						os = response.getOutputStream();
						int count = 0;
						byte[] buffer = new byte[1024*8];
						while((count = fis.read(buffer)) != -1) {
							os.write(buffer, 0, count);
							os.flush();
						}
					}
				}
				
			} else if("all".equals(status)){
				return imgSrcList;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null ){
					fis.close();
				}
				if(os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<String>();
	}
	
}
	
