

/**
 * Desc:插件信息注册。
 * @since 2013-06-01
 */
!function($){

	//表达式注册
	$tt.exps.func = FuncExp;
	$tt.exps.jq = JqExp;
	$tt.exps.qry = QryExp;
	$tt.exps.submit = SubmitExp;
	$tt.exps.act = ActExp;
	$tt.exps.reset = ResetExp;
	$tt.exps.sel = SelExp;
	
	//插件注册
	$tt.initPlugin($tui.TYPE_FLD, TFld);
	$tt.initPlugin($tui.TYPE_TBL, TTbl);
	$tt.initPlugin($tui.TYPE_DLG, TDlg);
	$tt.initPlugin($tui.TYPE_BTN, TBtn);
	$tt.initPlugin($tui.TYPE_FRM, TFrm);
	$tt.initPlugin($tui.TYPE_TMP, TTmp);
	$tt.initPlugin($tui.TYPE_TREE, TTree);
	$tt.initPlugin($tui.TYPE_QRY, TQry);
	$tt.initPlugin($tui.TYPE_QRY_AREA, TQryArea);
}(window.jQuery);

/**
 * Desc:插件初始化
 * @since 2013-06-01
 */
$(function(){
	//获取按键权限
	$tt.pvls = {};
	var pvlArr = $tt.callAjaxSync("pvl/PvlCtrl/get", {type : "btn"}).value;
	var l = pvlArr.length;
	for(var i = 0; i < l; ++i){
		$tt.pvls[pvlArr[i]["code"]] = true;
	}
	
	$tui.initTmpArgs();//先进行模板参数的初始化
	$("." + $tui.CSS_TBL).tTbl();
	$("." + $tui.CSS_DLG).tDlg();
	$("." + $tui.CSS_BTN).tBtn();
	$("." + $tui.CSS_FLD).tFld();
	$("." + $tui.CSS_FRM).tFrm();
	$("." + $tui.CSS_TREE).tTree();
	$("." + $tui.CSS_QRY).tQry();
	$("." + $tui.CSS_QRY_AREA).tQryArea();
	
	$("." + $tui.CSS_FIRE).each(function(){
		var jqFire = $(this);
		var target = jqFire.attr("target");
		if($tt.isEmpty(target)) return true;
		var oper = jqFire.attr("oper");
		var func = jqFire.attr("func");
		var jqTarget = jqFire.find(target);
		if(jqTarget.length == 0) return;
		var jqType = jqTarget.data($tui.JQ_TYPE);
		oper = $tt.isEmpty(oper) ? (
				(jqType == $tui.TYPE_QRY || jqType == $tui.TYPE_TBL) ? "q" : ""
				) : oper;
		func = $tt.isEmpty(func) ? jqTarget.data($tui.DEF_FUNC) : func;
		return jqTarget[jqType](func, {oper : oper , data : {}});
	});
});
