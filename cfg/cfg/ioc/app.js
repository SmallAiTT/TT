var app = {
		// 读取配置文件
		sysCfg : {
			type : "org.nutz.ioc.impl.PropertiesProxy",
			fields : {
				paths : ["cfg/sys/db.properties"]
			}
		},
		sqlCfg : {
			type : "org.nutz.ioc.impl.PropertiesProxy",
			fields : {
				paths : ["cfg/sys/sql-cfg.properties"]
			}
		},
		loginFilter : {
			type : "com.tt.pvl.filter.LoginFilter",
			args : ["Logout.jsp", false],
			fields : {
				passActs : []
			}
		},
		
		msgLoader : {
			type : "com.tt.pub.msg.impl.MsgLoader4Prop"
		}
};