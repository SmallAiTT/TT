package com.tt.pub.exception;

/**
 * Desc:权限异常。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-12
 *
 */
public class PvlException extends RuntimeException{
	
	/** 用户为登录 */
	public static String TYPE_NOT_LOGIN = "notLogin";
	/** 用户没权限 */
	public static String TYPE_NOT_PVL = "notPvl";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Desc:登出界面 */
	private String logout;
	/**	异常类型 */
	private String type;
	public PvlException(String logout){
		this.logout = logout;
		this.type = TYPE_NOT_LOGIN;
	}
	public PvlException(String logout, String type){
		this.logout = logout;
		this.type = type;
	}
	public String getLogout() {
		return logout;
	}
	public void setLogout(String logout) {
		this.logout = logout;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
