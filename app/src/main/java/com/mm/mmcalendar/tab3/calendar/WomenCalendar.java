package com.mm.mmcalendar.tab3.calendar;

import android.content.Context;
import android.util.Log;

import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.constant.CalendarConstant;
import com.mm.mmcalendar.common.dao.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WomenCalendar {

	private int lastYear;// 上次月经时间
	private int lastMonth;// 上次月经时间
	private int lastDay;// 上次月经时间
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private int nextYear;// 下次月经时间
	private int nextMonth;// 下次月经时间
	private int nextDay;// 下次月经时间
	private String nextDate = null;// 下次月经开始日期 yyyy-MM-dd

	// 行经期一般为3-8天
	private int days;// 行经期
	// 月经周期一般为26-35天。您的月经周期不在此范围，请注意调理。
	private int lastDays;// 月经周期，不能为0
	private Context ctx;
	private SpecialCalendar sc;

	// private static final int yJafter4=4;//停月经后4天为安全期

	private static final int yJbefore9 = 9;// 下次月经时前9天为安全期

	// private String[] SafetyDate=new String[yJafter4+yJbefore9];//保存安全期

	private Date mensesDate = null;// 上次月经日期

	private Date nextMensenDateS = null;// 下次月经所在月末日期

	private String currentDate = null;

	public WomenCalendar(Context ctx, SpecialCalendar sc) {
		super();
		this.ctx = ctx;
		this.sc = sc;

		initLastYJ();
	}

	/** 初始化上次月经期 */
	private void initLastYJ() {
		currentDate = sdf.format(new Date());
		String jqdate = SharedPreferencesUtils.getLastMensen(ctx, currentDate);

		days = SharedPreferencesUtils.getDays(ctx, 5);
		lastDays = SharedPreferencesUtils.getLastDays(ctx, 28);

		nextDate = getNextDate(jqdate,lastDays,days);
		nextDate = SharedPreferencesUtils.getNextMensen(ctx, nextDate);


		mensesDate = new Date(lastYear - 1900, lastMonth - 1, lastDay);

		Log.d(WomenCalendar.class.getName() + "--------nextDate=", nextDate
				+ "---mensesDate-" + sdf.format(mensesDate));
	}

	private void parseJqDate(String _jqDate){
		Log.d(WomenCalendar.class.getName() + "--------jqdate=", _jqDate);
		lastYear = Integer.parseInt(_jqDate.split("-")[0]);
		lastMonth = Integer.parseInt(_jqDate.split("-")[1]);
		lastDay = Integer.parseInt(_jqDate.split("-")[2]);
	}

	/** 计算下次月经日期 */
	public String getNextDate(String _jqDate,int _lastDays,int _days) {
		Log.d(WomenCalendar.class.getName() + "-------- _jqDate=",  _jqDate
				+ "---_lastDays=" + _lastDays+"--_days="+_days);
		parseJqDate(_jqDate);

		boolean isLeapYear = sc.isLeapYear(lastYear);

		String resultDate = null;
		// 得到上次月经所在当月的天数
		int _currentMonthDays = sc.getDaysOfMonth(isLeapYear, lastMonth);
		//上次月经所在当月的天数-上次月经所在当月号-月经周期数
		int tmpDays = _currentMonthDays -lastDay- _lastDays;
		if (tmpDays < 0)// 当月不包含月经周期数，须推到下月
		{
			int _nextMonthDays = 0;// 相对于上次月经月份的，往下一个月天数
			if (lastMonth < 12) {
				_nextMonthDays = sc.getDaysOfMonth(isLeapYear, lastMonth + 1);
				String _m = lastMonth + 1 < 10 ? "0" + (lastMonth + 1)
						: (lastMonth + 1) + "";
				resultDate = String.valueOf(lastYear) + "-" + _m + "-"
						+ (-tmpDays);
				nextYear = lastYear;
				nextMonth = lastMonth + 1;
				nextDay = -tmpDays;
			} else {
				_nextMonthDays = sc.getDaysOfMonth(sc.isLeapYear(lastYear + 1),
						1);
				resultDate = String.valueOf(lastYear + 1) + "-" + (01) + "-"
						+ (-tmpDays);
				nextYear = lastYear + 1;
				nextMonth = 1;
				nextDay = -tmpDays;
			}

		} else {
			String _m2 = lastMonth < 10 ? "0" + (lastMonth) : (lastMonth) + "";
			resultDate = String.valueOf(lastYear) + "-" + _m2 + "-"
					+ (_days + _lastDays);
			nextYear = lastYear;
			nextMonth = lastMonth;
			nextDay = _days + _lastDays;

		}

		nextMensenDateS = new Date(nextYear - 1900, nextMonth - 1,
				sc.getDaysOfMonth(sc.isLeapYear(nextYear), nextMonth));

		return resultDate;

	}

	private boolean isBetweenDate(Date myDate) {

		/*
		 * double myTime = Math.floor((myDate.getTime() - mensesDate.getTime())
		 * / (24 * 60 * 60 * 1000)); double
		 * nextTime=Math.floor((nextMensenDate.getTime() - myDate.getTime()) /
		 * (24 * 60 * 60 * 1000)); return myTime>=0 || nextTime>=0;
		 */

		return mensesDate.getTime() <= myDate.getTime()
				&& myDate.getTime() <= nextMensenDateS.getTime();

	}

	public String judgeJQ(int year, int month, int day) {

		Date myDate = new Date(year - 1900, month - 1, day);

		if (!isBetweenDate(myDate))
			return "I";// 不在上次月经与下次月经之内的日期，暂不显示提示

		double myTime = Math.floor((myDate.getTime() - mensesDate.getTime())
				/ (24 * 60 * 60 * 1000));
		double someDate = (myTime % lastDays + lastDays) % lastDays;
		String result = CalendarConstant.womenTip[0];
		if (someDate >= 0 && someDate <= (days - 1)) {
			// 这是月经期，要注意经期卫生，避免性事！"
			result = CalendarConstant.womenTip[2];
		}
		if (someDate >= lastDays && someDate <= (lastDays - 20)) {
			// "这是安全期，性事一般不会受孕，您放心吧！";
			result = CalendarConstant.womenTip[0];
		}
		if (someDate >= (lastDays - 19) && someDate <= (lastDays - 10)) {
			// msgStr = "这是排卵期，性事受孕可能性大，请千万要注意！";
			result = CalendarConstant.womenTip[1];
		}
		if (someDate >= (lastDays - 9) && someDate <= (lastDays - 1)) {
			// msgStr = "这是安全期，性事一般不会受孕，您放心吧！";
			result = CalendarConstant.womenTip[0];
		}
		return result;
	}

	/**
	 * 计算预产期
	 * @param mensenDate 末次月经日期 yyyy-MM-dd
	 * @param days  经期持续天数
	 * 整个孕期约为40周，即9个月零7天左右
	 * */
	public String getDueDate(String mensenDate,int days){
		String[] mensen=mensenDate.split("-");
		int year=Integer.valueOf(mensen[0]);
		int month=Integer.parseInt(mensen[1]);
		int day=Integer.parseInt(mensen[2]);
		//推算方法是按末次月经时间的第一日算起，月份减3或加9，日数加7
		int true_number=280;//整个孕期约为40周，即9个月零7天左右,280天

		if (days<28)
		{

			true_number=280-(28-days);

		}

		else if (days>28)
		{

			true_number=280+(days-28);

		}

		else if (days==28)
		{

			true_number=280;

		}
		Date m=new Date(year-1900,month-1,day);
		long l=m.getTime()+(long)true_number*24*3600*1000;

		return sdf.format(new Date(l));

	}


	/**
	 * 计算怀孕周数
	 * @param mensenDate 末次月经日期 yyyy-MM-dd
	 * @param days  经期持续天数
	 * */
	public String getDueDateWeek(String mensenDate,int days){
		String[] mensen=mensenDate.split("-");
		int year=Integer.valueOf(mensen[0]);
		int month=Integer.parseInt(mensen[1]);
		int day=Integer.parseInt(mensen[2]);

		Date m=new Date(year-1900,month-1,day);
		Date c=new Date();
		int myTime = (int)Math.floor((c.getTime() - m.getTime())
				/ (24 * 60 * 60 * 1000));
		int cday=(myTime%7);//天
		int w=(myTime-cday)/7;//周
		int sday=(40-w)*7-cday;
		if(sday<=0||w>40)
			return "宝宝已过预产期";
		//return String.valueOf(w)+" 周 "+cday+" 天 距宝宝出生还有 "+sday+" 天";
		return String.format(ctx.getResources().getString(R.string.due_str),
				w,cday,sday);

	}




	public int getLastYear() {
		return lastYear;
	}



	public int getLastMonth() {
		return lastMonth;
	}



	public int getLastDay() {
		return lastDay;
	}



}
