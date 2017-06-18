package com.mm.mmcalendar.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2016/9/22.
 */
public class SharedPreferenceUtils {

    public static String getPrefString(Context ctx,String key,final String defaultValue){
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString(key,defaultValue);
    }

    public static void setPrefString(Context ctx,final String key,final String value){
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        preferences.edit().putString(key,value).commit();
    }


    public static boolean getPrefBoolean(Context ctx,String key,final boolean defaultValue){
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getBoolean(key,defaultValue);
    }

    public static void setPrefBoolean(Context ctx,String key,final boolean value){
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        preferences.edit().putBoolean(key,value);
    }


}
