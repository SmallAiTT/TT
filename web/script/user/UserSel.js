var UserSel = {
		sex : [{
			value : "M", text : "男"
		},{
			value : "W", text : "女"
		}]
};

var UserFmt = {
		sex : function(value){return $tt.fmtSel(value, UserSel.sex);},
		date : function(value, row, index){
			if($tt.isEmpty(value)) return value;
			value = value.substring(0, 10);
//			row.entryDate = value;
			return value;
		}
};



(function($){

	var regexEnum = 
	{
		'int':['^-?[1-9]\\d*$',					'整数'],
		int1:['^[1-9]\\d*$',					'正整数'],
		int2:['^-[1-9]\\d*$',					'负整数'],
		num:['^([+-]?)\\d*\\.?\\d+$',			'数字'],
		num1:['^[1-9]\\d*|0$',					'正数'],
		num2:['^-[1-9]\\d*|0$',					'负数'],
		decmal:['^([+-]?)\\d*\\.\\d+$',			'浮点数'],
		decmal1:['^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$','正浮点数'],
		decmal2:['^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$','负浮点数'],
		decmal3:['^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$','浮点数'],
		decmal4:['^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$', '非负浮点数'],
		decmal5:['^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$','非正浮点数'],
		// ['email:['^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$', '邮件'],
		color:['^[a-fA-F0-9]{6}$',				'颜色'],
	    // url:["^http[s]?:['\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",	'url'],
		chinese:['^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$',					'中文'],
		ascii:['^[\\x00-\\xFF]+$',				'ACSII字符'],
		zipcode:['^\\d{6}$',						'邮编号码'],
		mobile:['^(13|15|18)[0-9]{9}$',				'手机号码'],
		ip4:['^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$',	'ip地址'],
//		notempty:['^\\S+$',						'非空'],
		picture:['(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$',	'图片'],
		rar:['(.*)\\.(rar|zip|7zip|tgz)$',								'压缩文件'],
		date:['^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$',					'日期'],
		qq:['^[1-9]*[1-9][0-9]*$',				'QQ号码'],
		tel:['^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$',	'电话号码'],
		username:['^\\w+$',						'用户名。匹配由数字、26个英文字母或者下划线组成的字符串'],
		letter:['^[A-Za-z]+$',					'字母'],
		letter_u:['^[A-Z]+$',					'大写字母'],
		letter_l:['^[a-z]+$',					'小写字母'],
		idcard:['^[1-9]([0-9]{14}|[0-9]{17})$', '身份证']
	};

	var rules = {};
	$.each(regexEnum, function(name, data){
		rules[name] =
			{ 
				validator: function(value, param){
					return new RegExp(data[0]).test(value); 
				},
				message: "请输入有效的" + data[1]
			};
	});

	$.extend($.fn.validatebox.defaults.rules, rules);

})(jQuery);
