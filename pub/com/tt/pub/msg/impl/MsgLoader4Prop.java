package com.tt.pub.msg.impl;

import java.util.ResourceBundle;

import org.nutz.lang.Strings;

import com.tt.pub.map.TMap;
import com.tt.pub.msg.IMsgLoader;

/**
 * DESC:通过properties方式获取提示消息
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public class MsgLoader4Prop implements IMsgLoader {
	private static final String SPLIT = "_";
	private String msgCfgFile = "cfg/msg/msg-cfg";
	
	private static ResourceBundle rb;
	private static TMap rbMap = new TMap();
	
	@Override
	public String getMsg(String msgCode, Object... args){
		if(Strings.isBlank(msgCode)){
			return "[ERROR]: msgCode Is Null!";
		}
		String itemName = getItemName(msgCode);
		if(Strings.isBlank(itemName)){
			return fmtMsg(msgCode, args);
//			return "[ERROR]: Item Not Found!----msgCode: " + msgCode;
		}
		String fileName = getMsgFileName(itemName);
		if(Strings.isBlank(fileName)){
			return "[ERROR]: Item Prop File Not Found!----msgCode: " + msgCode;
		}
		ResourceBundle msgRB = rbMap.get(fileName);
		if(msgRB == null){//采用延迟加载
			msgRB = ResourceBundle.getBundle(fileName);
			rbMap.put(fileName, msgRB);
		}
		if(msgRB == null){
			return fmtMsg(msgCode, args);
//			return "[ERROR]: Item Prop File Not Found!----msgCode: " + msgCode;
		}
		String msg = msgRB.getString(msgCode);
		if(Strings.isBlank(msg)){
			return "UN_KNOW";
		}
		return fmtMsg(msg, args);
	}

	/**
	 * Desc: 进行消息格式化。目前的格式化规则为：asdf{0}asdf{1}asdf
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 * 
	 * @param msg
	 * @param args
	 * @return
	 */
	protected String fmtMsg(String msg, Object... args){
		String pattern = msg;
		if(args != null){
			for(int i = 0; i < args.length; ++i){
				String regex = "\\{" + i + "\\}";
				pattern = pattern.replaceAll(regex, args[i].toString());
			}
		}
		return pattern;
	}
	
	/**
	 * DESC:通过模块名称获取到其提示消息的配置文件
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 *
	 * @param itemName
	 * @return
	 */
	private String getMsgFileName(String itemName){
		if(rb == null){//采用延迟加载
			rb = ResourceBundle.getBundle(msgCfgFile);
		}
		return rb.getString(itemName);
	}
	
	
	/**
	 * DESC:通过消息编码获取到相应的模块的名称
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-5-20
	 *
	 * @param msgCode
	 * @return
	 */
	private String getItemName(String msgCode){
		String[] buffArr = msgCode.split(SPLIT);
		if(buffArr.length <=1){
			return null;
		}
		return buffArr[0];
	}

	public String getMsgCfgFile() {
		return msgCfgFile;
	}

	public void setMsgCfgFile(String msgCfgFile) {
		this.msgCfgFile = msgCfgFile;
	}

}
