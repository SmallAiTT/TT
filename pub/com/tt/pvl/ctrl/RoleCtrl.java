package com.tt.pvl.ctrl;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pub.utils.CollUtils;
import com.tt.pvl.pojo.Role;
import com.tt.pvl.service.IRoleService;

/**
 * Desc:角色的Control类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@IocBean
@At("/pvl/RoleCtrl/")
public class RoleCtrl {
	
	@Inject("refer:roleService")
	private IRoleService roleService;
	
	@At
	public void create(@Param("..") Role role){
		roleService.create(role);
	}

	@At
	public void update(@Param("..") Role role){
		roleService.update(role);
	}

	@At
	public void delete(long id){
		roleService.delete(id);
	}

	@At
	public TPager query(@Param("::_qry.") QryObj qry){
		return roleService.queryP(qry);
	}
	
	@At
	public TPager query4User(@Param("::_qry.") QryObj qry){
		return roleService.queryPByUser(qry);
	}
	
	@At
	public void bindPvls(long roleId, String pvlCodes, String type){
		roleService.bindPvl(roleId, pvlCodes, type);
	}
	
	@At
	public void bindRoles(String userId, String roleIds){
		String[] roleIdArr = roleIds.split(",");
		List<Long> roleIdList = CollUtils.newArrayList();
		for(String roleIdStr : roleIdArr){
			roleIdList.add(Long.parseLong(roleIdStr));
		}
		roleService.bindRole(userId, roleIdList);
	}
	@At
	public void unBindRoles(String userId, String roleIds){
		String[] roleIdArr = roleIds.split(",");
		List<Long> roleIdList = CollUtils.newArrayList();
		for(String roleIdStr : roleIdArr){
			roleIdList.add(Long.parseLong(roleIdStr));
		}
		roleService.unBindRole(userId, roleIdList);
	}
}
