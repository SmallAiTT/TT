<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<jsp:include page="/pub/Metas4Base.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script src="script/user/UserSel.js" type="text/javascript"></script>
</head>
<body>
<div class="tt-tmp" 
	args="
		dlg='#userDlg'&roleListDlg='#roleListDlg'&bindRolesDlg='#bindRolesDlg'
		"
	>
	<div class="tt-tmp tt-fire" target=".tt-qry" data-options="height : 400" args="*">
		<jsp:include page="./frags/User_Qry.jsp"></jsp:include>
	</div>
	<jsp:include page="./frags/User_Dlg.jsp"></jsp:include>
	<div class="tt-tmp" args="*"></div>
		<jsp:include page="../pvl/frags/RoleList_Dlg.jsp"></jsp:include>
	</div>
	<div class="tt-tmp" args="roleListDlg=bindRolesDlg">
		<jsp:include page="../pvl/frags/RoleList_Dlg.jsp"></jsp:include>
	</div>
</div>
</body>
</html>