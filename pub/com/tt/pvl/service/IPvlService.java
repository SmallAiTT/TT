package com.tt.pvl.service;

import java.util.List;

import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pvl.pojo.Pvl;

public interface IPvlService {

	/**
	 * Desc:根据ID查询
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param id
	 * @return
	 */
	public Pvl select(long id);
	
	/**
	 * Desc:根据code查询
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param code
	 * @return
	 */
	public Pvl select(String code);
	
	/**
	 * Desc:根据QryObj进行动态查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param qry
	 * @return
	 */
	public List<Pvl> query(QryObj qry);
	/**
	 * Desc:根据QryObj进行动态查询。（分页）
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param qry
	 * @return
	 */
	public TPager queryP(QryObj qry);
	
	/**
	 * Desc:根据角色id获取到权限列表
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Pvl> getByRole(long roleId);
	/**
	 * Desc:根据角色id以及权限类型获取到
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param roleId
	 * @param type
	 * @return
	 */
	public List<Pvl> getByRole(long roleId, String type);
	
	/**
	 * Desc:根据类型获取权限列表。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param type
	 * @return
	 */
	public List<Pvl> getByType(String type);
	
	/**
	 * Desc:根据用户ID以及权限类型获取权限列表
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-7-14
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<Pvl> getByUser(String userId, String type);
	
	public void create(Pvl pvl);
	
	public void update(Pvl pvl);
	
	public void delete(long id);
	
	public void delete(String code);
}
