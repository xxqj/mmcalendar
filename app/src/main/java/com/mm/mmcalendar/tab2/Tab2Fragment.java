package com.mm.mmcalendar.tab2;

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

/**
 * Created by Administrator on 2016/10/15.
 */
public class Tab2Fragment extends BaseFragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener
        , ViewPager.OnPageChangeListener {

    private RadioGroup segmentImg;
    private ViewPager pager;
    private CommonPagerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.tab2_fragment;
    }

    @Override
    protected void findViews(View layoutView) {
        segmentImg = (RadioGroup)layoutView.findViewById(R.id.segment_img);
        segmentImg.setOnCheckedChangeListener(this);
        pager = (ViewPager)layoutView.findViewById(R.id.viewPager);
        pager.setOnPageChangeListener(this);
        mAdapter = new CommonPagerAdapter(getChildFragmentManager(), DataProvider.getTab2Frags());
        pager.setAdapter(mAdapter);
        pager.setOffscreenPageLimit(mAdapter.getCount());
        segmentImg.check(R.id.rbTool1);
        layoutView.findViewById(R.id.rbTool1).setOnClickListener(this);
        layoutView.findViewById(R.id.rbTool2).setOnClickListener(this);
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
                segmentImg.check(R.id.rbTool1);

                break;
            case 1:
                segmentImg.check(R.id.rbTool2);

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
            case  R.id.rbTool1:
                ((BaseFragment) mAdapter.getItem(0)).fetchData(true);
                segmentImg.check(R.id.rbTool1);
                 break;
            case R.id.rbTool2:
                ((BaseFragment) mAdapter.getItem(1)).fetchData(true);
                segmentImg.check(R.id.rbTool2);
                break;
        }
    }
}
