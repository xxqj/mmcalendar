package com.mm.mmcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mm.mmcalendar.common.BaseActivity;
import com.mm.mmcalendar.common.utils.AppConst;
import com.mm.mmcalendar.common.utils.SharedPreferenceUtils;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mMainHandler.sendEmptyMessageDelayed(0, 500);
    }

    private Handler mMainHandler = new Handler() {
	
         @Override
       public void handleMessage(Message msg) {

            Boolean isFirst= SharedPreferenceUtils.getPrefBoolean(SplashActivity.this, AppConst.FIRST_USED,true);

             if(isFirst){
                 SharedPreferenceUtils.setPrefBoolean(SplashActivity.this, AppConst.FIRST_USED,false);
                 Intent intent = new Intent(Intent.ACTION_MAIN);
                // intent.setClass(getApplication(), WelcomeGuideActivity.class);
                 intent.setClass(getApplication(), MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }else{
                 Intent intent = new Intent(Intent.ACTION_MAIN);
                 intent.setClass(getApplication(), MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }

           finish();
        }
     };


    @Override
    public void onBackPressed() {}

}
