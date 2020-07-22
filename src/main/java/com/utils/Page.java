package com.utils;

import java.util.List;
/**
 * 工具类/分页功能！
 * @author Administrator
 * @param <T>
 *
 */
public class Page<T> {
	// 总页数！
	private int pageCount;
	// 总记录数！
	private int totalCount;
	// 当前页数！
	private int currPageNo;
	// 页大小！
	private int pageSize;
	// 页新闻数据！
	private List<T> pageList;
	
	/**
	 * 无参数构造方法！
	 */
	public Page() {}

	/**
	 * 两参数的构造方法！
	 * @param currPageNo
	 * @param totalCount
	 */
	public Page(int currPageNo, int pageSize) {
		this.currPageNo = currPageNo;
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		// 加以限制！
		if(totalCount > 0) {
			this.totalCount = totalCount;
			// 计算总页数！
			pageCount = totalCount % pageSize == 0 ?(this.totalCount / pageSize):(this.totalCount / pageSize +1);
		}
	}

	public int getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		// 加以限制！
		if(pageSize > 0 ) {
			this.pageSize = pageSize;
		}
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

}
