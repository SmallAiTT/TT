package com.tt.pvl.pojo;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * Desc:权限。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@Table("T_Pvl")
public class Pvl {
	
	@Id
	private long id;
	/** 权限编码 */
	@Name
	private String code;
	private String name;
	/** 类型：menu/url/btn */
	private String type;
	/** 功能模块 */
	private String module;
	/** 权限内容 */
	private String content;
	/** 传参 */
	private String args;
	/** 备注 */
	private String remark;
	private String isLeaf;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

}
