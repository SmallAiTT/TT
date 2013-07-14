<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table class="tt-tbl" rownumbers="true" singleSelect="true" pagination="true" 
	btnExps="
		c:jq->$[dlg];u|r:jq->$[dlg]?*;d:act->user/UserCtrl/delete;
		roles:jq->$[roleListDlg]?userId=empId;
		"
	btnOpers="q:c,u,r,d,roles;"
	btnTxts="roles:角色"
	btnChgs="roles:q4User"
	titles="q:用户列表"
	acts="q:user/UserCtrl/query"
	
	qryDatas="q:empId&name"
	qryCndExps="q:empId&name=like"
	qryDefSorts="*:empId,name"
	>
  <thead>
    <tr>
      <th field="id" width="100">编号</th>
      <th field="empId" width="100" sortable="true">工号</th>
      <th field="name" width="100" sortable="true">姓名</th>
      <th field="password" width="100">密码</th>
      <th field="sex" width="100" formatter="UserFmt.sex">性别</th>
      <th field="phone" width="100">手机</th>
      <th field="email" width="100">邮箱</th>
      <th field="address" width="100">住址</th>
      <th field="entryDate" width="100">入职日期</th>
      <th field="birthDate" width="100">生日</th>
      <th field="post" width="100">职位</th>
    </tr>
  </thead>
</table>
