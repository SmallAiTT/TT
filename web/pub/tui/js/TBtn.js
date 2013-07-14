
//++++++++++++++++++++++++++++TBtn ends++++++++++++++++++++++++++++++++++++++++
//TODO TBtn
/**
 * Desc:按键的options。
 * @since 2013-06-12
 */
function TBtnOpts(){
	/** 公共信息解析器 */
	this.pubParser = new TPubParser();
	/** 按键信息解析器 */
	this.btnParser = new TBtnParser();
	/** 验证信息解析器 */
	this.validParser = new TValidParser();
	/** 映射 */
	this.maps = {};
}

/**
 * Desc:按键类。
 * @since 2013-06-12
 * @param {JQuery} jqObj
 * @param {TBtnOpts} opts
 */
function TBtn(jqObj, opts){
	var _t = this;
	/** 对应的jquery对象 */
	this.jqObj = jqObj;
	/** options */
	this.opts = new TBtnOpts();
	/** 按键的options数组 */
	this.btnSglArr = [];
	/** 验证信息映射，key为oper */
	this.validMap = {};
	/** 按键对象数组（相当于toolbar） */
	this.bar = [];
	/** 按键信息默认值 */
	this.btnDef = {
			txts : {c:"新增",u:"修改",r:"浏览",d:"删除", reset:"重置", q:"查询"},
			icons : {c:"icon-add",u:"icon-edit",r:"icon-search",d:"icon-remove"},
			opers : {c:"c",u:"u",r:"r",d:"d",reset:"reset"},
			ables : {"*": true},
			handlers : {"*" : handler},
			oks : {"sel" : selOk}
	};
	/** 对应于_self */
	this.self4Btn = {
			handlers : {
				handler : handler
			},
			oks : {
				sel : selOk
			}	
	};
	this.self4Valid = {};
	/** 验证信息默认值 */
	this.validDef = {};

	this.preReq = null;
	
	this.tmpArgs = null;
	
	this.map = {};
	
	/**
	 * Desc:按键的handler。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	function handler(req){
		req.preReq = _t.preReq;
		if(req.oper == "*" && _t.preReq != null) req.oper = _t.preReq.oper; 
		var target = _t.opts.btnParser.info.targets[req.oper];
		if(!$tt.isEmpty(target)){
			req.jqData = $(target);
		}else if(_t.jqObj.parents("form").length > 0){
			req.jqData = _t.jqObj.parents("form");
		}else if(_t.jqObj.parents("." + $tui.CSS_QRY_AREA + ":first").length > 0){
			req.jqData = _t.jqObj.parents("." + $tui.CSS_QRY_AREA + ":first").find("form");
		}else{
			req.jqData = _t.jqObj;
		}
		$tui.callReq(req);
	}
	
	/**
	 * @param {TReq} req
	 * @param {TRes} res
	 */
	function selOk(req, res){
		var data = res.data;
		if(data instanceof Array){
			dataTmp = data;
			data = {};
			$.each(dataTmp, function(i, v){
				$.each(v, function(k1, v1){
					if($tt.isEmpty(v1)) return true;
					if(!$tt.isEmpty(data[k1])) data[k1] += "," + v1;
					else data[k1] = v1;
				});
			});
		}
		var lData = {};
		var btnSgl = _t.opts.btnParser.getSgl(req.oper);
		$.each(btnSgl.map, function(k, v){
			if(k == "_seq") return true;
			lData[k] = data[v];
		});
		req.jqData.form("load", lData);
	}

	/**
	 * Desc:初始化。
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
			_t.opts.validParser.parse(_t.jqObj, _t.tmpArgs, _t.validDef);
			//解析映射
			_t.opts.maps = $tt.parseAttr(_t.jqObj, "maps", _t.tmpArgs);
		}
		$tt.chgAttr(_t.jqObj, _t.opts.pubParser.info.attrs);
		_t.btnSglArr = _t.opts.btnParser.getSglArr(_t.self4Btn);
		_t.validMap = _t.opts.validParser.getSglMap();
		_t.jqObj.attr("href", "javascript:;");
		var jqQry = _t.jqObj.parents("." + $tui.CSS_QRY + ":first");
		var jqOwner = jqQry.length > 0 ? jqQry : _t.jqObj;
		_t.bar = $tui.getToolBar(jqOwner, ["*"], _t.btnSglArr, _t.validMap);
		
		_t.map = $tt.parseAttr(_t.jqObj, "map");
		//默认操作
		_t.jqObj.data($tui.DEF_FUNC, "show");//设置默认调用方法
		var defOper = _t.jqObj.attr($tui.DEF_OPER);
		if(!$tt.isEmpty(defOper)) _t.show({oper : defOper});
	}
	/**
	 * Desc:根据oper显示按键。
	 * @since 2013-06-12
	 * @param {TReq} req	请求
	 */
	this.show = function(req){
		_t.preReq = req;//记得先保存
		var btn = null;
		var defBtn = null;
		for(var i = 0; i < _t.bar.length; ++i){
			if(_t.bar[i].sgl.oper == req.oper){
				btn = _t.bar[i];//先根据oper取出按键
				break;
			}
			if(_t.bar[i].sgl.oper == "*") defBtn = _t.bar[i];//*方式的按键
		}
		btn = btn == null ? defBtn : btn;
		if(btn == null){//如果为空就是无此按键，隐藏。
			_t.jqObj.hide();
			return false;
		}
		_t.jqObj.show();
		_t.jqObj.unbind("click"); //移除click
		_t.jqObj.bind("click", function(){//点击事件
			$tt.getFunc(btn.handler)();
		});

		var opts = {};
		opts.disabled = $tt.isF(btn.sgl.able);//按键是否可用
		opts.text = btn.text;
		opts.iconCls = btn.iconCls;
		_t.jqObj.linkbutton(opts);
	};
	
	init();
}
//++++++++++++++++++++++++++++TBtn ends++++++++++++++++++++++++++++++++++++++++
