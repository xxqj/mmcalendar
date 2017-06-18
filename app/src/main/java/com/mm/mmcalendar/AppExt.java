package com.mm.mmcalendar;

import android.app.Application;

/**
 * Created by Administrator on 2016/11/12.
 */
public class AppExt extends Application {

    private  static AppExt application;

    public static AppExt getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;


    }
}
