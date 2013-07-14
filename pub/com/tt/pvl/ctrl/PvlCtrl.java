package com.tt.pvl.ctrl;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.tt.pub.map.TCache;
import com.tt.pub.pojo.CtrlResult;
import com.tt.pub.pojo.QryObj;
import com.tt.pub.pojo.TPager;
import com.tt.pub.utils.CollUtils;
import com.tt.pub.utils.TUtils;
import com.tt.pub.utils.TreeGen;
import com.tt.pvl.consts.PvlConst;
import com.tt.pvl.consts.PvlMsg;
import com.tt.pvl.pojo.LoginInfo;
import com.tt.pvl.pojo.Pvl;
import com.tt.pvl.service.IPvlService;
import com.tt.pvl.tmp.MenuTreeTmp;
/**
 * Desc:权限的control类。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
@IocBean
@At("/pvl/PvlCtrl/")
public class PvlCtrl {
	
	@Inject("refer:pvlService")
	private IPvlService pvlService;
	
	@At
	public void create(@Param("..") Pvl pvl){
		pvlService.create(pvl);
	}

	@At
	public void update(@Param("..") Pvl pvl){
		pvlService.update(pvl);
	}

	@At
	public CtrlResult select(long id, String code){
		if(Strings.isEmpty(code)) return new CtrlResult(pvlService.select(id));
		return new CtrlResult(pvlService.select(code));
	}

	@At
	public void delete(long id, String code){
		if(Strings.isEmpty(code)) pvlService.delete(id);
		else pvlService.delete(code);
	}

	@At
	public TPager query(@Param("::_qry.") QryObj qry){
		return pvlService.queryP(qry);
	}
	
	@At
	public CtrlResult get(String type){
		LoginInfo loginInfo = TCache.me().getLoginInfo();
		if(PvlConst.PVL_TYPE_MENU.equals(type)) return new CtrlResult(loginInfo.getMenus());
		if(PvlConst.PVL_TYPE_URL.equals(type)) return new CtrlResult(loginInfo.getUrls());
		if(PvlConst.PVL_TYPE_BTN.equals(type)) return new CtrlResult(loginInfo.getBtns());
		return new CtrlResult(pvlService.query(null));
	}

	/**
	 * Desc:获取到菜单列表。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-14
	 * 
	 * @return
	 */
	@At
	public CtrlResult getMenus4Curr(){
		List<Pvl> allMenus = pvlService.getByType(PvlConst.PVL_TYPE_MENU);
		TCache cache = TCache.me();
		LoginInfo loginInfo = cache.getLoginInfo();
		List<Pvl> myMenus = loginInfo.getMenus();
		List<Pvl> menus = CollUtils.newArrayList();
		int index1 = 0, index2 = 0;
		for(; index1 < myMenus.size();index1++){
			Pvl m1 = myMenus.get(index1);
			if(!"T".equals(m1.getIsLeaf())){
				index1++;
				continue;
			}
			boolean start = true;
			for(; index2 < allMenus.size();){
				Pvl m2 = allMenus.get(index2);
				if(m1.getCode().startsWith(m2.getCode())){
					menus.add(m2);
					index2++;
					start = false;
				}else if(start){
					index2++;
				}else{
					break;
				}
			}
		}
		return new CtrlResult(TreeGen.gen(menus, new MenuTreeTmp()));
	}
	
	@At
	public CtrlResult getPvls2Bind(long roleId, String type){
		TUtils.pass((PvlConst.PVL_TYPE_MENU.equals(type) 
				|| PvlConst.PVL_TYPE_URL.equals(type)
				|| PvlConst.PVL_TYPE_BTN.equals(type))
				, PvlMsg.PVL_TYPE_ERR);
		List<Pvl> myPvls = pvlService.getByRole(roleId, type);
		List<Pvl> pvls = pvlService.getByType(type);
		return new CtrlResult(TreeGen.gen(pvls, new MenuTreeTmp(myPvls)));
	}
}
