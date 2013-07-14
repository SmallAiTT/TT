package com.tt.pvl.pojo;

import org.nutz.dao.entity.annotation.Table;
/**
 * Desc:用户与角色的关系表映射。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@Table("T_UserRoleRlt")
public class UserRoleRlt {

	private String userId;
	private long roleId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
}
