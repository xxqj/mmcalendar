package com.mm.mmcalendar.common;

public abstract class BasePageFragment extends BaseFragment {

	// 总记录数
	protected int rowCount;
	// 每页条数
	protected int pageSize = 12;
	// 当前页号
	protected int pageIndex = 1;
	// 总页�?
	private int pageCount = 1;

	public int getPageCount() {
		if (pageCount == 1)
			statisticsPageCount();
		return pageCount;
	}

	/** 计算页数 */
	private void statisticsPageCount() {
		int tempVar = rowCount % pageSize;
		if (tempVar == 0) {
			pageCount = rowCount / pageSize;
		} else {
			pageCount = rowCount / pageSize + 1;
		}
	}


}
