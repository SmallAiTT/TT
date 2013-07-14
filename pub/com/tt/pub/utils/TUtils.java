package com.tt.pub.utils;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Strings;
import org.nutz.resource.Scans;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.tt.pub.exception.MsgException;
import com.tt.pub.map.TCache;
import com.tt.pub.msg.IMsgLoader;
import com.tt.pub.pojo.CtrlResult;
/**
 * Desc:工具类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public final class TUtils {

	/**
	 * Desc:根据消息码获取消息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 * 
	 * @param msgCode		消息码
	 * @param args			传参
	 * @return
	 */
	public static String getMsg(String msgCode, Object... args){
		return getMsg(null, msgCode, args);
	}
	/**
	 * Desc:根据消息码获取消息，可以指定消息加载器。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 * 
	 * @param msgLoader		消息加载器
	 * @param msgCode		消息码
	 * @param args			传参
	 * @return
	 */
	public static String getMsg(IMsgLoader msgLoader, String msgCode, Object... args){
		msgLoader = msgLoader == null ? TCache.me().getMsgLoader() : msgLoader;
		return msgLoader.getMsg(msgCode, args);
	}

	/**
	 * Desc:
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 * 
	 * @param judge			判断是否通过
	 * @param msgCode		消息码
	 * @param args			传参
	 */
	public static void pass(boolean judge, String msgCode, Object... args){
		if(!judge){
			throw new MsgException(msgCode, args);
		}
	}
	/**
	 * Desc:封装成功信息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-21
	 * 
	 * @param value			返回的实际数据
	 * @param msgCode		消息码
	 * @param args			传参
	 * @return
	 */
	public static CtrlResult getOk(Object value, String msgCode, Object... args){
		CtrlResult cr = new CtrlResult();
		cr.setValue(value);
		cr.setMsg(getMsg(msgCode, args));
		return cr;
	}
	/**
	 * Desc:封装成功信息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-21
	 * 
	 * @param msgCode		消息码
	 * @param args			传参
	 * @return
	 */
	public static CtrlResult getOk(String msgCode, Object... args){
		return getOk(null, msgCode, args);
	}
	
	/**
	 * Desc:初始化表。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-21
	 * 
	 * @param dao
	 * @param pkg
	 */
	public static void createTbls(final Dao dao, final String pkg){
		Trans.exec(new Atom(){
			public void run() {
				for(Class<?> clazz : Scans.me().scanPackage(pkg)){
					if(null != clazz.getAnnotation(Table.class)){
						dao.create(clazz, false);
					}
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public static <E> E getValByDef(Object val, Object defVal){
		if(val instanceof String && Strings.isBlank((String)val)) return (E)defVal;
		if(val == null) return (E)defVal;
		return (E)val;
	}
	
}
