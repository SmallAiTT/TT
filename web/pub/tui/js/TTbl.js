//++++++++++++++++++++++++++++TTbl starts++++++++++++++++++++++++++++++++++++++++
//TODO TTbl
function TTblOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
	/** 按键信息解析器 */
	this.btnParser = new TBtnParser();
	/** 验证信息解析器 */
	this.validParser = new TValidParser();
	/** 查询信息解析器 */
	this.qryParser = new TQryParser();
}

/**
 * Desc:列表类定义。
 * @param {JQuery} jqObj
 * @param {TTblOpts} opts
 */
function TTbl(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TTblOpts();
	/** 按键的options数组 */
	this.btnSglArr = [];
	/** 验证信息映射，key为oper */
	this.validMap = {};
	/** 按键信息默认值 */
	this.btnDef = {
			txts : {c:"新增",u:"修改",r:"浏览",d:"删除"},
			icons : {c:"icon-add",u:"icon-edit",r:"icon-search",d:"icon-remove"},
			opers : {q:"c,u,r,d"},
			handlers : {"*" : handler},
			oks : {"*" : ok}
	};
	/** 验证信息默认值 */
	this.validDefs = {
			rels : {c:0, "*" : "1"},
			msgs : {u:"请选择需要修改的数据！",r:"请选择需要浏览的数据！",d:"请选择需要删除的数据！", "*" : "请选择需要操作的数据！"},
			confirms : {d:"确定删除该数据？"},
			custs : {},
	};
	this.isQryFirstTime = true;
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
	 * Desc:成功时回调。
	 * @since 2013-06-12
	 * @param {TReq} req
	 * @param {Object} returnData
	 * @param {CtrlResult} ctrlResult
	 */
	function ok(req, returnData, ctrlResult){
		_t.jqObj.datagrid("load");
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
			//解析按键信息
			_t.opts.btnParser.parse(_t.jqObj, _t.tmpArgs, _t.btnDef);
			//解析验证信息
			_t.opts.validParser.parse(_t.jqObj, _t.tmpArgs, _t.validDefs);
			//解析查询信息
			_t.opts.qryParser.parse(_t.jqObj, _t.tmpArgs);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);
		_t.btnSglArr = _t.opts.btnParser.getSglArr();
		_t.validMap = _t.opts.validParser.getSglMap();

		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "query");//设置默认调用方法
		var defOper = $tt.getValByDef(_t.jqObj.attr($tui.DEF_OPER), _t.tmpArgs.defOper);
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
	function onBeforeLoad(param){
		if(_t.isQryFirstTime){//为了解决点到下一页之后，重新点击查询，page不变的问题
			_t.isQryFirstTime = false;
			return false;
		}
		param["_qry.page"] = $tt.getValByDef(param.page, 0);
		param["_qry.rows"] = $tt.getValByDef(param.rows, 0);
		var sort = param.sort;
		var order = param.order;
		if(!$tt.isEmpty(order) && !$tt.isEmpty(order))
			param["_qry.sort"] = sort + " " + order;
		return true;
	}
	function initQry(req){
		var qrySgl = _t.opts.qryParser.getSgl(req.oper);
		$.each(qrySgl.data, function(k, v){
			if($tt.isEmpty(k)) return true;
			req.data["_qry.data." + k] = $tt.isConst(v) ? v.substring(1, v.length - 1) : req.data[v];
		});
		$.each(qrySgl.ignore, function(k, v){
			if(!$tt.isEmpty(k)) req.data["_qry.ignore." + k] = v;
		});
		$.each(qrySgl.replaceData, function(k, v){
			if(!$tt.isEmpty(k)) req.data["_qry.replaceData." + k] = v;
		});
		req.data["_qry.cndExp"] = qrySgl.cndExp;
		req.data["_qry.paraExp"] = qrySgl.paraExp;
		req.data["_qry.varExp"] = qrySgl.varExp;
		req.data["_qry.sort"] = qrySgl.sort;
		req.data["_qry.defSort"] = qrySgl.defSort;
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
		initQry(req);
		var toolBar = $tui.getToolBar(_t.jqObj, _t.opts.btnParser.info.opers[req.oper], 
				_t.btnSglArr, _t.validMap, true);
		var pubSgl = _t.opts.pubParser.getSgl(req.oper);
		var opts = {};
		opts.url = $tt.getValByDef(pubSgl.act, "");
		opts.queryParams = req.data;
		opts.title = $tt.getValByDef(pubSgl.title, "");
		opts.toolbar = toolBar;
		opts.loadFilter = loadFilter;
		opts.onBeforeLoad = onBeforeLoad;
		_t.jqObj.datagrid(opts);
		_t.jqObj.datagrid("load");
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
