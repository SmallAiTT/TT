
//++++++++++++++++++++++++++++TPaser Starts+++++++++++++++++++++++++++++++++++++
//TODO TPaser
/**
 * Desc:解析信息的基类。
 * @since 2013-06-04
 */
function TPaser(){
	this.opers = {};
	this.info = {};
	this.def = {};
	this.parseMap = {};
	this.onAfter = function(){};

	/**
	 * Desc:解析Info。
	 * @since 2013-06-04
	 * @param {JQuery} jqObj	要解析的jquery对象
	 * @param {Object} tmpArgs	模板参数
	 * @param {Object} def		默认值
	 */
	this.parse = function(jqObj, tmpArgs, def){
		var _this = this;
		_this.def = $tt.getValByDef(def, {});
		$.each(_this.parseMap, function(k, v){
			console.log(k);
			_this.info[k] = $tt.parseAttr(jqObj, v, tmpArgs, _this.def[k]);
			var _seq = _this.info[k]._seq;
			if(_seq == null) return true;
			var length = _seq.length;
			for(var i = 0; i < length; ++i){
				_this.opers[_seq[i]] = _seq[i];
			}
		});
		_this.onAfter();
	};
	/**
	 * Desc:通过oper获取到info的single信息。
	 * 		其中有一定规律，info中的字段名字都是"*s"的格式，获取的的sgl的所有字段直接去除"s"而已。
	 * @since 2013-06-04
	 * @param {String} oper
	 * @param {Object} sgl
	 * @param {Object} self	自身方法的注册对象
	 */
	this.setSglBase = function(oper, sgl, self){
		var _this = this;
		$.each(_this.info, function(k, v){
			if($tt.isEmpty(k)) return true;
			var key = k.substring(0, k.length - 1);
			var val = $tt.getValByDef4Map(v, oper, "*");
			sgl[key] = val;
			if(typeof val == "string" && val.startWith("_self.") && self != null){
				var funcMap = self[k];
				if($tt.isEmpty(funcMap)) return true;
				sgl[key] = funcMap[val.substring(6)];
			}
		});
	};
}
/**
 * Desc:公共信息Sgl类。
 * @since 2013-06-04
 */
function TPubSgl(){
	this.title = null;
	this.act = null;
	this.atttr = null;
	this.close = null;
}
/**
 * Desc:按键解析信息类。
 * @since 2013-06-04
 */
function TPubParser(){
	TPaser.call(this);
	this.info = { titles : {}, acts : {}, attrs : {}, closes : {} };
	this.parseMap = { titles : "titles", acts : "acts", attrs : "attrs", closes : "closes" };
	/**
	 * @param {String} oper
	 * @returns {TPubSgl}
	 */
	this.getSgl = function(oper){
		var _t = this;
		var sgl = new TPubSgl();
		_t.setSglBase(oper, sgl);
		return sgl;
	};
}
/**
 * Desc:按键信息Sgl类。
 * @since 2013-06-04
 */
function TBtnSgl(){
	this.oper = null;
	this.exp = null;
	this.pvl = null;
	this.icon = null;
	this.able = null;
	this.ok = null;
	this.chg = null;
	this.target = null;
	this.handler = null;
	this.map = {};
}
/**
 * Desc:按键解析信息类。
 * 		内容：
 * 
 *	<div>btnExps="r|c@{PVL_Pvl_C}|u@{PVL_Pvl_U}:dlg->#dlg;d@{PVL_Pvl_D}:act->delete;test:test;测试:test"</div>
 *	<div>btnTxts="r:浏览"</div>
 *	<div>btnIcons="c:icon-add"</div>
 *	<div>btnOpers="q:r,c,u,d"</div>
 *	<div>btnAbles="c:true"</div>
 *	<div>btnOks="c|u|d:owner->tTbl/reload"</div>
 *	<div>btnFails="c|u|d:owner->tTbl/reload"</div>
 *	<div>btnChgs="c:cChild"</div>
 *	<div>btnTargets="c:#form"</div>
 *	<div>btnHandlers="c:myHandler"</div>
 *	<div>btnPvls="c:PVL_1"</div>
 *	<div>btnMaps="c:id=testId"</div>
 *@since 2013-06-04
 */
