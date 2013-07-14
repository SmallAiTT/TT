package com.tt.pvl.pojo;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * Desc:角色。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@Table("T_Role")
public class Role {
	@Id
	private long id;
	@Name
	private String name;
	private String remark;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
