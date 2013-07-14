<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>系统登录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<link href="pub/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="pub/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="pub/charisma/css/charisma-app.css" rel="stylesheet">
	
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
		
			<div class="row-fluid">
				<div class="span12 center login-header"><h2>大棚管理系统</h2></div>
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div id="info" class="alert alert-info">请输入用户名/密码进行登录</div>
					<form id="frmLogin" class="form-horizontal" method="post">
						<fieldset>
							<div class="input-prepend" title="员工号" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span>
								<input autofocus class="input-large span10" name="userId" type="text" value="admin" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="密码" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span>
								<input class="input-large span10" name="password" type="password" value="111111" />
							</div>
						</fieldset>
					</form>
							<p class="center span5">
							<button id="btnLogin" class="btn btn-primary">Login</button>
							</p>
				</div><!--/span-->
			</div><!--/row-->
		</div><!--/fluid-row-->
	</div><!--/.fluid-container-->
	
	<form class="form-horizontal" id="frm4Main" action="view/Main.jsp" method="post"></form>
	
	
	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<script src="pub/jquery-1.8.0.min.js"></script>
	<script src="pub/bootstrap/js/bootstrap.js"></script>

	<script type="text/javascript">
	$(function(){
		$("#errInfo").hide();
		$("#btnLogin").click(function(){
			var action = "login";
			$.post(action, $("#frmLogin").serialize(), function(ctrlResult) {
				if(ctrlResult.msgType == "error"){
            		$("#info").html(ctrlResult.msg).removeClass("alert-info").addClass("alert-error");
            	}else if(ctrlResult.msgType == "info"){
            		$("#frm4Main").submit();
            	}
			}, "json");
		});
	});
	</script>
</body>
</html>
