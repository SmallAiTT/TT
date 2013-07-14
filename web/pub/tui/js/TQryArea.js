/**
 * Desc:查询区的options
 */
function TQryAreaOpts(){
	this.pubParser = new TPubParser();
	this.lnkParser = new TLnkParser();
}

/**
 * Desc:查询块。
 * @since 2013-06-12
 * @param {JQuery} jqObj
 * @param {TQryAreaOpts} opts
 */
function TQryArea(jqObj, opts){
	var _t = this;
	this.jqObj = jqObj;
	this.opts = new TQryAreaOpts();
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
			_t.opts.lnkParser.parse(_t.jqObj, {});
			if(_t.opts.lnkParser.info.opers._seq.length > 0) _t.jqObj.data("lnk", true);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);
		initHtml();//初始化html内容
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
		jqObj.addClass("easyui-accordion");
		jqObj.find("form:first").each(function(){
			var jqFrm = $(this);
			var jqBtns = $("<div class='" + $tui.CSS_QRY_BTNS + "'></div>");
			jqFrm.append(jqBtns);
			_t.okBtn = $('<a class="tt-btn tt-btn-qry" btnTxts="*:查询" btnExps="*:qry->?*"></a>');
			_t.resetBtn = $('<a class="tt-btn tt-btn-reset" defOper="reset" btnExps="reset:reset->?*"></a>');
			jqBtns.append(_t.okBtn);
			jqBtns.append(_t.resetBtn);
			jqBtns.find("a.tt-btn").tBtn();
		});
		jqObj.hide();//默认隐藏
	}
	/**
	 * Desc:查询。
	 * @param {TReq} req
	 */
	this.query = function(req){
		var jqObj = _t.jqObj;
		var lnkSgl = _t.opts.lnkParser.getSgl(req.oper);
		var pubSgl = _t.opts.pubParser.getSgl(req.oper);
		if($tt.isEmpty(lnkSgl.oper)) {
			jqObj.hide();
			jqObj.removeAttr("lnkState");
			return true;
		}
		jqObj.attr("lnkState", "active");
		jqObj.find("div:first").addClass("tt-qry-title").attr("title", pubSgl.title);
		jqObj.show();
		_t.preReq = req;
		var chg = $tt.getValByDef(lnkSgl.chg, null);
		var newReq = $tui.cloneReq(req, chg);
		var okBtn = jqObj.find("." + $tui.CSS_BTN_QRY + ":first");
		okBtn.tBtn("show", newReq);
		okBtn.click();
		jqObj.accordion();
	};
	
	init();
}
