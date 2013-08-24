package com.tt.pvl.ctrl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import com.tt.pub.map.TCache;
import com.tt.pub.map.TMap;
import com.tt.pvl.consts.PvlConst;
import com.tt.pvl.pojo.LoginInfo;
import com.tt.pvl.service.ILoginService;
import com.tt.pvl.service.IPvlService;

/**
 * Desc:登录相关的controller。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-12
 *
 */
@IocBean
public class LoginCtrl {

	/** 请注意此处，用户登录相关的service必须配置成loginService */
	@Inject("refer:loginService")
	private ILoginService loginService;
	@Inject("refer:pvlService")
	private IPvlService pvlService;

	/**
	 * Desc:登录。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-12
	 * 
	 * @param userId
	 * @param password
	 * @param validCode
	 */
	@At("/login")
	public void login(
			HttpServletRequest req, String userId, String password, String validCode){
		TMap userInfo = loginService.login(userId, password, validCode);
		
		//将用户信息设置到loginInfo中去
		LoginInfo loginInfo = initLoginInfo(req, userInfo, userId);
		TCache.me().setLoginInfo(loginInfo);
	}
	/**
	 * Desc:初始化登录信息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-12
	 * 
	 * @param req
	 * @param userInfo
	 * @return
	 */
	private LoginInfo initLoginInfo(HttpServletRequest req, TMap userInfo, String userId){
		String userName = userInfo.getStr(ILoginService.KEY_USER_NAME);
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserId(userId);												//用户账号
		loginInfo.setUserName(userName);											//用户名
		loginInfo.setLoginTime(new Date());											//登录时间
		loginInfo.setIp(req.getRemoteHost());										//登录者IP
		loginInfo.setMenus(pvlService.getByUser(userId, PvlConst.PVL_TYPE_MENU));	//菜单权限
		loginInfo.setUrls(pvlService.getByUser(userId, PvlConst.PVL_TYPE_URL));		//url权限
		loginInfo.setBtns(pvlService.getByUser(userId, PvlConst.PVL_TYPE_BTN));		//按键权限
		return loginInfo;
	}
	
	@At
	public void logout(){
		TCache.me().setLoginInfo(null);		//清空登录信息
	}
}
