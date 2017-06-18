package com.mm.mmcalendar.common.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	private boolean isCanScroll = false;

	public NoScrollViewPager(Context context) {
		super(context);
	}

	public NoScrollViewPager(Context context, AttributeSet attribute) {
		super(context, attribute);
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (isCanScroll) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}
	}

}
