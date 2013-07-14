package com.tt.pub.msg;

/**
 * DESC:提示信息加载器接口。
 * 		这是一个通用接口，可以根据需求具体实现具体方式。如：
 * 		1)通过properties文件方式存储提示信息；
 * 		2)通过数据库方式存储提示信息。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public interface IMsgLoader {
	
	/**
	 * DESC:通过信息编码获取到对应的提示信息，并为占位符填充相应信息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 *
	 * @param msgCode
	 * @param args
	 * @return
	 */
	public String getMsg(String msgCode, Object... args);
}
