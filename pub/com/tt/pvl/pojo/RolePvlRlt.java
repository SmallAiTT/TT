package com.tt.pvl.pojo;

import org.nutz.dao.entity.annotation.Table;
/**
 * Desc:角色与权限的关联映射。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@Table("T_RolePvlRlt")
public class RolePvlRlt {

	private long roleId;
	private long pvlId;
	private String type;
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getPvlId() {
		return pvlId;
	}
	public void setPvlId(long pvlId) {
		this.pvlId = pvlId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
