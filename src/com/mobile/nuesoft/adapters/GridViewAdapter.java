package com.mobile.nuesoft.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.nuesoft.R;


public class GridViewAdapter extends BaseAdapter {
	
	public static final String TAG = "GridViewAdapter";

	ArrayList<GridViewObject> dataList;
	
	private LayoutInflater mInflater;
	
	private Context mContext;

	public GridViewAdapter(final Context c) {
		mInflater = LayoutInflater.from(c);
		mContext = c;
		
		init();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public GridViewObject getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.gridview_basic_layout, null);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.nt_text);
		ImageView icon = (ImageView) convertView.findViewById(R.id.iv_icon);
		
		title.setText(dataList.get(position).getStringRes());
		icon.setImageResource(dataList.get(position).iconRes);
		
		return convertView;
	}
	
	private void init() {
		dataList = new ArrayList<GridViewObject>();
		String[] categories = mContext.getResources().getStringArray(R.array.profile_categories);
		int[] categoryIcons = mContext.getResources().getIntArray(R.array.profile_category_icons);
		
		Log.d(TAG, "DATA: " + categories.length + " AND " + categoryIcons.length);
		
		if(categories.length == categoryIcons.length) {
			for(int i = 0; i < categories.length; i++) {
				dataList.add(new GridViewObject(categories[i], categoryIcons[i]));
			}
		} else{
			Log.e(TAG, "NOT ALL GRIDVIEW TITLES HAVE AN ICON");
		}
	}

	public class GridViewObject {
		private String stringRes = "";
		private int iconRes = 0;

		public GridViewObject(final String stringRes, final int iconRes) {
			this.stringRes = stringRes;
			this.iconRes = iconRes;
		}
		
		public String getStringRes() {
			return stringRes;
		}
		
		public int getIconRes() {
			return iconRes;
		}
	}

}
