<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table class="tt-tbl" rownumbers="true" singleSelect="true" pagination="true" 
	btnExps="c:jq->$[dlgId];u|r:jq->$[dlgId]?*;d:act->pvl/PvlCtrl/delete?id"
	titles="q:权限列表"
	acts="q:pvl/PvlCtrl/query"
	
	qryDatas="q:code&name&type&module"
	qryCndExps="q:code=like+&name=like-+&type&module"
	qryDefSorts="q:code"
	>
  <thead>
    <tr>
      <th field="id" width="100" type="hidden">ID</th>
      <th field="code" width="100" sortable="true">权限编号</th>
      <th field="name" width="100">权限名称</th>
      <th field="type" width="100" formatter="PvlFmt.pvlType" sortable="true">权限类型</th>
      <th field="module" width="100" formatter="PvlFmt.module">功能模块</th>
      <th field="content" width="200">权限内容</th>
      <th field="args" width="100">传参</th>
      <th field="isLeaf" width="100" formatter="SysFmt.TF">叶子节点</th>
      <th field="remark" width="100">备注</th>
    </tr>
  </thead>
</table>

