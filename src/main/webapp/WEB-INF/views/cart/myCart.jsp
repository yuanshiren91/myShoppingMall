<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>购物车</title>
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
					<li class="active">购物车</li>
				</ol>
			</div>
		</div>
	</div>
	<!--end-breadcrumbs-->
	<!--start-checkout-->
	<div class="checkout">
		<div class="container">
			<div class="check-top heading">
				<h2>结算</h2>
			</div>
			<div class="check-top">
				<div class="cart-items">
					<div id="total"></div>
					<div class="in-check" >
						<ul class="unit">
							<li><span>项目</span></li>
							<li><span>商品名称</span></li>	
							<li><span>购买数量</span><li>	
							<li><span>商品单价</span>
							<li><span>总价</span></li>
							<div class="clearfix"> </div>
						</ul>			
					</div>
					<div id="cartItems" class="in-check" >		
					</div>
					<div style="text-align:right">
						<a href="javascript:void(0);" id="btnCheckout" onclick="checkout()" class="check-out">购买</a>
						<a href="javascript:void(0);" id="btnExit" onclick="history.back(-1);"class="exit">退出</a>
					</div>
					<div style="text-align:center">
						<ul id="pagination" class="pagination pagination-lb"></ul>
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
			var url = "${contextPath}/cart/countCartItems?status=identical";
			$("#pagination").common("initPagination",{
				url : url,
				itemsOnPage : defaultItemsOnPage,
				onChangePage : function (page) {
					changePage(page);
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
				url:"${contextPath}/cart/getCartItems",
				data:$.param(params),
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
                success: function (data, textStatus) {
                	var resultList = data.resultList;
                	var list = "";
                	for(var i = 0; i < resultList.length ; i ++) {
                		list += '<ul class="cart-header">'
                			 if(resultList[i].isValid != "1") {
		            				list += "<span class='invalid'><b>已失效</b></span>"
		            			}
                		list +=  '<div class="delete"> </div>'
              			 	 +  '<input type="hidden" name="isValid" value="' +resultList[i].isValid + '">'
                			 +  '<input type="hidden" name="goodsId" value=' + resultList[i].goodsId + '>'
							 +  '<li class="ring-in"><a href="${contextPath}/goods/showGoodsInfo?goodsId=' + resultList[i].goodsId + '" >'
							 if(resultList[i].imgSrc != null && resultList[i].imgSrc !=''){
								 list += '<img onerror="javascript:this.src=\'${imagesPath}/noPic.jpg\'" alt="' + resultList[i].goodsName + '" src="' + resultList[i].imgSrc + '"/>'; 
							 } else {
								 list += '<img onerror="javascript:this.src=\'${imagesPath}/noPic.jpg\'" alt="' + resultList[i].goodsName + '" src="${contextPath}/goods/showGoodsImage/' + resultList[i].goodsId+ '/single/0"/>';   
							 }
						list += '</a></li>'	
							 +	'<li><span class="name">' + resultList[i].goodsName + '</span></li>'
 							 +  '<li><span class="amount">' + resultList[i].purchasedAmount + '</span></li>'
 							 +  '<li><span class="amount">' + resultList[i].unitPrice + '</span></li>'
 							 +	'<li><span class="cost">' + resultList[i].priceSum + '</span></li>'
							 +  '<div class="clearfix"> </div>'
							 +  '</ul>';
                	}
                   	$("#cartItems").html(list);  
                   	$('.delete').click(function(c) {
                   		var goodsId = $(this).parent().find('[name="goodsId"]').val();
                   		deleteFromCart(this, goodsId);                   		
                   	});
                   	if(resultList.length <= 0 ) {
                   		$('#btnCheckout').addClass("btn-disable");
                   	} else {
                   		$('#btnCheckout').removeClass("btn-disable");
                   	}
                }
			});				
		}

		function deleteFromCart(target, goodsId) {
            Messager.confirm({ message: "确认要删除选中的商品吗？" }).on(function (e) {
                if (!e) {
                    return;
                }
    			$.ajax({
    				url:"${contextPath}/cart/deleteFromCart",
    				type:"post",
    				dataType:"json",
    				data:JSON.stringify({goodsId:goodsId}),
    				contentType:"application/json; charset=UTF-8",
    				error: function (data) { 
    					var responseText = data.responseText;
    					if(responseText == "loginRequired") {
    						window.location.href = "${contextPath}/login?returnTo=" + window.location.href;
    					} else if(responseText == "sessionFailed") {
    						window.location.href = "${contextPath}/index";
    					} else {
    						alert('运行超时，请重试！');
    					}
    				},
    				success: function (data, textStatus) {
                        if (data.status == 'success') {
                        	Messager.alert(data.msg);
                        	$(target).parent().fadeOut('slow', function(c){
                        		$(target).parent().remove();
                        		
    						}); 
                        	$("#pagination").common("refreshPage");
                    		getCartCount();
                        } else {
                        	Messager.alert(data.msg);
                        }
                    }
    			});
            });
			
		}
		
		function checkout() {
			var confirmMsg = "购买购物车中内容";
			Messager.confirm({ message: "确认" + confirmMsg }).on(function (e) {
                if (!e) {
                    return false;
                }			
				var params = {};
				var goodsIds = new Array();
				$(".cart-header").each(function () {
					if($(this).find("[name='isValid']").val() == "1" ) {
						goodsIds.push($(this).find("[name='goodsId']").val());
					}
				});
				if(goodsIds.length <= 0 ){
					Messager.alert("购物车中无有效商品！");
					return false;
				}
				params.goodsIds = goodsIds;				
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"${contextPath}/cart/checkout",
					data:JSON.stringify(params),
					contentType:"application/json; charset=utf-8",
					error: function (data) { 
						var responseText = data.responseText;
						if(responseText == "loginRequired") {
							window.location.href = "${contextPath}/login?returnTo=" + window.location.href;
						} else if(responseText == "sessionFailed") {
							window.location.href = "${contextPath}/index";
						} else {
							alert('运行超时，请重试！');
						}
					},
	                success: function (data, textStatus) {
	                	var res = eval("(" + data + ")");
	                	if(res.status == 'success') {
	                		Messager.alert('购买成功！');
	                		$("#pagination").common("refreshPage");
	                		getCartCount();
	                	} else {
	                		var invalidGoodsList = res.invalidGoodsList;
	                		for(var i = 0 ; i < invalidGoodsList.length; i++) {
	                			var goodsId = invalidGoodsList[i].goodsId;
	                			var msg = invalidGoodsList[i].msg;
	                			var invalidGoods = $(".cart-header").find("[value=" + goodsId + "]").parent().find(".ring-in").find("img");
	                			$(invalidGoods).attr("data-toggle","popover");
	                			$(invalidGoods).attr("data-placement","right");
	                			$(invalidGoods).attr("data-content",msg);
	                			$(invalidGoods).popover("toggle");
	                		}
	                		$("pagination").common("refreshPage");
	                	}	
	                }
				});	
			});
		}
	</script>
</body>
</html>