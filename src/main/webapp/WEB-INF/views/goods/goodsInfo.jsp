<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>商品信息</title>
	<link href="${stylesPath}/flexslider.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${stylesPath}/bootstrapSpinner.css" rel="stylesheet" type="text/css" media="all" />
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
					<li class="active">商品信息</li>
				</ol>
			</div>
		</div>
	</div>
	<!--end-breadcrumbs-->
	<!--start-single-->
	<div class="single contact">
		<div class="container">
			<div class="single-main">
				<div class="col-md-9 single-main-left" >
				<form id="dataForm" role="form">
				<input type="hidden" id="goodsId" name="goodsId" value="${goodsId}"/>
				<div class="sngl-top">
					<div class="col-md-5 single-top-left">	
						<div class="flexslider">
							  <ul class="slides">
								<li data-thumb="${imagesPath}/s-1.jpg">
									<div class="thumb-image"> <img src="" data-imagezoom="true" class="img-responsive" alt=""/> </div>
								</li>
								<li data-thumb="${imagesPath}/s-2.jpg">
									 <div class="thumb-image"> <img src="" data-imagezoom="true" class="img-responsive" alt=""/> </div>
								</li>
								<li data-thumb="${imagesPath}/s-3.jpg">
								   <div class="thumb-image"> <img src="" data-imagezoom="true" class="img-responsive" alt=""/> </div>
								</li> 
							  </ul>
						</div>
					</div>

					<div class="col-md-7 single-top-right">
						<div class="single-para simpleCart_shelfItem">
						<h2 name="goodsName"></h2>
							<div class="star-on">
								<ul class="star-footer">
										<li><a href="#"><i> </i></a></li>
										<li><a href="#"><i> </i></a></li>
										<li><a href="#"><i> </i></a></li>
										<li><a href="#"><i> </i></a></li>
										<li><a href="#"><i> </i></a></li>
									</ul>
								<div class="review">
									<a id="selledAmount" name="selledAmount" href="#">已售数量：</a>	
								</div>
							<div class="clearfix"> </div>
							</div>
							
							<h5 id="unitPrice" name="unitPrice" class="item_price">$</h5>
							<p name="abstractInfo"></p>
							<div class="available">
								<ul>
									<li>数量：
										<div class="spinner">  
					                        <div class="form-group form-group-options">  
					                            <div id="purchasedAmountSpinner" class="input-group input-group-option quantity-wrapper">  
					                                <span  class="input-group-addon input-group-addon-remove quantity-remove btn">  
					                                    <span class="glyphicon glyphicon-minus"></span>  
					                                </span>  							  
					                                <input id="purchasedAmount" type="text" onfocus="this.blur();" value="1" name="option[]" class="form-control quantity-count" placeholder="1">  							  
					                                <span class="input-group-addon input-group-addon-remove quantity-add btn">  
					                                    <span class="glyphicon glyphicon-plus"></span>  
					                                </span>                                      
					                            </div>  							                              
					                        </div> 
							            </div>
					            	</li>
					            	<li>库存：<a id="amount" name="amount" href="#"></a></li>					
								<div class="clearfix"> </div>
							</ul>
						</div>
						<c:if test="${sessionScope.roleId eq '2' }">
							<a id="btnEdit" href="${contextPath}/seller/editGoods?goodsId=${goodsId}" class="add-cart item_add">编辑</a>	
						</c:if>		
						<c:if test="${sessionScope.roleId ne '2' }">							
							<a id="btnAddToCart" href="javascript:void(0)" class="add-cart item_add ">加入购物车</a>				
						</c:if>	
						</div>
					</div>
					<div class="clearfix"> </div>
				</div>				
				<div class="tabs">
					<ul class="menu_drop">
					<li class="item1"><a href="#"><img src="${imagesPath}/arrow.png" alt="">摘要</a>
						<ul>
							<li class="subitem1"><a id="abstractInfo" name="abstractInfo" href="#"></a></li>							
						</ul>
					</li>
					<li class="item2"><a href="#"><img src="${imagesPath}/arrow.png" alt="">详细描述</a>
						<ul>
						    <li class="subitem1"><a id="description" name="description" href="#"></a></li>
						</ul>
					</li>				
	 				</ul>
				</div>
				<div class="latestproducts">
					<div class="product-one">

					</div>
				</div>
				</form>
			</div>
			</div>
		</div>
	</div>
	<!--end-single-->
	<jsp:include page="../commonFooter.jsp"></jsp:include>
	<script type="text/javascript" src="${scriptsPath}/imagezoom.js"></script>
	<script type="text/javascript" src="${scriptsPath}/jquery.flexslider.js"></script>	
	<script type="text/javascript" src="${scriptsPath}/bootstrapSpinner.js"></script>	
	<script type="text/javascript">
		$(function() {
			//加载商品信息
			loadGoodsInfo();
			
		    var menu_ul = $('.menu_drop > li > ul'),
		           menu_a  = $('.menu_drop > li > a');
		    
		    menu_ul.hide();
		
		    menu_a.click(function(e) {
		        e.preventDefault();
		        if(!$(this).hasClass('active')) {
		            menu_a.removeClass('active');
		            menu_ul.filter(':visible').slideUp('normal');
		            $(this).addClass('active').next().stop(true,true).slideDown('normal');
		        } else {
		            $(this).removeClass('active');
		            $(this).next().stop(true,true).slideUp('normal');
		        }
		    });
		    
		    <c:if test="${empty isPurchased or isPurchased eq false }">				
				$('#btnAddToCart').click(function(){
					addToCart();
				});	
			</c:if>
			<c:if test="${not empty isPurchased and isPurchased eq true }">
				$('#btnAddToCart').addClass("add-cart-disable");
			</c:if>
		
			//初始化图片轮播插件
			$('.flexslider').flexslider({
				animation: "slide",
				controlNav: "thumbnails",
				directionNav: false
		  	});
			
			$('#purchasedAmount').change(function() {
				var purchasedAmount = parseInt($(this).val());
				var amount = parseInt($("#amount").html());
				if(purchasedAmount > amount || amount == 0) {
					$("#btnAddToCart").addClass("add-cart-disable");
				} else {
					$("#btnAddToCart").removeClass("add-cart-disable");
				}
			});
			
			var mostPopularItems = 3;
			loadMostPopular(mostPopularItems);
		
		});
		
		function loadGoodsInfo() {
			var goodsId = $('#goodsId').val();
			var params = {};
			params.goodsId = goodsId;
			$('#dataForm').common("loadForm", {
				data:$.param(params),
				url:"${contextPath}/goods/getGoodsInfo",
				onLoadError: function (data) {
					alert('运行超时，请重试！');
				},
				onLoadComplete : function () {
					if($('#amount').val() == 0) {
						$("#btnAddToCart").addClass("add-cart-disable");
					}	
				}
			});
		}
		
		$('.slides').find('li').each(function () {
			$(this).attr("data-thumb","${contextPath}/goods/showGoodsImage/" + $('#goodsId').val() + "/single");
			$(this).find("img").attr("src","${contextPath}/goods/showGoodsImage/" + $('#goodsId').val() + "/single");
		});
		
		function loadGoodsImage(status) {
			var goodsId = $('#goodsId').val();
			$.ajax({
				type: "get",
				dataType: "json",
				url:"${contextPath}/goods/getGoodsImgSrc/" + goodsId + "/" +status,
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					
				},
				success: function(data, textStatus) {
					
				}
			});
		}
		function addToCart() {
			var confirmMsg = "加入购物车";
			Messager.confirm({ message: "确认" + confirmMsg }).on(function (e) {
                if (!e) {
                    return false;
                }	
				var params = {};
				params.goodsId = $("#goodsId").val();
				params.purchasedAmount = $("#purchasedAmount").val();
				$('#btnAddToCart').addClass("add-cart-disable");
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
							$('#dataForm').common("refresh");
							getCartCount();
						} 
						$('#btnAddToCart').removeClass("add-cart-disable");
					}
				});
			});
		}
		
		//加载最受欢迎的几个商品
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
                		list += '<div class="col-md-4 product-left p-left">' 
						     +  '<div class="product-main simpleCart_shelfItem">'
							 +  '<a href="${contextPath}/goods/showGoodsInfo?goodsId=' + resultList[i].goodsId + '" class="mask">';
							 if(resultList[i].imgSrc != null && resultList[i].imgSrc !=''){
								 list += "<img class='img-responsive zoom-img' alt='" + resultList[i].goodsName + "' src='" + resultList[i].imgSrc + "'/>"; 
							 } else {
								 list += "<img class='img-responsive zoom-img' alt='" + resultList[i].goodsName + "' src='${contextPath}/goods/showGoodsImage/" + resultList[i].goodsId+ "/single'/>";   
							 }	
						list += "</div>"
							 +  '<div class="product-bottom">'
							 +	'<h3>' + resultList[i].goodsName + '</h3>'
							 +	'<p>' + resultList[i].abstractInfo + '</p>'
							 +	'<h4><span class=" item_price">$' + resultList[i].unitPrice + '</span></h4>'
							 +  '</div>'
						     +  '</div>'
							 +  '</div>'		
                	}
                	list += '<div class="clearfix"></div>';
                   	$(".product-one").html(list);  	
                }
			});	
		}
		
		
	</script>	
</body>
</html>