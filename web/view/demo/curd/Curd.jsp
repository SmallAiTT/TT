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
<title>Curd</title>
<script type="text/javascript" src="script/demo/DemoPub.js"></script>
<script type="text/javascript" src="script/demo/Curd.js"></script>
</head>
<body>
<!-- 
tt-tmp 中的args将为tui插件进行参数传递，每个tui插件的参数取值为第一个父tmp的传参。
args="*"时，将为把该tmp的父tmp所有参数取过来，args的格式为：*&k1=v1&k2&k3='asdf'。
每个tmp的取值都将先从父tmp中取。
 -->
<div class="tt-tmp" args="dlg='#curdDlg'">
	<!-- 
	tt-fire样式用来表示自动触发，用target属性来制定触发的对象，用oper制定操作类型。
	tt-tmp中的data-options用于改变子easyui插件的data-options属性。
	 -->
	<div class="tt-tmp tt-fire" target=".tt-qry" oper="q" args="*" data-options="height : 300">
		<jsp:include page="./frags/Curd_Qry.jsp"></jsp:include>
	</div>
	<jsp:include page="./frags/Curd_Dlg.jsp"></jsp:include>
</div>
</body>
</html>