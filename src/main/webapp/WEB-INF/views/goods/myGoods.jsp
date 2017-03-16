<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的商品</title>
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
					<li class="active">我的商品</li>
				</ol>
			</div>
		</div>
	</div>
	<!--end-breadcrumbs-->
	<!--start-checkout-->
	<div class="checkout">
		<div class="container">
			<div class="check-top heading">
				<h2>我的商品</h2>
			</div>
			<div class="check-top">
				<div class="cart-items">
					<div id="total"></div>
					<div class="in-check" >
						<ul class="unit">
							<li><span>项目</span></li>
							<li><span>商品名称</span></li>	
							<li><span>数量</span><li>	
							<li><span>价格</span></li>
							<div class="clearfix"> </div>
						</ul>			
					</div>
					<div id="goodsItems" class="in-check" >
						
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
			var url = "${contextPath}/seller/countGoodsItems";
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
				url:"${contextPath}/seller/getGoodsItems",
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
               			 	 +  '<div class="delete"> </div>'
               			 	 +  '<div class="edit"> </div>'
               			 	 +  '<input type="hidden" name="goodsId" value=' + resultList[i].goodsId + '>'
							 +  '<li class="ring-in"><a style="width:50px" href="javascript:showItem(\'' + resultList[i].goodsId + '\');" ><img src="${contextPath}/goods/showGoodsImage/' + resultList[i].goodsId+ '/single" class="" alt=""></a></li>'
							 +	'<li><span class="name">' + resultList[i].goodsName + '</span></li>'
							 +  '<li><span class="amount">' + resultList[i].purchasedAmount + '</span></li>'
							 +	'<li><span class="cost">' + resultList[i].priceSum + '</span></li>'
							 +  '<div class="clearfix"> </div>'
							 +  '</ul>';
                	}
                   	$("#goodsItems").html(list);  
                   	
                   	$(".edit").click(function(c) {
                   		var goodsId = $(this).parent().find('[name="goodsId"]').val();
                   		window.location.href = "${contextPath}/seller/editGoods?goodsId=" + goodsId;  
                   	});
                   	
                   	$('.delete').click(function(c) {
                   		var goodsId = $(this).parent().find('[name="goodsId"]').val();
                   		deleteItem(this, goodsId);                   		
                   	});
                }
			});		
		}

		function deleteItem(target, goodsId) {
			var confirmMsg = "删除选中的商品";
            Messager.confirm({ message: "确认" + confirmMsg + "?" }).on(function (e) {
                if (!e) {
                    return;
                }
    			$.ajax({
    				url:"${contextPath}/seller/deleteMyGoods",
    				type:"post",
    				dataType:"json",
    				data: JSON.stringify({goodsId:goodsId}),
    				contentType:"application/json; charset=UTF-8",
    				error: function (data) {
    					alert("运行超时，请重试！");
    				},
    				success: function (data, textStatus) {
                        if (data.status == 'success') {
                        	alert(data.msg);
                        	$(target).parent().fadeOut('slow', function(c){
                        		$(target).parent().remove();
    						}); 
                        } else {
                        	alert(data.msg);
                        }
                    }
    			});
            });
			
		}
		
		function showItem(goodsId) {
			windows.location.href="${contextPath}/goods/showGoodsInfo?goodsId=" + goodsId;
		}
		
		function checkout(status) {
			var params = {};
			var goodsIds = new Array();
			params.status = status;
			$.ajax({
				type: "POST",
				dataType: "json",
				url:"${contextPath}/cart/checkout",
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
                success: function (data, textStatus) {
                	var status = parseInt(data);
                	if(status > 0) {
                		alert('购买成功！');
                	} else {
                		alert('购买失败！')
                	}
                	
                }
			});		

		}
	</script>
</body>
</html>