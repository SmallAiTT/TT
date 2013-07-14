<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table class="tt-tbl" rownumbers="true" singleSelect="true" pagination="true" 
	btnExps="
		c:jq->$[dlg];u|r:jq->$[dlg]?*;d:act->pvl/RoleCtrl/delete;
		b4Menu:jq->$[bindPvlDlg]?roleId=id&type='menu'&roleId=id;
		b4Url:jq->$[bindPvlDlg]?roleId=id&type='url'&roleId=id;
		b4Btn:jq->$[bindPvlDlg]?roleId=id&type='btn'&roleId=id;
		
		c4User:jq->$[bindRolesDlg]?userId=_pre.userId;
		d4User:act->pvl/RoleCtrl/unBindRoles?userId=_pre.userId&roleIds=id
	"
	btnTxts="
		b4Menu:菜单权限;b4Url:URL权限;b4Btn:按键权限;
		c4User:绑定角色;d4User:移除角色;
		"
	btnIcons="c4User:icon-add;d4User:icon-remove"
	btnChgs="c4User:b4User"
	validRels="c4User:0"
	btnOpers="
		q:r,c,u,d,b4Menu,b4Url,b4Btn;
		q4User:c4User,d4User;
		"
	titles="q|q4User|b4User:角色列表"
	acts="q|b4User:pvl/RoleCtrl/query;q4User:pvl/RoleCtrl/query4User"
	
	qryDatas="q:name;q4User:userId&name"
	qryCndExps="q|q4User|sel:name=like-+"
	qryParaExps="q4User:userId"
	qryDefSorts="*:name"
	>
  <thead>
    <tr>
      <th field="id" width="100" type="hidden">ID</th>
      <th field="name" width="100" sortable="true">角色名称</th>
      <th field="remark" width="200">备注</th>
    </tr>
  </thead>
</table>

