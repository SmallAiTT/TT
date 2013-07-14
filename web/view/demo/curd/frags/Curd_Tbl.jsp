<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 
tt-tbl即为在datagrid基础上进一步封装的datagrid。
意图在于用同一个html标签内容就可以完成多个相似的功能。
 -->
<!-- 
acts为查询的url。
btnExps为按键操作内容，为按键操作的核心。采用插件式思想，支持用户自定义表达式插件。
插件内容用“->”隔开，前面为插件的名称，用户根据需求解析后面内容。此部分参见TExp.js。
其中：c、u、r、d都已有默认的显示内容，可以通过btnTxts、btnIcons等覆盖。
 -->
<!-- 
qry***表示动态查询的封装。
qryDatas表示往后台传递哪些参数。
qryCndExps将映射到后台的cnd中。
qryParaExps将映射到自定义sql的params中。
qryVarExps将映射到自定义sql的vars中。
 -->
<table class="tt-tbl" rownumbers="true" singleSelect="true" pagination="true" 
	titles="q:CURD列表;sel:CURD选择列表"
	acts="q|sel:demo/CurdCtrl/query;q4Sql:demo/CurdCtrl/queryPBySql"
	btnExps="c:jq->$[dlg];u|r:jq->$[dlg]?*;d:act->demo/CurdCtrl/delete?id;"
	btnPvls="c:DEMO_B_0101"
	
	qryDatas="q|sel|q4Sql:code&name&neCode='111'"
	qryCndExps="q|sel|q4Sql:code=like+&name=like-+"
	qryParaExps="q4Sql:neCode"
	>
  <thead>
    <tr>
      <th field="id" width="100" type="hidden">ID</th>
      <th field="code" width="100" sortable="true">编码</th>
      <th field="name" width="100" sortable="true">名称</th>
      <th field="sel" width="100" formatter="DemoFmt.curdSel">下拉框</th>
      <th field="date" width="100">日期</th>
    </tr>
  </thead>
</table>