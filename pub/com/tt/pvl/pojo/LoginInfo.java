package com.tt.pvl.pojo;

import java.util.Date;
import java.util.List;

import com.tt.pub.map.TMap;

/**
 * Desc:用户登录信息。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-12
 *
 */
public class LoginInfo {
	/** 用户账号 */
	private String userId;
	/** 用户名 */
	private String userName;
	/** 用户登录时间 */
	private Date loginTime;
	/** 用户登出时间 */
	private Date logoutTime;
	/** 用户登录IP */
	private String ip;
	/** 是否已经登录 */
	private String isLogined;
	/** 其他属性 */
	private TMap attrs;
	/** 菜单权限 */
	private List<Pvl> menus;
	/** url权限 */
	private List<Pvl> urls;
	/** 按键权限 */
	private List<Pvl> btns;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIsLogined() {
		return isLogined;
	}
	public void setIsLogined(String isLogined) {
		this.isLogined = isLogined;
	}
	public TMap getAttrs() {
		return attrs;
	}
	public void setAttrs(TMap attrs) {
		this.attrs = attrs;
	}
	public List<Pvl> getMenus() {
		return menus;
	}
	public void setMenus(List<Pvl> menus) {
		this.menus = menus;
	}
	public List<Pvl> getUrls() {
		return urls;
	}
	public void setUrls(List<Pvl> urls) {
		this.urls = urls;
	}
	public List<Pvl> getBtns() {
		return btns;
	}
	public void setBtns(List<Pvl> btns) {
		this.btns = btns;
	}
}
