package com.tt.pvl.pojo;

import java.util.List;

import com.tt.pub.map.TMap;
import com.tt.pub.utils.CollUtils;
/**
 * Desc:菜单项。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
public class MenuItem {
	/** 唯一标识 */
	private String id;
	/** 菜单名称 */
	private String name;
	/** 菜单在界面上显示的名称 */
	private String text;
	/** 菜单图标 */
	private String icon;
	/** 菜单对应的url */
	private String url;
	private TMap attributes;
	private List<MenuItem> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<MenuItem> getChildren() {
		return children;
	}
	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
	/**
	 * Desc:添加子节点。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param child
	 */
	public void addChild(MenuItem child){
		if(children == null){
			children = CollUtils.newArrayList();
		}
		children.add(child);
	}
	public TMap getAttributes() {
		return attributes;
	}
	public void setAttributes(TMap attributes) {
		this.attributes = attributes;
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
}
