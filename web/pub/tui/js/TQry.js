//++++++++++++++++++++++++++++TQry starts++++++++++++++++++++++++++++++++++++++++
/**
 * Desc:查询块的options
 * @since 2013-06-12
 */
function TQryOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
}
/**
 * Desc:查询块。
 * @since 2013-06-12
 * @param {JQuery} jqObj
 * @param {TQryOpts} opts
 */
function TQry(jqObj, opts){
	var _t = this;
	this.jqObj = jqObj;
	this.opts = new TQryOpts();
	this.preReq = null;
	this.okBtn = null;
	this.resetBtn = null;
	
	function init(){
		var jqTmp = jqObj.parents("." + $tui.CSS_TMP + ":first");
		_t.tmpArgs = $tt.getValByDef(jqTmp.data($tui.TMP_ARGS), {});
		if(opts != null) {
			_t.opts = opts;
		}else{
			//解析公共信息
			_t.opts.pubParser.parse(_t.jqObj, _t.tmpArgs);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);
		//initHtml();//初始化html内容
		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "query");//设置默认调用方法
		var defOper = _t.jqObj.attr($tui.DEF_OPER);
		if(!$tt.isEmpty(defOper)) _t.query({oper : defOper});
	}
	/**
	 * Desc:初始化html内容。
	 */
	function initHtml(){
		var jqObj = _t.jqObj;
		jqObj.find("form").each(function(){
			var jqFrm = $(this);
			var jqBtns = $("<div class='" + $tui.CSS_QRY_BTNS + "'></div>");
			jqFrm.append(jqBtns);
			_t.okBtn = $('<a class="tt-btn tt-btn-qry" btnTxts="查询" btnExps="*:qry->?*"></a>');
			_t.resetBtn = $('<a class="tt-btn tt-btn-reset" defOper="reset" btnExps="reset:reset->?*"></a>');
			jqBtns.append(_t.okBtn);
			jqBtns.append(_t.resetBtn);
			jqBtns.find("a.tt-btn").tBtn();
		});
	}
	/**
	 * Desc:查询。
	 * @param {TReq} req
	 */
	this.query = function(req){
		var jqObj = _t.jqObj;
		jqObj.find("." + $tui.CSS_QRY_AREA).tQryArea("query", req);
		var jqShow = jqObj.find("." + $tui.CSS_QRY_AREA + "[lnkState='active']");
		if(jqShow.length == 0){
			jqObj.find("." + $tui.CSS_TBL + ":first").tTbl("query", req);
		}
	};
	
	init();
}
//++++++++++++++++++++++++++++TQry ends++++++++++++++++++++++++++++++++++++++++
