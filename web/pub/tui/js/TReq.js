
//TODO TReq
/**
 * Desc:请求的封装。这个是所有调用内容的核心，所有的调用内容的传参统一为其实例对象。
 * @param {JQuery} jqOwner		请求所有者(tbl/dlg/frm/btn等)
 * @param {String} oper			操作
 * @param {String} extStr		表达式
 * @param {TValidSgl} validSgl	验证信息
 */
function TReq(jqOwner, oper, extStr, validSgl){
	var _t = this;
	/** 操作类型 */
	this.oper = oper;
	/** 该字段用于操作的转换，在整个req链条中，请在当前对象调用完req后，马上清空 */
	this.operChg = null;
	/** 表达式信息 */
	this.exp = new TExp(extStr);
	/** 验证信息 */
	this.validSgl = $tt.getValByDef(validSgl, new TValidSgl());
	/** 按键信息 */
	this.btnSgl = new TBtnSgl();
	/** 数据 */
	this.data = {};
	/** 数据来源的jquery对象 */
	this.jqData = "";
	/** 请求的拥有者（非按键键级别，而是tbl、dlg等） */
	this.jqOwner = jqOwner;
	/** 属性 */
	this.attrs = {};
	/** 上一个请求 */
	this.preReq = null;
	/**
	 * Desc:回调函数。
	 * @since 2013-06-04
	 * @param {TRes} res		响应
	 */
	this.callback = function(res){
		var func = res.success ? _t.ok : _t.fail;
		if(!$tt.isEmpty(func)) $tt.getFunc(func)(_t, res);
		return $tui.doReqBack(_t.preReq, res);
	};
	/** 成功时的回调函数 */
	this.ok = null;
	/** 失败时的回调函数 */
	this.fail = null;
	
}
/**
 * Desc:响应类。
 * @since 2013-06-24
 * @param {Boolean} success		是否执行成功
 * @param {Object} data			返回数据
 * @param {Object} ctrlResult	后台返回结果
 */
function TRes(success, data, ctrlResult){
	/** 响应是否成功 */
	this.success = success;
	/** 响应的数据 */
	this.data = data || {};
	/** 后台返回结果 */
	this.ctrlResult = ctrlResult;
}