package com.tt.pub.el;

import java.util.List;

import org.nutz.el.ElException;
import org.nutz.el.opt.RunMethod;
import org.nutz.plugin.Plugin;
/**
 * Desc:获取数组或者list大小。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
public class SizeOf implements RunMethod, Plugin{

	@Override
	public boolean canWork() {
		return true;
	}

	@Override
	public String fetchSelf() {
		return "sizeOf";
	}

	@Override
	public Object run(List<Object> fetchParam) {
		if(fetchParam.size() != 1) throw new ElException("参数个数错误！");
		Object list = fetchParam.get(0);
		if(list == null) throw new ElException("参数不能为空！");
        if(list.getClass().isArray()){
        	Object[] listTemp = (Object[]) list;
        	return listTemp.length;
        } else if(list instanceof List){
        	@SuppressWarnings("unchecked")
			List<Object> listTemp = (List<Object>) list;
        	return listTemp.size();
        } else if(list instanceof String){
        	String[] listTemp = ((String) list).split(",");
        	return listTemp.length;
        } else{
        	return 1;
        }
	}

}
