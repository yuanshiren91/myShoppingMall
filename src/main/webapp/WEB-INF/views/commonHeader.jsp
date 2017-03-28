<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--top-header-->
	<div class="top-header">
		<div class="container">
			<div class="top-header-main">					
				<div class="col-md-6 top-header-left">
					<div class="box">
						<c:if test="${empty sessionScope['username']}">
							<a href="${pageContext.request.contextPath}/login">请[登录]</a>
							
						</c:if>	
						<c:if test="${not empty sessionScope['username']}">
							<a href="${pageContext.request.contextPath}/order/myOrder">
								欢迎您！${sessionScope['username']}
							</a>
							<a href="${pageContext.request.contextPath}/logout">[注销]</a>				
						</c:if>	
					</div>											
					<div class="box">
						<c:if test="${sessionScope['roleId'] eq 1 }">
						<select id="userrole" class="dropdown drop" onchange="self.location.href=options[selectedIndex].value">
							<option value="" class="label">我是买家</option>
							<option value="${pageContext.request.contextPath}/order/myOrder">我的账务</option>
							<option value="${pageContext.request.contextPath}/cart/myCart">我的购物车</option>
						</select>
						</c:if>
						<c:if test="${sessionScope['roleId'] eq 2 }">
							<select id="userrole" class="dropdown drop" onchange="self.location.href=options[selectedIndex].value">
								<option value="" class="label">我是卖家</option>
								<option value="${pageContext.request.contextPath}/seller/getMyGoods">我的商品</option>
								<option value="${pageContext.request.contextPath}/seller/editGoods?goodsId=0">发布商品</option>
							</select>
						</c:if>	
					</div>								
				</div>
				<div class="col-md-6 top-header-right">
					<div class="cart box_1">
						<a href="${pageContext.request.contextPath}/order/myOrder">账务</a>
						<a href="${pageContext.request.contextPath}/cart/myCart">
							<img src="${imagesPath}/cart-1.png" alt="" />
							<div class="total">							
							</div>
						</a>
						<div class="clearfix"> </div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
<!--top-header-->
<!--start-logo-->
	<div class="logo">
		<a href="${pageContext.request.contextPath}/index"><h1>网易购物城</h1></a>
	</div>
<!-- end-logo -->
<script type="text/javascript">
 	$(function(){
 		getCartCount();
 	});
 	
 	function getCartCount(){
		$.ajax({
			type: "GET",
			dataType: "json",
			url:"${pageContext.request.contextPath}/cart/countCartItems",
			contentType:"application/json; charset=utf-8",
			error: function (data) { 
				alert('运行超时，请重试！');
			},
            success: function (data, textStatus) {
            	var total = parseInt(data);
            	var totalShow = "<span>(" + total + ")</span>";
            	$('.total').html(totalShow);
            }
		});		
	}
</script>
	