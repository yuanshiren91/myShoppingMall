<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发布商品</title>
	<link href="${stylesPath}/fileinput.min.css" rel="stylesheet" type="text/css" media="all" />	
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
					<li class="active">
					<c:if test="${goodsId eq 0}">
						发布商品
					</c:if>
					<c:if test="${goodsId ne 0}">
						编辑商品
					</c:if></li>
				</ol>
			</div>
		</div>
	</div>
	<!--end-breadcrumbs-->
	<!--release-start-->
	<div class="contact">
		<div class="container">
			<div class="contact-top heading">
				<c:if test="${goodsId eq 0}">
					<h2>发布新商品</h2>
				</c:if>
				<c:if test="${goodsId ne 0}">
					<h2>编辑商品信息</h2>
				</c:if>
				
			</div>
				<div class="contact-text">
					<div class="col-md-12 contact-right">
						<form id="dataForm" role="form" action="" method="post" class="form-horizontal">
							<input type="hidden" id="goodsId" name="goodsId" value="${goodsId}"/>
							<div class="form-group "> 
							 	<label class="control-label" for="goodsName">商品名称</label> 
								<input name="goodsName" type="text" placeHolder="2-80字符"/>
							</div>
							<div class="form-group "> 
								<label class="control-label" for="unitPrice">商品价格</label> 
								<input name="unitPrice" type="text" />
							</div>
							<div class="form-group "> 
								<label class="control-label" for="amount">商品数量</label> 
								<input name="amount" type="number" minLength='0' />
							</div>	
							<div class="form-group "> 	
								<label class="control-label" for="abstractInfo">商品摘要</label> 						
								<textarea name="abstractInfo"  placeHolder="2-140字符"></textarea>
							</div>						
							<div class="form-group "> 
								<label class="control-label" for="description">商品描述</label> 
								<textarea name="description"  placeHolder="2-1000字符" ></textarea>
							</div>	
							<div>
								<ul style="list-style:none;">
									<li style="display:inline-block"><label class="radio left"><input type="radio" name="imagePath" value="url" checked=""><i></i>图片地址</label></li>
									<li style="display:inline-block"><label class="radio"><input type="radio" name="imagePath" value="file"><i></i>本地图片</label></li>
									<div class="clearfix"></div>
								</ul>
							</div>	
							<div id="urlUpload" name="upload" style="display:block" class="form-group" >
								<label class="control-label" for="imgSrc" ></label> 
								<input type="text" name="imgSrc">
							</div>			
							<div id="fileUpload" name="upload" style="display:none" class="form-group">
								<label class="control-label" ></label> 
								<input id=imgUpload name="goodsImage" multiple type="file" >					
								<p class="help-block">支持jpg、jpeg、png格式，大小不超过1.0M，最多可同时上传3张图片,上传前请先保存商品信息</p>
