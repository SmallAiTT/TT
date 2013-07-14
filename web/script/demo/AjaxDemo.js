var AjaxDemo = {
		getReqData : function(){
			return {
				msg : $("#msg").val(),
				msgType : $("#msgType").val()
			};
		},
		callback : function(data){
			$tt.alertInfo(data.msgType + "--->" + data.msg);
		},
		callAjax : function(){
			$tt.callAjax("demo/AjaxDemoCtrl/callAjax", AjaxDemo.getReqData(), AjaxDemo.callback);
		},
		callAjaxSync : function(){
			var ctrlResult = $tt.callAjaxSync("demo/AjaxDemoCtrl/callAjaxSync", AjaxDemo.getReqData());
			AjaxDemo.callback(ctrlResult);
		},
		submitFrm : function(){
			$tt.submitFrm($("#frm"), "demo/AjaxDemoCtrl/submitFrm", AjaxDemo.callback);
		}
};