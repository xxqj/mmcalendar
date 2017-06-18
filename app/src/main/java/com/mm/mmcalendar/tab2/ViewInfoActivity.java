package com.mm.mmcalendar.tab2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mm.mmcalendar.R;
import com.mm.mmcalendar.bmobmodel.InfoBean;
import com.mm.mmcalendar.common.BaseH5Activity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/6.
 */
public class ViewInfoActivity extends BaseH5Activity {

    private String assertPath;
    private FrameLayout adContain;
    private TextView topTitle;

    Handler jsHandler= new Handler();

    //AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewinfo);
        topTitle=(TextView)findViewById(R.id.top_title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Serializable s=getIntent().getSerializableExtra(InfoBean.ITEM_KEY);
        if(s!=null){
            final InfoBean type= (InfoBean)s;
            topTitle.setText(type.getInfoTitle());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myWebView.loadDataWithBaseURL(null, type.getInfoContent(), "text/html", "utf-8", null);
                }
            });

        }else{
            topTitle.setText("全部");
            myWebView.loadDataWithBaseURL(null, "非常抱歉，加载数据失败", "text/html", "utf-8", null);
        }
        //myWebView.loadUrl("file:///android_asset/xiongchumo/index.html");
        //bindEvt();
        adContain=(FrameLayout)findViewById(R.id.webId);
        adContain.removeAllViews();
        adContain.addView(myWebView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initAd();
            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initAd(){
        // 创建广告View
      /*  String adPlaceId = "3071672"; //  重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        adView = new AdView(this, adPlaceId);
        // 设置监听器
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
                Log.w("", "onAdSwitch");
            }

            public void onAdShow(JSONObject info) {
                // 广告已经渲染出来
                Log.w("", "onAdShow " + info.toString());
            }

            public void onAdReady(AdView adView) {
                // 资源已经缓存完毕，还没有渲染出来
                Log.w("", "onAdReady " + adView);
            }

            public void onAdFailed(String reason) {
                Log.w("", "onAdFailed " + reason);
            }

            public void onAdClick(JSONObject info) {
                // Log.w("", "onAdClick " + info.toString());

            }

            @Override
            public void onAdClose(JSONObject arg0) {
                Log.w("", "onAdClose");
            }
        });

        RelativeLayout ad=(RelativeLayout)findViewById(R.id.baiduAd);
        ad.removeAllViews();
        ad.addView(adView);*/
    }


    private void bindEvt(){
        myWebView.addJavascriptInterface(new Object(){

            //h5页面中js调用android端代码
            public String getLoginUid() {
                return  "Ben.Jiang";
            }

            public void goHome(){
                finish();
            }

            public void goLogin(){

                finish();
            }
        }, "userObjInAdroid");
    }

    @Override
    protected void onDestroy() {
        //adView.destroy();
        super.onDestroy();
    }
}
