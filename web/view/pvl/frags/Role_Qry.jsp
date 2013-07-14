<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<div class="tt-qry">  
	<div class="tt-qry-area" titles="q|q4User|b4User:角色查询" lnkOpers="q;q4User;b4User" >
		<div>
			<form>
				<input name="userId" type="hidden">
				<table>
					<tr>
						<td><label>名称:</label></td>
						<td><input class="tt-fld" name="name"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<jsp:include page="./Role_Tbl.jsp"></jsp:include>
</div>   
