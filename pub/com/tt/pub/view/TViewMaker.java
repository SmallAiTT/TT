package com.tt.pub.view;

import org.nutz.ioc.Ioc;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

/**
 * Desc:自定义视图的Maker类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public class TViewMaker implements ViewMaker {
	
	/** 异常视图 */
	public static final String VIEW_EXCEPTION = "exception";
	/** 成功视图 */
	public static final String VIEW_SUCCESS = "success";

	@Override
	public View make(Ioc ioc, String type, String value) {
        type = type.toLowerCase();
        if (VIEW_EXCEPTION.equals(type))//异常
        	 if (Strings.isBlank(value)) 
                 return new ExceptionView(JsonFormat.compact());
             else
                 return new ExceptionView(Json.fromJson(JsonFormat.class, value));
        else if (VIEW_SUCCESS.equals(type))//成功
	       	 if (Strings.isBlank(value)) 
	             return new SuccessView(JsonFormat.compact());
	         else
	             return new SuccessView(Json.fromJson(JsonFormat.class, value));
        return null;
	}

}
