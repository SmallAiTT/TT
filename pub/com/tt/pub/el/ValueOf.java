package com.tt.pub.el;

import java.util.List;
import java.util.Map;

import org.nutz.el.ElException;
import org.nutz.el.opt.RunMethod;
import org.nutz.plugin.Plugin;
/**
 * Desc:获取map或者arr或者list的值（key、index、index）。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
public class ValueOf implements RunMethod, Plugin{

	@Override
	public boolean canWork() {
		return true;
	}

	@Override
	public String fetchSelf() {
		return "valueOf";
	}

	@Override
	public Object run(List<Object> fetchParam) {
		if(fetchParam.size() != 2) throw new ElException("参数个数错误！");
		Object obj = fetchParam.get(0);
		if(obj == null) throw new ElException("参数不能为空！");
        if(obj instanceof Map){
        	@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>)obj;
        	return map.get(fetchParam.get(1));
        } else if(obj.getClass().isArray()){
        	Object[] listTemp = (Object[]) obj;
        	int index = (Integer) fetchParam.get(1);
        	return listTemp[index];
        } else if(obj instanceof List){
        	@SuppressWarnings("unchecked")
			List<Object> listTemp = (List<Object>) obj;
        	int index = (Integer) fetchParam.get(1);
        	return listTemp.get(index);
        } else if(obj instanceof String){
        	String[] listTemp = ((String) obj).split(",");
        	int index = (Integer) fetchParam.get(1);
        	return listTemp[index];
        } else{
        	return obj;
        }
	}
}
