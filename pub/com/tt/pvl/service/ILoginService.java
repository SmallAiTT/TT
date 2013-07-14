package com.tt.pvl.service;

import com.tt.pub.map.TMap;

public interface ILoginService {
	public static final String KEY_USER_ID = "userId";
	public static final String KEY_USER_NAME = "userName";

	/**
	 * Desc:登录验证的方法，返回值将被放置到loginInfo中。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-12
	 * 
	 * @param userId		用户ID
	 * @param password		用户名称
	 * @param validCode		验证码
	 * @return
	 */
	public TMap login(String userId, String password, String validCode);
}
