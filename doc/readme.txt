tui主要是基于easyui所进行拓展的一套前台快速开发框架，目的在于尽量简化常见的开发编写的代码量并提高复用，使得后期维护时，不需要维护太多界面。
目前只对常见的几种控件如datagrid、linkbutton进行了二次封装，并且只对常用功能进行了封装，后期会根据需求继续添加。


demo里面目前只放置了些简单、常见的例子，由于时间关系，更为复杂的例子以后将会补上。




tt目前提供了个权限框架，主要有如下几方面控制：
1、页面的url控制，如***.jsp
2、action的url控制
3、前台按键的权限控制(还未完全测试通过)
权限开关建web.xml的
	<filter>
		<filter-name>viewFilter</filter-name>
		<filter-class>com.tt.pvl.filter.ViewFilter</filter-class>
		<init-param>
			<param-name>logoutPage</param-name>
			<param-value>Logout.jsp</param-value>
		</init-param>
		<init-param>
			<param-name>fire</param-name>
			<param-value>false</param-value>
		</init-param>
	</filter>
以及app.js的
		loginFilter : {
			type : "com.tt.pvl.filter.LoginFilter",
			args : ["Logout.jsp", false],
			fields : {
				passActs : []
			}
		}
false为关闭权限，true为打开权限。

按键权限控制在TT.js中，将OPEN_BTN_PVL设置为true时打开权限控制。
		
值得注意的是，登录时，需要添加一个loginService，需要继承与ILoginService，
并且在ioc容器中的名字必须为loginService，且只能有一个。

这阵子时间不多，都是平时抽空写的，很多说明都还不够详细，有问题者可以给我发邮件，一起学习一起进步哈。
536762164@qq.com