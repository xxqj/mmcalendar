package com.mm.mmcalendar.common.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.content.Context;
import android.os.Environment;


import com.mm.mmcalendar.BuildConfig;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Log工具类
 * 
 * @author xxqj
 * @date 2016-11-3
 * 
 */
public class LogUtil {

	private static final String FILE_PATH = "/com/demo/";
	private static final String FILE_NAME = "log.log";

	private static boolean mDebug = BuildConfig.DEBUG;

	public static void init(Context context, boolean debug) {
		mDebug = debug;
		if (!mDebug) {
			return;
		}
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			LogConfigurator logConfigurator = new LogConfigurator();
			logConfigurator.setFileName(Environment.getExternalStorageDirectory() + FILE_PATH + FILE_NAME);
			logConfigurator.setRootLevel(Level.DEBUG);
			logConfigurator.setLevel("org.apache", Level.DEBUG);
			logConfigurator.setMaxFileSize(1024*1024*10);
			logConfigurator.configure();
		} else {
			mDebug = false;
		}
	}

	public static void d(String tag, String message) {
		if (mDebug) {
			System.out.println(message);
			Logger.getLogger(tag).debug(message);
		}
	}

	public static void e(String tag, String message) {
		if (mDebug) {
			System.out.println(message);
			Logger.getLogger(tag).error(message);
		}
	}

	public static void i(String tag, String message) {
		if (mDebug) {
			System.out.println(message);
			Logger.getLogger(tag).info(message);
		}
	}

	public static void w(String tag, String message) {
		if (mDebug) {
			System.out.println(message);
			Logger.getLogger(tag).warn(message);
		}
	}

	public static void f(String tag, String message) {
		if (mDebug) {
			System.out.println(message);
			Logger.getLogger(tag).fatal(message);
		}
	}

}
