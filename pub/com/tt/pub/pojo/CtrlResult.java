package com.tt.pub.pojo;

/**
 * Desc:Control返回信息的包装类。
 * 		将要返回的数据放置在value中，并可以设置消息以及消息类型。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-15
 *
 */
public class CtrlResult {

	/** info的消息类型 */
	public static final String MSG_TYPE_INFO = "info";
	/** warning的消息类型 */
	public static final String MSG_TYPE_WARN = "warning";
	/** error的消息类型 */
	public static final String MSG_TYPE_ERR = "error";
	
	/** 提示信息 */
	private String msg;
	/** 提示信息的类型 */
	private String msgType = MSG_TYPE_INFO;
	/** 返回界面的值 */
	private Object value;
	
	public CtrlResult(){}
	public CtrlResult(Object value){
		this.value = value;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	

}
