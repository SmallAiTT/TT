package com.tt.pvl.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.tt.pub.dao.TDao;
import com.tt.pub.map.TMap;
import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pub.utils.DaoUtils;
import com.tt.pub.utils.TUtils;
import com.tt.pvl.consts.PvlMsg;
import com.tt.pvl.consts.PvlSqls;
import com.tt.pvl.pojo.Role;
import com.tt.pvl.pojo.RolePvlRlt;
import com.tt.pvl.pojo.UserRoleRlt;
import com.tt.pvl.service.IRoleService;
/**
 * Desc:角色Service的实现类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@IocBean
public class RoleService implements IRoleService {
	
	@Inject("refer:dao")
	private TDao dao;

	@Override
	public Role select(long id) {
		return dao.fetch(Role.class, id);
	}

	@Override
	public List<Role> query(QryObj qry) {
		return dao.query(Role.class, qry);
	}

	@Override
	public TPager queryP(QryObj qry) {
		return dao.queryP(Role.class, qry);
	}

	@Override
	public TPager queryPByUser(QryObj qry) {
		return dao.queryP(Role.class, PvlSqls.ROLE_WITH_USER_Q, qry);
	}

	@Override
	public void create(Role role) {
		Role r = dao.fetch(Role.class, role.getName());
		TUtils.pass(r == null, PvlMsg.ROLE_EXIST);
		dao.insert(role);
	}

	@Override
	public void update(Role role) {
		Role r = dao.fetch(Role.class, 
				Cnd.where("id", "!=", role.getId()).and("name", "=", role.getName()));
		TUtils.pass(r == null, PvlMsg.ROLE_EXIST);
		dao.update(role);
	}

	@Override
	public void delete(long id) {
		dao.delete(Role.class, id);
	}

	@Override
	public void bindRole(final List<String> userIds, final long roleId) {
		Trans.exec(new Atom(){
			@Override
			public void run() {
				for(String userId : userIds){
					UserRoleRlt urr = dao.fetch(UserRoleRlt.class, 
							Cnd.where("userId", "=", userId).and("roleId", "=", roleId));
					if(urr != null) continue;//已存在就不再进行插入操作。
					UserRoleRlt rlt = new UserRoleRlt();
					rlt.setRoleId(roleId);
					rlt.setUserId(userId);
					dao.fastInsert(rlt);
				}
			}});
	}

	@Override
	public void bindRole(final String userId, final List<Long> roleIds) {
		Trans.exec(new Atom(){
			@Override
			public void run() {
				for(long roleId : roleIds){
					UserRoleRlt urr = dao.fetch(UserRoleRlt.class, 
							Cnd.where("userId", "=", userId).and("roleId", "=", roleId));
					if(urr != null) continue;//已存在就不再进行插入操作。
					UserRoleRlt rlt = new UserRoleRlt();
					rlt.setRoleId(roleId);
					rlt.setUserId(userId);
					dao.fastInsert(rlt);
				}
			}});
	}

	@Override
	public void bindPvl(final long roleId, final List<Long> pvlIds) {
		Trans.exec(new Atom(){
			@Override
			public void run() {
				for(long pvlId : pvlIds){
					RolePvlRlt rpr = dao.fetch(RolePvlRlt.class, 
							Cnd.where("pvlId", "=", pvlId).and("roleId", "=", roleId));
					if(rpr != null) continue;//已存在就不再进行插入操作。
					RolePvlRlt rlt = new RolePvlRlt();
					rlt.setRoleId(roleId);
					rlt.setPvlId(pvlId);
					dao.fastInsert(rlt);
				}
			}});
	}

	@Override
	public void bindPvl(final long roleId, final String pvlCodes, final String type) {
		Trans.exec(new Atom(){
			@Override
			public void run() {
				//先清空原来的权限绑定
				dao.clear(RolePvlRlt.class, Cnd.where("roleId", "=", roleId).and("type", "=", type));
				//在绑定现有选择的权限
				String codes = DaoUtils.getListStr(pvlCodes);
				TMap params = new TMap();
				params.put("roleId", roleId);
				params.put("type", type);
				TMap vars = new TMap();
				vars.put("codes", codes);
				DaoUtils.exec(dao, PvlSqls.BIND_PVLS_4ROLE, params, vars);
			}});
	}

	@Override
	public void unBindRole(final String userId, final List<Long> roleIds) {
		Trans.exec(new Atom(){
			@Override
			public void run() {
				for(long roleId : roleIds){
					dao.clear(UserRoleRlt.class, 
							Cnd.where("userId", "=", userId).and("roleId", "=", roleId));	
				}			
			}
			
		});
		
	}

}
