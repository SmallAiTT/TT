package com.tt.pub.map;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;

import com.tt.pub.msg.IMsgLoader;
import com.tt.pvl.pojo.LoginInfo;

/**
 * Desc:系统缓存。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-14
 *
 */
public class TCache  extends TMap{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 默认的IOC配置路径 */
	public static final String DEF_NUT_IOC_PATH = "cfg/ioc/ioc.js";

	/** web根路径的key */
	public static final String BASE_REAL_PATH = "baseRealPath";
	
	/** loginInfo存放在session中对应的key名称 */
	public static final String KEY_LOGIN_INFO = "loginInfo";
	
	/** nutz配置实例 */
	private NutConfig nuztConfig;
	
	/** ioc容器实例 */
	private Ioc ioc;
	
	/** 消息加载器实例 */
	private IMsgLoader msgLoader;
	
	private static TCache cache = new TCache();
	
	private TCache(){};
	
	public static TCache me(){
		cache = cache == null ? new TCache() : cache;
		return cache;
	}

	public NutConfig getNuztConfig() {
		return nuztConfig;
	}

	public void setNuztConfig(NutConfig nuztConfig) {
		this.nuztConfig = nuztConfig;
	}

	public Ioc getIoc() {
		ioc = ioc == null ? new NutIoc(new JsonLoader(DEF_NUT_IOC_PATH)) : ioc;
		return ioc;
	}

	public void setIoc(Ioc ioc) {
		this.ioc = ioc;
	}
	
	public void setBasePath(String path){
		this.put(BASE_REAL_PATH, path);
	}
	
	public String getBasePath(){
		return this.getStr(BASE_REAL_PATH);
	}

	public IMsgLoader getMsgLoader() {
		return msgLoader;
	}

	public void setMsgLoader(IMsgLoader msgLoader) {
		this.msgLoader = msgLoader;
	}
	
	/** 
	 * Desc:设置登录信息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param loginInfo
	 */
	public void setLoginInfo(LoginInfo loginInfo){
		Mvcs.getReq().getSession().setAttribute(KEY_LOGIN_INFO, loginInfo);
	}
	/**
	 * Desc:获取登录信息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	public LoginInfo getLoginInfo(){
		return (LoginInfo) Mvcs.getReq().getSession().getAttribute(KEY_LOGIN_INFO);
	}
}
