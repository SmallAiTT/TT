
//++++++++++++++++++++++++++++TDlg starts++++++++++++++++++++++++++++++++++++++++
//TODO TDlg
/**
 * Desc:TDlg的options类定义。
 */
function TDlgOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
	/** 按键信息解析器 */
	this.btnParser = new TBtnParser();
	/** 验证信息解析器 */
	this.validParser = new TValidParser();
}

/**
 * Desc:弹出框定义。
 * @param {JQuery} jqObj
 * @param {TDlgOpts} opts
 */
function TDlg(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TDlgOpts();
	/** 按键的options数组 */
	this.btnSglArr = [];
	/** 验证信息映射，key为oper */
	this.validMap = {};
	/** 按键信息默认值 */
	this.btnDef = {
			txts : {c:"保存",u:"保存",sel:"选择"},
			icons : {c:"icon-save",u:"icon-save",sel:"icon-ok"},
			opers : {c:"c",u:"u",sel:"sel"},
			handlers : {"*" : handler},
			oks : {"*" : ok},
	};
	/** 验证信息默认值 */
	this.validDefs = {
			rels : {"*" : "1"},
			confirms : {},
	};
	
	this.preReq = null;
	
	this.tmpArgs = null;
	
	/**
	 * Desc:按键的触发事件。
	 * @since 2013-06-12
	 * @param {TReq} req
	 */
	function handler(req){
		req.preReq = _t.preReq;
		var expName = req.exp.name;
		var target = _t.opts.btnParser.info.targets[req.oper];
		var jqTmp = req.jqOwner.find("." + $tui.CSS_TMP + "[lnkState='active']:first");
		var jqFrm = jqTmp.find("." + $tui.CSS_FRM + ":first");
		var jqTbl = jqTmp.find("." + $tui.CSS_TBL + ":first");
		var jqTree = jqTmp.find("." + $tui.CSS_TREE + ":first");
		if(!$tt.isEmpty(target)){
			req.jqData = $(target);
		}else if(expName == "submit"){
			req.jqData = jqFrm;
		}else if(expName == "sel"){
			req.jqData = jqTbl;
		}else if(jqFrm.length == 1){
			req.jqData = jqFrm;
		}else if(jqTbl.length == 1){
			req.jqData = jqTbl;
		}else if(jqTree.length == 1){
			req.jqData = jqTree;
		}else{
			req.jqData = _t.jqObj;
		}
		$tui.callReq(req);
	}

	/**
	 * Desc:成功时的回调。
	 * @since 2013-06-12
	 * @param {TReq} req
	 * @param {Tres} res
	 */
	function ok(req, res){
		var oper = req.oper;
		var pubSgl = _t.opts.pubParser.getSgl(oper);
		if(!$tt.isF(pubSgl.close)) _t.jqObj.tDlg("close");
	}
	/**
	 * Desc:初始化。主要负责解析标签属性等工作。
	 * @since 2013-06-12
	 */
	function init(){
		//模板参数获取
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
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);//改变属性
		_t.btnSglArr = _t.opts.btnParser.getSglArr();
		_t.validMap = _t.opts.validParser.getSglMap();

		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "open");//设置默认调用方法
		var defOper = _t.jqObj.attr($tui.DEF_OPER);
		if(!$tt.isEmpty(defOper)) _t.open({oper : defOper});
		else {
			_t.jqObj.dialog({closed : true});
		}
	}

	/**
	 * 获取到easyui所需的options。
	 * @since 2013-06-12
	 * @param {TReq} req
	 */
	function getEOpts(req){
		var opts = {};
		var btns = $tui.getToolBar(_t.jqObj, _t.opts.btnParser.info.opers[req.oper], 
				_t.btnSglArr, _t.validMap);
		btns.push({
			text : "关闭",
			handler : function(){
				_t.jqObj.tDlg("close");
			}
		});
		opts.title = $tt.getValByDef(_t.opts.pubParser.info.titles[req.oper], req.oper);
		opts.buttons = btns;
		opts.closed = false;
		return opts;
	}
	/**
	 * Desc:初始化界面按键是否可用。
	 * @since 2013-06-12
	 * @param {Object} eOpts	窗口的easyui对应的options
	 */
	function initBtnAbles(eOpts){
		var btns = eOpts.buttons;
		var jqBtns = _t.jqObj.parent().find("div.dialog-button");
		jqBtns.find("a.l-btn").each(function(i){
			if(btns[i] == "-") return true;
			if(btns[i].sgl == null) return true;//最后的一个close按键就没有
			if($tt.isF(btns[i].sgl.able)) $(this).linkbutton("disable");
		});
	}
	/**
	 * Desc:动态显示子项。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	function showSub(req){
		_t.jqObj.find("." + $tui.CSS_TMP).each(function(){
			var jqTmp = $(this);
			if(!$tt.isT(jqTmp.data("lnk"))) return true;
			var newReq = $tui.cloneReq(req);
			jqTmp.tTmp("show", newReq);
		});
	}
	/**
	 * Desc:打开窗口。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.open = function(req){
		_t.preReq = req;//记得先加上
		showSub(req);
		var eOpts = getEOpts(req);
		_t.jqObj.dialog(eOpts);
		//按键是否可用
		initBtnAbles(eOpts);
	};

	/**
	 * Desc:关闭窗口。
	 * @since 2013-06-12
	 */
	this.close = function(){
		_t.jqObj.find(".tt-fld").each(function(){//解决窗口关闭时，tooltip还在界面上的问题
			$(this).blur();
		});
		_t.jqObj.dialog("close");
	};
	init();
}

//++++++++++++++++++++++++++++TDlg ends++++++++++++++++++++++++++++++++++++++++
