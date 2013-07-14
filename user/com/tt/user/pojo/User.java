package com.tt.user.pojo;

import java.util.Date;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("T_User")
public class User {

	@Id
	/** id */
	private long id;
	/** 工号 */
	private int empId;
	/** 姓名 */
	private String name;
	/** 密码 */
	private String password;
	/** 性别 */
	private String sex;
	/** 手机 */
	private String phone;
	/** 邮箱 */
	private String email;
	/** 住址 */
	private String address;
	/** 入职日期 */
	private Date entryDate;
	/** 生日 */
	private Date birthDate;
	/** 职位 */
	private String post;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
}
