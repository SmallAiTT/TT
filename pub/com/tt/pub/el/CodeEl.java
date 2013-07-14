package com.tt.pub.el;

import org.nutz.el.El;
import org.nutz.lang.util.Context;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.tt.pub.map.TMap;
/**
 * Desc:简单的代码el解析。暂未提供大括号支持。等号两边请记得保持空格。
 * 		格式如下：
 * 		a = 1;
 * 		b = 2;
 * 		c = a + b;
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
public class CodeEl {
	private static Log log = Logs.getLog(CodeEl.class);
	
	public static final String SYS_CXT = "sys_cxt";
	public static final String SYS_MAP = "sys_map";

	public static TMap eval(Context context, String code){
		TMap resultMap = new TMap();
		String[] subExps = code.split(";");
		context.set(SYS_CXT, context);
		context.set(SYS_MAP, resultMap);
		resultMap = (TMap) context.get(SYS_MAP);
		for(String subExp : subExps){
			subExp = subExp.trim();
//			String[] strs = subExp.split(" = ");
			int index = subExp.indexOf(" = ");
			if(index < 0){
				El.eval(context, subExp);
			} else if(index >= 1){
				String name = subExp.substring(0, index);
				String e = subExp.substring(index + 3);
				log.debug(name + "===>" + e);
				Object obj = El.eval(context, e);
				System.out.println(obj);
				context.set(name, obj);
				if(!name.startsWith("_")){//私有不设置到结果集中
					resultMap.put(name, obj);
				}
			} else{
				
			}
		}
		return resultMap;
	}

}
