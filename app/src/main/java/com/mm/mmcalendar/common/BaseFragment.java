package com.mm.mmcalendar.common;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于预加载数
 */
public abstract class BaseFragment extends Fragment {

	protected Context context;
	private boolean isInitViews=false;
	private boolean isVisibleToUser=false;
	protected boolean isLoaded=false;
	protected boolean isReadCache=true;
	protected OnFragmentInteractionListener mListener;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		if (activity instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) activity;
		} else {
			throw new RuntimeException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(getLayoutResId(), null);
	}



	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		findViews(view);
		isInitViews=true;
		fetchData(false);
	}

	protected abstract  int getLayoutResId();

	protected abstract  void findViews(View layoutView);

	protected abstract  void reqRemoteDatas(boolean _isForceFetchData);

	public  void fetchData(boolean _isForceFetchData){
		isReadCache=_isForceFetchData?false:true;
		if((_isForceFetchData || !isLoaded )&& (isInitViews && isVisibleToUser)){
			reqRemoteDatas(_isForceFetchData);
			isLoaded=true;
		}
	}



	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		this.isVisibleToUser=isVisibleToUser;
		fetchData(false);
	}


	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Bundle args);
	}
}
