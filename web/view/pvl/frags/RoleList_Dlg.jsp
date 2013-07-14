<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="tt-dlg" data-options="width : 600 , height : 460"
 	titles="q4User:用户角色列表;b4User:绑定角色;"
	attrs="id:$[roleListDlg]"
	btnExps="b4User:act->pvl/RoleCtrl/bindRoles?userId=_pre.userId&roleIds=id"
	btnOpers="b4User"
	btnTxts="b4User:绑定"
	btnIcons="b4User:icon-save"
	>
	<div class="tt-tmp" args="bindRolesDlg" data-options="height : 250" lnkOpers="q4User">
		<jsp:include page="./Role_Qry.jsp"></jsp:include>
	</div>
	<div class="tt-tmp" data-options="height : 250" lnkOpers="b4User">
		<jsp:include page="./Role_Qry.jsp"></jsp:include>
	</div>
</div>   
