var $tt = {
		//提示信息类型
		MSG_TYPE_INFO : "info",
		MSG_TYPE_WARN : "warning",
		MSG_TYPE_ERROR : "error",
		MSG_TYPE_QUEST : "question",
        //默认标题名称
        TITLE_MSG_DEF : "系统提示",
        TITLE_INFO : "系统信息",
        TITLE_WARN : "系统警告",
        TITLE_ERROR : "系统错误",
        TITLE_QUEST : "系统疑问",
		RESULT_MSG : "msg",
		RESULT_MSG_TYPE : "msgType",
		RESULT_VALUE : "value",
		
		
		JQ_SRC : "jqSrc",
		JQ_TYPE : "jqType",
		JQ_TYPE_TBL : "tbl",
		JQ_TYPE_DLG : "dlg",
		JQ_TYPE_FRM : "frm",

        //========================异常信息==================================
        EXCEPTION_ARG_ERROR : "传参错误！",
        EXCEPTION_FUNC_ERROR : "函数有误！",
        
        /** 是否打开按键权限，开发模式的时候可以定义为close */
        OPEN_BTN_PVL : true,
        
        regs : {},
        teRegs : {},
        exps : {},
        pvls : {},
        
        
        
        /**
         * Desc:判断一个对象是否为空。
		 * @since 2013-06-01
         * @param {Object} value
         * @returns {Boolean}
         */
        isEmpty : function(value){
            return (value == undefined || value == null 
            		|| (typeof value == "string" && value.length == 0)
            		|| (value instanceof Array && value.length == 0)
            	);
        },
        /**
         * Desc:判断value是否为true。
		 * @since 2013-06-01
         * @param value
         * @returns {Boolean}
         */
        isT : function(value){
        	return (value == true || value == "true");
        },
        /**
         * Desc:判断value是否为false。
		 * @since 2013-06-01
         * @param value
         * @returns {Boolean}
         */
        isF : function(value){
        	return (value == false || value == "false");
        },
        /**
         * Desc:获取方法对象。
		 * @since 2013-06-01
         * @param func
         * @returns
         */
        getFunc : function(func){
            if(typeof func == "function") return func;
            else if(typeof func == "string"){
                var _func = eval(func);
                if(_func == undefined){
                    throw "Arguments Error! Make sure that the function '" + func + "' exists.";
                };
                return _func;
            }
            throw "Arguments Error! Make sure that the function '" + func + "' exists.";
        },
        /**
         * Desc:如果取值为空，就返回默认值。
		 * @since 2013-06-01
         * @param value
         * @param defValue
         * @returns
         */
        getValByDef : function(value, defValue){
            return $tt.isEmpty(value) ? defValue : value;
        },
        /**
         * Desc:根据map获取默认值。
		 * @since 2013-06-01
         * @param map
         * @param key		目标值的key
         * @param defKey	默认值的key
         * @returns
         */
        getValByDef4Map : function(map, key, defKey){
        	if(map == null) return null;
        	return $tt.getValByDef(map[key], map[defKey]);
        },
        /**
         * Desc:深度复制对象，而非引用。
         * @param src
         * @returns
         */
        clone : function(src){
        	if(src == undefined 
        			|| src == null 
        			|| typeof src == "string"
        			|| typeof src == "function"
        			|| typeof src == "boolean"
        			|| typeof src == "number") return src;
            var objClone = {};
            for(var key in src){
            	if($tt.isEmpty(key)) continue;
                objClone[key] = $tt.clone(src[key]);
            }
            return objClone;
        },
        
		/**
		 * Desc: 提示信息封装，如果以后改用前台的插件，只需要改动方法中的实现，业务代码无需修改。
		 * @since 2013-06-01
		 * @param title
		 * @param msg
		 * @param type
         * @example
         *      <div>alertMsg('你的话费欠费了');                       //单参数情况</div>
         *      <div>alertMsg('你的话费欠费了', '温馨提示');             //两个参数情况</div>
         *      <div>alertMsg('你的话费欠费了', '温馨提示', 'warning');   //三个参数情况</div>
         * @returns {Boolean} false
		 */
		alert : function(msg, title, type){
			var args = arguments;
			//采用easyui的信息提示框
			switch(args.length){
			case 3:
				title = $tt.getDefAlertTitle(title, type);
				$.messager.alert(title,msg,type);
				return false;
			case 2:
				$.messager.alert(title,msg);
				return false;
			case 1:
                //当只有一个参数是，该参数则为提示消息
                $.messager.alert($tt.TITLE_INFO,msg);
                return false;
			default : throw $tt.EXCEPTION_ARG_ERROR;
			}
		},
		/**
		 * Desc:获取默认的标题。
		 * @since 2013-06-01
		 * @param {String} title
		 * @param {String} type
		 * @returns {String}
		 */
		getDefAlertTitle : function(title, type){
			if(!$tt.isEmpty(title))return title;
            switch(type){
                case $tt.MSG_TYPE_INFO 	: return $tt.TITLE_INFO;
                case $tt.MSG_TYPE_WARN 	: return $tt.TITLE_WARN;
                case $tt.MSG_TYPE_ERROR : return $tt.TITLE_ERROR;
                default					: return $tt.TITLE_INFO;
            }
		},
		
		/**
		 * Desc:普通提示信息。
		 * @since 2013-06-01
		 * @param {String} msg
		 * @param {String} title
         * @example
         * 		<div>alertInfo("你好！");					//单个参数</div>
         * 		<div>	显示结果==》标题：系统提示；内容：你好！</div>
         * 		<div>alertInfo("这是标题", "你好！");		//两个参数</div>
         * 		<div>	显示结果==》标题：这是标题；内容：你好！</div>
         * 		<div>alertInfo("你好！", "");				//两个参数</div>
         * 		<div>	显示结果==》标题：系统提示；内容：你好！</div>
         * @returns {Boolean} false
		 */
		alertInfo : function(msg, title){
			return $tt.alert(msg, title, $tt.MSG_TYPE_INFO);
		},
		/**
		 * Desc:警告提示信息。
		 * @since 2013-06-01
		 * @param {String} msg
		 * @param {String} title
         * @example
         * 		<div>alertWarn("你的手机掉了！");					//单个参数</div>
         * 		<div>	显示结果==》标题：系统提示；内容：你的手机掉了！</div>
         * 		<div>alertWarn("你的手机掉了！", "这是标题");			//两个参数</div>
         * 		<div>	显示结果==》标题：这是标题；内容：你的手机掉了！</div>
         * 		<div>alertWarn("你的手机掉了！", "");				//两个参数</div>
         * 		<div>	显示结果==》标题：系统提示；内容：你的手机掉了！</div>
         * @returns {Boolean} false
		 */
		alertWarn : function(msg, title){
			return $tt.alert(msg, title, $tt.MSG_TYPE_WARN);
		},
		/**
		 * Desc:错误提示信息。
		 * @since 2013-06-01
		 * @param {String} msg
		 * @param {String} title
         * @example
         * 		<div>alertErr("你犯了严重的错误！");					//单个参数</div>
         * 		<div>	显示结果==》标题：系统提示；内容：你犯了严重的错误！</div>
         * 		<div>alertErr("你犯了严重的错误！", "这是标题");		//两个参数</div>
         * 		<div>	显示结果==》标题：这是标题；内容：你犯了严重的错误！</div>
         * 		<div>alertErr("你犯了严重的错误！", "");				//两个参数</div>
         * 		<div>	显示结果==》标题：系统提示；内容：你犯了严重的错误！</div>
         * @returns {Boolean} false
		 */
		alertErr : function(msg, title){
			return $tt.alert(msg, title, $tt.MSG_TYPE_ERROR);
		},
		
		/**
		 * Desc:显示调用完服务器端方法之后返回的提示信息。
		 * @since 2013-06-01
		 * @param ctrlResult
		 * @param callback
         * @returns {Boolean} false
		 */
		alertCtrl : function(ctrlResult){
			if($tt.isEmpty(ctrlResult)
					|| $tt.isEmpty(ctrlResult[$tt.RESULT_MSG])
					) return false;
            return $tt.alert(ctrlResult[$tt.RESULT_MSG], null, ctrlResult[$tt.RESULT_MSG_TYPE]);
		},
		/**
		 * Desc:confirm接口。
		 * @since 2013-06-01
		 * @param msg
		 * @param title
		 * @param callback
		 */
		confirm : function(msg, title, callback){
			var args = arguments;
			var tl = "";
			var func = null;
			//采用easyui的信息提示框
			switch(args.length){
			case 3:
				tl = $tt.getDefAlertTitle(args[1], $tt.MSG_TYPE_QUEST);
				func = args[2];
				break;
			case 2:
				tl = $tt.getDefAlertTitle(null, $tt.MSG_TYPE_QUEST);
				func = args[1];
				break;
			default : throw $tt.EXCEPTION_ARG_ERROR;
			}
			$.messager.confirm(tl, msg, function(r){  
			    if (r){  
			    	if(!$tt.isEmpty(func)) $tt.getFunc(func)();
			    }
			}); 
		},
		
		/**
		 * Desc:通过ajax异步调用远端服务器方法。
         * 		该方法封装的格式：
         * 			<div>dataType--->json;</div>
         * 			<div>type------->post;</div>
		 * @since 2013-06-01
		 * @param {String} action		url
		 * @param {String} data			传递参数
		 * @param {Function} callback	回调函数，可以为空
		 * @param {Boolean} silent		为true是将不显示提示信息，默认为false
		 */
		callAjax : function(action, data, callback, silent){
			$.post(action, data, function(ctrlResult) {
				if(!$tt.isT(silent)) $tt.alertCtrl(ctrlResult);
				if(!$tt.isEmpty(callback)) $tt.getFunc(callback)(ctrlResult);
			}, "json");
		},
		/**
		 * Desc:通过ajax同步步调用远端服务器方法。
         * 		该方法封装的格式：
         * 			<div>dataType--->json;</div>
         * 			<div>type------->post;</div>
		 * @since 2013-06-01
		 * @param {String} action		url
		 * @param {String} data			传递参数
		 * 
		 * @returns {Object}
		 */
		callAjaxSync : function(action, data){
            var resultData = {};
            var strData = $tt.isEmpty(data) ? "" : $.param(data);
            strData = decodeURIComponent(strData);
            $.ajax( {
                url         : action,
                data        : strData,
                async       : false,
                dataType    : 'json',
                type        : 'post',
                success : function(ctrlResult) {
                    //closeProcessBar();
                    resultData = ctrlResult;
                }
            });
            return resultData;
		},
		/**
		 * Desc:表单提交的异步形式。
		 * @since 2013-06-01
		 * @param {Jquery} jqFrm 				表单对象
		 * @param {String} action 				url
		 * @param {String || Function} callback 回调函数
		 */
		submitFrm : function(jqFrm, action, callback){
			action = $tt.isEmpty(action) ? jqFrm.attr( 'action' ) : action;
			$tt.callAjax(action, jqFrm.serialize(), callback);
		},
		
		isCtrlInfo : function(ctrlResult){
			return !(ctrlResult == null
					|| ctrlResult[$tt.RESULT_MSG_TYPE] != $tt.MSG_TYPE_INFO);
		},
		/**
		 * Desc:解析attr。格式如下：
		 * 		<div>key1:value1;key2|key3:value2</div>
		 * @since 2013-06-01
		 * @param {JQuery} jqObj		要解析的jquery对象
		 * @param {String} attrName		要解析的属性名字
		 * @param {Object} tmpArgs		模板参数
		 * @param {Object} defResult	默认值
		 * @returns {Object}
		 */
		parseAttr : function(jqObj, attrName, tmpArgs, defResult){
			defResult = $tt.isEmpty(defResult) ? {} : defResult;
			for(var key in defResult){
				if($tt.isEmpty(key)) continue;
				
			}
			if($tt.isEmpty(defResult._seq)) defResult._seq = [];
			var attrVal = jqObj.attr(attrName);
			if($tt.isEmpty(attrVal)) return defResult;
			attrVal = attrVal.trim();
			if($tt.isEmpty(attrVal)) return defResult;
			if(tmpArgs != null){
				$.each(tmpArgs, function(k, v){
					attrVal = attrVal.replaceAll("\\$\\[" + k + "\\]", v);
				});
			}
			var result = {};
			var seq = [];
			//先根据";"号拆分
			var arr1 = attrVal.split(";");
			for(var i = 0; i < arr1.length; ++i){
				if($tt.isEmpty(arr1[i])) continue;
				arr1[i] = arr1[i].trim();
				if($tt.isEmpty(arr1[i])) continue;
				//接着根据":"号拆分
				var arr2 = arr1[i].split(":");
				if(arr2.length == 1){
					arr2[0] = arr2[0].trim();
					if($tt.isEmpty(arr2[0])) continue;
					result[arr2[0]] = arr2[0];
					seq.push(arr2[0]);
				}else if(arr2.length == 2){
					arr2[0] = arr2[0].trim();
					arr2[1] = arr2[1].trim();
					if($tt.isEmpty(arr2[0])) continue;
					//根据"|"来解析key
					var arr3 = arr2[0].split("|");
					for(var j = 0; j < arr3.length; ++j){
						if($tt.isEmpty(arr3[j])) continue;
						arr3[j] = arr3[j].trim();
						if($tt.isEmpty(arr3[j])) continue;
						result[arr3[j]] = arr2[1];
						seq.push(arr3[j]);
					}
				}else {
					
				}
			}
			result._seq = seq;
			result = $.extend({}, defResult, result);
			result = $.extend({}, defResult, result);
			return result;
		},
		
		/**
		 * Desc:将数据的value其转换为数组。
		 * 		<dvi>数据格式为：{k : 'v1,v2,v3'}。</div>
		 * 		<div>解析结果为：{k : ['v1', 'v2', 'v3']}</div>
		 * @since 2013-06-01
		 * @param {Object} data
		 * @returns {Object}
		 */
		parseValToArr : function(data){
			var result = {};
			for(var key in data){
				if($tt.isEmpty(key) || key == "_seq") continue;
				var value = data[key];
				if($tt.isEmpty(value)) result[key] = [];
				else result[key] = value.split(",");
			}
			return result;
		},
		/**
		 * Desc:解析参数表达式。表达式格式为：*&k1=v1&k2&k3='asdf'
		 * @param argsExp
		 * @returns {Object}
		 */
		parseArgs : function(argsExp){
			var args = {};
			if($tt.isEmpty(argsExp)) return args;
			var argStrArr = argsExp.split("&");
			for(var i = 0, l = argStrArr.length; i < l; ++i){
				var argStr = argStrArr[i].trim();
				if($tt.isEmpty(argStr)) continue;
				var arr = argStr.split("=");
				if(arr.length == 1) args[arr[0].trim()] = arr[0].trim();
				else if(arr.length == 2) args[arr[0].trim()] = arr[1].trim();
			}
			return args;
		},
		/**
		 * Desc:根据参数映射和传递的data生成参数结果。
		 * @param argMap
		 * @param data
		 */
		genArgs : function(argMap, data){
			var args = {};
			if(!$tt.isEmpty(argMap["*"]) && data != null){
				$.each(data, function(k, v){
					args[k] = v;
				});
			}
			
			$.each(argMap, function(k, v){
				if(k == "_seq" || k == "*") return true;
				if($tt.isConst(v)) args[k] = v.substring(1, v.length - 1);
				else if(data != null) args[k] = $tt.getValByDef(data[v], v);
			});
			return args;
		},
		/**
		 * Desc:改变jquery对象的属性。
		 * 		<div>如果要转换id且value中以"#"开头，则去掉"#"号。</div>
		 * 		<div>如果要转换class且value中以"."开头，则去掉"."。</div>
		 * @since 2013-06-01
		 * @param jqObj		目标jquery对象
		 * @param map		改变的内容，key为标签的属性
		 */
		chgAttr : function(jqObj, map){
			$.each(map, function(k, v){
				if($tt.isEmpty(k) || v == null) return true;
				if((typeof v == "string") || (v instanceof Number)) {
					if(k == "id" && v.indexOf("#") == 0) jqObj.attr(k, v.substring(1));
					else if(k == "class" && v.indexOf(".") == 0) jqObj.attr(k, v.substring(1));
					else jqObj.attr(k, v);
				}
			});
		},
		
		/**
		 * Desc:根据value获取数组中value与其相同的text。
		 * 		用于easyui的datagrid中combobox类型的formatter。
		 * @since 2013-06-01
		 * @param value
		 * @param dataArr
		 * @returns
		 */
		fmtSel : function(value, dataArr){
			if($tt.isEmpty(value)) return value;
			if($tt.isEmpty(dataArr)) return value;
			for(var i = 0; i < dataArr.length; ++i){
				var val = dataArr[i].value;
				if(val == value) return dataArr[i].text;
			}
			return value;
		},
		
		/**
		 * Desc:插件初始化方法。
		 * @since 2013-06-01
		 * @param {String} jqType		插件类型
		 * @param {Function} clazz		插件类
		 */
		initPlugin : function(jqType, clazz){
			$tt.regs[jqType] = clazz;;
			$.fn[jqType] = function(){
				var args = arguments;
				return this.each(function(){
					var jqObj = $(this);
					var oldJqType = jqObj.data("jqType");
					$tt.isEmpty(oldJqType) && jqObj.data("jqType", jqType);
					switch(args.length){
					case 0 : //无参情况
						var reg = $tt.regs[jqType];
						jqObj.data("regObj", new reg(jqObj, null));
						return jqObj;;
					case 1 : //单个参数
						if(typeof args[0] == "string"){//如果为String类型则调用其对应的注册对象的方法
							var regObj = jqObj.data("regObj");
							if(regObj == null){
								var reg = $tt.regs[jqType];
								regObj = new reg(jqObj, null);
								jqObj.data("regObj", regObj);
							}
							return regObj[args[0]]();
						}else{//否则开始进行插件对象注册
							var reg = $tt.regs[jqType];
							jqObj.data("regObj", new reg(jqObj, args[0]));
							return jqObj;
						}
					case 2 ://两个参数时，第一个参数为调用的方法，第二为传递的参数
						var regObj = jqObj.data("regObj");
						return regObj[args[0]](args[1]);
					}
				});
			};
		},
		
		/**
		 * Desc:判断是否为常量。常量格式为被单引号或者双引号包含。
		 * @since 2013-06-01
		 * @param {String} value
		 * @returns {Boolean}
		 */
		isConst : function(value){
			return /^'[\s\S]*'$/.test(value) || /^"[\s\S]*"$/.test(value);
		},
		

		/**
		 * Desc:校验是否有权限
		 * @since 2013-06-01
		 * @param {String} pvl	权限码
		 * @returns
		 */
		hasPvl : function(pvl){
			if($tt.OPEN_BTN_PVL == false) return true;
			return $tt.isEmpty(pvl) || !$tt.isEmpty($tt.pvls[pvl]);
		},
};

