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

<link rel="stylesheet" type="text/css" href="style/tt/tt.css" />
<script src="script/Main.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	Main.init();
});
</script>

<title>TT管理系统</title>
</head>
<body style="height: 100%">
	<div id="mainDiv">
		<div id="mainTop" class="tt-head">
			<div>
				<a id="logoutBtn" href="javascript:;">退出</a>
			</div>
		</div>
		
		<div id="mainCenter" class="easyui-layout" style="width:100%;height:100%;">
			<div id="leftMenu" region="west" split="true" title="菜单"
				style="width:280px;padding1:1px;overflow:hidden;">
				<div id="leftMenuPanel" class="easyui-accordion" fit="true" border="true">
				</div>
			</div>
			<div id="tabPanel" region="center" style="overflow:hidden;">
				<div id="tabs" class="easyui-tabs" fit="true" border="false" >
				</div>
			</div>
		</div>
	</div>

</body>
</html>