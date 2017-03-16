/**
 * 通用js文件，用于一些页面组件初始化，表单验证等常用功能
 */
(function ($) {
	
	//填充表单数据
	var fillForm = function (target, data) {
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
	function loadForm(target, options) {
    	var t = $(target);
    	if(options && options.url && new RegExp("/(-?[\\d+|\\w])").test(options.url)) {
    		var opts = $.extend($.fn.common.loadFormOptions, $.fn.common.defaultsOfAjax, {}, options);
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
                    fillForm(target, data);
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
    		fillForm(target, options);
    	}
    }
	
	//刷新表单
	var refreshForm = function(target) {
		loadForm(target, $.fn.common.loadFormOptions);
	}
	
	var initPagination = function (target, options) {
		var t = $(target);
		if(options && options.url && new RegExp("/(-?[\\d+|\\w])").test(options.url)) {
    		var opts = $.extend({}, $.fn.common.defaultsOfInitPagination, {}, options);
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
				loadForm(this, params);
			});
		},
		refreshForm : function(target) {
			return target.each(function() {
				refreshForm(this);
			});
		},
		initPagination : function(target, params) {
			return target.each(function() {
				initPagination(this, params);
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
	        url: null,
	        contentType: "application/json; charset=utf-8",
	        data: null,
	        dataType: "json",
	        onBeforeLoad: function(XHR) { return true; },
	        onLoadSuccess: function(data, status, XHR) {},
	        onLoadError: function(XHR, status, errorThrow) {},
	        onLoadComplete: function(XHR, status) {}	
	};
	$.fn.common.loadFormOptions = {
			
	}
	
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
	
			
})(jQuery);