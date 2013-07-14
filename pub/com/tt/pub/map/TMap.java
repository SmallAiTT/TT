package com.tt.pub.map;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.nutz.lang.Strings;

/**
 * Desc:自定义的一个map类，继承HashMap，以<String, Object>的形式。
 * 		目的是为了简化代码编写。以<String, Object>的形式是因为这种形式较为常见。
 * 		并且提供了一些常用类型的get方法，不用再手动的去转型。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-14
 *
 */
public class TMap extends HashMap<String, Object>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TMap(){}
	
	public TMap put(String key, Object value){
		super.put(key, value);
		return this;
	}
	public String getStr(String key){
		return getStr(key, null);
	}
	public String getStr(String key, String def){
		Object value = this.get(key);
		if(value == null) return def;
		String str = String.valueOf(value);
		if(str == null) return def;
		return str;
	}
	public int getInt(String key){
		return getInt(key, 0);
	}
	public int getInt(String key, int def){
		Object value = this.get(key);
		if(value == null) return def;
		String str = String.valueOf(value);
		if(Strings.isBlank(str)) return def;
		return Integer.parseInt(str);
	}
	public long getLong(String key){
		return getLong(key, 0);
	}
	public long getLong(String key, long def){
		Object value = this.get(key);
		if(value == null) return def;
		String str = String.valueOf(value);
		if(Strings.isBlank(str)) return def;
		return Long.parseLong(str);
	}
	public double getDouble(String key){
		return getDouble(key, 0);
	}
	public double getDouble(String key, double def){
		Object value = this.get(key);
		if(value == null) return def;
		String str = String.valueOf(value);
		if(Strings.isBlank(str)) return def;
		return Double.parseDouble(str);
	}
	public BigDecimal getBigDecimal(String key){
		return getBigDecimal(key, new BigDecimal(0));
	}
	public BigDecimal getBigDecimal(String key, BigDecimal def){
		Object value = this.get(key);
		if(value == null) return def;
		String str = String.valueOf(value);
		if(Strings.isBlank(str)) return def;
		return new BigDecimal(str);
	}
	public boolean getBool(String key){
		return getBool(key, false);
	}
	public boolean getBool(String key, boolean def){
		Object value = this.get(key);
		if(value == null) return def;
		String str = String.valueOf(value);
		if(Strings.isBlank(str)) return def;
		return Boolean.parseBoolean(str);
	}
	/**
	 * Desc:value格式为：v1,v2,v3。获取到拆分后数组。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getList4Str(String key){
		Object value = super.get(key);
		if(value instanceof String){
			String[] strs = ((String) value).split(",");
			return Arrays.asList(strs);
		}else if(value instanceof String[]){
			return Arrays.asList((String[])value);
		}
		return this.get(key);
	}
	public <T> T get(String key){
		return get(key, null);
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object defValue){
		Object value = super.get(key);
		return value == null ? (T) defValue : (T) value;
	}
	
	public Iterator<String> getKItor(){
		return this.keySet().iterator();
	}



}
