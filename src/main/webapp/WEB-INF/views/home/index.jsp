<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Home</title>
	<jsp:include page="../common.jsp"></jsp:include>
</head>
<body> 
	<jsp:include page="../commonHeader.jsp"></jsp:include>
	<jsp:include page="../commonMenu.jsp"></jsp:include>
	<!--about-starts-->
	<div class="about"> 
		<div class="container">
			<div class="about-top grid-1">
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!--about-end-->
	<!--product-starts-->
	<div class="product"> 
		<div class="container">
			<div class="product-top">				
									
			</div>			
			<div style="text-align:center">
				<ul id="pagination" class="pagination pagination-lb"></ul>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<!--product-end-->
	<jsp:include page="../commonFooter.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			var mostPopularItems = 3;
			loadMostPopular(mostPopularItems);
			
			//初始化分页插件
			var defaultItemsOnPage = 8;	
			var status = $('[name="status"]').val();
			var url = "${contextPath}/goods/countAllGoods?status=" + status;
			$("#pagination").common("initPagination",{
				url:url,
				itemsOnPage:defaultItemsOnPage,
				onChangePage: function (page) {
					changePage(page);
				}
			});
					
		});
		
		function changePage(currentPage) {
			var params = {};
			var currentPage = currentPage == "" ? 1 : $('#pagination').pagination("getCurrentPage");
			params.currentPage = currentPage;
			params.itemsOnPage = $('#pagination').pagination("getItemsOnPage");
			params.status = $('[name="status"]').val();
			params.keywords = $('#keywords').val();
			 $.ajax({
				type: "GET",
				dataType: "json",
				data: $.param(params),
				url:"${contextPath}/goods/getAllGoods",
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
                success: function (data, textStatus) {
                	var resultList = data.resultList;
                	var list = "";                	
                	for(var i = 0; i < resultList.length ; i ++) {
                		if( i % 4 == 0) {
                			list += "<div class='product-one'>"
                		} 
                		list +=  "<div class='col-md-3 product-left'>"
							 + "<div class='product-main simpleCart_shelfItem'>"
							 + "<a href='${contextPath}/goods/showGoodsInfo?goodsId=" + resultList[i].goodsId + "' class='mask'>";
							 if(parseInt(resultList[i].selledAmount) > 0) {
								 <c:if test="${sessionScope.roleId eq '1'}">
								 	 list += "<span class='had'><b>已购买</b></span>" ;
								 </c:if>
							 }
							 if(resultList[i].imgSrc != null && resultList[i].imgSrc !=''){
								 list += "<img class='img-responsive zoom-img' onerror='javascript:this.src=\"${imagesPath}/noPic.jpg\"' alt='" + resultList[i].goodsName + "' src='" + resultList[i].imgSrc + "'/>"; 
							 } else {
								 list += "<img class='img-responsive zoom-img' onerror='javascript:this.src=\"${imagesPath}/noPic.jpg\"' alt='" + resultList[i].goodsName + "' src='${contextPath}/goods/showGoodsImage/" + resultList[i].goodsId+ "/single/0'/>";   
							 }	 
						list += "</a>"
							 +	"<div class='product-bottom'>"
							 +	"<h3 style='overflow:hidden'>" + resultList[i].goodsName + "</h3>"
							 <c:if test="${sessionScope.roleId ne '2'}">
							 	+	"<h4><a class='item_add' href='javascript:addToCart("+ resultList[i].goodsId + ", 1)'><i></i></a> <span class='item_price'>$ " + resultList[i].unitPrice + "</span></h4>"
							 </c:if>
							 if(parseInt(resultList[i].selledAmount) > 0) {
								 <c:if test="${sessionScope.roleId eq '2'}">
								 	list += "<span class='had'><b>已售出</b></span>"
								 </c:if>
							 }
						list +=	"</div>"
							 +  "</div>" 
							 +	"</div>"		 
						 if( i % 4 == 3 || i == resultList.length - 1) {
							 list += "<div class='clearfix'></div>"
							      + "</div>"
	                	 }  
                	}
                   	$(".product-top").html(list);  	
                }
			});		
		}

		//主页加载最受欢迎的几个商品
		function loadMostPopular(mostPopularItems) {
			$.ajax({
				type: "GET",
				dataType: "json",
				url:"${contextPath}/goods/getMostPopular/" + mostPopularItems,
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
                success: function (data, textStatus) {
                	var resultList = data;
                	var list = "";
                	for(var i = 0; i < resultList.length ; i ++) {
                		list += '<div class="col-md-4 about-left">'
							 + '<figure class="effect-bubba">'
							 + '<img class="img-responsive" src="${imagesPath}/abt-1.jpg" alt=""/>'
							 + '<figcaption>'
							 + '	<h2>' + resultList[i].goodsName + '</h2>'
							 + '	<p>' + resultList[i].abstractInfo + '</p>'	
							 + '</figcaption>'			
							 + '</figure>'
							 + '</div>'
                	}
                   	$(".about-top").html(list);  	
                }
			});	
		}
		
		function addToCart(goodsId, purchasedAmount) {
			var confirmMsg = "加入购物车";
			Messager.confirm({ message: "确认" + confirmMsg }).on(function (e) {
                if (!e) {
                    return false;
                }				
				$('.item_add').attr("disabled",'disabled');
				var params = {};
				params.goodsId = goodsId;
				params.purchasedAmount = purchasedAmount;
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"${contextPath}/cart/addToCart",
					data:JSON.stringify(params),
					contentType:"application/json; charset=utf-8",
					error: function (data) { 
						var responseText = data.responseText;
						if(responseText == "loginRequired") {
							window.location.href = "${contextPath}/login?returnTo=" + window.location.href;
						}
					},
					success: function(data, textStatus) {
						var res = eval("(" + data + ")");
						alert(res.msg);					
						if (res.status == 'success') {
							getCartCount();
						} 
						$('.item_add').removeAttr('disabled');
					}
				});
			});
		}	
		
	</script>
</body>
</html>