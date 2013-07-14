package com.tt.pvl.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.tt.pub.dao.TDao;
import com.tt.pub.map.TMap;
import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pub.utils.DaoUtils;
import com.tt.pub.utils.TUtils;
import com.tt.pvl.consts.PvlMsg;
import com.tt.pvl.consts.PvlSqls;
import com.tt.pvl.pojo.Pvl;
import com.tt.pvl.service.IPvlService;

@IocBean
@InjectName("pvlService")
public class PvlService implements IPvlService {
	
	@Inject("refer:dao")
	private TDao dao;

	@Override
	public Pvl select(long id) {
		return dao.fetch(Pvl.class, id);
	}

	@Override
	public Pvl select(String code) {
		return dao.fetch(Pvl.class, code);
	}

	@Override
	public List<Pvl> query(QryObj qry) {
		return dao.query(Pvl.class, qry);
	}

	@Override
	public TPager queryP(QryObj qry) {
		return dao.queryP(Pvl.class, qry);
	}

	@Override
	public List<Pvl> getByRole(long roleId) {
		return dao.query(Pvl.class, Cnd.where("1", "=", 1));
	}
	
	@Override
	public List<Pvl> getByRole(long roleId, String type) {
		TMap params = new TMap();
		params.put("roleId", roleId);
		params.put("type", type);
		return dao.query(Pvl.class, PvlSqls.PVL_WITH_ROLE1_Q, params);
	}

	@Override
	public List<Pvl> getByType(String type) {
		return dao.query(Pvl.class, 
				Cnd.where("type", "=", type).asc("module").asc("code"));
	}

	@Override
	public List<Pvl> getByUser(String userId, String type) {
		TMap params = new TMap();
		params.put("userId", userId);
		params.put("type", type);
		return DaoUtils.get(dao, Pvl.class, PvlSqls.PVL_WITH_USER1_Q, params);
	}

	@Override
	public void create(Pvl pvl) {
		Pvl p = dao.fetch(Pvl.class, pvl.getCode());
		TUtils.pass(p == null, PvlMsg.PVL_EXIST);
		dao.insert(pvl);
	}

	@Override
	public void update(Pvl pvl) {
		Pvl p = dao.fetch(Pvl.class, 
				Cnd.where("id", "!=", pvl.getId()).and("code", "=", pvl.getCode()));
		TUtils.pass(p == null, PvlMsg.PVL_EXIST);
		dao.update(pvl);
	}

	@Override
	public void delete(long id) {
		dao.delete(Pvl.class, id);
	}

	@Override
	public void delete(String code) {
		dao.delete(Pvl.class, code);
	}

}
