var DemoSel = {
		sex : [{
			value : "M", text : "男"
		},{
			value : "W", text : "女"
		}],
		
		curdSel : [{
			value : "M", text : "男"
		},{
			value : "W", text : "女"
		}]
};

var DemoFmt = {
		sex : function(value){return $tt.fmtSel(value, DemoSel.sex);},
		curdSel : function(value){return $tt.fmtSel(value, DemoSel.curdSel);},
};