<!--  								<a id="btnUpload" name="upload"  href="javascript:void(0)" class="btn btn-primary btn-block">批量上传</a>  -->
							</div>	
																
							<div class="submit-btn">
								<c:if test="${goodsId eq 0}">
									<input type="submit" name="save" value="发布">
								</c:if>
								
								<c:if test="${goodsId ne 0}">
									<input type="submit" name="release" value="保存">
								</c:if>
								
							</div>
						</form>
					</div>	
					<div class="clearfix"></div>
				</div>
		</div>
	</div>
	<!--release-end-->
	<jsp:include page="../commonFooter.jsp"></jsp:include>
	<script src="${scriptsPath}/bootstrapValidator.min.js"></script>	
	<script src="${scriptsPath}/fileinput.min.js"> </script>
	<script src="${scriptsPath}/zh.js"> </script>
	<script src="${scriptsPath}/bootstrapMessager.js"></script>
	<script type="text/javascript">
		$(function(){
			loadGoodsInfo();
			
			$('#dataForm').bootstrapValidator({
	            message: 'This value is not valid',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                goodsName: {
	                    validators: {
	                        notEmpty: {
	                            message: '商品名不能为空'
	                        },
	                        stringLength: {  
			                    min: 2,  
			                    max: 80,  
			                    message: '商品名称长度在[2,80]字符内'  
			                }  
	                    }
			            
	                },
	                unitPrice: {
	                    validators: {
	                        notEmpty: {
	                            message: '商品价格不能为空'
	                        },
	                        regexp: {
	                            regexp: /^[0-9]+.?[0-9]*$/,
	                            message: '商品价格为数字'
	                        }

	                    }
	                },
	                amount: {
	                	validators: {
	                        notEmpty: {
	                            message: '商品数量不能为空'
	                        }
	                    }
	                },
	                abstractInfo: {
                	 	notEmpty: {
                            message: '商品摘要不能为空'
                        },
	                	validators:{
	                		stringLength: {  
			                    min: 2,  
			                    max: 140,  
			                    message: '商品摘要长度在[2,80]字符内'  
		                    }  	
	                	}
	                	
	                },
	                description: {
	                	validators:{
	                		notEmpty: {
		                        message: '商品描述不能为空'
		                    },
	                		stringLength: {  
			                    min: 2,  
			                    max: 1000,  
			                    message: '商品详细描述长度在[2,1000]字符内'  
		                    }  
	                	}
	                },
	                imgSrc:{
	                	validators:{
	                		regexp: {
	                           // regexp: /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig,
	                            regexp: /^(http|ftp|https|file):\/\/([\w\-]+\.).*?\/.*?\.(jpg|png|gif|jpeg)$/i,
	                            message: 'url地址不合法'
	                        },
	                        stringLength: {   
	                        	min: 2,
			                    max: 1000,  
			                    message: 'url地址长度在[2,1000]字符内'  
		                    }  
	                	}
	                }
	                
	            }
	        }).on('success.form.bv', function(e) {
	            // Prevent form submission
	            e.preventDefault();

	            // Get the form instance
	            var $form = $(e.target);

	            doEdit($form);

	        });			

			$('input[type="radio"][name="imagePath"]').click(function(){
				var type = $(this).val();
				if(type == "file") {
					$("#fileUpload").show();
					$("#urlUpload").hide();
				} else {
					$("#fileUpload").hide();
					$("#urlUpload").show();
				}
			});
			
			initFileInput("imgUpload", "${contextPath}/seller/uploadImage");
			
			//$("#btnUpload").click(upload);
		
		});
		function doEdit(target) {
			var status = $('#goodsId').val() == 0 ? "release" : "edit";
			var confirmMsg = $('#goodsId').val() == 0 ? "发布" : "保存";
			Messager.confirm({ message: "确认" + confirmMsg }).on(function (e) {
                if (!e) {
                    return false;
                }				
				$('input[type="submit"]').attr("disabled",'disabled');
				var jsonObject = target.find("[type!='radio']").serializeObject();
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"${contextPath}/seller/editGoods/" + status,
					data:JSON.stringify(jsonObject),
					contentType:"application/json; charset=utf-8",
					error: function (data) { 
						alert('运行超时，请重试！');
						$('input[type="submit"]').attr("disabled",'enable');
					},
	                success: function (data, textStatus) {
	                    if (data.result == 'success') {
	                    	Messager.alert(data.msg).on(function(){
	                    		$('#goodsId').val(data.goodsId);
	                    		if(status == "release") {
			                    	window.location.href = "${contextPath}/seller/editGoods?goodsId=" + data.goodsId;
	                    		} else {
			                    	window.location.href = "${contextPath}/goods/showGoodsInfo?goodsId=" + data.goodsId;
	                    		}
	                    	});	                    	
	                    } else {
	                    	Messager.alert(data.msg);	              	    	
	                    }
	                    $('input[type="submit"]').removeAttr('disabled');
	                }
				});
			});
		}
		
		//初始化文件上传插件
		function initFileInput(ctrlName, uploadUrl) {    
		    var control = $('#' + ctrlName); 
		    
		    control.fileinput({
		        language: 'zh', 
		        uploadUrl: uploadUrl, 
		        allowedFileExtensions : ['jpg', 'png','jpeg'],
		        showRemove: true,
		        showUpload: true, 
		        showCaption: true,
		        browseClass: "btn btn-primary",             
		        dropZoneEnabled: false,
		        showUploadedThumbs: false,
		        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
		        maxFileSize: 1024,
		        maxFileCount: 3,
		        enctype: 'multipart/form-data',
		        uploadExtraData: function () {
		        	var goodsId = $("#goodsId").val();
		        	var fileType = "goodsImage";
		        	return {goodsId:goodsId,fileType:fileType};
		        }
		    }).on("filepreupload", function (event, data, previewId, index) {
		    	var extra = data.extra;
		  		if(extra.goodsId == 0){
		  			if(index == 0) {
			  			return {
	 		  				message: '请先保存商品信息!',
	 		  				data: {}
			  			};
		  			} else {
		  				return {
		  					message: '',
		  					data: {}
		  				};
		  			}
		  		}
		  		
		    }).on("fileuploaded", function (event, data) {
		        var res = data.response;
		        if (res.status == 'fail') {
		        	Messager.alert(res.msg);
		        } 
		    })
		}
		
		function loadGoodsInfo() {
			var goodsId = $('#goodsId').val();
			if(goodsId != '0'){
				var params = {};
				params.goodsId = goodsId;
				$('#dataForm').common("loadForm", {
					data:$.param(params),
					url:"${contextPath}/goods/getGoodsInfo",
					onLoadError: function (data) {
						alert('运行超时，请重试！');
					},
					onLoadSuccess : function (data, textStatus) {
						alert('加载成功！');
					}
				});
			}	
		}
		
	</script>
</body>
</html>