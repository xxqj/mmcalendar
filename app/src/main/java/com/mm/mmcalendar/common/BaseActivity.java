package com.mm.mmcalendar.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mm.mmcalendar.common.utils.AppManager;

public class BaseActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		AppManager.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		AppManager.finishActivity(this);
		super.onDestroy();
	}

	/**
	 * 获取当前应用版本号
	 * 
	 * @return
	 */
	public int getVersionCode() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
		}
		return 1;
	}


}
