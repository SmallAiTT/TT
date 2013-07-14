package com.tt.pub.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import com.tt.pub.pojo.CtrlResult;
import com.tt.pub.pojo.TPager;

/**
 * Desc:成功视图，与@Ok配对，目的在于简化control中返回值操作。
 * 		例如，一个insert操作，返回值可以定义为void，操作成功的话，直接会默认帮忙封装提示消息“操作成功！”。
 * 		如果想自定义返回结果，只要返回一个CtrlResult实例即可。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public class SuccessView implements View {

    private JsonFormat format;

    public SuccessView(JsonFormat format) {
        this.format = format;
    }

    public void render(HttpServletRequest req, HttpServletResponse resp, Object obj)
            throws IOException {
    	if(obj!=null){//如果不为空
    		if(obj instanceof TPager) {//分页数据
    			CtrlResult cr = new CtrlResult();
    			cr.setValue(obj);
    			Mvcs.write(resp, cr, format);
    		}else {//直接返回当前数据。
    			Mvcs.write(resp, obj, format);
    		}
    	}else{//void形式时，返回提示信息“操作成功！”
    		CtrlResult cr = new CtrlResult();
    		cr.setMsg("操作成功！");
            Mvcs.write(resp, cr, format);
    	}
    }
}
