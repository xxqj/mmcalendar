package com.mm.mmcalendar.tab3;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.constant.CalendarConstant;
import com.mm.mmcalendar.common.dao.ScheduleDAO;
import com.mm.mmcalendar.common.dao.SharedPreferencesUtils;
import com.mm.mmcalendar.common.vo.ScheduleDateTag;
import com.mm.mmcalendar.tab3.calendar.LunarCalendar;
import com.mm.mmcalendar.tab3.calendar.SpecialCalendar;
import com.mm.mmcalendar.tab3.calendar.WomenCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * 日历gridview中的每一个item显示的textview
 * @author jack_peng
 *
 */
public class CalendarView extends BaseAdapter {

	private ScheduleDAO dao = null;
	private boolean isLeapyear = false;  //是否为闰年
	private int daysOfMonth = 0;      //某月的天数
	private int dayOfWeek = 0;        //具体某一天是星期几
	private int lastDaysOfMonth = 0;  //上一个月的总天数
	private Context context;
	private String[] dayNumber = new String[49];  //一个gridview中的日期存入此数组中
	private static String week[] = {"周日","周一","周二","周三","周四","周五","周六"};
	private SpecialCalendar sc = null;
	private LunarCalendar lc = null;
	private WomenCalendar wc=null;
	private Resources res = null;


	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int currentFlag = -1;     //用于标记当天
	private int[] schDateTagFlag = null;  //存储当月所有的日程日期

	private String showYear = "";   //用于在头部显示的年份
	private String showMonth = "";  //用于在头部显示的月份
	private String animalsYear = "";
	private String leapMonth = "";   //闰哪一个月
	private String cyclical = "";   //天干地支
	//系统当前时间
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";

	//日程时间(需要标记的日程日期)
	private String sch_year = "";
	private String sch_month = "";
	private String sch_day = "";

