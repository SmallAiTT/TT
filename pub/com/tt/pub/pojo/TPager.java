package com.tt.pub.pojo;

import java.util.List;

/**
 * Desc:分页信息包裹类，主要与easyui相对应。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-5-19
 *
 */
public class TPager {

	private List<?> rows;
	private int total;
	
	public TPager(List<?> rows, int total){
		this.rows = rows;
		this.total = total;
	}
	
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
