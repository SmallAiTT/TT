<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="tt-dlg" data-options="width : 600, height : 400"
	titles="sel:CURD选择"
	attrs="id:$[selDlg]"
	btnExps="sel:sel->?*;">
	<div class="tt-tmp" data-options="height : 200" args="*" lnkOpers="sel">
		<jsp:include page="./Curd_Qry.jsp"></jsp:include>
	</div>
</div>