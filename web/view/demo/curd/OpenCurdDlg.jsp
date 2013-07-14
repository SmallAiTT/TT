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
<title>OpenCurdDlg</title>
<script type="text/javascript" src="script/demo/DemoPub.js"></script>
</head>
<body>

<div class="tt-tmp" args="dlg='#curdDlg'">
	<jsp:include page="./frags/Curd_Dlg.jsp"></jsp:include>
	<div>
		<div>通过按键也可以直接调用弹出框：</div>
		<br>
		<a class="tt-btn" defOper="r" btnExps="r:jq->$[dlg]?code='asdf'&name='name1'" btnTxts="chg:显示浏览窗"></a>	
		<a class="tt-btn" defOper="c" btnExps="c:jq->$[dlg]?code='asdf'" btnTxts="chg:显示新增窗"></a>	
		<a class="tt-btn" defOper="u" btnExps="u:jq->$[dlg]?code='asdf'" btnTxts="chg:显示修改窗"></a>	
	</div>
</div>
</body>
</html>