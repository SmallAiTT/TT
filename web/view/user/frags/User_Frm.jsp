<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="tt-frm" 
	acts="c:user/UserCtrl/create;u:user/UserCtrl/update">
	<input class="tt-fld" name="id" type="hidden">
	<table>
		<tr>
			<td><label>工号：</label></td>
			<td><input class="tt-fld" name="empId" requireds="c;u" types="*:numberbox"></td>
			<td><label>姓名：</label></td>
			<td><input class="tt-fld" name="name" requireds="c;u"></td>
		</tr>
		<tr>
			<td><label>密码：</label></td>
			<td><input class="tt-fld" name="password" requireds="c;u"></td>
			<td><label>性别：</label></td>
			<td><input class="tt-fld" name="sex" readonlys="u" types="*:combobox" datas="*:UserSel.sex"></td>
		</tr>
		<tr>
			<td><label>手机：</label></td>
			<td><input class="tt-fld" name="phone"></td>
			<td><label>邮箱：</label></td>
			<td><input class="tt-fld" name="email"></td>
		</tr>
		<tr>
			<td><label>住址：</label></td>
			<td><input class="tt-fld" name="address"></td>
			<td><label>入职日期：</label></td>
			<td><input class="tt-fld" name="entryDate" types="*:datebox"></td>
		</tr>
		<tr>
			<td><label>生日：</label></td>
			<td><input class="tt-fld" name="birthDate" types="*:datebox"></td>
			<td><label>职位：</label></td>
			<td><input class="tt-fld" name="post"></td>
		</tr>
	</table>

</form>
