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
<title>SelCurd</title>
<script type="text/javascript" src="script/demo/DemoPub.js"></script>
</head>
<body>
<!-- 
注意$[selDlg]为以变量，通过tmp传递参数。
 -->
<div class="tt-tmp" args="dlg='#curdDlg'&selDlg='#curdSelDlg'">
	<div>
		<div>通过弹出框，选择后回填</div>
		<jsp:include page="./frags/Curd_SelDlg.jsp"></jsp:include>
		<form>
			测试回填(按键在form里面)：<input name="test"/>
			<a class="tt-btn" defOper="sel" btnExps="sel:jq->$[selDlg]?code='asdf'" btnMaps="sel:test=code"></a>
		</form>
		<hr>
		<form id="targetFrm">
			测试回填(按键不在form里面)：<input name="test"/>
		</form>
		<a class="tt-btn" defOper="sel" btnTargets="sel:#targetFrm" btnExps="sel:jq->$[selDlg]?code='asdf'" btnMaps="sel:test=code"></a>
	</div>
</div>
</body>
</html>