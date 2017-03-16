<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String error = request.getParameter("error");
    String showError = "";
    if (null != error)
    {
        if (error.equals("1")) {
            showError = "用户不存在";
        } else if (error.equals("2")) {
            showError = "密码错误";
        } else {
            showError = "";
        }
    }
%>
<html>
<head>
	<title>登录</title>
	<jsp:include page="../common.jsp"></jsp:include>	
	<!-- bootstrap validation -->
	<link href="${stylesPath}/bootstrapValidator.min.css">
</head>
<body> 
	<jsp:include page="../commonHeader.jsp"></jsp:include>
	<jsp:include page="../commonMenu.jsp"></jsp:include>
	<!--start-breadcrumbs-->
	<div class="breadcrumbs">
		<div class="container">
			<div class="breadcrumbs-main">
				<ol class="breadcrumb">
					<li><a href="${pageContent.request.contextPath}/index">主页</a></li>
					<li class="active">账户登录</li>
				</ol>
			</div>
		</div>
	</div>
	<!--end-breadcrumbs-->
	<!--account-starts-->
	<div class="account">
		<div class="container">
		<div class="account-top heading">
				<h2>账户登录</h2>
			</div>
			<div class="account-main">
				<div class="col-md-6 col-md-offset-3 account-left">				
					<div class="account-bottom ">
						<form id="loginForm" role="form" action="" method="post" class="form-horizontal">
							<input type="hidden" id="returnTo" name="returnTo" value="${returnTo}">
    						<div class="form-group has-error">  
								<input type="text" id="username" name="username" class="form-control" placeholder="Username" />														
								<span id="error1" name="error" style="color: red; display:none">用户名不存在！</span>
	         				</div>
							<div class="form-group has-error">
								<input type="password" id="password" name="password" class="form-control" placeholder="Password" />
								<span id="error2" name="error" style="color: red; display:none">密码错误！</span>
							</div>	
							<div class="form-group ">
								<button id="btnLogin" name="error" type="submit" class="btn btn-primary btn-block">登录</button>
							</div>
						</form>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!--account-end-->
	<jsp:include page="../commonFooter.jsp"></jsp:include>	
	<script src="${scriptsPath}/md5.js"></script>	
	<script src="${scriptsPath}/bootstrapValidator.min.js"></script>	
	<script type="text/javascript" src="${scriptsPath}/zh_CN.js"></script>
	<script type="text/javascript">
		$(function(){
			initPassword();			
			$('#loginForm').bootstrapValidator({
	            message: 'This value is not valid',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                username: {
	                    message: '用户名无效',
	                    validators: {
	                        notEmpty: {
	                            message: '用户名不能为空'
	                        }
	                    }
	                },
	                password: {
	                    validators: {
	                        notEmpty: {
	                            message: '密码不能为空'
	                        }
	                    }
	                }
	            }
	        }).on('success.form.bv', function(e) {
	            // Prevent form submission
	            e.preventDefault();
	            
	            doLogin();

	        });
			
		});
		
		function doLogin() {	
			var username = $("#username").val();
			var password = $("#password").val();
			
			var params = {};
			params.username = username;
			params.password = hex_md5(password);
			$("span[name='error']").css({ display: "none" }); 
 			$("#btnLogin").attr('disabled', 'disabled').html('正在登录...');
			$.ajax({
				type: "POST",
				dataType: "json",
				url:"${contextPath}/doLogin",
				data:JSON.stringify(params),
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('login error');
					$("#btnLogin").attr('disabled', 'enable').html('登录');
				},
                success: function (data, textStatus) {
                    if (data.result == 'success') {
                    	$("#password").val(password);
                    	saveUserPassowrd(username, password); 
                    	$("#btnLogin").removeAttr('disabled').html('登录');
                    	var returnTo = $('#returnTo').val();
                    	if(returnTo == "") {
                    		location.href = "${contextPath}/index";
                    	} else {
                    		location.href = returnTo;
                    	}
                        
                    } else {
                    	var errorCode = data.errorCode;
              	    	$("#error"+errorCode).css({ display: "block" }); 
              	    	$("#btnLogin").removeAttr('disabled').html('登录');
                    }
                }
			});
		}	
		
		/**添加设置cookie**/
		function addCookie(name,value,hours,path){     
			var name = escape(name);
            var value = escape(value);
            var expires = new Date();
            expires.setTime(expires.getTime() + hours*3600000);
            path = path == "" ? "" : ";path=" + path;
            _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
            document.cookie = name + "=" + value + _expires + path; 
		} 
		
		//获取cookie值
        function getCookieValue(name){
            var name = escape(name);
            //读cookie属性，这将返回文档的所有cookie
            var allcookies = document.cookie;       
            //查找名为name的cookie的开始位置
            name += "=";
            var pos = allcookies.indexOf(name);    
            //如果找到了具有该名字的cookie，那么提取并使用它的值
            if (pos != -1){                                             //如果pos值为-1则说明搜索"version="失败
                var start = pos + name.length;                  //cookie值开始的位置
                var end = allcookies.indexOf(";",start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
                if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie
                var value = allcookies.substring(start,end);  //提取cookie的值
                return unescape(value);                           //对它解码      
                }   
            else return "";                                             //搜索失败，返回空字符串
        }
		
      	//删除cookie
        function deleteCookie(name,path){
            var name = escape(name);
            var expires = new Date(0);
            path = path == "" ? "" : ";path=" + path;
            document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
        } 
      	
      	function saveUserPassowrd(username, password) {
            addCookie("cookie_username", username, 24 * 30, "/");
            addCookie("cookie_password",password, 24*30, "/");
      	}
      	
      	function initPassword() {
      		var username = getCookieValue("username");
      		var password = getCookieValue("password");
      		$("#username").val(username);
      		$("#password").val(password);
      	}		
	</script>
</body>
</html>