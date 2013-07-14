//++++++++++++++++++++++++++++TTbl starts++++++++++++++++++++++++++++++++++++++++
//TODO TTbl
function TTreepts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
	/** 查询信息解析器 */
	this.qryParser = new TQryParser();
}

/**
 * Desc:列表类定义。
 * @param {JQuery} jqObj
 * @param {TTreepts} opts
 */
function TTree(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TTreepts();
	this.preReq = null;
	
	this.tmpArgs = null;
	
	/**
	 * Desc:按键的触发事件。
	 * @since 2013-06-12
	 * @param {TReq} req
	 */
	function handler(req){
		req.preReq = _t.preReq;
		req.jqData = req.jqOwner;//设置jqData为当前tbl
		$tui.callReq(req);
	}

	/**
	 * Desc:初始化。主要负责解析标签属性等工作，注意，这里还没使其成为datagrid，为的是避免多次调用后台的url。
	 * @since 2013-06-12
	 */
	function init(){
		var jqTmp = jqObj.parents("." + $tui.CSS_TMP + ":first");
		_t.tmpArgs = $tt.getValByDef(jqTmp.data($tui.TMP_ARGS), {});
		var dataOpts = jqTmp.attr("data-options");
		if(!$tt.isEmpty(dataOpts)) _t.jqObj.attr("data-options", dataOpts);
		if(opts != null) {
			_t.opts = opts;
		}else{
			//解析公共信息
			_t.opts.pubParser.parse(_t.jqObj, _t.tmpArgs);
			//解析查询信息
			_t.opts.qryParser.parse(_t.jqObj, _t.tmpArgs);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);

		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "query");//设置默认调用方法
		var defOper = $tt.getValByDef(_t.jqObj.attr($tui.DEF_OPER), _t.tmpArgs.defOper);
		if(!$tt.isEmpty(defOper)) _t.query({oper : defOper});
	}

	/**
	 * Desc:加载数据。
	 * @since 2013-06-12
	 * @param {TReq} req
	 */
	this.query = function(req){
		_t.isQryFirstTime = true;
		_t.preReq = req;//记得先加上
		req.data = req.data == null ? {} : req.data;
		var qrySgl = _t.opts.qryParser.getSgl(req.oper);
		var pubSgl = _t.opts.pubParser.getSgl(req.oper);
		qrySgl.initQry(req);
		req.data["_qry.page"] = 0;
		req.data["_qry.rows"] = 0;

		var url = $tt.getValByDef(pubSgl.act, "");
		$tt.callAjax(url, req.data, function(ctrlResult){
			if($tt.isCtrlInfo(ctrlResult)){
				_t.jqObj.tree({
					data : ctrlResult.value
				});
			}
		});
	};
	
	init();
}

//++++++++++++++++++++++++++++TTbl ends++++++++++++++++++++++++++++++++++++++++
