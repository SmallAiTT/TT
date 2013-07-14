package com.tt;

import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.tt.pub.utils.TInit;
import com.tt.pub.utils.TUtils;

public class TSetup implements Setup{
	
	private static Log log = Logs.get();

	@Override
	public void destroy(NutConfig cfg) {
	}

	@Override
	public void init(final NutConfig cfg) {
		log.debug("++++++++++++TSetup init() starts++++++++");
		Trans.exec(new Atom(){
			public void run() {
				TInit.init(cfg);//公共的初始化
				Ioc ioc = cfg.getIoc();
				NutDao dao = ioc.get(NutDao.class, "dao");
				TUtils.createTbls(dao, "com.tt.user.pojo");
				TUtils.createTbls(dao, "com.tt.demo.pojo");
				TUtils.createTbls(dao, "com.tt.pvl.pojo");
			}
		});
		log.debug("++++++++++++TSetup init() ends++++++++++");
	}
}
