//++++++++++++++++++++++++++++TTbl starts++++++++++++++++++++++++++++++++++++++++
//TODO TTbl
function TEleOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
}

/**
 * Desc:列表类定义。
 * @param {JQuery} jqObj
 * @param {TEleOpts} opts
 */
function TTbl(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TEleOpts();
	this.preReq = null;
	this.tmpArgs = null;
	
	/**
	 * Desc:初始化。主要负责解析标签属性等工作，注意，这里还没使其成为datagrid，为的是避免多次调用后台的url。
	 * @since 2013-06-12
	 */
	function init(){
		var jqTmp = jqObj.parents("." + $tui.CSS_TMP + ":first");
		_t.tmpArgs = $tt.getValByDef(jqTmp.data($tui.TMP_ARGS), {});
		if(opts != null) {
			_t.opts = opts;
		}else{
			//解析公共信息
			_t.opts.pubParser.parse(_t.jqObj, _t.tmpArgs);
			//解析按键信息
			_t.opts.btnParser.parse(_t.jqObj, _t.tmpArgs, _t.btnDef);
			//解析验证信息
			_t.opts.validParser.parse(_t.jqObj, _t.tmpArgs, _t.validDefs);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);
		_t.btnSglArr = _t.opts.btnParser.getSglArr();
		_t.validMap = _t.opts.validParser.getSglMap();

		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "query");//设置默认调用方法
		var defOper = _t.jqObj.attr($tui.DEF_OPER);
		if(!$tt.isEmpty(defOper)) _t.query({oper : defOper});
	}

	/**
	 * Desc:获取到数据集之后的过滤器。
	 * @since 2013-06-12
	 * @param {Object} data
	 */
	function loadFilter(data){
    	var defResult = {total : 0, rows : []};
        if (data[$tt.RESULT_MSG_TYPE] == $tt.MSG_TYPE_INFO) {
        	//注意，如果加了loadFilter，那么loadFilter返回值一定是分页数据的格式，而不能是单纯的数据列表
            return data.value;
        }else{
        	$tt.alertCtrl(data);
        	return defResult;
        }
    }
	/**
	 * Desc:加载数据。
	 * @since 2013-06-12
	 * @param {TReq} req
	 */
	this.query = function(req){
		_t.preReq = req;//记得先加上
		var opts = {};
		var toolBar = $tui.getToolBar(_t.jqObj, _t.opts.btnParser.info.opers[req.oper], 
				_t.btnSglArr, _t.validMap, true);
		opts.queryParams = req.data;
		var pubSgl = _t.opts.pubParser.getSgl(req.oper);
		opts.url = $tt.getValByDef(pubSgl.act, "");
		opts.title = $tt.getValByDef(pubSgl.title, "");
		opts.toolbar = toolBar;
		opts.loadFilter = loadFilter;
		_t.jqObj.datagrid(opts);
		//按键是否可用
		var jqToolbar = _t.jqObj.parent().prev("div.datagrid-toolbar");
		jqToolbar.find("a.l-btn").each(function(i){
			if(toolBar[i] == "-") return true;
			if(toolBar[i].sgl == null) return true;
			if($tt.isF(toolBar[i].sgl.able)) $(this).linkbutton("disable");
		});
	};
	
	init();
}

//++++++++++++++++++++++++++++TTbl ends++++++++++++++++++++++++++++++++++++++++
