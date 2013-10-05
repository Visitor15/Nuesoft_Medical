package com.mobile.nuesoft.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class ListViewAdapter extends BaseAdapter {

	private HashMap<String, ArrayList<String>> masterDataList = null;

	private ArrayList<ListViewItem> dataList = null;

	private LayoutInflater mInflater;

	public ListViewAdapter(final Context c, final ArrayList<String> dataList) {
		mInflater = LayoutInflater.from(c);
		init();
	}

	public ListViewAdapter(final Context c,
	        final HashMap<String, ArrayList<String>> dataList) {
		this.masterDataList = dataList;
		mInflater = LayoutInflater.from(c);
		init();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public ListViewItem getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		ListViewItem currentItem = dataList.get(position);
		if (convertView == null) {
			if (currentItem.isHeader()) {
				convertView = mInflater.inflate(
				        R.layout.list_view_item_header_view, null);
//				convertView.setClickable(false);
			} else {
				convertView = mInflater.inflate(R.layout.list_view_item_view,
				        null);
//				convertView.setClickable(true);
			}
		}

		((TextView) convertView.findViewById(R.id.nt_text)).setText(currentItem
		        .getTitle());

		return convertView;
	}

	public void init() {
		PatientObj patient = Nuesoft.getCurrentPatient();
		dataList = new ArrayList<ListViewItem>();
		if (masterDataList != null) {
			for (final String key : this.masterDataList.keySet()) {
				dataList.add(new ListViewItem(key, true));
				for (String entry : this.masterDataList.get(key)) {
					if (entry.equalsIgnoreCase("Medication")) {
						if (patient != null) {
							entry += " ("
							        + patient.getMEDICATION_CURRENT().size()
							        + ")";
						}
					} else if (entry.equalsIgnoreCase("Allergies")) {
						if (patient != null) {
							entry += " (" + patient.getALLERGIES().size() + ")";
						}
					} else if (entry.equalsIgnoreCase("Family History")) {

					} else if (entry.equalsIgnoreCase("Patient History")) {
						if (patient != null) {
							entry += " (" + patient.getMEDICAL_ENCOUNTERS().size() + ")";
						}
					} else if (entry.equalsIgnoreCase("Social Information")) {

					} else if (entry.equalsIgnoreCase("Immunization")) {
						
					}
					dataList.add(new ListViewItem(entry, false));
				}
			}
		}
	}

	private class ListViewItem {
		private final boolean isHeader;
		private final String mTitle;

		public ListViewItem(final String title, final boolean isHeader) {
			this.isHeader = isHeader;
			this.mTitle = title;
		}

		public String getTitle() {
			return mTitle;
		}

		public boolean isHeader() {
			return isHeader;
		}
	}
}