	public CalendarView(){
		Date date = new Date();
		sysDate = sdf.format(date);  //当期日期
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];

	}

	public CalendarView(Context context,Resources rs,int jumpMonth,int jumpYear,int year_c,int month_c,int day_c){
		this();
		this.context= context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		wc=new WomenCalendar(context,sc);
		this.res = rs;

		int stepYear = year_c+jumpYear;
		int stepMonth = month_c+jumpMonth ;
		if(stepMonth > 0){
			//往下一个月滑动
			if(stepMonth%12 == 0){
				stepYear = year_c + stepMonth/12 -1;
				stepMonth = 12;
			}else{
				stepYear = year_c + stepMonth/12;
				stepMonth = stepMonth%12;
			}
		}else{
			//往上一个月滑动
			stepYear = year_c - 1 + stepMonth/12;
			stepMonth = stepMonth%12 + 12;
			if(stepMonth%12 == 0){

			}
		}

		currentYear = String.valueOf(stepYear);;  //得到当前的年份
		currentMonth = String.valueOf(stepMonth);  //得到本月 （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		currentDay = String.valueOf(day_c);  //得到当前日期是哪天

		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));

	}

	public CalendarView(Context context,Resources rs,int year, int month, int day){
		this();
		this.context= context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		wc=new WomenCalendar(context,sc);
		this.res = rs;
		currentYear = String.valueOf(year);;  //得到跳转到的年份
		currentMonth = String.valueOf(month);  //得到跳转到的月份
		currentDay = String.valueOf(day);  //得到跳转到的天

		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Drawable drawable = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.calendar, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
		String d = dayNumber[position].split("\\.")[0];
		String dv = dayNumber[position].split("\\.")[1];

		String jq="";
		if(dayNumber[position].split("\\.").length>=3)
			jq=dayNumber[position].split("\\.")[2];
		//Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica.ttf");
		//textView.setTypeface(typeface);
		SpannableStringBuilder sp = new SpannableStringBuilder(d+"\n"+dv);

		sp.setSpan(new AbsoluteSizeSpan(40) , 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.BLACK), 0,d.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if(dv != null || dv != ""){
			sp.setSpan(new AbsoluteSizeSpan(12), d.length()+1, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			sp.setSpan(new ForegroundColorSpan(Color.GRAY), d.length()+1, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		//

		if(position<7){
			//设置周
			//textView.setTextColor(Color.BLACK);
			switch(position){
				case 0:
					drawable = res.getDrawable(R.mipmap.sun);
					break;
				case 1:
					drawable = res.getDrawable(R.mipmap.mon);
					break;
				case 2:
					drawable = res.getDrawable(R.mipmap.tues);
					break;
				case 3:
					drawable = res.getDrawable(R.mipmap.wed);
					break;
				case 4:
					drawable = res.getDrawable(R.mipmap.thur);
					break;
				case 5:
					drawable = res.getDrawable(R.mipmap.fri);
					break;
				case 6:
					drawable = res.getDrawable(R.mipmap.sat);
					break;
			}

			textView.setBackgroundDrawable(drawable);
		}else{

		}

		if (position < daysOfMonth + dayOfWeek+7 && position >= dayOfWeek+7) {
			// 当前月信息显示
			textView.setTextColor(Color.BLACK);// 当月字体设黑
			if(SharedPreferencesUtils.isShowWomen(context,true)){
				if(CalendarConstant.womenTip[0].equalsIgnoreCase(jq))
					drawable = res.getDrawable(R.drawable.day_s);
				else if(CalendarConstant.womenTip[1].equalsIgnoreCase(jq))
					drawable = res.getDrawable(R.drawable.day_d);
				else if(CalendarConstant.womenTip[2].equalsIgnoreCase(jq))
					drawable = res.getDrawable(R.drawable.day_m);
				else
					;
			}else{

			}

			textView.setBackgroundDrawable(drawable);


		}else{
			//非当月
			if(position>=7){
				textView.setTextColor(Color.GRAY);
				drawable = res.getDrawable(R.mipmap.week_top);
				textView.setBackgroundDrawable(drawable);
			}
		}
		if(schDateTagFlag != null && schDateTagFlag.length >0){
			for(int i = 0; i < schDateTagFlag.length; i++){
				if(schDateTagFlag[i] == position){
					//设置日程标记背景
					if(SharedPreferencesUtils.isShowWomen(context,true)){
						if(CalendarConstant.womenTip[0].equalsIgnoreCase(jq))
							drawable = res.getDrawable(R.mipmap.mark_day_s);
						else if(CalendarConstant.womenTip[1].equalsIgnoreCase(jq))
							drawable = res.getDrawable(R.mipmap.mark_day_d);
						else if(CalendarConstant.womenTip[2].equalsIgnoreCase(jq))
							drawable = res.getDrawable(R.mipmap.mark_day_m);
						else
							drawable=res.getDrawable(R.mipmap.mark);
					}else{
						drawable=res.getDrawable(R.mipmap.mark);
					}
					textView.setBackgroundDrawable(drawable);

				}
			}
		}
		if(currentFlag == position){
			//设置当天的背景
			if(SharedPreferencesUtils.isShowWomen(context,true)){
				if(CalendarConstant.womenTip[0].equalsIgnoreCase(jq)){
					drawable = res.getDrawable(R.drawable.current_day_s);
					SharedPreferencesUtils.setStringValue(context,
							SharedPreferencesUtils.TODAY_WHAT, "安全期") ;

				}else if(CalendarConstant.womenTip[1].equalsIgnoreCase(jq)){
					drawable = res.getDrawable(R.drawable.current_day_d);
					SharedPreferencesUtils.setStringValue(context,
							SharedPreferencesUtils.TODAY_WHAT, "月经期") ;
				}else if(CalendarConstant.womenTip[2].equalsIgnoreCase(jq)){
					drawable = res.getDrawable(R.drawable.current_day_m);
					SharedPreferencesUtils.setStringValue(context,
							SharedPreferencesUtils.TODAY_WHAT, "排卵期") ;
				}else{
					drawable = res.getDrawable(R.drawable.current_day);
				}
			}else{
				drawable = res.getDrawable(R.drawable.current_day);
			}
			textView.setBackgroundDrawable(drawable);
			textView.setTextColor(Color.BLACK);
		}
		if(position>=7)
			textView.setText(sp.toString());

		return convertView;
	}

	//得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month){
		isLeapyear = sc.isLeapYear(year);              //是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);  //某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month);      //某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month-1);  //上一个月的总天数
		Log.d("DAY", isLeapyear+" ======  "+daysOfMonth+"  ============  "+dayOfWeek+"  =========   "+lastDaysOfMonth);
		getweek(year,month);
	}

	//将一个月中的每一天的值添加入数组dayNuber中
	private void getweek(int year, int month) {
		int j = 1;
		int flag = 0;
		String lunarDay = "";

		//得到当前月的所有日程日期(这些日期需要标记)
		dao = new ScheduleDAO(context);
		ArrayList<ScheduleDateTag> dateTagList = dao.getTagDate(year,month);
		if(dateTagList != null && dateTagList.size() > 0){
			schDateTagFlag = new int[dateTagList.size()];
		}

		for (int i = 0; i < dayNumber.length; i++) {
			// 周一
			if(i<7){
				dayNumber[i]=week[i]+"."+" "+".I";
			}
			else if(i < dayOfWeek+7){  //前一个月
				int temp = lastDaysOfMonth - dayOfWeek+1-7;
				lunarDay = lc.getLunarDate(year, month-1, temp+i,false);
				String rs=wc.judgeJQ(year, month-1,temp+i);
				dayNumber[i] = (temp + i)+"."+lunarDay+"."+rs;
			}else if(i < daysOfMonth + dayOfWeek+7){   //本月
				String day = String.valueOf(i-dayOfWeek+1-7);   //得到的日期
				lunarDay = lc.getLunarDate(year, month, i-dayOfWeek+1-7,false);
				String rs=wc.judgeJQ(year, month, i-dayOfWeek+1-7);
				dayNumber[i] = i-dayOfWeek+1-7+"."+lunarDay+"."+rs;
				//对于当前月才去标记当前日期
				if(sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)){
					//笔记当前日期
					currentFlag = i;
				}

				//标记日程日期
				if(dateTagList != null && dateTagList.size() > 0){
					for(int m = 0; m < dateTagList.size(); m++){
						ScheduleDateTag dateTag = dateTagList.get(m);
						int matchYear = dateTag.getYear();
						int matchMonth = dateTag.getMonth();
						int matchDay = dateTag.getDay();
						if(matchYear == year && matchMonth == month && matchDay == Integer.parseInt(day)){
							schDateTagFlag[flag] = i;
							flag++;
						}
					}
				}

				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
				setAnimalsYear(lc.animalsYear(year));
				setLeapMonth(lc.leapMonth == 0?"":String.valueOf(lc.leapMonth));
				setCyclical(lc.cyclical(year));
			}else{   //下一个月
				lunarDay = lc.getLunarDate(year, month+1, j,false);
				String rs=wc.judgeJQ(year, month+1, j);
				dayNumber[i] = j+"."+lunarDay+"."+rs;
				j++;
			}
		}

		String abc = "";
		for(int i = 0; i < dayNumber.length; i++){
			abc = abc+dayNumber[i]+":";
		}
		Log.d("DAYNUMBER",abc);


	}


	public void matchScheduleDate(int year, int month, int day){

	}

	/**
	 * 点击每一个item时返回item中的日期
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position){
		return dayNumber[position];
	}

	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * @return
	 */
	public int getStartPositon(){
		return dayOfWeek+7;
	}

	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 * @return
	 */
	public int getEndPosition(){
		return  (dayOfWeek+daysOfMonth+7)-1;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}

	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}
}
