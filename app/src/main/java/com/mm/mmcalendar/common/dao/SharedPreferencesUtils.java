package com.mm.mmcalendar.common.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

	public static final String SHAREDPREDB_NAME = "LOVECAT";

	/**是否显示女性安全期提示*/
	public static final String IS_SHOW_WOMEN_KEY = "IS_SHOW_WOMEN";

	/**上次月经时间 yyyy-MM-dd*/
	public static final String LAST_MENSEN = "LASTMENSEN";

	/**下次月经时间 yyyy-MM-dd*/
	public static final String NEXT_MENSEN = "NEXTMENSEN";

	public static final String PHOTO_PATH="photopath";

	/**平均行经期*/
	public static final String DAYS = "DAYS";

	/**平均月经周期*/
	public static final String LASTDAYS = "LASTDATES";

	/**预产期*/
	public static final String DUEDATE="duedate";
	/**孕几周*/
	public static final String DUEWEEK="dueweek";

	public static final String SET_SHOW_FLAG="show";

	public static final String TODAY_WHAT="what";



	/**是否显示女性安全期提示*/
	public static boolean isShowWomen(Context ctx,boolean defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getBoolean(IS_SHOW_WOMEN_KEY, defaultValue);

	}
	/**设置是否显示女性安全期提示*/
	public static void setShowWomen(Context ctx, boolean value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putBoolean(IS_SHOW_WOMEN_KEY, value);
		ed.commit();
	}

	/**取上次月经时间 yyyy-MM-dd 默认值当前时间*/
	public static String getLastMensen(Context ctx,String defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getString(LAST_MENSEN, defaultValue);

	}

	/**取上次月经时间 yyyy-MM-dd*/
	public static void setLastMensen(Context ctx,String value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putString(LAST_MENSEN, value);
		ed.commit();
	}

	/**取下次月经时间 yyyy-MM-dd 默认值当前时间*/
	public static String getNextMensen(Context ctx,String defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getString(NEXT_MENSEN, defaultValue);

	}

	/**取下次月经时间 yyyy-MM-dd*/
	public static void setNextMensen(Context ctx,String value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putString(NEXT_MENSEN, value);
		ed.commit();
	}

	/**平均行经期 default return 7*/
	public static int getDays(Context ctx,int defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getInt(DAYS, defaultValue);

	}

	/**平均行经期*/
	public static void setDays(Context ctx, int  value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putInt(DAYS, value);
		ed.commit();
	}
	/**平均月经周期 default return 28*/
	public static int getLastDays(Context ctx,int defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getInt(LASTDAYS, defaultValue);

	}

	/**平均月经周期*/
	public static void setLastDays(Context ctx, int value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putInt(LASTDAYS, value);
		ed.commit();
	}

	public static String getStringValue(Context ctx,String key,String defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getString(key, defaultValue);

	}


	public static void setStringValue(Context ctx, String key,String value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putString(key, value);
		ed.commit();
	}

	public static int getIntValue(Context ctx,String key,int defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		return sp.getInt(key, defaultValue);

	}


	public static void setIntValue(Context ctx, String key,int value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);

		Editor ed = sp.edit();
		ed.putInt(key, value);
		ed.commit();
	}
}
