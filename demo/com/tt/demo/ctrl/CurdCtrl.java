package com.tt.demo.ctrl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.tt.demo.consts.DemoMsg;
import com.tt.demo.consts.DemoSqls;
import com.tt.demo.pojo.Curd;
import com.tt.pub.dao.TDao;
import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pub.utils.TUtils;

@IocBean
@At("demo/CurdCtrl")
public class CurdCtrl {
	protected static Log log = Logs.getLog(CurdCtrl.class);

	@Inject("refer:dao")
	private TDao dao;

	@At
	public Curd select(long id){
		return dao.fetch(Curd.class, id);
	}
	@At
	public void create(@Param("..") Curd curd){
		Curd c = dao.fetch(Curd.class, curd.getCode());
		TUtils.pass(c == null, DemoMsg.CURD_EXIST);
		dao.insert(curd);
	}
	
	@At
	public void update(@Param("..") Curd curd){
		Curd c = dao.fetch(Curd.class, 
				Cnd.where("id", "!=", curd.getId())
				.and("code", "=", curd.getCode()));
		TUtils.pass(c == null, DemoMsg.CURD_EXIST);
		dao.update(curd);
	}
	
	@At
	public void delete(long id){
		dao.delete(Curd.class, id);
	}
	
	@At
	public List<Curd> queryAll(){
		return dao.query(Curd.class, Cnd.orderBy().asc("name"));
	}

	@At
	public TPager query(@Param("::_qry.") QryObj qry){
		return dao.queryP(Curd.class, qry);
	}

	@At
	public TPager queryPBySql(@Param("::_qry.") QryObj qry){
		return dao.queryP(Curd.class, DemoSqls.CURD_Q, qry);
	}
}
