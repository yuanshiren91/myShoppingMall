/**
 * 通用js文件，用于一些页面组件初始化，表单验证等常用功能
 */
(function ($) {
	
	//填充表单数据
	var _fillForm = function (target, data) {
		var form = $(target);
	    var jsonObj = data;
	    if (typeof data === 'string') {
	        jsonObj = $.parseJSON(data);
	    }

	    for (var key in jsonObj) {  
	        var objtype = jsonObjType(jsonObj[key]);

	         if (objtype === "array") { 

	            var objArray = jsonObj[key];
	            for (var arraykey in objArray) {
	                var arrayobj = objArray[arraykey];
	                for (var smallkey in arrayobj) {
	                    setCkb(key, arrayobj[smallkey]);
	                    break;
	                }
	            }
	        } else if (objtype === "object") { 

	        } else if (objtype === "string") { 
	            var str = jsonObj[key];
	            
	            var date = new Date(str);
	            if (isDate(date)) {  
	            	var tagobjs = $("[name=" + key + "]", form);
	            	if(isAppend(tagobjs)) {
	            		tagobjs.append(date.format("yyyy-MM-dd"));
	            	} else {
	            		tagobjs.val(date.format("yyyy-MM-dd"));
	            	}
	                continue;
	            }

	            var tagobjs = $("[name=" + key + "]", form);
	            if ($(tagobjs[0]).attr("type") == "radio") {
	                $.each(tagobjs, function (keyobj,value) {
	                    if ($(value).attr("val") == jsonObj[key]) {
	                        value.checked = true;
	                    }
	                });
	                continue;
	            }
	            if(isAppend(tagobjs)) {
	            	tagobjs.append(jsonObj[key]);
	            } else {
	            	tagobjs.val(jsonObj[key]);
	            }
	            
	            
	        } else { 
	        	var tagobjs = $("[name=" + key + "]", form);
	        	if(isAppend(tagobjs)) {
	        		tagobjs.append(jsonObj[key]);
	        	} else {
	        		tagobjs.val(jsonObj[key]);
	        	}
	            
	        }

	    }
	}
	
	var jsonObjType = function (obj) {
	    if (typeof obj === "object") {
	        var teststr = JSON.stringify(obj);
	        if (teststr[0] == '{' && teststr[teststr.length - 1] == '}') return "class";
	        if (teststr[0] == '[' && teststr[teststr.length - 1] == ']') return "array";
	    }
	    return typeof obj;
	}

	var setCkb = function (name, value) {
	    $("[name=" + name + "][val=" + value + "]").attr("checked", "checked");
	}
	
	//加载表单数据
	var _loadForm = function(target, options) {
    	var t = $(target);   
    	var opts = $.data(target,"formOpts");
    	if(opts) {
    		$.extend(opts, options);
    	} else {
    		opts = $.data(target, "formOpts",  $.extend({}, $.fn.common.defaultsOfAjax, {}, options));
    	}
    	if(opts && opts.url && new RegExp("/(-?[\\d+|\\w])").test(opts.url)) {
    		$.ajax({
                async: opts.async,
                cache: opts.cache,
                type: opts.type,
                url: opts.url,
                dataType: opts.dataType,
                data: opts.data,
    			beforeSend: function(XHR) {
               	 	return opts.onBeforeLoad.call(target, XHR); 
                },
                success: function(data, status, XHR) {
                    t.css({ visibility: "visible" });   
                    //填充数据
                    _fillForm(target, data);
                	//命名Combo
                	if(opts.names && opts.names.length > 0) {
                    	for(var i = 0; i < opts.names.length; i++) {
                    		var item = opts.names[i];
                    		var fKeyField = item.fKeyField;
                    		if(!fKeyField) {
                    			continue;
                    		}
                    		var b = t.find(':input[comboname="' + fKeyField + '"]');
                			if(b.length == 0) {
                				continue;
                			}
                    	}
                    }
                },
                error: function(XHR, status, errorThrow) {
                    t.css({ visibility: "visible" });
                    opts.onLoadError.call(target, XHR, status, errorThrow);
                },
                complete: function(XHR, status) {
                    opts.onLoadComplete.call(target);
                }
            });
    	}
    	else {
    		_fillForm(target, options);
    	}
    }
	
	var _initPagination = function (target, options) {
		var t = $(target);
		var opts = $.data(target, 'paginationOpts');
		if(opts) {
			$.extend(opts, options);
		} else {
			opts = $.data(target, 'paginationOpts', $.extend({}, $.fn.common.defaultsOfInitPagination, {}, options));
		}
		if(opts && opts.url && new RegExp("/(-?[\\d+|\\w])").test(opts.url)) {
			$.ajax({
				type: "GET",
				dataType: "json",
				url:opts.url,
				contentType:"application/json; charset=utf-8",
				error: function (data) { 
					alert('运行超时，请重试！');
				},
	            success: function (data, textStatus) {
	            	var total = parseInt(data);		                	
	            	t.pagination({
	            		items : total,
	    		        itemsOnPage : opts.itemsOnPage,
	    		        prevText: '<span aria-hidden="true">前一页</span>',
	    		        nextText: '<span aria-hidden="true">后一页</span>',
	    		        onInit: function() {
	    		        	return opts.onChangePage.apply(1);
	    		        },
	    		        onPageClick : function (page, ev) {
	    		        	return opts.onChangePage.apply(page);
	    		        }
	             	});		                	
	            }
			});	
		}
	}
	
	//刷新表单
	var _refreshForm = function(target) {
		_loadForm(target, {});
	}

	//刷新分页
	var _refreshPage =  function(target) {
		_initPagination(target, {});
	}
	
	$.fn.common = function(options, params) {
        if (typeof options == "string") {
            var fn = $.fn.common.methods[options];
            if (fn) {
                return fn(this, params);
            }
        }
	};
	
	$.fn.common.methods = {
		loadForm : function(target, params) {
			return target.each(function() {
				_loadForm(this, params);
			});
		},
		refreshForm : function(target) {
			return target.each(function() {
				_refreshForm(this);
			});
		},
		initPagination : function(target, params) {
			return target.each(function() {
				_initPagination(this, params);
			});
		},
		refreshPage : function(target) {
			return target.each(function() {
				_refreshPage(this);
			});
		}
	}
	
	$.fn.common.defaultsOfInitPagination = {
			itemsOnPage: 1
	}
	
	$.fn.common.defaultsOfAjax = {
			async: true,
	        cache: false,
	        type: "GET",
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        onBeforeLoad: function(XHR) { return true; },
	        onLoadSuccess: function(data, status, XHR) {},
	        onLoadError: function(XHR, status, errorThrow) {},
	        onLoadComplete: function(XHR, status) {}	
	};
	
	/**
	 * 判断一个元素是否使用append添加内容
	 */
	var isAppend = function(tagobjs) {
		var element = tagobjs.get(0);
		if(typeof(element) != "undefined") {
			return element.tagName === "INPUT" ? false : true;
		}
	}
	
	/**
	 * 判断是否是一个日期型数据
	 */
	var isDate = function(json) {
		var reg = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
		var isDate = reg.test(json.date);
		return isDate;
	}
	
	/** 
	* 时间对象的格式化 
	*/  
	Date.prototype.format = function(format)  
	{  
		/* 
		* format="yyyy-MM-dd hh:mm:ss"; 
		*/  
		var o = {  
		"M+" : this.getMonth() + 1,  
		"d+" : this.getDate(),  
		"h+" : this.getHours(),  
		"m+" : this.getMinutes(),  
		"s+" : this.getSeconds(),  
		"q+" : Math.floor((this.getMonth() + 3) / 3),  
		"S" : this.getMilliseconds()  
		}  
		  
		if (/(y+)/.test(format)) {  
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
			- RegExp.$1.length));  
		}  
		  
		for (var k in o) {  
			if (new RegExp("(" + k + ")").test(format)) {  
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
					: ("00" + o[k]).substr(("" + o[k]).length));  
			}  
		}  
		return format;  
	}  
	
	/**
	 * 将序列化的表单值转换成json对象
	 */
	$.fn.serializeObject = function() {
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	}
	
	$.ajaxSetup({
		contentType:"application/json; charset=utf-8",
		error: function (data) { 
			var responseText = data.responseText;
			if(responseText == "loginRequired") {
				window.location.href = getContextPath() + "/login?returnTo=" + window.location.href;
			} else if(responseText == "sessionFailed") {
				alert("页面过期！");
				window.location.href = getContextPath() + "/index";
			} else {
				alert('运行超时，请重试！');
			}
		},
		complete:function (XMLHttpRequest, textStatus) {
			var sessionStatus = XMLHttpRequest.getResponseHeader("sessionStatus");
			if(sessionStatus == "timeout") {
				alert("页面过期，请重新登录！")
				window.location.href = getContextPath() + "/index";
			}
		}
	})
	
	function getContextPath() {
	    var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
	}
	
			
})(jQuery);