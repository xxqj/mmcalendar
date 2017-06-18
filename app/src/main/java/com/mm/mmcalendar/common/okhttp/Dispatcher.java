package com.mm.mmcalendar.common.okhttp;

/**
 * 消息派发者
 * @author xxqj
 *
 */
public interface Dispatcher {
	
	/**
	 * 成功
	 * @param code
	 * @param arg2
	 * @param obj
	 */
	public void onSuccess(int code, int arg1, int arg2, Object obj);
	
	/**
	 * 失败
	 * @param code
	 * @param arg2
	 * @param obj
	 */
	public void onError(int code, int arg1, int arg2, Object obj);

}
