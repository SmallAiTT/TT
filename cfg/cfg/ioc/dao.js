var ioc = {
		// 读取配置文件
		dbCfg : {
			type : "org.nutz.ioc.impl.PropertiesProxy",
			fields : {
				paths : ["cfg/sys/db.properties"]
			}
		},
		// 数据源
		dataSource : {
			type :"com.alibaba.druid.pool.DruidDataSource",
			events : {
				depose :"close"
			},
			fields : {
				driverClassName : {java :"$dbCfg.get('driver')"},
				url             : {java :"$dbCfg.get('url')"},
				username        : {java :"$dbCfg.get('username')"},
				password        : {java :"$dbCfg.get('password')"}
				//initialSize     : 10,
				//maxActive       : 100,
				//testOnReturn    : true,
				//validationQueryTimeout : 5,
				//validationQuery : "select 1"
			}
		},
		dao	:	{
			type	:	"com.tt.pub.dao.TDao",
			args	:	[{refer	:	"dataSource"}]
		}
};