<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<div class="tt-qry">  
	<div class="tt-qry-area" titles="q|sel:权限查询" lnkOpers="q;sel" >
		<div>
			<form class="tt-frm">
				<table>
					<tr>
						<td><label>权限编号:</label></td>
						<td><input class="tt-fld" name="code"></td>
						<td><label>权限名称:</label></td>
						<td><input class="tt-fld" name="name"></td>
						<td><label>权限类型:</label></td>
						<td><input class="tt-fld" name="type" types="*:combobox" datas="*:PvlSel.pvlType"></td>
						<td><label>模块:</label></td>
						<td><input class="tt-fld" name="module" types="*:combobox" datas="*:PvlSel.module"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<jsp:include page="./Pvl_Tbl.jsp"></jsp:include>
</div>   
