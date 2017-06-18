package com.mm.mmcalendar.bmobmodel;

/**
 * Created by Administrator on 2017/2/19.
 */
public class AppVersion extends BaseBmob {

    public static final String restApiUrl="https://api.bmob.cn/1/classes/AppVersion";

    public static final String ITEM_KEY="appVersion";

    protected String  appUrl;

    protected String appUpdateDes;

    protected String appName;

    protected int appCode;

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppUpdateDes() {
        return appUpdateDes;
    }

    public void setAppUpdateDes(String appUpdateDes) {
        this.appUpdateDes = appUpdateDes;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppCode() {
        return appCode;
    }

    public void setAppCode(int appCode) {
        this.appCode = appCode;
    }
}
