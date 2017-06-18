package com.mm.mmcalendar.common.okhttp;

import android.os.Handler;
import android.os.Message;

/**
 * 任务执行观察者
 * 
 * @author xxqj
 * 
 */
public abstract class TaskWatcher extends Handler implements Dispatcher {

	/**
	 * 任务开始
	 * 
	 * @param code
	 */
	public void onStart(int code) {
	}

	/**
	 * 任务结束
	 * 
	 * @param code
	 */
	public void onStop(int code) {
	}

	@Override
	public void handleMessage(Message msg) {
		if (msg.what > 0) {
			onSuccess(msg.what, msg.arg1, msg.arg2, msg.obj);
			onStop(msg.what);
		} else {
			onError(-msg.what, msg.arg1, msg.arg2, msg.obj);
			onStop(-msg.what);
		}
		// super.handleMessage(msg);
	}

}
