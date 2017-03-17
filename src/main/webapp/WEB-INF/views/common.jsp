<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="session"/>  
<c:set var="imagesPath" value="${contextPath}/resources/img"  scope="session"/>  
<c:set var="stylesPath" value="${contextPath}/resources/css"  scope="session"/>  
<c:set var="scriptsPath" value="${contextPath}/resources/js"  scope="session"/>  
<c:set var="fontsPath" value="${contextPath}/resources/fonts" scope="session"/>

<link href="${stylesPath}/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
<!--jQuery(necessary for Bootstrap's JavaScript plugins)-->
<script type="text/javascript" src="${scriptsPath}/jquery-1.11.0.min.js"></script>
<!--Custom-Theme-files-->
<!--theme-style-->
<link href="${stylesPath}/style.css" rel="stylesheet" type="text/css" media="all" />	
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- bootstrap -->
<script type="text/javascript" src="${scriptsPath}/bootstrap.min.js"></script>
<!--开始菜单-->
<script type="text/javascript" src="${scriptsPath}/simpleCart.min.js"></script>
<link href="${stylesPath}/memenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="${scriptsPath}/memenu.js"></script>
<script>$(document).ready(function(){$(".memenu").memenu();});</script>	
<!-- 消息提示框插件 -->
<script type="text/javascript" src="${scriptsPath}/bootstrapMessager.js"></script>
<!--下拉菜单-->
<script type="text/javascript" src="${scriptsPath}/jquery.easydropdown.js"></script>	
<!--分页插件  -->
<link href="${stylesPath}/simplePagination.css" />
<script type="text/javascript"t src="${scriptsPath}/jquery.simplePagination.js"></script>
<!-- 通用js -->
<script type="text/javascript" src="${scriptsPath}/common.js"></script>






 	