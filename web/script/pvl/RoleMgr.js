var RoleMgr = {
		/**
		 * Desc:绑定权限
		 * @param {TReq} req
		 */
		bindPvl : function(req){
			var nodes = req.jqData.tree('getChecked');
			var pvlCodes = "";
			$.each(nodes, function(i, node){
		    	$this = $(node.target);
		    	if($this.tree("isLeaf", node.target) == false) return;
				if (pvlCodes != '') pvlCodes += ',';
				pvlCodes += node.id;
			});
			var roleId = req.preReq.data.roleId;
			var type = req.preReq.data.type;
			var data = {pvlCodes : pvlCodes, roleId : roleId, type : type};
			$tt.callAjax("pvl/RoleCtrl/bindPvls", data, function(ctrlResult){
				return $tui.doReqBak4Ctrl(req, ctrlResult);
			});
		}
};
