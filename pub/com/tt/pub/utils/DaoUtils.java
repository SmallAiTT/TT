package com.tt.pub.utils;

import java.util.Iterator;
import java.util.List;

import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
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
import com.tt.pub.pojo.TPager;

/**
 * Desc:Dao工具类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-19
 *
 */
public class DaoUtils {

	/**
	 * Desc:分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-19
	 * 
	 * @param dao
	 * @param clazz
	 * @param cnd
	 * @param page
	 * @param rows
	 * @return
	 */
	public static <E> TPager query(Dao dao, Class<E> clazz, Condition cnd, int page, int rows){
		int count = dao.count(clazz, cnd);
		Pager p = new Pager();
		p.setPageNumber(page);
		p.setPageSize(rows);
		List<E> list = dao.query(clazz, cnd, p);
		TPager pager = new TPager(list, count);
		return pager;
	}
	/**
	 * Desc:分页查询
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-19
	 * 
	 * @param dao
	 * @param table
	 * @param cnd
	 * @param page
	 * @param rows
	 * @return
	 */
	public static TPager query(Dao dao, String table, Condition cnd, int page, int rows){
		int count = dao.count(table, cnd);
		Pager p = new Pager();
		p.setPageNumber(page);
		p.setPageSize(rows);
		List<Record> list = dao.query(table, cnd, p);
		TPager pager = new TPager(list, count);
		return pager;
	}
	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param clazz
	 * @param sqlCode
	 * @param page
	 * @param rows
	 * @param params
	 * @return
	 */
	public static <E> TPager query(Dao dao, Class<E> clazz, String sqlCode, int page, int rows, TMap params){
		return query(dao, clazz, sqlCode, page, rows, params, null);
	}
	/**
	 * Desc:通过自定义的sql进行分页查询。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param clazz
	 * @param sqlCode
	 * @param page
	 * @param rows
	 * @param params
	 * @param vars
	 * @return
	 */
	public static <E> TPager query(Dao dao, Class<E> clazz, String sqlCode, int page, int rows, TMap params, TMap vars){
		Sql sql = getSqlByCode((NutDao)dao, sqlCode);	//创建sql
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
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(clazz));
		dao.execute(sql);
		int count = sql.getList(clazz).size();
		sql.setPager(dao.createPager(page, rows));
		dao.execute(sql);
		List<E> list = sql.getList(clazz);
		return new TPager(list, count);
	}
	/**
	 * Desc:根据自定义sql获取查询结果（非分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @return
	 */
	public static <E> List<E> get(Dao dao, Class<E> clazz, String sqlCode, TMap params){
		return get(dao, clazz, sqlCode, params, null);
	}
	/**
	 * Desc:根据自定义sql获取查询结果（非分页）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @param vars
	 * @return
	 */
	public static <E> List<E> get(Dao dao, Class<E> clazz, String sqlCode, TMap params, TMap vars){
		Sql sql = getSqlByCode((NutDao)dao, sqlCode);	//创建sql
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
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(clazz));
		dao.execute(sql);
		return sql.getList(clazz);
	}

	/**
	 * Desc:根据自定义sql获取查询结果（单条记录）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @return
	 */
	public static <E> E select(Dao dao, Class<E> clazz, String sqlCode, TMap params){
		return select(dao, clazz, sqlCode, params, null);
	}
	/**
	 * Desc:根据自定义sql获取查询结果（单条记录）。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param clazz
	 * @param sqlCode
	 * @param params
	 * @param vars
	 * @return
	 */
	public static <E> E select(Dao dao, Class<E> clazz, String sqlCode, TMap params, TMap vars){
		Sql sql = getSqlByCode((NutDao)dao, sqlCode);	//创建sql
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
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(clazz));
		dao.execute(sql);
		return sql.getObject(clazz);
	}

	/**
	 * Desc:根据自定义sql执行。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param sqlCode
	 * @param params
	 */
	public static void exec(Dao dao, String sqlCode, TMap params){
		exec(dao, sqlCode, params, null);
	}
	public static void exec(final Dao dao, final String sqlCode, final TMap params, final TMap vars){
		Trans.exec(new Atom(){
			@Override
			public void run() {
				Sql sql = getSqlByCode((NutDao)dao, sqlCode);	//创建sql
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
				dao.execute(sql);
			}});
	}
	
	/**
	 * Desc:根据sqlCode，加载相应的sqls文件，并获取到期sql内容。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param dao
	 * @param code
	 * @return
	 */
	private static Sql getSqlByCode(NutDao dao, String code){
		TCache cache = TCache.me();
		Ioc ioc = cache.getIoc();
		PropertiesProxy sqlCfg = ioc.get(PropertiesProxy.class, "sqlCfg");
		int index = code.indexOf("_");
		String key = code.substring(0, index);
		String sqlCode = code.substring(index+1);
		String sqlPath = sqlCfg.get(key);
		dao.setSqlManager(new FileSqlManager(sqlPath));
		Sql sql = dao.sqls().create(sqlCode);	//创建sql
		return sql;
	}
	
	/**
	 * Desc:由于Nutz在进行 in 方式时，无法直接通过列表自动注入值，故采用 $list 的方式。该方法为拼接list内容。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-15
	 * 
	 * @param strs
	 * @return
	 */
	public static String getListStr(String strs){
		String[] tmps = strs.split(",");
		StringBuffer listStr = new StringBuffer();
		for(String code : tmps){
			if(listStr.length() > 0) listStr.append(",");
			listStr.append("'").append(code).append("'");
		}
		return listStr.toString();
	}
}
