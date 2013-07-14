package com.tt.pub.dao;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.nutz.dao.Condition;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.tt.pub.map.TCache;
import com.tt.pub.map.TMap;
import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;

/**
 * Desc:自定义Dao，继承与NutDao。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-29
 *
 */
public class TDao extends NutDao {
	private static final String SQL_CFG = "sqlCfg";
	
	public TDao(DataSource dataSource){
		super(dataSource);
	}
	public TDao(DataSource dataSource, SqlManager sqlMgr){
		super(dataSource, sqlMgr);
	}

	/**
	 * Desc:分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param clazz		实体类
	 * @param cnd		条件
	 * @param page		第几页
	 * @param rows		查询的行数
	 * @return
	 */
	public <E> TPager queryP(Class<E> clazz, Condition cnd, int page, int rows){
		int count = this.count(clazz, cnd);
		Pager p = new Pager();
		p.setPageNumber(page);
		p.setPageSize(rows);
		List<E> list = this.query(clazz, cnd, p);
		TPager pager = new TPager(list, count);
		return pager;
	}
	
	/**
	 * Desc:分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param table		要查询的表
	 * @param cnd		条件
	 * @param page		第几页
	 * @param rows		查询的行数
	 * @return
	 */
	public TPager queryP(String table, Condition cnd, int page, int rows){
		int count = this.count(table, cnd);
		Pager p = new Pager();
		p.setPageNumber(page);
		p.setPageSize(rows);
		List<Record> list = this.query(table, cnd, p);
		TPager pager = new TPager(list, count);
		return pager;
	}
	/**
	 * Desc:分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param clazz
	 * @param qry		查询条件
	 * @return
	 */
	public <E> TPager queryP(Class<E> clazz, QryObj qry){
		return queryP(clazz, qry.getSqlCnd(), qry.getPage(), qry.getRows());
	}

	/**
	 * Desc:分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param table		要查询的表
	 * @param qry		查询条件
	 * @return
	 */
	public <E> TPager queryP(String table, QryObj qry){
		return queryP(table, qry.getSqlCnd(), qry.getPage(), qry.getRows());
	}

	/**
	 * Desc:根据查询条件查询（非分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param clazz
	 * @param qry
	 * @return
	 */
	public <E> List<E> query(Class<E> clazz, QryObj qry){
		return this.query(clazz, qry.getSqlCnd());
	}
	/**
	 * Desc:根据查询条件查询（非分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param clazz
	 * @param qry
	 * @return
	 */
	public List<Record> query4Tbl(String table, QryObj qry){
		return this.query(table, qry.getSqlCnd());
	}
	

	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param page
	 * @param rows
	 * @param params
	 * @param vars
	 * @return
	 */
	public <E> TPager queryP(Class<E> clazz, String sqlCode, int page, int rows, TMap params, TMap vars, Condition cnd){
		Sql sql = initSql(clazz, sqlCode, params, vars);
		if(cnd != null) sql.setCondition(cnd);
		execute(sql);
		int count = sql.getList(clazz).size();
		sql.setPager(createPager(page, rows));
		execute(sql);
		List<E> list = sql.getList(clazz);
		return new TPager(list, count);
	}
	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param page
	 * @param rows
	 * @param params
	 * @param vars
	 * @return
	 */
	public <E> TPager queryP(Class<E> clazz, String sqlCode, int page, int rows, TMap params, TMap vars){
		return queryP(clazz, sqlCode, page, rows, params, vars, null);
	}
	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param page
	 * @param rows
	 * @param params
	 * @return
	 */
	public <E> TPager queryP(Class<E> clazz, String sqlCode, int page, int rows, TMap params){
		return queryP(clazz, sqlCode, page, rows, params, null, null);
	}

	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param qry
	 * @return
	 */
	public <E> TPager queryP(Class<E> clazz, String sqlCode, QryObj qry){
		return queryP(clazz, sqlCode, qry.getPage(), qry.getRows(), qry.getPara(), qry.getVar(), qry.getSqlCnd());
	}

	/**
	 * Desc:根据自定义sql获取查询结果（非分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @return
	 */
	public <E> List<E> query(Class<E> clazz, String sqlCode, TMap params){
		return query(clazz, sqlCode, params, null);
	}
	/**
	 * Desc:根据自定义sql获取查询结果（非分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @param vars
	 * @return
	 */
	public <E> List<E> query(Class<E> clazz, String sqlCode, TMap params, TMap vars){
		Sql sql = initSql(clazz, sqlCode, params, vars);
		execute(sql);
		return sql.getList(clazz);
	}

	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param qry
	 * @return
	 */
	public <E> List<E> query(Class<E> clazz, String sqlCode, QryObj qry){
		return query(clazz, sqlCode, qry.getPara(), qry.getVar());
	}
	

	/**
	 * Desc:根据自定义sql获取查询结果（单条记录）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @return
	 */
	public <E> E fetch(Class<E> clazz, String sqlCode, TMap params){
		return fetch(clazz, sqlCode, params, null);
	}
	/**
	 * Desc:根据自定义sql获取查询结果（单条记录）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @param vars
	 * @return
	 */
	public <E> E fetch(Class<E> clazz, String sqlCode, TMap params, TMap vars){
		Sql sql = initSql(clazz, sqlCode, params, vars);
		execute(sql);
		return sql.getObject(clazz);
	}

	/**
	 * Desc:根据自定义sql执行。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param sqlCode
	 * @param params
	 */
	public void exec(String sqlCode, TMap params){
		exec(sqlCode, params, null);
	}
	/**
	 * Desc:根据自定义sql执行。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param sqlCode
	 * @param params
	 * @param vars
	 */
	public void exec(final String sqlCode, final TMap params, final TMap vars){
		Trans.exec(new Atom(){
			@Override
			public void run() {
				Sql sql = initSql(null, sqlCode, params, vars);
				execute(sql);
			}});
	}
	
	/**
	 * Desc:初始化Sql实例。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @param vars
	 * @return
	 */
	private Sql initSql(Class<?> clazz, String sqlCode, TMap params, TMap vars){
		Sql sql = getSqlByCode(sqlCode);	//创建sql
		//遍历，插入参数
		Iterator<String> itor = params.getKItor();
		while(itor.hasNext()){
			String key = itor.next();
			sql.params().set(key, params.get(key));
		}
		if(vars != null){//遍历，插入变量
			Iterator<String> itor1 = vars.getKItor();
			while(itor1.hasNext()){
				String key = itor1.next();
				sql.vars().set(key, vars.get(key));
			}
		}
		if(clazz != null) {//设置entity
			sql.setCallback(Sqls.callback.entities());
			sql.setEntity(getEntity(clazz));
		}
		return sql;
	}

	
	
	/**
	 * Desc:根据sqlCode，加载相应的sqls文件，并获取到期sql内容。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param code
	 * @return
	 */
	private Sql getSqlByCode(String code){
		TCache cache = TCache.me();
		Ioc ioc = cache.getIoc();
		PropertiesProxy sqlCfg = ioc.get(PropertiesProxy.class, SQL_CFG);
		int index = code.indexOf("_");		//通过第一个分割符区分出sqls路径在配置文件中的key
		String key = code.substring(0, index);
		String sqlCode = code.substring(index+1);
		String sqlPath = sqlCfg.get(key);				//获取sqls文件路径
		setSqlManager(new FileSqlManager(sqlPath));		//加载
		Sql sql = sqls().create(sqlCode);				//创建sql
		return sql;
	}
}
