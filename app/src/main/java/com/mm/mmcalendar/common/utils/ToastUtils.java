package com.mm.mmcalendar.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示工具类
 * 
 */
public class ToastUtils {

	private static Toast mToast;

	/**
	 * Toast消息提醒
	 * 
	 * @param context
	 * @param text
	 */
	public static void show(Context context, String text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast消息提醒
	 * 
	 * @param context
	 * @param resId
	 *            R.string.xxx
	 */
	public static void show(Context context, int resId) {
		show(context, resId, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration) {
		show(context, context.getResources().getString(resId), duration);
	}

	public static void show(Context context, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

}
