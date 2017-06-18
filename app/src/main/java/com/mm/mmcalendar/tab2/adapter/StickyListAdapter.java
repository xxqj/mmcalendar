package com.mm.mmcalendar.tab2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.adapter.BaseViewHolder;
import com.mm.mmcalendar.common.utils.TimeUtil;
import com.mm.mmcalendar.common.widget.stickylist.StickyListHeadersAdapter;
import com.mm.mmcalendar.bmobmodel.InfoBean;
import com.mm.mmcalendar.tab2.ViewInfoActivity;

import java.util.List;

public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
	
	private List<InfoBean> items;
	private Context mContext;

	public StickyListAdapter(Context mContext,List<InfoBean> items) {
		super();
		this.items = items;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public InfoBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.tab2_frag_right_item, parent, false);
		}
		final InfoBean vo=getItem(position);
		TextView title=BaseViewHolder.get(convertView,R.id.headerTip);
		title.setText(vo.getInfoTitle());
		
		TextView viewsCnt=BaseViewHolder.get(convertView,R.id.tvDesc);
		viewsCnt.setText(vo.getInfoDesc());
		convertView.setTag(R.id.listItem, vo);

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(mContext,ViewInfoActivity.class);
				i.putExtra(InfoBean.ITEM_KEY,vo);
				mContext.startActivity(i);
			}
		});

		return convertView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.tab2_frag_right_item_head, parent, false);
			//convertView=LayoutInflater.from(mContext).inflate(R.layout.baidu_ad_layout, parent, false);
		}


		TextView txt=BaseViewHolder.get(convertView, R.id.headerTip);
		//InfoBean vo=getItem(position);
		txt.setText(TimeUtil.dateToString(getItem(position).getUpdatedAt(),"yyyy年MM月"));
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return TimeUtil.dateToString(getItem(position).getUpdatedAt(),"yyyy年MM月").hashCode();
	}

	public boolean changeDataSource(List<InfoBean> _items){
		if(items==null)return false;
		items.clear();
		boolean flag=items.addAll(_items);
		notifyDataSetChanged();
		return flag;
	}

	public void addDataSource(List<InfoBean> _items){
		if(_items==null)return ;
		items.addAll(_items);
		notifyDataSetChanged();
	}

}
