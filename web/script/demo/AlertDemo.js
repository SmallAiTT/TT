var AlertDemo = {
		alert : function(){
			$tt.alert("这个是三个参数的INFO", "Title INFO", $tt.MSG_TYPE_INFO);
			$tt.alert("这个是三个参数的WARN", "Title WARN", $tt.MSG_TYPE_WARN);
			$tt.alert("这个是三个参数的ERR", "Title ERR", $tt.MSG_TYPE_ERROR);
			$tt.alert("这个是三个参数的ERRtitle为null的", null, $tt.MSG_TYPE_ERROR);
			$tt.alert("这个是两个参数的title为空串的", "");
			$tt.alert("这个是两个参数的title为null的", null);
			$tt.alert("这个是一个参数的");
		},
		alertInfo : function(){
			$tt.alertInfo("这个是有Title的", "Title Info");
			$tt.alertInfo("这个是没有Title的");
			$tt.alertInfo("这个是Title为空字符串的", "");
			$tt.alertInfo("这个是Title为null的", null);
		},
		
		alertWarn : function(){
			$tt.alertWarn("这个是有Title的", "Title Info");
			$tt.alertWarn("这个是没有Title的");
			$tt.alertWarn("这个是Title为空字符串的", "");
			$tt.alertWarn("这个是Title为null的", null);
		},
		
		alertErr : function(){
			$tt.alertErr("这个是有Title的", "Title Info");
			$tt.alertErr("这个是没有Title的");
			$tt.alertErr("这个是Title为空字符串的", "");
			$tt.alertErr("这个是Title为null的", null);
		},
		
		confirm : function(){
			$tt.confirm("这个是有Title的", "Title Info", function(){
				alert("这个是有Title的--->确定");
			});
			$tt.confirm("这个是有Title的，标题为空字符串", "", function(){
				alert("这个是有Title的，标题为空字符串--->确定");
			});
			$tt.confirm("这个是有Title的，标题为null", null, function(){
				alert("这个是有Title的，标题为null--->确定");
			});
			$tt.confirm("这个是没有Title的", function(){
				alert("这个是有Title的--->确定");
			});
		}
};