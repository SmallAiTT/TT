package com.tt.pub.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import com.tt.pub.exception.PvlException;
import com.tt.pub.pojo.CtrlResult;
/**
 * Desc:异常处理视图，与@Faile配对，返回CtrlResult实例的json数据。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-20
 *
 */
public class ExceptionView implements View {

    private JsonFormat format;

    private Object data;

    public void setData(Object data) {
        this.data = data;
    }

    public ExceptionView(JsonFormat format) {
        this.format = format;
    }

    public void render(HttpServletRequest req, HttpServletResponse resp, Object obj)
            throws IOException {
    	if(obj!=null){
    		CtrlResult cr = new CtrlResult();
    		if(obj instanceof PvlException){//服务超时
    			PvlException te = (PvlException) obj;
        		cr.setMsgType(CtrlResult.MSG_TYPE_ERR);
        		cr.setValue(te.getLogout());
        		String msg = "服务超时，请重新登录！";
        		if(PvlException.TYPE_NOT_PVL.equals(te.getType())) msg = "您无执行该操作的权限！";
        		cr.setMsg(msg);
    		}else if(obj instanceof RuntimeException){//运行时异常
        		cr.setMsgType(CtrlResult.MSG_TYPE_ERR);
        		cr.setValue(obj);
        		RuntimeException re = (RuntimeException) obj;
        		String msg = re.getMessage();
        		msg = Strings.isEmpty(msg) ? "程序出现异常，请联系管理员！" : msg;
        		cr.setMsg(msg);
    		}else{
        		cr.setMsgType(CtrlResult.MSG_TYPE_ERR);
        		cr.setValue(obj);
        		Exception re = (Exception) obj;
        		String msg = re.getMessage();
        		msg = Strings.isEmpty(msg) ? "程序出现异常，请联系管理员！" : msg;
        		cr.setMsg(msg);
    		}
            Mvcs.write(resp, cr, format);
    	}else{
            Mvcs.write(resp, data, format);
    	}
    }
}
