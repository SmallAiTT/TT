<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 
attrs属性表示要替换掉的当前对象的属性值，值内容从tmp中取。
此属性的目的在于为模板动态决定某些属性值，例如id。
 -->
<div class="tt-dlg" data-options="width : 500"
	titles="r:CURD浏览;c:CURD新增;u:CURD修改;"
	attrs="id:$[dlg]"
	btnExps="c|u:submit->?*;">
	<div class="tt-tmp" args="defOper='c'" lnkOpers="c;c1;">
		<jsp:include page="./Curd_Frm.jsp"></jsp:include>
	</div>
	<div class="tt-tmp" args="defOper='u'" lnkOpers="u;u1;">
		<jsp:include page="./Curd_Frm.jsp"></jsp:include>
	</div>
	<div class="tt-tmp" args="defOper='r'" lnkOpers="r;r1;">
		<jsp:include page="./Curd_Frm.jsp"></jsp:include>
	</div>
</div>