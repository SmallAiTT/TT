//++++++++++++++++++++++++++++TFld starts++++++++++++++++++++++++++++++++++++++++
//TODO TFld
/**
 * Desc:TFld的options
 * @since 2013-06-12
 */
function TFldOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
	/** fld的信息解析器 */
	this.fldParser = new TFldParser();
}
/**
 * Desc:输入框定义类。
 * @since 2013-06-12
 * @param {JQuery} jqObj
 * @param {TFldOpts} opts
 */
function TFld(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TFldOpts();
	
	/** 默认fldInfo值 */
	this.fldDef = {
		types : {"*" : "validatebox"},
		readonlys : {r : "r"},
	};
	/**
	 * Desc:初始化。
	 * @since 2013-06-12
	 */
	function init(){
		//获取模板参数
		var jqTmp = jqObj.parents("." + $tui.CSS_TMP + ":first");
		_t.tmpArgs = $tt.getValByDef(jqTmp.data($tui.TMP_ARGS), {});
		if(opts != null) {
			_t.opts = opts;
		}else{
			//解析公共信息
			_t.opts.pubParser.parse(_t.jqObj, _t.tmpArgs);
			//解析fld信息
			_t.opts.fldParser.parse(_t.jqObj, _t.tmpArgs, _t.fldDef);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);

		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "show");//设置默认调用方法
		var defOper = _t.jqObj.attr($tui.DEF_OPER);
		if(!$tt.isEmpty(defOper)) _t.show({oper : defOper});
	}
	
	/**
	 * Desc：获取easyui所需要的options
	 * @since 2013-06-12
	 */
	function getEOpts(sgl){
		var opts = {};
		if(!$tt.isEmpty(sgl.readonly)) {//是否只读
			_t.jqObj.attr("readonly", "readonly");
			opts.disabled = true;
		}else{
			_t.jqObj.removeAttr("readonly");
		}
		opts.editable = $tt.isEmpty(sgl.editable);//是否可编辑
		opts.required = !$tt.isEmpty(sgl.required);//是否必须
		if(!$tt.isEmpty(sgl.hidden)){//TODO 隐藏，目前还未完全实现
			opts.required = false;
			_t.jqObj.parents(".tt-fldGrp").hide();
		}else{
			_t.jqObj.parents(".tt-fldGrp").show();
		}
		if(!$tt.isEmpty(sgl.data)){//添加数据
			if(typeof sgl.data == "string"){
				try{
					opts.data = eval(sgl.data);
				}catch(e){
					opts.data = null;
				}
			}else{
				opts.data = sgl.data;
			}
		}
		return opts;
	}
	
	/**
	 * Desc:展示。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.show = function(req){
		var oper = req.oper;
		var sgl = _t.opts.fldParser.getSgl(oper);
		var opts = getEOpts(sgl);
		_t.jqObj[sgl.type](opts);
		_t.jqObj[sgl.type]("validate");
		_t.jqObj.blur();		
	};
	
	init();
}
//++++++++++++++++++++++++++++TFld Ends++++++++++++++++++++++++++++++++++++++++