package com.netease.myShoppingMall.core.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
import com.netease.myShoppingMall.core.service.face.IGoodsService;
import com.netease.myShoppingMall.core.service.face.ISellerService;

@Controller
@RequestMapping("/seller")
public class SellerController extends CommonController{
	
	@Resource
	private IGoodsService goodsService;
	
	@Resource
	private ISellerService sellerService;
		
	/**
	 * 获取我的商品列表 主页
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMyGoods")
	public String getMyGoodsIndex(HttpSession session, HttpServletRequest request) {
		return "/views/goods/myGoods";
	}
	
	/**
	 * 获取我的商品列表
	 * @param currentPage
	 * @param itemsOnPage
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGoodsItems")
	public PageResult<Goods> getGoodsItems(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getSessionParams(request);
		PageEntity pageEntity = generatePageEntity(currentPage, itemsOnPage, params);		
		PageResult<Goods> pageResult = goodsService.findByPage(pageEntity);
		return pageResult;
		
	}
	
	/**
	 * 获取我的商品数量
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/countGoodsItems")
	public int countCartItems(HttpServletRequest request, HttpServletResponse response ) {
		Map<String, Object> params = getSessionParams(request);
		String _userId = params.get("userId") == null ? "" : params.get("userId").toString();
		if(!StringUtils.isEmpty(_userId)) {
			int userId = Integer.valueOf(_userId);
			params.put("userId", userId);
			int total = goodsService.count(params);
			return total;
		} else{
			return 0;
		}	
	}
	/**
	 * 新建，编辑商品
	 * @param goodsId
	 * @param request
	 * @param model
	 * @return 视图页面
	 */
	@RequestMapping("/editGoods")
	public String editGoodsIndex(@RequestParam("goodsId") Integer goodsId, HttpServletRequest request, ModelMap model) {
		model.addAttribute("goodsId" , goodsId);
		return "/views/goods/editGoods";
	}
	
	/**
	 * 新建，编辑商品 保存
	 * @param goodsInfo
	 * @param status
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/editGoods/{status}", method = RequestMethod.POST) 
	public Map<String, Object> editGoods(@RequestBody Goods goodsInfo, @PathVariable("status") String status, HttpSession session, HttpServletRequest request) {
		int sellerId = session.getAttribute("userId") == null ? 0 : Integer.valueOf(session.getAttribute("userId").toString());
		goodsInfo.setSellerId(sellerId);
		return sellerService.releaseOrEditGoods(goodsInfo, status);
	}
	
	
	/**
	 * 卖家删除商品，若商品已存在于订单或购物车中，则标记为无效
	 * @param goodsId
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMyGoods", method = RequestMethod.POST)
	public Map<String, Object> deleteMyGoods(@RequestBody Goods goodsToDelete, HttpSession session, HttpServletRequest request) {
		Integer sellerId = (Integer)session.getAttribute("userId");
		goodsToDelete.setSellerId(sellerId);
		return sellerService.deleteMyGoods(goodsToDelete);
	}
	
	/**
	 * 上传商品图片
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public Map<String, Object> uploadImage(@RequestParam(value = "goodsImage", required = false) MultipartFile goodsImage, @RequestParam("fileType") String fileType, @RequestParam("goodsId") Integer goodsId, HttpServletRequest request, HttpServletResponse response) throws IOException {	
		Integer sellerId =(Integer)request.getSession().getAttribute("userId");
		Map<String, Object> res = new HashMap<String, Object>();
		response.setCharacterEncoding("utf-8");		
		response.setContentType("text/html; charset=utf-8");
		if(goodsId == 0) {
			res.put("status", "fail");
			res.put("msg", "请先保存商品信息！");
		} else if(goodsImage != null) {
			//卖家ID作为子目录
			String pathToSave = "goodsImage" + File.separator + sellerId ;
			res = sellerService.uploadGoodsImage(goodsImage, pathToSave, goodsId);	
		}
//		out.print(res);
//		out.flush();
//		out.close();
		return res;
	}
	
	/**
	 * 删除商品图片
	 * @param goodsId
	 */
	@RequestMapping(value="/deleteImage", method = RequestMethod.POST)
	public void deleteImage(@RequestParam("goodsId") Integer goodsId) {
		
	}
	
}
