package com.mm.mmcalendar.tab1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.BaseFragment;
import com.mm.mmcalendar.common.dao.SharedPreferencesUtils;
import com.mm.mmcalendar.common.utils.ToastUtils;
import com.mm.mmcalendar.tab3.calendar.SpecialCalendar;
import com.mm.mmcalendar.tab3.calendar.WomenCalendar;



import java.util.ArrayList;
import java.util.List;

/**
 * 描述：预产期
 */
public class DueDateFragment extends BaseFragment {

    private EditText mensenEtx;

    private Spinner avglastdays;

    private ArrayAdapter<Integer> avgAdapter;

    private EditText duedate;

    private EditText week;

    private Button getdue;
    private Button shareSNSbtn;

    private WomenCalendar wc;

    private DatePickerDialog datePicker;

    boolean isOpenAd=false;

    @Override
    protected int getLayoutResId() {
        return R.layout.duedate_fragment;
    }

    @Override
    protected void findViews(View layoutView) {

        mensenEtx=(EditText)layoutView.findViewById(R.id.mensenEtx);
        mensenEtx.setInputType(InputType.TYPE_NULL);
        avglastdays=(Spinner)layoutView.findViewById(R.id.avglastdays);
        duedate=(EditText)layoutView.findViewById(R.id.duedate);
        week=(EditText)layoutView.findViewById(R.id.inweek);
        getdue=(Button)layoutView.findViewById(R.id.getdue);
        shareSNSbtn=(Button)layoutView.findViewById(R.id.shareSNS);
        initSpinnerData();
        initTxtChangeEvt();
        initNeedClass();
        initWigetEvt();
        initBaiduAd();
    }

    private void initBaiduAd(){
       /* String adPlaceId = "3071498"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        interAd = new InterstitialAd(context, adPlaceId);
        interAd.setListener(new InterstitialAdListener() {

            @Override
            public void onAdClick(InterstitialAd arg0) {
                Log.i("InterstitialAd", "onAdClick");
            }

            @Override
            public void onAdDismissed() {
                Log.i("InterstitialAd", "onAdDismissed");
                interAd.loadAd();
            }

            @Override
            public void onAdFailed(String arg0) {
                Log.i("InterstitialAd", "onAdFailed");
            }

            @Override
            public void onAdPresent() {
                Log.i("InterstitialAd", "onAdPresent");
            }

            @Override
            public void onAdReady() {
                Log.i("InterstitialAd", "onAdReady");
            }

        });
        interAd.loadAd();*/
    }

    @Override
    protected void reqRemoteDatas(boolean _isForceFetchData) {

    }

    private void initTxtChangeEvt(){
        mensenEtx.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String _lastmensen = mensenEtx.getText().toString();
                if (_lastmensen == null || _lastmensen.trim().length()==0) {
                    //mensenEtx.setError(getResources().getString(R.string.mensen_error));
                    //ToastUtils.show(context,R.string.mensen_error);

                }else{
                    //mensenEtx.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }


        });

    }
    private void initSpinnerData(){

        List<Integer> avg=new ArrayList<Integer>();
        for(int i=20;i<=45;i++){
            avg.add(i);
        }
        avgAdapter=new ArrayAdapter<Integer>(getActivity(),R.layout.spinner_textview, avg);
        avgAdapter.setDropDownViewResource(R.layout.spinner_checkedview);
        avglastdays.setAdapter(avgAdapter);
        int index=avg.indexOf(28);
        avglastdays.setSelection(index);


    }
    private void initNeedClass() {
        wc = new WomenCalendar(getActivity(), new SpecialCalendar());
        datePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                mensenEtx.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                datePicker.dismiss();
            }
        }, 2013, 1 - 1, 18);//2013-01-18特别的日子
    }


    private void initWigetEvt() {
        mensenEtx.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!datePicker.isShowing())
                    datePicker.show();
                return false;
            }

        });

        getdue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditText()) {
                    setDueEtx();
                    /*if(!isOpenAd){
                        isOpenAd=true;
                        if (interAd.isAdReady()) {
                            interAd.showAd((Activity)context);
                        } else {
                            interAd.loadAd();
                        }
                    }*/

                }
            }
        });

    }

    private boolean checkEditText() {
        String _lastmensen = mensenEtx.getText().toString();
        if (_lastmensen == null || _lastmensen.trim().length()==0) {
           // mensenEtx.setError(getResources().getString(R.string.mensen_error));
            ToastUtils.show(context,R.string.mensen_error);
            return false;
        }else{
            //mensenEtx.setError(null);
        }


        return true;
    }

    private void setDueEtx() {
        duedate.setText(wc.getDueDate(mensenEtx.getText().toString(),
                Integer.parseInt(avglastdays.getSelectedItem().toString()))
        );
        week.setText(wc.getDueDateWeek(mensenEtx.getText().toString(),
                Integer.parseInt(avglastdays.getSelectedItem().toString()))
        );
        SharedPreferencesUtils.setStringValue(getActivity(), SharedPreferencesUtils.DUEDATE, duedate.getEditableText().toString());
        SharedPreferencesUtils.setStringValue(getActivity(), SharedPreferencesUtils.DUEWEEK, week.getEditableText().toString());
    }


}