function TBtnParser(){
	TPaser.call(this);
	var _t = this;
	
	this.info = {
			exps : {}, txts : {}, icons : {}, opers : {}, ables : {}, 
			oks : {}, fails : {}, chgs : {}, targets : {}, handlers : {},
			pvls : {}, maps : {}
	};
	this.parseMap = {
			exps : "btnExps",
			txts : "btnTxts",
			icons : "btnIcons",
			pvls : "btnPvls",
			opers : "btnOpers",
			ables : "btnAbles",
			oks : "btnOks",
			fails : "btnFails",
			chgs : "btnChgs",
			targets : "btnTargets",
			handlers : "btnHandlers",
			maps : "btnMaps",
	};
	/**
	 * Desc:解析后置处理。
	 * @since 2013-06-04
	 */
	this.onAfter = function(){
		_t.info.opers = $tt.parseValToArr(_t.info.opers);

		var seq = _t.info.exps._seq;
		if(seq == null) return;
//		var seqTmp = [];
//		$.each(seq, function(i, val){
//			var arr = val.split("@{");//pvl的格式为@{asdf}
//			var oper = arr[0];
//			var pvl = arr.length > 1 ? arr[1].substring(0, arr[1].indexOf("}")) : null;
//			_t.info.exps[oper] = _t.info.exps[val];
//			if(!$tt.isEmpty(pvl)) delete _t.info.exps[val];
//			_t.info.pvls[oper] = pvl;
//			seqTmp.push(oper);
//		});
//		_t.info.exps._seq = seqTmp;
		
		var mapsTmp = {};
		$.each(_t.info.maps, function(k, v){
			if(k == "_seq") return true;
			mapsTmp[k] = $tt.parseArgs(v);
		});
		_t.info.maps = mapsTmp;
	};
	/**
	 * Desc:根据操作获取到单个信息。
	 * @since 2013-06-04
	 * @param {String} oper	操作
	 * @param {Object} self	自身方法的注册对象
	 * @returns {TBtnSgl}
	 */
	this.getSgl = function(oper, self){
		var _t = this;
		var sgl = new TBtnSgl();
		_t.setSglBase(oper, sgl, self);
		sgl.oper = oper;
		sgl.map = sgl.map == null ? {} : sgl.map;
		return sgl;
	};

	/**
	 * Desc:转换为sgl数组。
	 * @since 2013-06-04
	 * @param {Object} self	自身方法的注册对象
	 * @returns {[TBtnSgl]}
	 */
	this.getSglArr = function(self){
		var arr = [];
		var exps = _t.info.exps;
		var btnSglArr = [];
		if(exps._seq == null) return btnSglArr;
		$.each(exps._seq, function(index, val){
			if($tt.isEmpty(val)) return true;
			var sgl = _t.getSgl(val, self);
			arr.push(sgl);
		});
		return arr;
	};
}
/**
 * Desc:验证信息Sgl类。
 * @since 2013-06-04
 */
function TValidSgl(){
	this.rel = null;
	this.msg = null;
	this.confirm = null;
	this.cust = null;
}
/**
 * Desc:验证信息解析信息类。
 * 		内容：
 * 
 *		<div>validRels="test:0"</div>
 *		<div>validMsgs="测试:请选择需要测试的数据！"</div>
 *		<div>validConfirms="test:确定要Test？"</div>
 *		<div>validCusts="test:testCust"</div>
 * @since 2013-06-04
 */
function TValidParser(){
	TPaser.call(this);
	this.info = {
			rels : {}, msgs : {}, confirms : {}, custs : {},
	};
	this.parseMap = {
			rels : "validRels",
			msgs : "validMsgs",
			confirms : "validConfirms",
			custs : "validCusts",
	};
	/**
	 * Desc:获取到单个信息
	 * @since 2013-06-04
	 * @param {String} oper
	 * @returns {TValidSgl}
	 */
	this.getSgl = function(oper){
		var _t = this;
		var sgl = new TValidSgl();
		_t.setSglBase(oper, sgl);
		return sgl;
	};
	
	/**
	 * Desc:根据解析得到的验证信息，获取到TValid的映射。
	 * @since 2013-06-12
	 * @returns {Object}
	 */
	this.getSglMap = function(){
		var validMap = {};
		var _this = this;
		function createValid(info){
			$.each(info, function(k, v){
				if(k == "*") return true;//记得先排除该字段
				if(k == "_seq") return true;//记得先排除该字段
				if(validMap[k] == null) validMap[k] = _this.getSgl(k);
			});
		}
		createValid(_this.info.rels);
		createValid(_this.info.msgs);
		createValid(_this.info.confirms);
		createValid(_this.info.custs);
		
		var allSgl = new TValidSgl();
		allSgl.rel = _this.info.rels["*"];
		allSgl.confirm = _this.info.confirms["*"];
		allSgl.cust = _this.info.custs["*"];
		allSgl.msg = _this.info.msgs["*"];
		validMap["*"] = allSgl;
		return validMap;
	};
}
/**
 * Desc:Fld的单一解析信息。
 * @since 2013-06-12
 */
function TFldSgl(){
	this.readonly;
	this.required;
	this.hidden;
	this.data;
	this.dataSrc;
	this.type;
	this.editable;
}
/**
 * Desc:Fld信息解析类。
 * @since 2013-06-12
 */
