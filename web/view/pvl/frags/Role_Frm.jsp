<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="tt-frm" 
	acts="c:pvl/RoleCtrl/create;u:pvl/RoleCtrl/update">
	<input class="tt-fld" name="id" type="hidden">
	<table>
		<tr>
			<td><label>角色名称：</label></td>
			<td><input class="tt-fld" name="name" requireds="c;u"></td>
		</tr>
		<tr>
			<td><label>备注：</label></td>
			<td><textarea class="tt-fld" name="remark" cols="50"></textarea></td>
		</tr>
	</table>

</form>
