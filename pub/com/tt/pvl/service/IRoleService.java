package com.tt.pvl.service;

import java.util.List;

import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pvl.pojo.Role;

public interface IRoleService {

	public Role select(long id);
	
	/**
	 * Desc:动态查询角色。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param qry
	 * @return
	 */
	public List<Role> query(QryObj qry);
	/**
	 * Desc:动态查询角色（分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param qry
	 * @return
	 */
	public TPager queryP(QryObj qry);

	/**
	 * Desc:根据用户动态查询角色（分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param qry
	 * @return
	 */
	public TPager queryPByUser(QryObj qry);
	
	public void create(Role role);
	
	public void update(Role role);
	
	public void delete(long id);
	/**
	 * Desc:绑定角色。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param userIds
	 * @param roleId
	 */
	public void bindRole(List<String> userIds, long roleId);

	/**
	 * Desc:绑定角色。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void bindRole(String userId, List<Long> roleIds);
	/**
	 * Desc:解除角色绑定
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void unBindRole(String userId, List<Long> roleIds);
	
	/**
	 * Desc:绑定权限到角色。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param roleId
	 * @param pvlIds
	 */
	public void bindPvl(long roleId, List<Long> pvlIds);
	/**
	 * Desc:绑定权限到角色。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param roleId
	 * @param pvlCodes
	 * @param type
	 */
	public void bindPvl(long roleId, String pvlCodes, String type);
}
