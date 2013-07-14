package com.tt.pub.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DESC:集合工具类。
 * @author zheng.xiaojun
 * @since 2012-6-8
 *
 */
public class CollUtils {

	/**
	 * Desc:实例化HashMap对象。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	public static <K,V> Map<K,V> newHashMap(){
		return new HashMap<K, V>();
	}
	
	/**
	 * Desc:返回具有线程安全的 ConcurrentHashMap对象。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	public static <K,V> Map<K, V> newConcurrentHashMap(){
		return new ConcurrentHashMap<K, V>();
	}
	
	/**
	 * Desc:实例化ArrayList。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	public static <V> List<V> newArrayList(){
		return new ArrayList<V>();
	}
	
	/**
	 * Desc:实例化LinkedList。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	public static <V> List<V> newLinkedList(){
		return new LinkedList<V>();
	}
	
	/**
	 * Desc:创建栈。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	public static <V> Stack<V> newStack(){
		return new Stack<V>();
	}
}
