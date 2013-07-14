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
<title>权限管理</title>
<script src="script/pvl/PvlPub.js" type="text/javascript"></script>
</head>
<body>

<div class="tt-tmp" args="dlgId='#pvlDlgId'">
	<div class="tt-tmp tt-fire" target=".tt-qry" args="*" data-options="height : 400" data-options="height : 400">
		<jsp:include page="./frags/Pvl_Qry.jsp"></jsp:include>
	</div>
	<jsp:include page="./frags/Pvl_Dlg.jsp"></jsp:include>
</div>
</body>
</html>