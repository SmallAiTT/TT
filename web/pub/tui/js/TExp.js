
//TODO TExp
/**
 * Desc:表达式类。
 * @since 2013-06-04
 * @param {String} expStr	表达式
 */
function TExp(expStr){
	var _t = this;
	/** 表达式内容 expName->target/func*oper?key1=val1&key2*/
	this.exp = "";
	/** 表达式名称 expName*/
	this.name = "";
	/** 操作类型 */
	this.oper = "";
	/** 目标 target/toDo*/
	this.url = null;
	/** 参数（可选） key1 : "val1"*/
	this.args = {};
	
	/**
	 * Desc:根据表达式创建TExp实例。
	 * @since 2013-06-04
	 * @param {String} expStr	表达式
	 */
	function initExp(expStr){
		if($tt.isEmpty(expStr)) return;
		_t.exp = expStr;
		var i1 = expStr.indexOf("->");//根据->分割出exp名称
		if(i1 >= 0){
			_t.name = expStr.substring(0, i1);//名称
			if(i1+2 == expStr.length) return;
			var temp1 = expStr.substring(i1+2);
			var arr1 = temp1.split("?");//?后面为参数表达式
			_t.url = arr1[0];//url
			if(arr1.length > 1) _t.args = $tt.parseArgs(arr1[1]);//参数
		}else{//无->模式默认为name
			_t.name = "";
			var arr1 = expStr.split("?");//?后面为参数表达式
			_t.url = arr1[0];//url
			if(arr1.length > 1) _t.args = $tt.parseArgs(arr1[1]);//参数
		}
	}
	
	initExp(expStr);
}


//++++++++++++++++++++++++++++Exps Starts++++++++++++++++++++++++++++++++++++++++
//TODO Exps
/**
* Desc:格式为：func->funcName。
* @since 2013-06-12
*/
function FuncExp(){
	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		var exp = req.exp;
		return $tt.getFunc(exp.url)(req);
	};
}
/**
* Desc:格式为：jq->#target/func?k1=v1&k2&k3='1'&*
* @since 2013-06-12
*/
function JqExp(){
	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		var exp = req.exp;
		if($tt.isEmpty(exp.url)) return false;
		var arr = exp.url.split("/");
		if(arr.length == 0) return false;
		var target = arr[0];
		var jqTarget = $(target);
		if(jqTarget.length == 0) return false;
		var funcName = arr.length > 1 ? arr[1] : jqTarget.data($tui.DEF_FUNC);
		return jqTarget[jqTarget.data($tui.JQ_TYPE)](funcName, req);
	};
}
/**
* Desc:格式为：qry->#target?k1=v1&k2&*
* @since 2013-06-12
*/
function QryExp(){

	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		var jqTbl = req.jqOwner.find("." + $tui.CSS_TBL + ":first");
		jqTbl.tTbl("query", req);
	};
}

/**
* Desc:格式为：submit->#target?k1=v1&k2&*
* @since 2013-06-12
*/
function SubmitExp(){
	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		if(req.jqData == null) return;
		req.jqData.tFrm("submit", req);
	};
}
/**
* Desc:格式为：act->url?k1=v1&k2&*
* @since 2013-06-12
*/
function ActExp(){
	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		$tt.callAjax(req.exp.url, req.data, function(ctrlResult){
			return $tui.doReqBak4Ctrl(req, ctrlResult);
		});
	};
}
/**
* Desc:格式为：reset->?*
* @since 2013-06-12
*/
function ResetExp(){
	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		req.jqData.form("clear");
	};
}
/**
* Desc:格式为：sel->?*
* @since 2013-06-12
*/
function SelExp(){
	/**
	 * Desc:执行请求。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.exec = function(req){
		var res = new TRes(true, req.data);
		$tui.doReqBack(req, res);
	};
}
//++++++++++++++++++++++++++++Exps Ends++++++++++++++++++++++++++++++++++++++++