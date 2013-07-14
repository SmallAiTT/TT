<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="tt-dlg" data-options="width : 500"
	titles="r:权限浏览;c:权限新增;u:权限修改;"
	attrs="id:$[dlgId]"
	btnExps="c|u:submit->?*;">
	<div class="tt-tmp" args="defOper='c'" lnkOpers="c;c1;">
		<jsp:include page="./Pvl_Frm.jsp"></jsp:include>
	</div>
	<div class="tt-tmp" args="defOper='u'" lnkOpers="u;u1;">
		<jsp:include page="./Pvl_Frm.jsp"></jsp:include>
	</div>
	<div class="tt-tmp" args="defOper='r'" lnkOpers="r;r1;">
		<jsp:include page="./Pvl_Frm.jsp"></jsp:include>
	</div>
</div>