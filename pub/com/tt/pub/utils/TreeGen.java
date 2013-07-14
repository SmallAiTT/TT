package com.tt.pub.utils;

import java.util.List;
import java.util.Stack;
/**
 * Desc:树生成工具。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
public class TreeGen {

	/**
	 * Desc:树生成模板。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 *
	 * @param <E>
	 * @param <V>
	 */
	public static interface ITreeTmp<E, V> {
	
		/**
		 * Desc:将源对象转换为目标对象（树节点）。
		 * @author Small
		 * @Email 536762164@qq.com
		 * @since 2013-6-14
		 * 
		 * @param src
		 * @return
		 */
		public V trans(E src);
		
		/**
		 * Desc:是否将当前node入栈？如果入栈，请记得为节点构造父子关系。
		 * @author Small
		 * @Email 536762164@qq.com
		 * @since 2013-6-14
		 * 
		 * @param node
		 * @param lastNode
		 * @return
		 */
		public boolean isPush(V node, V lastNode);
	}

	/**
	 * Desc:通过有一定规律的数组（请先进行排序），构建树数组。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param srcList
	 * @param tmp
	 * @return
	 */
	public static <E, V> List<V> gen(List<E> srcList, ITreeTmp<E, V> tmp){
		List<V> treeList = CollUtils.newArrayList();
		Stack<V> stack = CollUtils.newStack();
		for(int i = 0; i < srcList.size(); ++i){
			E src = srcList.get(i);
			V node = tmp.trans(src);
			while(stack.size() > 0){//进行栈操作
				V lastNode = stack.lastElement();//取顶级元素进行比较
				if(tmp.isPush(node, lastNode)) {
					stack.push(node);//当前元素进栈
					break;
				}
				else stack.pop();//未匹配到则出栈
			}
			if(stack.size() == 0){//如果栈为空，则为树根
				treeList.add(node);//添加为根节点
				stack.push(node);//当前元素进栈
				continue;
			}
		}
		return treeList;
	}
}
