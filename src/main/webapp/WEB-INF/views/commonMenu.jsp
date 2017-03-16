<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!--bottom-header-->
	<div class="header-bottom">
		<div class="container">
			<div class="header">
				<div class="col-md-9 header-left">
				<div class="top-nav">
					<input type="hidden" name="status" value="all">
					<ul class="memenu skyblue"><li class="active" name="all"><a href="${contextPath}/index">主页</a></li>
						<li class="grid" name="all"><a href="javascript:changePage('')">所有内容</a>
						<!-- 买家菜单 -->
						<c:if test="${sessionScope.roleId eq '1' }">
							<li class="grid" name="unPurchased"><a href="javascript:changePage('')">未购买</a>
							<li class="grid" name="purchased"><a href="javascript:changePage('')">已购买</a>
						</c:if>
						<!-- 卖家菜单 -->
						<c:if test="${sessionScope.roleId eq '2' }">
							<li class="grid" name="unSelled"><a href="javascript:changePage('')">未售出</a>
							<li class="grid" name="selled"><a href="javascript:changePage('')">已售出</a>
						</c:if>
					</ul>
				</div>
				<div class="clearfix"> </div>
			</div>
			<div class="col-md-3 header-right"> 
				<div class="search-bar">
					<input type="hidden" id="keywords">
					<input type="text" id="inputSearch" placeHolder="搜索" value=""  onBlur="if (this.value == '') {this.value = '';}">
					<input type="submit" id="btnSearch" value="">
				</div>
			</div>
			<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<script type="text/javascript">	
		$(function(){
			$(".top-nav li").click(function(){
				var a = $(this).find('a').get(0);
				if(a != undefined && a.href.indexOf("index") > 0){
					return ;
				}
				$(this).siblings('li').removeClass('active');  
		        $(this).addClass('active');   
		        $('[name=status]').val($(this).attr("name"));
		        var status = $(this).attr("name");
		      	var itemsOnPage = $('#pagination').pagination("getItemsOnPage");
		        var url = "${contextPath}/goods/countAllGoods?itemsOnPage=" + itemsOnPage + "&status=" + status;
		        //重新初始化分页插件
		        $("#pagination").common("initPagination",{
					url:url,
					itemsOnPage: itemsOnPage,
					onChangePage: function (page) {
						changePage(page);
					}
				});
			});
			
			$("#btnSearch").click(function() {
				var keywords = $('#inputSearch').val();
				var status = $(".top-nav li .active").attr("name");
				$('#keywords').val(keywords);
				changePage("");
			});
			
		});
	</script>