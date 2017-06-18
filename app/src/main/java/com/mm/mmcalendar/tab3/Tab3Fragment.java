package com.mm.mmcalendar.tab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.BaseFragment;
import com.mm.mmcalendar.common.dao.ScheduleDAO;
import com.mm.mmcalendar.common.dao.SharedPreferencesUtils;
import com.mm.mmcalendar.common.widget.borderText.BorderText;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Administrator on 2016/10/15.
 */
public class Tab3Fragment extends BaseFragment implements OnGestureListener,View.OnClickListener{

    private ViewFlipper flipper = null;
    private GestureDetector gestureDetector = null;
    private CalendarView calV = null;
    private GridView gridView = null;
    private BorderText topText = null;
    private Drawable draw = null;
    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";

    private ScheduleDAO dao = null;

    public Tab3Fragment() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        dao = new ScheduleDAO(context);
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.tab3_fragment;
    }

    @Override
    protected void findViews(View layoutView) {
        gestureDetector = new GestureDetector(this);
        flipper = (ViewFlipper) layoutView.findViewById(R.id.flipper);
        flipper.removeAllViews();

        topText = (BorderText) layoutView.findViewById(R.id.toptext);

        layoutView.findViewById(R.id.tvLeft).setOnClickListener(this);
        layoutView.findViewById(R.id.tvCenter).setOnClickListener(this);
        layoutView.findViewById(R.id.tvRight).setOnClickListener(this);
        // MensenDateActivity.goShowMensenCalendar

    }


    @Override
    public void onResume() {
        super.onResume();
        String mensenDate=null;
        Bundle  b=getArguments();
        if(b!=null)
            mensenDate=b.getString("mensenDate");

        if (mensenDate != null && mensenDate.trim().length() != 0 && mensenDate.contains("-")) {
            String[] rs=mensenDate.split("-");
            int lastYear = Integer.parseInt(rs[0]);
            int lastMonth = Integer.parseInt(rs[1]);
            int lastDay = Integer.parseInt(rs[2]);
            showCalendar(lastYear, lastMonth, lastDay);
        } else {
            calV = new CalendarView(getActivity(), getResources(), jumpMonth, jumpYear,
                    year_c, month_c, day_c);

            addGridView();
            gridView.setAdapter(calV);
            // flipper.addView(gridView);
            flipper.addView(gridView, 0);
            addTextToTopTextView(topText);
        }

    }

    @Override
    protected void reqRemoteDatas(boolean _isForceFetchData) {

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
        if (e1.getX() - e2.getX() > 120) {
            // 像左滑动
            addGridView(); // 添加一个gridView
            jumpMonth++; // 下一个月

            calV = new CalendarView(getActivity(), getResources(), jumpMonth, jumpYear,
                    year_c, month_c, day_c);
            gridView.setAdapter(calV);
            // flipper.addView(gridView);
            addTextToTopTextView(topText);
            gvFlag++;
            flipper.addView(gridView, gvFlag);
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_left_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_left_out));
            this.flipper.showNext();
            flipper.removeViewAt(0);
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            // 向右滑动
            addGridView(); // 添加一个gridView
            jumpMonth--; // 上一个月

            calV = new CalendarView(getActivity(), getResources(), jumpMonth, jumpYear,
                    year_c, month_c, day_c);
            gridView.setAdapter(calV);
            gvFlag++;
            addTextToTopTextView(topText);
            // flipper.addView(gridView);
            flipper.addView(gridView, gvFlag);

            this.flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_right_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_right_out));
            this.flipper.showPrevious();
            flipper.removeViewAt(0);
            return true;
        }
        return false;
    }

    /**
     * 创建菜单
     */
    //@SuppressLint("NewApi")
    //@Override
	/*public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, menu.FIRST, menu.FIRST, "今天").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, menu.FIRST + 1, menu.FIRST + 1, "跳转").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//menu.add(0, menu.FIRST + 2, menu.FIRST + 2, "日程").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, menu.FIRST + 3, menu.FIRST + 3, "日期转换").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, menu.FIRST + 4, menu.FIRST + 4, "安全期计算").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, menu.FIRST + 5, menu.FIRST + 5, "预产期计算").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, menu.FIRST + 6, menu.FIRST + 6, "设置安全期标识").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}*/

    /**
     * 选择菜单
     *//*
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			// 跳转到今天
			int xMonth = jumpMonth;
			int xYear = jumpYear;
			int gvFlag = 0;
			jumpMonth = 0;
			jumpYear = 0;
			addGridView(); // 添加一个gridView
			year_c = Integer.parseInt(currentDate.split("-")[0]);
			month_c = Integer.parseInt(currentDate.split("-")[1]);
			day_c = Integer.parseInt(currentDate.split("-")[2]);
			calV = new CalendarView(getActivity(), getResources(), jumpMonth, jumpYear,
					year_c, month_c, day_c);
			gridView.setAdapter(calV);
			addTextToTopTextView(topText);
			gvFlag++;
			flipper.addView(gridView, gvFlag);
			if (xMonth == 0 && xYear == 0) {
				// nothing to do
			} else if ((xYear == 0 && xMonth > 0) || xYear > 0) {
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.push_left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.push_left_out));
				this.flipper.showNext();
			} else {
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.push_right_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.push_right_out));
				this.flipper.showPrevious();
			}
			flipper.removeViewAt(0);
			break;
		case Menu.FIRST + 1:

			new DatePickerDialog(getActivity(), new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					showCalendar(year, monthOfYear, dayOfMonth);
				}
			}, year_c, month_c - 1, day_c).show();
			break;
		case Menu.FIRST + 2:
			Intent intent = new Intent();
			intent.setClass(CalendarActivity.this, ScheduleAll.class);
			startActivity(intent);
			break;
		case Menu.FIRST + 3:
			Intent intent1 = new Intent();
			intent1.setClass(CalendarActivity.this, CalendarConvert.class);
			intent1.putExtra("date", new int[] { year_c, month_c, day_c });
			startActivity(intent1);
			break;
		case Menu.FIRST + 4:
			Intent intent2 = new Intent();
			intent2.setClass(CalendarActivity.this, MensenDateActivity.class);
			// intent2.putExtra("date", new int[]{year_c,month_c,day_c});
			startActivity(intent2);
			break;
		case Menu.FIRST + 5:
			Intent intent3 = new Intent();
			intent3.setClass(CalendarActivity.this, DueDateActivity.class);
			// intent2.putExtra("date", new int[]{year_c,month_c,day_c});
			startActivity(intent3);
			break;
		case Menu.FIRST + 6:
			setAnqun();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
    */
    private void setAnqun(){
        new AlertDialog.Builder(getActivity()).setTitle("设置安全期标识")
                .setMultiChoiceItems(new String[]{"显示安全期标识"},
                        null, new OnMultiChoiceClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                // 设置已经引导
                                SharedPreferencesUtils.setShowWomen(getActivity(), isChecked);
                            }

                        })
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();


    }




    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    // 添加头部的年份 闰哪月等信息
    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        draw = getResources().getDrawable(R.mipmap.top_day);
        view.setBackgroundDrawable(draw);
        textDate.append(calV.getShowYear()).append("年")
                .append(calV.getShowMonth()).append("月").append("\t");
        if (!calV.getLeapMonth().equals("") && calV.getLeapMonth() != null) {
            textDate.append("闰").append(calV.getLeapMonth()).append("月")
                    .append("\t");
        }
        textDate.append(calV.getAnimalsYear()).append("年").append("(")
                .append(calV.getCyclical()).append("年)");
        view.setText(textDate);
        view.setTextColor(Color.BLACK);
        view.setTypeface(Typeface.DEFAULT_BOLD);
    }

    // 添加gridview
    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        // 取得屏幕的宽度和高度
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();

        gridView = new GridView(getActivity());
        gridView.setNumColumns(7);
        gridView.setColumnWidth(46);

        if (Width == 480 && Height == 800) {
            gridView.setColumnWidth(69);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT)); // 去除gridView边框
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setBackgroundResource(R.mipmap.gridview_bk);
        gridView.setOnTouchListener(new OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return Tab3Fragment.this.gestureDetector
                        .onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            // gridView中的每一个item的点击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {/*
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position && position <= endPosition) {
					String scheduleDay = calV.getDateByClickItem(position)
							.split("\\.")[0]; // 这一天的阳历
					// String scheduleLunarDay =
					// calV.getDateByClickItem(position).split("\\.")[1];
					// //这一天的阴历
					String scheduleYear = calV.getShowYear();
					String scheduleMonth = calV.getShowMonth();
					String week = "";

					// 通过日期查询这一天是否被标记，如果标记了日程就查询出这天的所有日程信息
					String[] scheduleIDs = dao.getScheduleByTagDate(
							Integer.parseInt(scheduleYear),
							Integer.parseInt(scheduleMonth),
							Integer.parseInt(scheduleDay));
					if (scheduleIDs != null && scheduleIDs.length > 0) {
						// 跳转到显示这一天的所有日程信息界面
						Intent intent = new Intent();
						intent.setClass(CalendarActivity.this,
								ScheduleInfoView.class);
						intent.putExtra("scheduleID", scheduleIDs);
						startActivity(intent);

					} else {
						// 直接跳转到需要添加日程的界面

						// 得到这一天是星期几
						switch (position % 7) {
						case 0:
							week = "星期日";
							break;
						case 1:
							week = "星期一";
							break;
						case 2:
							week = "星期二";
							break;
						case 3:
							week = "星期三";
							break;
						case 4:
							week = "星期四";
							break;
						case 5:
							week = "星期五";
							break;
						case 6:
							week = "星期六";
							break;
						}

						ArrayList<String> scheduleDate = new ArrayList<String>();
						scheduleDate.add(scheduleYear);
						scheduleDate.add(scheduleMonth);
						scheduleDate.add(scheduleDay);
						scheduleDate.add(week);
						// scheduleDate.add(scheduleLunarDay);

						Intent intent = new Intent();
						intent.putStringArrayListExtra("scheduleDate",
								scheduleDate);
						intent.setClass(CalendarActivity.this,
								ScheduleView.class);
						startActivity(intent);
					}
				}
			*/}
        });
        gridView.setLayoutParams(params);

    }

    private void showCalendar(int year, int monthOfYear, int dayOfMonth) {

        // 1901-1-1 ----> 2049-12-31
        if (year < 1901 || year > 2049) {
            // 不在查询范围内
            new AlertDialog.Builder(getActivity()).setTitle("错误日期")
                    .setMessage("跳转日期范围(1901/1/1-2049/12/31)")
                    .setPositiveButton("确认", null).show();
        } else {
            //int gvFlag = 0;
            addGridView(); // 添加一个gridView
            calV = new CalendarView(getActivity(),
                    Tab3Fragment.this.getResources(), year,
                    monthOfYear + 1, dayOfMonth);
            gridView.setAdapter(calV);
            addTextToTopTextView(topText);
            //gvFlag++;
            //int cnt=flipper.getChildCount()-1;
            flipper.removeAllViews();
            flipper.addView(gridView, 0);
            if (year == year_c && monthOfYear + 1 == month_c) {
                // nothing to do
            }
            if ((year == year_c && monthOfYear + 1 > month_c) || year > year_c) {
                Tab3Fragment.this.flipper.setInAnimation(AnimationUtils
                        .loadAnimation(getActivity(),
                                R.anim.push_left_in));
                Tab3Fragment.this.flipper.setOutAnimation(AnimationUtils
                        .loadAnimation(getActivity(),
                                R.anim.push_left_out));
                Tab3Fragment.this.flipper.showNext();
            } else {
                Tab3Fragment.this.flipper.setInAnimation(AnimationUtils
                        .loadAnimation(getActivity(),
                                R.anim.push_right_in));
                Tab3Fragment.this.flipper.setOutAnimation(AnimationUtils
                        .loadAnimation(getActivity(),
                                R.anim.push_right_out));
                Tab3Fragment.this.flipper.showPrevious();
            }
            //flipper.removeViewAt(0);
            //flipper.setDisplayedChild(whichChild);
            // 跳转之后将跳转之后的日期设置为当期日期
            year_c = year;
            month_c = monthOfYear + 1;
            day_c = dayOfMonth;
            jumpMonth = 0;
            jumpYear = 0;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvLeft:
            case R.id.tvCenter:
            case R.id.tvRight:
                if(mListener!=null){
                    Bundle bundle=new Bundle();
                    bundle.putInt("selectTabIndx",0);
                    mListener.onFragmentInteraction(bundle);
                }
                break;
        }
    }
}
