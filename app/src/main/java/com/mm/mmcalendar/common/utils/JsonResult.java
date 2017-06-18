package com.mm.mmcalendar.common.utils;

import java.lang.reflect.Type;

public class JsonResult<T> {

	private boolean success=true;
	private String msg;

	private int currentPageCount;// 当前页码

	private int pageNumber;// 每页条数

	private int totalRows;// 总记录数
	
	private int totalPageNumber;
	
	private boolean needCount;
	
    public static final String BLANK_RESULT="{\"results\":[]}";
	private T results;

	public JsonResult() {
	}

	public JsonResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
	
	public int getCurrentPageCount() {
		return currentPageCount;
	}

	public void setCurrentPageCount(int currentPageCount) {
		this.currentPageCount = currentPageCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public boolean isNeedCount() {
		return needCount;
	}

	public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
	}

	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}

	public static <T> JsonResult<T> parse(String json, Type type) {
		try {
			return JsonUtils.fromJson(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResult<T> result = new JsonResult<T>(false, "系统错误，请联系管理员");
		return result;
	}

}
