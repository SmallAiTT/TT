<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form class="tt-frm" acts="c:demo/CurdCtrl/create;u:demo/CurdCtrl/update"
	validConfirms="c:确定要创建？;u:确定要修改？"
	validCusts="u:Curd.cust4Update"
	>
<input name="id" type="hidden"/>
<table>
	<tr>
		<td><label>编码:</label></td>
		<td><input class="tt-fld" name="code" requireds="c;u"></td>
		<td><label>名称:</label></td>
		<td><input class="tt-fld" name="name" requireds="c;u"></td>
	</tr>
	<tr>
		<td><label>下拉框:</label></td>
		<td><input class="tt-fld" name="sel" requireds="" types="*:combobox" datas="*:DemoSel.curdSel"></td>
		<td><label>日期:</label></td>
		<td><input class="tt-fld" name="date" requireds="" types="*:datebox"></td>
	</tr>
</table>
</form>