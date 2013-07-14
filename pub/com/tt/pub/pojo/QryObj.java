package com.tt.pub.pojo;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.lang.Strings;

import com.tt.pub.map.TMap;
import com.tt.pub.utils.CollUtils;
import com.tt.pub.utils.TUtils;

/**
 * Desc:动态查询对象。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-7-2
 *
 */
public class QryObj {
	/** 等于 */
	public static final String CND_EQ = "eq";
	/** 不等 */
	public static final String CND_NE = "ne";
	/** 小于 */
	public static final String CND_LT = "lt";
	/** 小于等于 */
	public static final String CND_LE = "le";
	/** 大于 */
	public static final String CND_GT = "gt";
	/** 大于等于 */
	public static final String CND_GE = "ge";
	/** like %asdf% */
	public static final String CND_LIKE = "like";
	/** like %asdf */
	public static final String CND_LIKE_S = "like-";
	/** like asdf% */
	public static final String CND_LIKE_E = "like+";
	/** like %asdf% */
	public static final String CND_LIKE_SE = "like-+";
	/** in */
	public static final String CND_IN = "in";
	
	/**  表达式与符号值的映射关系 */
	private static Map<String, String> CndMap = CollUtils.newHashMap();
	
	static{
		CndMap.put(CND_EQ, "=");
		CndMap.put(CND_NE, "!=");
		CndMap.put(CND_LT, "<");
		CndMap.put(CND_LE, "<=");
		CndMap.put(CND_GT, ">");
		CndMap.put(CND_GE, ">=");
		CndMap.put(CND_LIKE, "like");
		CndMap.put(CND_LIKE_S, "like");
		CndMap.put(CND_LIKE_E, "like");
		CndMap.put(CND_LIKE_SE, "like");
		CndMap.put(CND_IN, "in");
		CndMap.put("", "=");
	}
	
	/**  */
	private TMap data = new TMap();
	private TMap ignore = new TMap();
	private String paraExp = "";
	private String varExp = "";
	private String cndExp = "";
	private String sort = "";
	private String defSort = "";
	private int rows;
	private int page;
	public TMap getData() {
		return data;
	}
	public void setData(TMap data) {
		this.data = data;
	}
	public TMap getIgnore() {
		return ignore;
	}
	public void setIgnore(TMap ignore) {
		this.ignore = ignore;
	}
	public String getParaExp() {
		return paraExp;
	}
	public void setParaExp(String paraExp) {
		this.paraExp = paraExp;
	}
	public String getVarExp() {
		return varExp;
	}
	public void setVarExp(String varExp) {
		this.varExp = varExp;
	}
	public String getCndExp() {
		return cndExp;
	}
	public void setCndExp(String cndExp) {
		this.cndExp = cndExp;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDefSort() {
		return defSort;
	}
	public void setDefSort(String defSort) {
		this.defSort = defSort;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setPageInfo(int page, int rows){
		this.page = page;
		this.rows = rows;
	}

	/**
	 * Desc:获取条件表达式。
	 * @author Small
	 * @Email 536762164@qq.com
	 * @since 2013-6-29
	 * 
	 * @return
	 */
	public Cnd getSqlCnd(){
		Cnd c = Cnd.where("1", "=", 1);
		List<String[]> argList = parseArgs(cndExp);
		TMap dataMap = getArg(argList);
		for(String[] argExpArr : argList){
			String col = argExpArr[0];
			String conditioin = CndMap.get(argExpArr[1]);
			//值为空，并且要忽略（默认）
			if((data.get(col) == null || Strings.isEmpty(data.getStr(col)))
					&& (ignore.get(col) == null || "".equals(ignore.getStr(col)))) continue;
			c.and(col, conditioin, dataMap.getStr(col));
		}
		String sortExp = TUtils.getValByDef(sort, defSort);
		if(!Strings.isEmpty(sortExp)){//排序
			String[] sorts = sortExp.split(",");
			for(String sort : sorts){
				sort = sort.trim();
				String[] arr = sort.split(" ");
				if(arr.length == 1) c.asc(sort);//默认为升序
				else if(arr.length == 2){
					if(arr[1].trim().toLowerCase().equals("asc")) c.asc(arr[0].trim());
					else if(arr[1].trim().toLowerCase().equals("desc")) c.desc(arr[0].trim());
				}
			}
		}
		return c;
	}

	public TMap getVar(){
		List<String[]> argList = parseArgs(varExp);
		return getArg(argList);
	}
	public TMap getPara(){
		List<String[]> argList = parseArgs(paraExp);
		return getArg(argList);
	}
	public TMap getArg(List<String[]> argList){
		TMap arg = new TMap();
		for(String[] argExpArr : argList){
			if(CND_LIKE.equals(argExpArr[1])) arg.put(argExpArr[0], "%" + data.getStr(argExpArr[0]) + "%");
			else if(CND_LIKE_S.equals(argExpArr[1])) arg.put(argExpArr[0], "%" + data.getStr(argExpArr[0]));
			else if(CND_LIKE_E.equals(argExpArr[1])) arg.put(argExpArr[0], data.getStr(argExpArr[0]) + "%");
			else if(CND_LIKE_SE.equals(argExpArr[1])) arg.put(argExpArr[0], "%" + data.getStr(argExpArr[0]) + "%");
			else if(CND_IN.equals(argExpArr[1])) arg.put(argExpArr[0], data.get(argExpArr[0]));
			else arg.put(argExpArr[0], data.get(argExpArr[0]));
		}
		return arg;
	}
	
	public List<String[]> parseArgs(String argExp){
		List<String[]> list = CollUtils.newArrayList();
		if(Strings.isBlank(argExp)) return list;
		String[] argStrArr = argExp.split("&");
		for(String argStr : argStrArr){
			if(Strings.isBlank(argStr)) continue;
			String[] tmp = argStr.split("=");
			if(tmp.length == 1) list.add(new String[]{tmp[0].trim(), ""});
			else if(tmp.length == 2) list.add(new String[]{tmp[0].trim(), tmp[1].trim()});
		}
		return list;
	}
}
