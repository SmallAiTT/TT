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
<title>角色管理</title>
<script src="script/pvl/PvlPub.js" type="text/javascript"></script>
<script src="script/pvl/RoleMgr.js" type="text/javascript"></script>
</head>
<body>

<div class="tt-tmp" 
	args="
		dlg='#pvlDlg'&bindPvlDlg='#bindPvlDlg'
		"
	>
	<div class="tt-tmp tt-fire" args="*" target=".tt-qry" data-options="height : 400">
		<jsp:include page="./frags/Role_Qry.jsp"></jsp:include><!-- target -->
	</div>
	<jsp:include page="./frags/Role_Dlg.jsp"></jsp:include>
	<jsp:include page="./frags/BindPvl_Dlg.jsp"></jsp:include>
</div>

</body>
</html>