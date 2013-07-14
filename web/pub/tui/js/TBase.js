var $tui = {
	//------------------css 常量-------------
	/**  */
	JQ_CSS : "jqCss",
	CSS_FRM : "tt-frm",
	CSS_TBL : "tt-tbl",
	CSS_DLG : "tt-dlg",
	CSS_BTN : "tt-btn",
	CSS_FLD : "tt-fld",
	CSS_TMP : "tt-tmp",
	CSS_TREE : "tt-tree",
	CSS_QRY : "tt-qry",
	CSS_FIRE : "tt-fire",
	CSS_QRY_AREA : "tt-qry-area",
	CSS_QRY_BTNS : "tt-qry-btns",
	CSS_BTN_QRY : "tt-btn-qry",
	
	//-----------------类型 常量------------------
	JQ_TYPE : "jqType",
	TYPE_FRM : "tFrm",
	TYPE_TBL : "tTbl",
	TYPE_DLG : "tDlg",
	TYPE_BTN : "tBtn",
	TYPE_FLD : "tFld",
	TYPE_TMP : "tTmp",
	TYPE_TREE : "tTree",
	TYPE_QRY : "tQry",
	TYPE_QRY_AREA : "tQryArea",
	
	//------------------标签属性 常量--------------
	TMP_ARGS : "tempArgs",
	DEF_OPER : "defOper",
	DEF_FUNC : "defFunc",
	
	//----------------------------------------

	/**
	 * Desc:执行req的回调函数。
	 * @since 2013-06-04
	 * @param {TReq} req				当前的req
	 * @param {TRes} res				响应
	 * @returns {Boolean}
	 */
	doReqBack : function(req, res){
		if(req != null && !$tt.isEmpty(req.callback)) return $tt.getFunc(req.callback)(res);
		return false;
	},

	/**
	 * Desc:为调用后台后封装的执行req的回调函数。
	 * @since 2013-06-04
	 * @param {TReq} req				当前的req
	 * @param {CtrlResult} ctrlResult	调用后台分会的结果
	 * @returns {Boolean}
	 */
	doReqBak4Ctrl : function(req, ctrlResult){
		var res = new TRes($tt.isCtrlInfo(ctrlResult), {}, ctrlResult);
		return $tui.doReqBack(req, res);
	},
	/**
	 * Desc:执行Exp
	 * @since 2013-06-04
	 * @param {TExp} exp	要执行的表达式对象
	 * @param {TReq} req	要执行的req
	 */
	doExp : function(exp, req){
		if($tt.isEmpty(exp.name)) return $tt.getFunc(exp.url)(req);
		var ExpExec = $tt.exps[exp.name];
		if(ExpExec == null) return $tt.alertErr("表达式执行器[" + exp.name + "]未注册！");
		var expExec = new ExpExec();
		expExec.exec(req);
	},
	/**
	 * Desc:执行请求。
	 * @since 2013-06-04
	 * @param {TReq} req	要执行的请求
	 */
	callReq : function(req){

		/**
		 * Desc:通过frm获取数据
		 * @since 2013-06-04
		 * @param {TReq} req	要处理的请求
		 * @returns {Boolean} true为成功，false为失败
		 */
		function initDataByFrm(req){
			var jqData = req.jqData;
			var validSgl = req.validSgl;
			if(validSgl.rel != "0"){//不为0则需要验证
				if(jqData.form("validate") == false){//验证不通过
					if(!$tt.isEmpty(validSgl.msg)) return $tt.alertWarn(validSgl.msg);
					return false;
				}
			}
			/**
			 * Desc:通过输入框获取值
			 * @since 2013-06-04
			 * @param {JQuery} jqInput	输入框对象
			 * @returns {Boolean} true为成功，false为失败
			 */
			function setData(jqInput){
				var name = jqInput.attr("name");
				if($tt.isEmpty(name)) return true;
				if(req.data[name] != null) return true;
				req.data[name] = jqInput.val();
			};
			//获取不同类型的输入框的值
			jqData.find("input").each(function(){
				return setData($(this));
			});
			jqData.find("select").each(function(){
				return setData($(this));
			});
			jqData.find("textarea").each(function(){
				return setData($(this));
			});
			return true;
		}
		/**
		 * Desc:通过tbl获取数据
		 * @since 2013-06-04
		 * @param {TReq} req	要处理的请求
		 * @returns {Boolean} true为成功，false为失败
		 */
		function initDataByTbl(req){
			var jqData = req.jqData;
			var validSgl = req.validSgl;
			var rows = jqData.datagrid("getSelections");
			if(validSgl.rel == "0"){
				if(rows.length == 0) req.data = {};
				else if(rows.length == 1) req.data = rows[0];
				else req.data = rows;
			}else{
				if(rows.length == 0){
					return $tt.alertWarn($tt.getValByDef(validSgl.msg, "请选择数据！"));
				}
				if(validSgl.rel == "1"){
					if(rows.length > 1) return $tt.alertWarn("该操作只允许操作一条数据，不允许操作多条数据！");
					req.data = rows[0];
				}else if(validSgl.rel == "+"){
					req.data = rows;
				}else {
					req.data = rows.length == 1 ? rows[0] : rows;
				}
			}
			return true;
		}
		/**
		 * Desc:获取数据
		 * @since 2013-06-04
		 * @param {TReq} req	要处理的请求
		 * @returns {Boolean} true为成功，false为失败
		 */
		function initData(req){
			//先对数据进行处理
			req.data = {};
			var jqData = req.jqData;
			if(jqData == null) return true;
			var jqDataType = jqData.data($tui.JQ_TYPE);
			var tagName = jqData.get(0).tagName.toLowerCase();
			if(jqDataType == $tui.TYPE_TBL){
				return initDataByTbl(req);//如果是tbl类型
			}else if(tagName == "form"){
				return initDataByFrm(req);//如果是frm类型
			}
			return true;
		}
		/**
		 * Desc:验证。
		 * @since 2013-06-04
		 * @param {TReq} req	要处理的请求
		 */
		function doValid(req, doExp){
			var args = req.exp.args;
			var data = req.data;
			if($tt.isEmpty(args["*"])){
				req.data = {};
			}
			$.each(args, function(k, v){
				if(k == "*") return true;
				if(typeof v == "string" && v.startWith("_pre.")) 
					req.data[k] = req.preReq == null ? null : req.preReq.data[v.substring(5)];
				else req.data[k] = $tt.isConst(v) ? v.substring(1, v.length - 1) : data[v];
			});
			var validSgl = req.validSgl;
			//用户自定义验证
			if(!$tt.isEmpty(validSgl.cust) && $tt.getFunc(validSgl.cust)(req) == false) return;
			//是否需要确认
			if($tt.isEmpty(validSgl.confirm)) doExp(req.exp, req);
			else $tt.confirm(validSgl.confirm, function(){
				doExp(req.exp, req);
			});
		}
		
		try{
			if(initData(req) == false) return;
			doValid(req, $tui.doExp);
		}catch(e){
			$tt.alertErr(e, "JS执行错误");
			throw e;
		}
	},
	
	cloneReq : function(req, oper){
		var expStr = req.exp == null ? "" : req.exp.exp;
		var newReq = new TReq(req.jqOwner, $tt.isEmpty(oper) ? req.oper : oper, expStr, req.validSgl);
		newReq.operChg = req.operChg;
		newReq.btnSgl = req.btnSgl;
		newReq.data = req.data;
		newReq.jqData = req.jqData;
		newReq.jqOwner = req.jqOwner;
		newReq.attrs = req.attrs;
		newReq.preReq = req.preReq;
		newReq.callback = req.callback;
		newReq.ok = req.ok;
		newReq.fail = req.fail;
		return newReq;
	},

	//++++++++++++++++++++++++++++ToolBtn Starts++++++++++++++++++++++++++++++++++++++
	//TODO ToolBtn
	/**
	 * Desc:生成工具按键。
	 * @since 2013-06-04
	 * @param {JQuery} jqOwner		query对象 
	 * @param {TBtnSgl} btnSgl 		按键信息
	 * @param {TValidSgl} validSgl 	验证信息
	 * @returns {{text : "", iconsCls : "", sgl : new TBtnSgl(), handler : function(){}}}
	 */
	getToolBtn : function(jqOwner, btnSgl, validSgl){
		var oper = $tt.getValByDef(btnSgl.chg, btnSgl.oper);//根据按键的oper获取到req的oper
		var req = new TReq(jqOwner, oper, btnSgl.exp, validSgl);
		req.ok = btnSgl.ok;
		req.btnSgl = btnSgl;
		var result = {
			text : $tt.getValByDef(btnSgl.txt, btnSgl.oper),
			iconCls : $tt.getValByDef(btnSgl.icon, ""),
			sgl : btnSgl,
			handler : function(){
				if($tt.isF(btnSgl.able)) return;//不可用则直接返回。
				$tt.getFunc(btnSgl.handler)(req);
			}
		};
		return result;
	},

	/**
	 * Desc:获取到工具栏
	 * @since 2013-06-04
	 * @param {JQuery} jqOwner				目标jquery对象
	 * @param {Array} opers					当前所有操作
	 * @param {TBtnSgl[]} btnSglArr			按键信息数组
	 * @param {Object} validMap				验证信息映射
	 * @param {Boolean} hasSplit			是否添加间隔标志
	 * @returns {Array}						按键数组
	 */
	getToolBar : function(jqOwner, opers, btnSglArr, validMap, hasSplit){
		var btns = [];
		if(opers == null) return btns;
		$.each(btnSglArr, 
				/**
				 * @param {Number} index
				 * @param {TBtnSgl} opts
				 */
				function(index, opts){
			if(!$tt.hasPvl(opts.pvl)) return true;
			var flag = false;
			if(opers.length == 1 && opers[0] == "*"){//全部
				flag = true;
			}else{
				$.each(opers, function(index1, oper){
					if(oper == opts.oper){
						flag = true;
						return false;
					}
				});
			}
			if(flag){//可以添加
				var btn = $tui.getToolBtn(jqOwner, opts, $tt.getValByDef(validMap[opts.oper], validMap["*"]));
				if(hasSplit && btns.length > 0) btns.push("-");
				if(btn != null) btns.push(btn);
			}
		});
		return btns;
	},

	/**
	 * Desc:初始化模板参数。
	 * @since 2013-06-04
	 */
	initTmpArgs : function(){
		$("." + $tui.CSS_TMP).each(function(){
			var jqTmp = $(this);
			jqTmp.tTmp();
			if(jqTmp.data($tui.TMP_ARGS) != null) return true;
			var jqPTmp = jqTmp.parents("." + $tui.CSS_TMP + ":first");
			var pTmpArgs = $tt.getValByDef(jqPTmp.data($tui.TMP_ARGS), {});
			
			var argsExp = jqTmp.attr("args");
			var args = $tt.genArgs($tt.parseArgs(argsExp), pTmpArgs);
			jqTmp.data($tui.TMP_ARGS, args);
		});
	},

	//++++++++++++++++++++++++++++ToolBtn Ends++++++++++++++++++++++++++++++++++++++++

};