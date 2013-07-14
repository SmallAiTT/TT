var SysSel = {
		TF : [{
			value : "T", text : "是"
		},{
			value : "F", text : "否"
		}],
};

var SysFmt = {
		TF : function(value){return $tt.fmtSel(value, SysSel.TF);}
};