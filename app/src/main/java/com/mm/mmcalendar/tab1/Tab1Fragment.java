package com.mm.mmcalendar.tab1;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.BaseFragment;
import com.mm.mmcalendar.common.adapter.CommonPagerAdapter;
import com.mm.mmcalendar.common.dao.DataProvider;
import com.mm.mmcalendar.common.widget.NoScrollViewPager;

/**
 * Created by Administrator on 2016/10/16.
 */
public class Tab1Fragment extends BaseFragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener
        , ViewPager.OnPageChangeListener {

    private RadioGroup segmentImg;
    private NoScrollViewPager pager;
    private CommonPagerAdapter mAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.tab1_fragment;
    }

    @Override
    protected void findViews(View layoutView) {
        segmentImg = (RadioGroup)layoutView.findViewById(R.id.segment_img);
        segmentImg.setOnCheckedChangeListener(this);
        pager = (NoScrollViewPager)layoutView.findViewById(R.id.viewPager);
        pager.setCanScroll(false);
        pager.setOnPageChangeListener(this);

        mAdapter = new CommonPagerAdapter(getChildFragmentManager(), DataProvider.getContactMain());
        pager.setAdapter(mAdapter);

        pager.setOffscreenPageLimit(mAdapter.getCount());
        segmentImg.check(R.id.rbTool1);
    }

    @Override
    protected void reqRemoteDatas(boolean _isForceFetchData) {

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
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == segmentImg) {
            if (checkedId == R.id.rbTool1) {
                pager.setCurrentItem(0);
            } else if (checkedId == R.id.rbTool2) {
                pager.setCurrentItem(1);
            }
        }

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

        }
    }

}
