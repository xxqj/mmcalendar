package com.mm.mmcalendar.common.dao;

import android.support.v4.app.Fragment;

import com.mm.mmcalendar.tab1.DueDateFragment;
import com.mm.mmcalendar.tab1.MensenDateFragment;
import com.mm.mmcalendar.tab2.Tab2LeftFragment;
import com.mm.mmcalendar.tab2.Tab2RightFragment;
import com.mm.mmcalendar.tab4.Tab4LeftFragment;
import com.mm.mmcalendar.tab4.Tab4RightFragment;
import com.mm.mmcalendar.tab5.Tab5LeftFragment;
import com.mm.mmcalendar.tab5.Tab5RightFragment;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
	
	public static List<Fragment> getContactMain() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new MensenDateFragment());
		list.add(new DueDateFragment());

		return list;
	}

	public static List<Fragment> getTab2Frags() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(Tab2LeftFragment.newInstance());
		list.add(Tab2RightFragment.newInstance());
		return list;
	}

	public static List<Fragment> getTab4Frags() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(Tab4LeftFragment.newInstance());
		list.add(Tab4RightFragment.newInstance());
		return list;
	}

	public static List<Fragment> getTab5Frags() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(Tab5LeftFragment.newInstance());
		list.add(Tab5RightFragment.newInstance());
		return list;
	}
}