function TFldParser(){
	TPaser.call(this);
	this.info = { 
			readonlys : {}, requireds : {}, hiddens : {}, 
			datas : {}, dataSrcs : {} , types : {}, editables : {}
	};
	this.parseMap = { 
			readonlys : "readonlys", requireds : "requireds", hiddens : "hiddens" ,
			datas : "datas", dataSrcs : "dataSrcs", types : "types", editables : "editables"
	};
	/**
	 * Desc:根据oper获取到单一信息
	 * @since 2013-06-12
	 * @param {String} oper		操作
	 * @returns {TFldSgl}
	 */
	this.getSgl = function(oper){
		var _t = this;
		var sgl = new TFldSgl();
		_t.setSglBase(oper, sgl);
		return sgl;
	};
}
/**
 * Desc:lnk的单一信息类。
 * @since 2013-06-12
 */
function TLnkSgl(){
	this.oper;
	this.state;
}
/**
 * Desc:lnk信息的解析类。
 * @since 2013-06-12
 */
function TLnkParser(){
	TPaser.call(this);
	this.info = { 
			opers : {}, chgs : {}
	};
	this.parseMap = { 
			opers : "lnkOpers", chgs : "lnkChgs"
	};
	/**
	 * @param {String} oper
	 * @returns {TLnkSgl}
	 */
	this.getSgl = function(oper){
		var _t = this;
		var sgl = new TLnkSgl();
		_t.setSglBase(oper, sgl);
		return sgl;
	};
}
/**
 * Desc:qry的单一信息类。
 * @since 2013-06-12
 */
function TQrySgl(){
	this.data = {};
	this.ignore = {};
	this.cndExp = "";
	this.paraExp = "";
	this.varExp = "";
	this.sort = "";
	this.defSort = "";
	this.replaceData = {};
	
	/**
	 * Desc:根据请求初始化查询对象到请求中。
	 * @param {TReq} req
	 */
	this.initQry = function(req){
		$.each(this.data, function(k, v){
			if($tt.isEmpty(k)) return true;
			req.data["_qry.data." + k] = $tt.isConst(v) ? v.substring(1, v.length - 1) : req.data[v];
		});
		$.each(this.ignore, function(k, v){
			if(!$tt.isEmpty(k)) req.data["_qry.ignore." + k] = v;
		});
		$.each(this.replaceData, function(k, v){
			if(!$tt.isEmpty(k)) req.data["_qry.replaceData." + k] = v;
		});
		req.data["_qry.cndExp"] = this.cndExp;
		req.data["_qry.paraExp"] = this.paraExp;
		req.data["_qry.varExp"] = this.varExp;
		req.data["_qry.sort"] = this.sort;
		req.data["_qry.defSort"] = this.defSort;
		req.data["_qry.replaceData"] = this.replaceData;
	};
}
/**
 * Desc:qry信息的解析类。
 * @since 2013-06-12
 */
function TQryParser(){
	var _t = this;
	TPaser.call(this);
	this.info = { 
			datas : {}, ignores : {}, 
			cndExps : {}, paraExps : {}, varExps : {}, 
			sorts : {}, defSorts : {}, replaceDatas : {}
	};
	this.parseMap = { 
			datas : "qryDatas", ignores : "qryIgnores", 
			cndExps : "qryCndExps",  paraExps : "qryParaExps", varExps : "qryVarExps", 
			sorts : "qrySorts", defSorts : "qryDefSorts", replaceDatas : "replaceDatas"
	};

	/**
	 * Desc:解析后置处理。
	 * @since 2013-06-04
	 */
	this.onAfter = function(){
		var dataTmp = {};
		var ignoreTmp = {};
		var replaceDataTmp = {};
		$.each(_t.opers, function(k, v){
			dataTmp[k] = $tt.parseArgs(_t.info.datas[k]);
			ignoreTmp[k] = $tt.parseArgs(_t.info.ignores[k]);
			replaceDataTmp[k] = $tt.parseArgs(_t.info.replaceDatas[k]);
		});
		_t.info.datas = dataTmp;
		_t.info.ignores = ignoreTmp;
		_t.info.replaceDatas = replaceDataTmp;
	};
	/**
	 * @param {String} oper
	 * @returns {TQrySgl}
	 */
	this.getSgl = function(oper){
		var _t = this;
		var sgl = new TQrySgl();
		_t.setSglBase(oper, sgl);
		sgl.data = sgl.data == null ? {} : sgl.data;
		sgl.cnd = sgl.cnd == null ? {} : sgl.cnd;
		sgl.transf = sgl.transf == null ? {} : sgl.transf;
		sgl.ignore = sgl.ignore == null ? {} : sgl.ignore;
		sgl.replaceData = sgl.replaceData == null ? {} : sgl.replaceData;
		return sgl;
	};
}
//++++++++++++++++++++++++++++TPaser Ends++++++++++++++++++++++++++++++++++++++
