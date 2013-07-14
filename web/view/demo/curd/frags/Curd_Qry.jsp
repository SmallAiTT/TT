<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 
带查询条件的列表查询通过tt-qry包裹。
 -->    
<div class="tt-qry">  
	<!-- 
	tt-qry-area会自动添加上查询和重置按键。
	titles指定标题。
	lnkOpers指定什么操作类型的时候显示查询输入框。
	 -->
	<div class="tt-qry-area" titles="q|sel:CURD查询;q4Sql:自定义Sql查询" lnkOpers="q;sel;q4Sql" >
		<div>
			<form>
				<table>
					<tr>
						<td><label>编码:</label></td>
						<td><input class="tt-fld" name="code"></td>
						<td><label>名称:</label></td>
						<td><input class="tt-fld" name="name"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 
	在此处嵌入列表。
	 -->
	<jsp:include page="./Curd_Tbl.jsp"></jsp:include>
</div>