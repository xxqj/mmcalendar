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
import android.widget.Toast;


import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.BaseFragment;
import com.mm.mmcalendar.common.dao.SharedPreferencesUtils;
import com.mm.mmcalendar.common.utils.ToastUtils;
import com.mm.mmcalendar.tab3.calendar.SpecialCalendar;
import com.mm.mmcalendar.tab3.calendar.WomenCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：安全期
 */
public class MensenDateFragment extends BaseFragment {

    private EditText lastmensenEtx;

    private Spinner avglastdaysEtx;

    private ArrayAdapter<Integer> avgAdapter;

    private Spinner lastdaysEtx;

    private ArrayAdapter<Integer> lastAdapter;

    private EditText nextmensenEtx;

    private Button getBtn;

    private Button showBtn;

    private WomenCalendar wc;

    private DatePickerDialog datePicker;
    //InterstitialAd interAd;
    boolean isOpenAd=false;

    @Override
    protected int getLayoutResId() {
        return R.layout.mensen_date_fragment;
    }

    @Override
    protected void findViews(View layoutView) {
        lastmensenEtx = (EditText) layoutView.findViewById(R.id.lastmensen);
        lastmensenEtx.setInputType(InputType.TYPE_NULL);

        avglastdaysEtx = (Spinner) layoutView.findViewById(R.id.avglastdays);


        lastdaysEtx = (Spinner) layoutView.findViewById(R.id.lastdays);

        nextmensenEtx = (EditText) layoutView.findViewById(R.id.nextmensen);

        getBtn = (Button) layoutView.findViewById(R.id.get);

        showBtn = (Button) layoutView.findViewById(R.id.show);
        initSpinnerData();
        initNeedClass();
        initTxtChangeEvt();
        initWigetEvt();
        initBaiduAd();
    }

    @Override
    protected void reqRemoteDatas(boolean _isForceFetchData) {

    }


    private void initTxtChangeEvt(){
        lastmensenEtx.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String _lastmensen = lastmensenEtx.getText().toString();
                if (_lastmensen == null || _lastmensen.trim().length()==0) {
                   // lastmensenEtx.setError(getResources().getString(R.string.lastmensen));
                   // ToastUtils.show(context,R.string.lastmensen);
                }else{
                    //lastmensenEtx.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }


        });

    }
    private void initSpinnerData(){
        String lastMensen= SharedPreferencesUtils.getLastMensen(getActivity(), null);
        if(lastMensen!=null)
            lastmensenEtx.setText(lastMensen);

        String nextMensen=SharedPreferencesUtils.getNextMensen(getActivity(), null);
        if(nextMensen!=null)
            nextmensenEtx.setText(nextMensen);

        List<Integer> avg=new ArrayList<Integer>();
        for(int i=26;i<=35;i++){
            avg.add(i);
        }

        avgAdapter=new ArrayAdapter<Integer>(getActivity(),R.layout.spinner_textview, avg);
        avgAdapter.setDropDownViewResource(R.layout.spinner_checkedview);
        avglastdaysEtx.setAdapter(avgAdapter);


        int index=avg.indexOf(SharedPreferencesUtils.getLastDays(getActivity(), 28));

        avglastdaysEtx.setSelection(index);

        List<Integer> last=new ArrayList<Integer>();
        for(int i=3;i<=8;i++){
            last.add(i);
        }
        lastAdapter=new ArrayAdapter<Integer>(getActivity(),R.layout.spinner_textview, last);
        lastAdapter.setDropDownViewResource(R.layout.spinner_checkedview);
        lastdaysEtx.setAdapter(lastAdapter);

        int index2=last.indexOf(SharedPreferencesUtils.getDays(getActivity(), 5));
        lastdaysEtx.setSelection(index2);


    }


    private void initNeedClass() {
        wc = new WomenCalendar(getActivity(), new SpecialCalendar());
        datePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                lastmensenEtx.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                datePicker.dismiss();


            }
        }, wc.getLastYear(), wc.getLastMonth() - 1, wc.getLastDay());
    }

    private void initWigetEvt() {

        lastmensenEtx.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!datePicker.isShowing())
                    datePicker.show();
                return false;
            }

        });

        getBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkEditText()) {
                    setnextmensenEtx();
                    if(!isOpenAd){
                        isOpenAd=true;
                        /*if (interAd.isAdReady()) {
                            interAd.showAd((Activity) context);
                        } else {
                            interAd.loadAd();
                        }*/
                    }
                }

            }

        });

        showBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkEditText()) {
                    setnextmensenEtx();
                    saveInputData();
                    goShowMensenCalendar();

                }
            }

        });
    }



    private boolean checkEditText() {
        String _lastmensen = lastmensenEtx.getText().toString();
        if (_lastmensen == null || _lastmensen.trim().length()==0) {
            //lastmensenEtx.setError(getResources().getString(R.string.lastmensen));
            ToastUtils.show(context,R.string.lastmensen);
            return false;
        }else{
           // lastmensenEtx.setError(null);
        }

        String _avglastdays = avglastdaysEtx.getSelectedItem().toString();
        if (_avglastdays == null || _avglastdays.trim().length()==0) {

            Toast.makeText(getActivity(), R.string.avglastdays, Toast.LENGTH_LONG)
                    .show();
            return false;
        } else {

            int _avglastdays2 = Integer.parseInt(_avglastdays);
            if (!(26 <= _avglastdays2 && _avglastdays2 <= 35)) {
                Toast.makeText(getActivity(), R.string.avglastdays2, Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        String _lastdays = lastdaysEtx.getSelectedItem().toString();
        if (_lastdays == null || _lastdays.trim().length()==0) {
            Toast.makeText(getActivity(), R.string.lastdays, Toast.LENGTH_LONG).show();
            return false;
        } else {
            int _lastDays2 = Integer.parseInt(_lastdays);
            if (!(3 <= _lastDays2 && _lastDays2 <= 8)) {
                Toast.makeText(getActivity(), R.string.lastdays2, Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        return true;
    }

    private void setnextmensenEtx() {
        nextmensenEtx.setText(wc.getNextDate(
                lastmensenEtx.getText().toString(),
                Integer.parseInt(avglastdaysEtx.getSelectedItem().toString()),
                Integer.parseInt(lastdaysEtx.getSelectedItem().toString())));
    }

    private void saveInputData(){
        SharedPreferencesUtils.setLastMensen(getActivity(), lastmensenEtx.getText().toString());
        SharedPreferencesUtils.setLastDays(getActivity(), Integer.parseInt(avglastdaysEtx.getSelectedItem().toString()));
        SharedPreferencesUtils.setDays(getActivity(), Integer.parseInt(lastdaysEtx.getSelectedItem().toString()));
        SharedPreferencesUtils.setNextMensen(getActivity(), nextmensenEtx.getText().toString());
    }

    private void goShowMensenCalendar(){
       // RadioGroup r=(RadioGroup) getActivity().findViewById(R.id.rg_tab);
        //r.check(R.id.women_date);
       // FragmentTransaction transaction =  getFragmentManager().beginTransaction();
       // Fragment fragment = FragmentFactory.getInstanceByIndex(R.id.women_date);
       // transaction.replace(R.id.content, fragment);
        //transaction.
       // transaction.commitAllowingStateLoss();
        if(mListener!=null){
            Bundle bundle=new Bundle();
            bundle.putInt("selectTabIndx",2);
            mListener.onFragmentInteraction(bundle);
        }

    }


    private void initBaiduAd(){
        /*String adPlaceId = "3071498"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
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




}
