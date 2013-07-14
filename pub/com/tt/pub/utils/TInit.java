package com.tt.pub.utils;

import javax.servlet.ServletContext;

import org.nutz.conf.NutConf;
import org.nutz.el.opt.custom.CustomMake;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;

import com.tt.pub.map.TCache;
import com.tt.pub.msg.IMsgLoader;

/**
 * Desc:初始化时进行公共操作。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-14
 *
 */
public final class TInit {

	/**
	 * Desc:进行系统初始化工作。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-14
	 * 
	 * @param cfg
	 */
	public static void init(NutConfig cfg){
		TCache cache = TCache.me();
		cache.setNuztConfig(cfg);//保存当前的NutConfig
		ServletContext cxt = cfg.getServletContext();
		if(cxt != null) cache.setBasePath(cxt.getRealPath(""));//保存web服务的路径
		Ioc ioc = cfg.getIoc();
		cache.setIoc(ioc);//保存当前的ioc

		//保存消息加载器实例到cache中
		IMsgLoader msgLoader = ioc.get(IMsgLoader.class, "msgLoader");
		cache.setMsgLoader(msgLoader);
		
		NutConf.load("com/tt/pub/el/el.js");
		CustomMake.init();
		
	}
}
