package com.tt.pub.exception;

import com.tt.pub.utils.TUtils;


/**
 * Desc:消息异常。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public class MsgException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 消息码 */
	protected String msgCode = "";
	/** 传参 */
	protected Object[] args;
	/**
	 * @param e				异常类
	 * @param msgCode		消息码
	 * @param args			传参
	 */
	public MsgException(Throwable e, String msgCode, Object... args){
		super(e);
		this.msgCode = msgCode;
		this.args = args;
	}
	/**
	 * @param msgCode		消息码
	 * @param args			传参
	 */
	public MsgException(String msgCode, Object... args){
		this.msgCode = msgCode;
		this.args = args;
	}

	/**
	 * Desc:获取消息。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 * 
	 * @return
	 */
	public String getMsg(){
		return TUtils.getMsg(msgCode, args);
	}
	
	public String getMessage(){
		return TUtils.getMsg(msgCode, args);
	}
}
