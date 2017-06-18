package com.mm.mmcalendar.tab4;

import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mm.mmcalendar.R;
import com.mm.mmcalendar.bmobmodel.InfoBean;
import com.mm.mmcalendar.bmobmodel.InfoType;
import com.mm.mmcalendar.bmobmodel.RequestTaskIds;
import com.mm.mmcalendar.bmobmodel.SubCategoryBean;
import com.mm.mmcalendar.common.BasePageFragment;
import com.mm.mmcalendar.common.adapter.CommonAdapter;
import com.mm.mmcalendar.common.log.LogUtil;
import com.mm.mmcalendar.common.okhttp.OKHttpManager;
import com.mm.mmcalendar.common.okhttp.TaskWatcher;
import com.mm.mmcalendar.common.utils.JsonResult;
import com.mm.mmcalendar.common.utils.ToastUtils;
import com.mm.mmcalendar.common.widget.PullToRefreshExpendableStickyListHeadersListView;
import com.mm.mmcalendar.common.widget.stickylist.ExpandableStickyListHeadersListView;
import com.mm.mmcalendar.common.widget.stickylist.StickyListHeadersListView;
import com.mm.mmcalendar.tab2.adapter.StickyListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * tab界面-首页-免费账号
 * @author 喧嚣求静
 * @since 2015-08-18
 * @version 1.0
 * */
