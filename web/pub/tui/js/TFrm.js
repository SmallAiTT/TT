
//++++++++++++++++++++++++++++TFrm starts++++++++++++++++++++++++++++++++++++++++
//TODO TFrm
/**
 * Desc:TFrm的options。
 * @since 2013-06-12
 */
function TFrmOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
	/** 验证信息解析器 */
	this.validParser = new TValidParser();
}
/**
 * TFrm定义类。
 * @since 2013-06-12
 * @param {JQuery} jqObj
 * @param {TFrmOpts} opts
 */
function TFrm(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TFrmOpts();
	/** 验证信息映射，key为oper */
	this.validMap = {};
	/** 验证信息默认值 */
	this.validDefs = {};
	/** 前一个req */
	this.preReq = null;
	/** 模板参数 */
	this.tmpArgs = null;
	
	this.curAct = null;
	
	/**
	 * Desc:初始化。
	 * @since 2013-06-12
	 */
	function init(){
		//初始化模板参数
		var jqTmp = jqObj.parents("." + $tui.CSS_TMP + ":first");
		_t.tmpArgs = $tt.getValByDef(jqTmp.data($tui.TMP_ARGS), {});
		if(opts != null) {
			_t.opts = opts;
		}else{
			//解析公共信息
			_t.opts.pubParser.parse(_t.jqObj, _t.tmpArgs);
			//解析验证信息
			_t.opts.validParser.parse(_t.jqObj, _t.tmpArgs, _t.validDefs);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);
		_t.validMap = _t.opts.validParser.getSglMap();
		
		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "load");//设置默认调用方法
		var defOper = $tt.getValByDef(_t.jqObj.attr($tui.DEF_OPER), _t.tmpArgs.defOper);
//		if(!$tt.isEmpty(defOper)) _t.load({oper : defOper});
		_t.reNew({ oper : defOper});
	}
	/**
	 * Desc:重新定义frm样式。
	 * @since 2013-06-12
	 * @param {TReq} req
	 */
	this.reNew = function(req){
        var pubSgl = _t.opts.pubParser.getSgl(req.oper);
        _t.curAct = pubSgl.act;
		_t.jqObj.attr($tui.DEF_OPER, req.oper);//重载之后，需要更新defOper
		//初始化fld
		_t.jqObj.find("." + $tui.CSS_FLD).tFld();
		_t.jqObj.find("." + $tui.CSS_FLD).tFld("show", req);
		_t.jqObj.find("." + $tui.CSS_BTN).tBtn();
		_t.jqObj.find("." + $tui.CSS_BTN).tBtn("show", req);

		_t.jqObj.find("." + $tui.CSS_FLD).blur();
	},
	/**
	 * Desc:根据oper加载信息。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.load = function(req){
		_t.preReq = req;//记得先保存
		_t.jqObj.form("clear");
		_t.jqObj.form("load", req.data);
		_t.jqObj.find("." + $tui.CSS_FLD).blur();//目的在于取消验证信息的tooltip
	};

	/**
	 * Desc:表单提交。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.submit = function(req){
		_t.preReq = req;
		var jqObj = _t.jqObj;
    	//表单验证
		var validSgl = _t.opts.validParser.getSgl(req.oper);
		if(validSgl.rel != "0"){//不为0则需要验证
			if(jqObj.form("validate") == false){//验证不通过
				if(!$tt.isEmpty(validSgl.msg)) return $tt.alertWarn(validSgl.msg);
				return false;
			}
		}
		//用户自定义验证
		if(!$tt.isEmpty(validSgl.cust) && $tt.getFunc(validSgl.cust)(req) == false) return;
		//是否需要确认
		if($tt.isEmpty(validSgl.confirm)) {
			$tt.submitFrm(_t.jqObj, _t.curAct, function(ctrlResult){
				$tui.doReqBak4Ctrl(req, ctrlResult);
			});
		}
		else $tt.confirm(validSgl.confirm, function(){
			$tt.submitFrm(_t.jqObj, _t.curAct, function(ctrlResult){
				$tui.doReqBak4Ctrl(req, ctrlResult);
			});
		});
	};
	
	init();
}
//++++++++++++++++++++++++++++TFrm ends++++++++++++++++++++++++++++++++++++++++
