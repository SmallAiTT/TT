var Curd = {
		/**
		 * Desc:自定义更新验证
		 * @param req
		 */
		cust4Update : function(req){
			var jqData = req.jqData;
			var code = jqData.find("[name='code']").val();
			if(code == "code1") return $tt.alertWarn("不允许更新code为'code1'的数据！");
			return true;
		}
};