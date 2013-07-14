package com.tt.pvl.filter;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import com.tt.pub.exception.PvlException;
import com.tt.pub.map.TCache;
import com.tt.pub.map.TMap;
import com.tt.pvl.pojo.LoginInfo;
import com.tt.pvl.pojo.Pvl;

/**
 * Desc:登录过滤器。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-12
 *
 */
public class LoginFilter implements ActionFilter {
	private static Log log = Logs.getLog(LoginFilter.class);
	
	private static TMap passActMap = new TMap();
    
    private String logout;
    
    private boolean isFired;

    static {
    	passActMap.put("/login", true);
    	passActMap.put("/logout", true);
    	passActMap.put("/pvl/PvlCtrl/getMenus4Curr", true);
    }
    
    public void setPassActs(List<String> actList){
    	for(String act : actList){
    		passActMap.put(act, true);
    	}
    }
    
    public LoginFilter(String logout, boolean isFired) {
        this.logout = logout;
        this.isFired = isFired;
    }

    public View match(ActionContext context) {
    	if(!isFired) return null;
    	String currPath = context.getPath();
    	log.debug("Current Path: " + currPath);
    	if(passActMap.getBool(currPath)) return null;
    	HttpSession session = Mvcs.getHttpSession();
    	if(session == null)throw new PvlException(logout);
    	LoginInfo loginInfo = TCache.me().getLoginInfo();
    	if(loginInfo == null )throw new PvlException(logout);
    	List<Pvl> urls = loginInfo.getUrls();
    	for(Pvl url : urls){
    		if(currPath.equals(url.getContent())) return null;
    	}
    	throw new PvlException(logout, PvlException.TYPE_NOT_PVL);
    }

}
