package com.tt.user.service.impl;

import org.nutz.dao.Cnd;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.tt.pub.dao.TDao;
import com.tt.pub.map.TMap;
import com.tt.pub.utils.TUtils;
import com.tt.pvl.service.ILoginService;
import com.tt.user.consts.UserMsg;
import com.tt.user.pojo.User;

/**
 * Desc:登录的service，必须取名为loginService。权限模块的loginCtrl将会取该Service。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-7-5
 *
 */
@IocBean
@InjectName
public class LoginService implements ILoginService {

	@Inject("refer:dao")
	private TDao dao;
	@Override
	public TMap login(String userId, String password, String validCode) {
		TMap userInfo = new TMap();
		User user = dao.fetch(User.class, Cnd.where("empId", "=", userId));
		TUtils.pass(user != null, UserMsg.USER_NOT_EXIST);
		TUtils.pass(user.getPassword().equals(password), UserMsg.PWD_ERR);
		//将用户信息返回，其中userId、userName是必须的。
		userInfo.put(ILoginService.KEY_USER_ID, userId);
		userInfo.put(ILoginService.KEY_USER_NAME, user.getName());
		return userInfo;
	}

}
