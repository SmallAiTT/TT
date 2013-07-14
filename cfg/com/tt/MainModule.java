package com.tt;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.tt.pub.view.TViewMaker;
import com.tt.pvl.filter.LoginFilter;


/**
 * Desc:入口类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-14
 *
 */
@Modules(scanPackage=true)
@Views(TViewMaker.class)	//自定义的
@Ok("success")				//自定义的
@Fail("exception")			//自定义的
@Filters(@By(type=LoginFilter.class, args={"ioc:loginFilter"}))	//自定义的，通过ioc管理
@IocBy(type=ComboIocProvider.class, args={
	"*org.nutz.ioc.loader.json.JsonLoader", "cfg/ioc/",
	"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.tt"
})
@SetupBy(value=TSetup.class)
public class MainModule {
	
}