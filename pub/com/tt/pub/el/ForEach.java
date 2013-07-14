package com.tt.pub.el;

import java.util.ArrayList;
import java.util.List;

import org.nutz.el.El;
import org.nutz.el.ElException;
import org.nutz.el.opt.RunMethod;
import org.nutz.lang.util.Context;
import org.nutz.plugin.Plugin;

import com.tt.pub.map.TMap;

/**
 * Desc:
 * forEach(list, name, )
 * @author zheng.xiaojun
 * @since 2013-5-3
 *
 */
public class ForEach  implements RunMethod, Plugin{
	
	/** 前一个对象 */
	private Object pre;					
	/** 当前对象 */
	private Object cur;					
	/** 如果没有指定元素名称，则默认为_item，即items = _item:items */
    private String itemName = "_item";
    /** list的名字 */
    private String listName = "";
    /** 游标 */
    private int index = 0;
	
    //处理方法, fetchParam为函数的参数. 它会将EL中函数括号后的所有内容传递过来
    public Object run(List<Object> fetchParam) {
        if(fetchParam.size() <= 1) throw new ElException("ForEach方法参数错误");
        if(fetchParam.size() == 2) return null;
        
        Context context = (Context) fetchParam.get(0);	//规定第一个参数要传context
        TMap result = (TMap) fetchParam.get(1);			//规定第二个参数要传一个TMap结果集
        String itorInfo = (String) fetchParam.get(2);	//规定第三个参数为迭代对象信息
        String[] iis = itorInfo.split(":");				//迭代对象用":"进行隔开，格式如：_item:items
        if(iis.length == 1){
        	listName = iis[0].trim();
        }else if(iis.length == 2){
        	itemName = iis[0].trim();
        	listName = iis[1].trim();
        }else{
        	throw new ElException("ForEach方法参数错误");
        }
        List<String> expList = new ArrayList<String>();
        for(int i = 3; i < fetchParam.size(); ++i){//之后的参数为String，每个String作为迭代中执行的新的表达式
        	expList.add((String)fetchParam.get(i));
        }
        return handleItor(context, expList, result);
    }
    
    /**
     * Desc:处理迭代内容。
     * @author Small
     * @Email 536762164@qq.com
     * @since 2013-7-5
     * 
     * @param context
     * @param expList
     * @param result
     * @return
     */
    private TMap handleItor(Context context, List<String> expList, TMap result){
        Object list = context.get(listName);
        if(list.getClass().isArray()){
        	Object[] listTemp = (Object[]) list;
        	for(Object item : listTemp){
        		work(result, context, item, expList);
        	}
        } else if(list instanceof List){
        	@SuppressWarnings("unchecked")
			List<Object> listTemp = (List<Object>) list;
        	for(Object item : listTemp){
        		work(result, context, item, expList);
        	}
        } else if(list instanceof String){
        	String[] listTemp = ((String) list).split(",");
        	for(String item : listTemp){
        		work(result, context, item, expList);
        	}
        } else {
        	Object[] listTemp = new Object[]{list};
        	for(Object item : listTemp){
        		work(result, context, item, expList);
        	}
        }
        return result;
    }
    
    /**
     * Desc:处理每个迭代项
     * @author Small
     * @Email 536762164@qq.com
     * @since 2013-7-5
     * 
     * @param result
     * @param context
     * @param item
     * @param expList
     */
    private void work(TMap result, Context context, Object item, List<String> expList){
		pre = cur;
		cur = item;
		context.set("_pre", pre);
		context.set("_index", index++);
		context.set(itemName, cur);
    	for(String exp : expList){
    		String[] strs = exp.split(" = ");
    		Object obj = El.eval(context, strs[1]);
    		context.set(strs[0], obj);
    		if(!strs[0].startsWith("_")){
    			result.put(strs[0], obj);
    		}
    	}
    }
    //是否可以执行
    public boolean canWork() {
        return true;
    }
    //在EL表达式中的函数名
    public String fetchSelf() {
        return "forEach";
    }
    
}
