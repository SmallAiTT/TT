
//++++++++++++++++++++++++++++TTmp starts++++++++++++++++++++++++++++++++++++++++
//TODO TTmp
/**
 * Desc:模板的options
 * @since 2013-06-12
 */
function TTmpOpts(){
	this.defOper;
	this.lnkParser = new TLnkParser();
}
/**
 * Desc:模板类。
 * @since 2013-06-12
 * @param jqObj
 * @param {TTmpOpts} opts
 * @returns
 */
function TTmp(jqObj, opts){
	var _t = this;
	this.jqObj = jqObj;
	this.opts = new TTmpOpts();
	function init(){
		if(opts != null) {
			_t.opts = opts;
		}else{
			//解析公共信息
			_t.opts.lnkParser.parse(_t.jqObj, {});
			if(_t.opts.lnkParser.info.opers._seq.length > 0) _t.jqObj.data("lnk", true);
		}
		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "show");//设置默认调用方法
	}
	/**
	 * @param {TReq} req
	 */
	this.show = function(req){
		var lnkSgl = _t.opts.lnkParser.getSgl(req.oper);
		if($tt.isEmpty(lnkSgl.oper)){
			_t.jqObj.removeAttr("lnkState");
			_t.jqObj.hide();
		}else{
			_t.jqObj.attr("lnkState", "active");
			_t.jqObj.show();
			var chg = $tt.getValByDef(lnkSgl.chg, null);
			var newReq = $tui.cloneReq(req, chg);
			var jqFrm = _t.jqObj.find("." + $tui.CSS_FRM + ":first");
			if(jqFrm.length == 1) jqFrm.tFrm("load", newReq);
			else _t.jqObj.find("form").form("load", newReq.data);
			//datagrid
			var jqQry = _t.jqObj.find("." + $tui.CSS_QRY + ":first");
			if(jqQry.length == 1) jqQry.tQry("query", newReq);
			else _t.jqObj.find("." + $tui.CSS_TBL + ":first").tTbl("query", newReq);
			//tree
			var jqTree = _t.jqObj.find("." + $tui.CSS_TREE + ":first");
			jqTree.tTree("query", newReq);
		}
	};
	init();
}
//++++++++++++++++++++++++++++TTmp ends++++++++++++++++++++++++++++++++++++++++

