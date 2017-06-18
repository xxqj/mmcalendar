package com.mm.mmcalendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mm.mmcalendar.common.BaseActivity;
import com.mm.mmcalendar.common.widget.CircleIndicator;


public class WelcomeGuideActivity extends BaseActivity {

    private ViewPager viewpager;
    private CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);
        this.indicator = (CircleIndicator) findViewById(R.id.indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        //viewpager.setAdapter(mPageAdapter);
        indicator.setViewPager(viewpager);

    }


}
