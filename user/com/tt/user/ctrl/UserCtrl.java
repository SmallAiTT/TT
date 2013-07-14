package com.tt.user.ctrl;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.tt.pub.dao.TDao;
import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pub.utils.TUtils;
import com.tt.user.consts.UserMsg;
import com.tt.user.pojo.User;
/**
 * Desc:用户相关的Control。方法都很简单，就不写注释了。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-7-5
 *
 */
@IocBean
@At("user/UserCtrl")
public class UserCtrl {

	@Inject("refer:dao")
	private TDao dao;

	@At
	public void create(@Param("..") User user){
		User u = dao.fetch(User.class, Cnd.where("empId", "=", user.getEmpId()));
		TUtils.pass(u == null, UserMsg.USER_EXIST);
		dao.insert(user);
	}
	@At
	public void update(@Param("..") User user){
		User u = dao.fetch(User.class, Cnd.where("id", "!=", user.getId())
				.and("empId", "=", user.getEmpId()));
		TUtils.pass(u == null, UserMsg.USER_EXIST);
		dao.update(user);
	}
	@At
	public void delete(@Param("..") User user){
		dao.delete(user);
	}
	@At
	public TPager query(@Param("::_qry.") QryObj qry){
		return dao.queryP(User.class, qry);
	}
}