//+++++++++++++++++++++++++++++ajax全局设置++++++++++++++++++++
$(function(){
	$.ajaxSetup( {
		timeout : 60000,
		type : "post",
		cache : false
	});

	//全局AJAX.error事件
	$(this).ajaxError(function(event, request, settings, exception) {
		if (exception == 'timeout') {
			$tt.alertErr("连接服务器超时,请重试!");
		} else if (exception == 'abort') {
			$tt.alertErr("请求被意外终止,请重试!");
		} else {
			$tt.alertErr('系统错误,返回的信息[' + exception + ']');
		}
	});
	
	$(this).ajaxComplete(function(event,request,options){
		try{
//			closeProcessBar();
			var data = eval("(" + request.responseText + ")");
			alertResultMsg(data);
		}
		catch(e){
			
		}
	});
	
});

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
!function ($) {
	if(String.prototype.replaceAll == undefined){
		/**
		 * Desc:String的全部替换方法
		 * @since 2013-06-01
		 * @param {String|RegExp} s1	替换规则
		 * @param s2					替换内容
		 * @returns
		 */
		String.prototype.replaceAll = function(s1,s2){
			if(s1 instanceof RegExp){
				return this.replace(s1, s2);
			}
			return this.replace(new RegExp(s1,"gm"),s2);    
		};
	}
	if(String.prototype.trim == undefined){
		/**
		 * Desc:String的trim方法
		 * @since 2013-06-01
		 * @returns {String}
		 */
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
	}
	String.prototype.startWith=function(str){
		return !$tt.isEmpty(str) 
			&& str.length < this.length
			&& this.substr(0,str.length)==str;
	};
	String.prototype.endWith=function(str){
		return !$tt.isEmpty(str) 
			&& str.length < this.length
			&& this.substring(this.length-str.length) == str;
	};
}(window.jQuery);
