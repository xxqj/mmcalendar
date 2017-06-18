package com.mm.mmcalendar;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.mm.mmcalendar.bmobmodel.AppVersion;
import com.mm.mmcalendar.bmobmodel.InfoType;
import com.mm.mmcalendar.bmobmodel.RequestTaskIds;
import com.mm.mmcalendar.common.BaseActivity;
import com.mm.mmcalendar.common.BaseFragment;
import com.mm.mmcalendar.common.adapter.CommonPagerAdapter;
import com.mm.mmcalendar.common.log.LogUtil;
import com.mm.mmcalendar.common.okhttp.OKHttpManager;
import com.mm.mmcalendar.common.okhttp.TaskWatcher;
import com.mm.mmcalendar.common.utils.AppManager;
import com.mm.mmcalendar.common.utils.JsonResult;
import com.mm.mmcalendar.common.utils.ToastUtils;
import com.mm.mmcalendar.common.widget.NoScrollViewPager;
import com.mm.mmcalendar.tab1.Tab1Fragment;
import com.mm.mmcalendar.tab2.Tab2Fragment;
import com.mm.mmcalendar.tab3.Tab3Fragment;
import com.mm.mmcalendar.tab4.Tab4Fragment;
import com.mm.mmcalendar.tab5.Tab5Fragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements
          View.OnClickListener,ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener,BaseFragment.OnFragmentInteractionListener {

    private NoScrollViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragments;
    private int currentIndex;

    /**
     * 4个模块
     */
    private RadioButton rbTab1;
    private RadioButton rbTab2;
    private RadioButton rbTab3;
    private RadioButton rbTab4;
    private RadioButton rbTab5;
    private RadioGroup rgTabContainer;

    boolean isOpenAd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (NoScrollViewPager) findViewById(R.id.id_viewpager);
        viewPager.setCanScroll(false);

        rbTab1=(RadioButton)findViewById(R.id.rbTab1);
        rbTab2=(RadioButton)findViewById(R.id.rbTab2);
        rbTab3=(RadioButton)findViewById(R.id.rbTab3);
        rbTab4=(RadioButton)findViewById(R.id.rbTab4);
        rbTab5=(RadioButton)findViewById(R.id.rbTab5);
        rgTabContainer=(RadioGroup)findViewById(R.id.rgTabContainer);

        rgTabContainer.setOnCheckedChangeListener(this);
        initData();
        checkAppUpdate();
       /*new Thread(new Runnable() {
            @Override
            public void run() {
                initBaiduAd();
            }
        }).start();*/
    }


    private void initData(){

        fragments =new ArrayList<Fragment>();
        fragments.add(new Tab1Fragment());
        fragments.add(new Tab2Fragment());
        fragments.add(new Tab3Fragment());
        fragments.add(new Tab4Fragment());
        fragments.add(new Tab5Fragment());

        adapter = new CommonPagerAdapter(getSupportFragmentManager(),fragments);

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(5);
        //rbTab3.setChecked(true);
        rgTabContainer.check(rbTab3.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbTab1:
                viewPager.setCurrentItem(0);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        exit();
    }


    // 定义一个变量，来标识是否退出
    private long exitTime = 0;
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {

            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();

            exitTime = System.currentTimeMillis();
            if(!isOpenAd){
                isOpenAd=true;
                /*if (interAd.isAdReady()) {
                    interAd.showAd(this);
                } else {
                    interAd.loadAd();
                }*/
            }

        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }

        currentIndex = position;

    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        switch (checkedId) {
            case R.id.rbTab1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rbTab2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rbTab3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rbTab4:
                viewPager.setCurrentItem(3);
                break;
            case R.id.rbTab5:
                viewPager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Bundle args) {
       int selectTabIndx= args.getInt("selectTabIndx",3);
        switch (selectTabIndx){
            case 0:
                rgTabContainer.check(rbTab1.getId());
                break;
            case 1:
                rgTabContainer.check(rbTab2.getId());
                break;
            case 2:
                rgTabContainer.check(rbTab3.getId());
                break;
            case 3:
                rgTabContainer.check(rbTab4.getId());
                break;
            case 4:
                rgTabContainer.check(rbTab5.getId());
                break;
        }
    }

    private void initBaiduAd(){
        /*String adPlaceId = "3071498"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        interAd = new InterstitialAd(this, adPlaceId);
        interAd.setListener(new InterstitialAdListener() {

            @Override
            public void onAdClick(InterstitialAd arg0) {
                Log.i("InterstitialAd", "onAdClick");
            }

            @Override
            public void onAdDismissed() {
                Log.i("InterstitialAd", "onAdDismissed");
                interAd.loadAd();
            }

            @Override
            public void onAdFailed(String arg0) {
                Log.i("InterstitialAd", "onAdFailed");
            }

            @Override
            public void onAdPresent() {
                Log.i("InterstitialAd", "onAdPresent");
            }

            @Override
            public void onAdReady() {
                Log.i("InterstitialAd", "onAdReady");
            }

        });
        interAd.loadAd();*/
    }

    private void checkAppUpdate() {
        //Gson gson=new Gson();
        //UserBean person=new UserBean("张三", MD5.encode("123"));
        //RequestBody body = RequestBody.create(OKHttpManager.JSON, gson.toJson(person));
        //OKHttpManager.getInstance().doPostRequest(1, UserBean.restApiUrl,body,watcher);
        JSONObject jsonObject=new JSONObject();
        //try {
            //jsonObject.put("subCategoryId", SubCategoryBean.shyyId);
            //String encodeUrl= URLEncoder.encode(jsonObject.toString(),"UTF-8");
            //String params=encodeUrl+"&order=postionOrder";

            OKHttpManager.getInstance().doGetRequest(RequestTaskIds.MainActivity_AppVersion_Qry,
                    AppVersion.restApiUrl,watcher,false);

            //OKHttpManager.getInstance().doGetRequest(RequestTaskIds.LeftFragment_InfoType_Qry,
            //	InfoType.restApiUrl,watcher);
       // } catch (JSONException e) {
           // e.printStackTrace();
       // }catch(UnsupportedEncodingException e){
         //   e.printStackTrace();
       // }
    }

    private TaskWatcher watcher = new TaskWatcher() {

        public void onStart(int code) {};

        public void onStop(int code) {};

        @Override
        public void onSuccess(int code, int arg1, int arg2, Object obj) {
            switch (code) {
                case RequestTaskIds.MainActivity_AppVersion_Qry:

                    LogUtil.d(MainActivity.this.getClass().getSimpleName(),obj.toString());
                    JsonResult<List<AppVersion>> jsonResult = JsonResult.parse(obj.toString(), new TypeToken<JsonResult<List<AppVersion>>>() {
                    }.getType());
                    if (jsonResult.isSuccess()) {
                        //ToastUtils.show(MainActivity.this, "1"+obj.toString());
                        if(jsonResult.getResults()!=null && jsonResult.getResults().size()>0) {
                                 //  ToastUtils.show(MainActivity.this, "2"+obj.toString());
                            AppVersion ver=jsonResult.getResults().get(0);
                           if( getVersionCode()<ver.getAppCode()){
                                 //远程版本大于本地的，更新app
                               dialog(ver.getAppUpdateDes() ,ver.getAppUrl());
                           }
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onError(int code, int arg1, int arg2, Object obj) {
            ToastUtils.show(MainActivity.this, obj.toString());
            LogUtil.d(MainActivity.this.getClass().getSimpleName(),obj.toString());
        }
    };


    protected void dialog(final String msg ,final String appUrl) {
         AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          builder.setMessage(msg);
          builder.setTitle("应用更新");
          builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
             @Override
               public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
                 Uri uri = Uri.parse(appUrl);
                 Intent it = new Intent(Intent.ACTION_VIEW,uri);
                 startActivity(it);
             }
            });
          builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
           public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
             }
            });
         builder.create().show();
        }

}
