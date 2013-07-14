package com.tt.pvl.pojo;

import java.util.List;

import com.tt.pub.map.TMap;
import com.tt.pub.utils.CollUtils;

public class TreeNode4EUi {
	
	public static final String KEY_URL = "url";
	public static final String KEY_ARGS = "args";
	
	private String id;
	private String text;
	private String iconCls;
	private boolean checked = false;
	/** open/colsed */
	private String state = "open";

	private TMap attributes = new TMap();
	private List<TreeNode4EUi> children = CollUtils.newArrayList();;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public TMap getAttributes() {
		return attributes;
	}
	public void setAttributes(TMap attributes) {
		this.attributes = attributes;
	}
	public List<TreeNode4EUi> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode4EUi> children) {
		this.children = children;
	}

	/**
	 * Desc:添加到attributes中。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param key
	 * @param value
	 */
	public void addAttr(String key, Object value){
		if(attributes == null){
			attributes = new TMap();
		}
		attributes.put(key, value);
	}
	/**
	 * Desc:添加子节点。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param child
	 */
	public void addChild(TreeNode4EUi child){
		if(children == null){
			children = CollUtils.newArrayList();
		}
		children.add(child);
	}
	
	/**
	 * Desc:设置菜单对应的URL。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param url
	 */
	public void setUrl(String url){
		this.addAttr(KEY_URL, url);
	}
	
	/**
	 * Desc:设置菜单节点对应的传参。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param args
	 */
	public void setArgs(TMap args){
		this.addAttr(KEY_ARGS, args);
	}
}
