<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="tt-dlg" data-options="width : 500"
	titles="b4Menu:菜单权限;b4url:Url权限;b4Btn:按键权限;"
 	btnExps="b4Menu|b4Url|b4Btn:RoleMgr.bindPvl"
 	btnOpers="b4Menu;b4Url;b4Btn"
	attrs="id:$[bindPvlDlg]"
 	btnTxts="b4Menu|b4Url|b4Btn:绑定"
 	btnIcons="b4Menu|b4Url|b4Btn:icon-save">
	<div class="tt-tmp" lnkOpers="b4Menu;b4Url;b4Btn;">
		<ul class="tt-tree" data-options="animate:true,checkbox:true"
			acts="b4Menu|b4Url|b4Btn:pvl/PvlCtrl/getPvls2Bind">
		</ul>
	</div>
</div>