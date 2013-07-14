package com.tt.pvl.consts;

/**
 * Desc:sqlCode的配置，值的前置为PVL_
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-13
 *
 */
public class PvlSqls {

	/** 按照角色查询权限 */
	public static final String PVL_WITH_ROLE_Q = "PVL_PvlWithRole_Q";
	/** 按照角色查询权限(加type关联) */
	public static final String PVL_WITH_ROLE1_Q = "PVL_PvlWithRole1_Q";
	/** 按照用户查询权限(加type关联) */
	public static final String PVL_WITH_USER1_Q = "PVL_PvlWithUser1_Q";
	
	/** 根据用户ID查询出其拥有的角色 */
	public static final String ROLE_WITH_USER_Q = "PVL_RoleWithUser_Q";
	
	/** 为角色绑定权限 */
	public static final String BIND_PVLS_4ROLE = "PVL_BindPvls4Role";
	
}
