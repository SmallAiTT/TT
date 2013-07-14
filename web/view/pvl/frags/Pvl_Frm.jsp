<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="tt-frm" 
	acts="c:pvl/PvlCtrl/create;u:pvl/PvlCtrl/update">
	<input class="tt-fld" name="id" type="hidden">
	<table>
		<tr>
			<td><label>权限编号：</label></td>
			<td><input class="tt-fld" name="code" requireds="c;u"></td>
			<td><label>权限名称：</label></td>
			<td><input class="tt-fld" name="name" requireds="c;u"></td>
		</tr>
		<tr>
			<td><label>权限类型：</label></td>
			<td><input class="tt-fld" name="type" requireds="c;u" types="*:combobox" datas="*:PvlSel.pvlType"></td>
			<td><label>功能模块：</label></td>
			<td><input class="tt-fld" name="module" requireds="c;u" types="*:combobox" datas="*:PvlSel.module"></td>
		</tr>
		<tr>
			<td><label>叶子节点：</label></td>
			<td><input class="tt-fld" name="isLeaf" requireds="c;u" types="*:combobox" datas="*:SysSel.TF"></td>
		</tr>
		<tr>
			<td><label>权限内容：</label></td>
			<td colspan="3"><textarea class="tt-fld" name="content" cols="50"></textarea></td>
		</tr>
		<tr>
			<td><label>传参：</label></td>
			<td colspan="3"><textarea class="tt-fld" name="args" cols="50"></textarea></td>
		</tr>
		<tr>
			<td><label>备注：</label></td>
			<td colspan="3"><textarea class="tt-fld" name="remark" cols="50"></textarea></td>
		</tr>
	</table>

</form>
