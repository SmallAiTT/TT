<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<div class="tt-qry">  
	<div class="tt-qry-area" titles="q|sel:用户查询" lnkOpers="q;sel" >
		<div>
			<form>
				<table>
					<tr>
						<td><label>工号:</label></td>
						<td><input class="tt-fld" name="empId"></td>
						<td><label>姓名:</label></td>
						<td><input class="tt-fld" name="name"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<jsp:include page="./User_Tbl.jsp"></jsp:include>
</div>   