public class Tab4RightFragment extends BasePageFragment implements
		OnItemClickListener {

	private ListView leftListView;
	private ExpandableStickyListHeadersListView rightListView;
	private PullToRefreshExpendableStickyListHeadersListView pullRightListView;
	private StickyListAdapter rightAdapter;
	private CommonAdapter<InfoType> leftAdapter;

	private static final int STATE_REFRESH = 0;// 下拉刷新
	private static final int STATE_MORE = 1;// 加载更多
	private int curActionType=STATE_REFRESH;

	private String curTypeId = null;

	private int limit = 10; // 每页的数据是10条
	private int curPage = 0; // 当前页的编号，从0开始

	//AdView adView;

	public static Fragment newInstance() {
		Tab4RightFragment fragment = new Tab4RightFragment();
		return fragment;
	}


	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_shareaccount;
	}

	@Override
	protected void findViews(View layoutView) {
		leftListView = (ListView) layoutView.findViewById(R.id.tvType);
		pullRightListView = (PullToRefreshExpendableStickyListHeadersListView)layoutView.findViewById(R.id.vipcontent);
		pullRightListView.setOnRefreshListener(refreshListener);
		rightListView = pullRightListView.getRefreshableView();
		initAd();
		init();
	}

	@Override
	protected void reqRemoteDatas(boolean _isForceFetchData) {
		LogUtil.d(Tab4RightFragment.this.getClass().getSimpleName(),"加载数据……reqRemoteDatas");
		leftqryData();
	}

	private void init() {

		leftAdapter = new CommonAdapter<InfoType>(context,R.layout.tab2_frag_left_item,
				new ArrayList<InfoType>()) {
			@Override
			public void convert(ViewHolder helper,
					final InfoType item,final int _selectedPosition) {
				helper.setText(R.id.headerTip, item.getInfoTypeName());
				helper.getConvertView().setTag(R.id.listItem,item);
				if(_selectedPosition==helper.getPosition()) {
					helper.getConvertView().setBackgroundColor(context.getResources().getColor(R.color.white));
					helper.getView(R.id.headerTip).setSelected(true);
				}else{
					helper.getConvertView().setBackgroundColor(context.getResources().getColor(R.color.list_item_bgcolor));
					helper.getView(R.id.headerTip).setSelected(false);
				}
			}
		};

		leftListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		leftListView.setAdapter(leftAdapter);

		leftListView.requestFocus();

		leftListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (view.getTag(R.id.listItem) != null) {

					InfoType type = (InfoType) view.getTag(R.id.listItem);
					curTypeId = type.getObjectId();
					leftAdapter.setSelectedPosition(position);
					leftAdapter.notifyDataSetChanged();
					getRightData(0, STATE_REFRESH, curTypeId);
				}
			}
		});
		//View adHead=LayoutInflater.from(context).inflate(R.layout.baidu_ad_layout, null, false);
		//rightListView.addFooterView(adView);
		rightAdapter = new StickyListAdapter(context,new ArrayList<InfoBean>());
		rightListView.setAdapter(rightAdapter);
		rightListView
				.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
					@Override
					public void onHeaderClick(StickyListHeadersListView l,
							View header, int itemPosition, long headerId,
							boolean currentlySticky) {
						if (rightListView.isHeaderCollapsed(headerId)) {
							rightListView.expand(headerId);
						} else {
							rightListView.collapse(headerId);
						}
					}
				});
		rightListView.setOnItemClickListener(this);
	}


	private void initAd(){
		// 创建广告View
		/*String adPlaceId = "3071672"; //  重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
		adView = new AdView(context, adPlaceId);
		// 设置监听器
		adView.setListener(new AdViewListener() {
			public void onAdSwitch() {
				Log.w("", "onAdSwitch");
			}

			public void onAdShow(JSONObject info) {
				// 广告已经渲染出来
				Log.w("", "onAdShow " + info.toString());
			}

			public void onAdReady(AdView adView) {
				// 资源已经缓存完毕，还没有渲染出来
				Log.w("", "onAdReady " + adView);
			}

			public void onAdFailed(String reason) {
				Log.w("", "onAdFailed " + reason);
			}

			public void onAdClick(JSONObject info) {
				// Log.w("", "onAdClick " + info.toString());

			}

			@Override
			public void onAdClose(JSONObject arg0) {
				Log.w("", "onAdClose");
			}
		});*/
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Object obj =view.getTag(R.id.listItem);
		//ToastUtils.show(context,"itemClick");
		if (obj != null) {
			//InfoBean data=(InfoBean) obj;
			//Intent i = new Intent(context,ViewInfoActivity.class);
			//i.putExtra(InfoBean.ITEM_KEY,data);
			//startActivity(i);
			//ToastUtils.show(context,"startActvity");
		}
	}

	OnRefreshListener2<ExpandableStickyListHeadersListView> refreshListener = new OnRefreshListener2<ExpandableStickyListHeadersListView>() {

		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ExpandableStickyListHeadersListView> refreshView) {
			// 下拉刷新
			String time = DateUtils.formatDateTime(context,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullRightListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
			pullRightListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
			pullRightListView.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + time);

			// 调用数据
			getRightData(0, STATE_REFRESH, curTypeId);
		}

		@Override
		public void onPullUpToRefresh(
				PullToRefreshBase<ExpandableStickyListHeadersListView> refreshView) {
			// 上拉翻页
			String time = DateUtils.formatDateTime(context,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			pullRightListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
			pullRightListView.getLoadingLayoutProxy().setPullLabel("上拉翻页");
			pullRightListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + time);
			// 调用数据
			getRightData(curPage, STATE_MORE, curTypeId);
		}
	};


	private void getRightData(final int page, final int actionType, String typeId){
		curActionType=actionType;
		curTypeId=typeId;
		if (actionType == STATE_REFRESH) {
			// 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
			curPage = 0;
		} else if (actionType == STATE_MORE) {
			curPage=page;
		}
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("infoTypeId",typeId);
			String encodeUrl=URLEncoder.encode(jsonObject.toString(),"UTF-8");

			String params="?where="+encodeUrl+"&limit="+limit+"&skip="+(curPage * limit)+"&order=-updatedAt,postionOrder";

			OKHttpManager.getInstance().doGetRequest(RequestTaskIds.LeftFragment_InfoBean_Qry,
					InfoBean.restApiUrl+params,watcher,isReadCache);

			//OKHttpManager.getInstance().doGetRequest(RequestTaskIds.LeftFragment_InfoType_Qry,
			//	InfoType.restApiUrl,watcher);
		} catch (JSONException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

	}


	private void leftqryData() {
		//Gson gson=new Gson();
		//UserBean person=new UserBean("张三", MD5.encode("123"));
		//RequestBody body = RequestBody.create(OKHttpManager.JSON, gson.toJson(person));
		//OKHttpManager.getInstance().doPostRequest(1, UserBean.restApiUrl,body,watcher);
		JSONObject jsonObject=new JSONObject();
		try {
			 jsonObject.put("subCategoryId", SubCategoryBean.jkyyId);
			 String encodeUrl=URLEncoder.encode(jsonObject.toString(),"UTF-8");
			 String params=encodeUrl+"&order=postionOrder";

           OKHttpManager.getInstance().doGetRequest(RequestTaskIds.LeftFragment_InfoType_Qry,
				                      InfoType.restApiUrl+"?where="+params,watcher,isReadCache);

			//OKHttpManager.getInstance().doGetRequest(RequestTaskIds.LeftFragment_InfoType_Qry,
				//	InfoType.restApiUrl,watcher);
		} catch (JSONException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}

	private TaskWatcher watcher = new TaskWatcher() {

		public void onStart(int code) {};

		public void onStop(int code) {};

		@Override
		public void onSuccess(int code, int arg1, int arg2, Object obj) {
			switch (code) {
				case RequestTaskIds.LeftFragment_InfoType_Qry:

					LogUtil.d(Tab4RightFragment.this.getClass().getSimpleName(),obj.toString());
					JsonResult<List<InfoType>> jsonResult = JsonResult.parse(obj.toString(), new TypeToken<JsonResult<List<InfoType>>>() {
					}.getType());
					if (jsonResult.isSuccess()) {
						if(jsonResult.getResults()!=null && jsonResult.getResults().size()>0) {
							leftAdapter.change(jsonResult.getResults());
							leftAdapter.setSelectedPosition(0);
							curTypeId = leftAdapter.getItem(0).getObjectId();
							getRightData(0, STATE_REFRESH, curTypeId);
						}
					}
					break;
				case RequestTaskIds.LeftFragment_InfoBean_Qry:
					pullRightListView.onRefreshComplete();
					LogUtil.d(Tab4RightFragment.this.getClass().getSimpleName(),obj.toString());
					JsonResult<List<InfoBean>> jsonResult2 = JsonResult.parse(obj.toString(), new TypeToken<JsonResult<List<InfoBean>>>() {
					}.getType());
					if (jsonResult2.isSuccess()) {
						if (curActionType == STATE_REFRESH) {
							// 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
							rightAdapter.changeDataSource(jsonResult2.getResults());
						} else if (curActionType == STATE_MORE) {
							rightAdapter.addDataSource(jsonResult2.getResults());
						}
						curPage+=1;
					}
					break;

				default:
					break;
			}
		}

		@Override
		public void onError(int code, int arg1, int arg2, Object obj) {
			ToastUtils.show(context, obj.toString());
			LogUtil.d(Tab4RightFragment.this.getClass().getSimpleName(),obj.toString());
		}
	};

}
