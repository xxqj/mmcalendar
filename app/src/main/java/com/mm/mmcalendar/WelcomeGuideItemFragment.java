package com.mm.mmcalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *描述：
 */
public class WelcomeGuideItemFragment extends Fragment {

    private  int drawableId;

    private boolean isLastView;

    public WelcomeGuideItemFragment() { super();}

    @SuppressLint("ValidFragment")
    public WelcomeGuideItemFragment(int drawableId, boolean isLastView) {
        this.drawableId = drawableId;
        this.isLastView = isLastView;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_guideitem, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

}
