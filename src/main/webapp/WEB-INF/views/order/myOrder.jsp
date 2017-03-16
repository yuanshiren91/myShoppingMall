<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>账务</title>
	<jsp:include page="../common.jsp"></jsp:include>
</head>
<body> 
	<jsp:include page="../commonHeader.jsp"></jsp:include>
	<!--start-cart-->
	<!--start-breadcrumbs-->
	<div class="breadcrumbs">
		<div class="container">
			<div class="breadcrumbs-main">
				<ol class="breadcrumb">
					<li><a href="${contextPath}/index">主页</a></li>
					<li class="active">账务</li>
				</ol>
			</div>
		</div>
	</div>
	<!--end-breadcrumbs-->
	<!--start-order-->
	<div class="checkout">
		<div class="container">
			<div class="check-top heading">
				<h2>我的订单</h2>
			</div>
			<div class="check-top">
				<div class="order-items">
			 		<div id="total"></div>	
					<div class="in-order" >
						<ul class="unit">
							<li><span>项目</span></li>
							<li><span>商品名称</span></li>	
							<li><span>购买时间</span>
							<li><span>数量</span><li>	
							<li><span>价格</span></li>
							<div class="clearfix"> </div>
						</ul>			
					</div> 
					<div id="orderItems" class="in-order">
					
					</div>
					<div class="orderSum" style="text-align:right">
						<span id="orderSum" >总计：</span>
					</div>
					<div style="text-align:center">
							<ul id="pagination" class="pagination pagination-lb"></ul>
					</div> 
				</div>
			</div>	
		</div>
	</div>
	<!--end-checkout-->
	<jsp:include page="../commonFooter.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){			
			//初始化分页插件
			var defaultItemsOnPage = 6;	
			var url = "${contextPath}/order/countOrderItems";
			$("#pagination").common("initPagination",{
				url : url,
				itemsOnPage : defaultItemsOnPage,
				onChangePage : function (page) {
					changePage(page);
				}
			});
			
			//获取订单总价格
			$.ajax({
				type: "GET",
				dataType: "json",
				url:"${contextPath}/order/getOrderSum",
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
                success: function (data, textStatus) {
                	var total = data;
                	$("#orderSum").append(total);
                }
			});		
			
		});
		
		function changePage(currentPage) {
			var params = {};
			var currentPage = currentPage == "" ? 1 : $('#pagination').pagination("getCurrentPage");
			params.currentPage = currentPage;
			params.itemsOnPage = $('#pagination').pagination("getItemsOnPage");
			$.ajax({
				type: "GET",
				dataType: "json",
				url:"${contextPath}/order/getOrderItems",
				data:$.param(params),
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
                success: function (data, textStatus) {
                	var resultList = data.resultList;
                	var list = "";
                	for(var i = 0; i < resultList.length ; i ++) {
                		list += '<ul class="order-header">'
                			 + '<input type="hidden" name="goodsId" value=' + resultList[i].goodsId + '>'
							 +  '<li class="ring-in"><a href="javascript:showItem(\'' + resultList[i].goodsId + '\');" ><img src="${imagesPath}/p-8.png" class="" alt=""></a></li>'
							 +	'<li><span class="name">' + resultList[i].goodsName + '</span></li>'
							 +	'<li><span class="time">' + resultList[i].orderTime + '</span></li>'
							 +  '<li><span class="amount">' + resultList[i].purchasedAmount + '</span></li>'
 							 +	'<li><span class="cost">' + resultList[i].priceSum + '</span></li>'
							 +  '<div class="clearfix"> </div>'
							 +  '</ul>';
                	}
                   	$("#orderItems").html(list);  
                }
			});		
		}

		function showItem(goodsId) {
			windows.location.href="${contextPath}/goods/showGoodsInfo?goodsId=" + goodsId;
		}
		
	</script>
</body>
</html>