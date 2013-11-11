package com.mobile.nuesoft.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Immunization;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

//import com.mobile.nuesoft.ui.MedicationFragment.ExpandableAdapter;

public class ImmunizationFragment extends NuesoftFragment {
	private LayoutInflater mInflater;
	private ExpandableAdapter mAdapter;
	private ExpandableListView listView;
	private TextView titleText;

	public ImmunizationFragment() {
		super();
		TAG = "ImmunizationFragment";
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFragmentPaused() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFragmentResume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSave(Bundle outState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFragmentStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFragmentStop() {
		// TODO Auto-generated method stub
	}

	@Override
	public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View rootView = mInflater.inflate(R.layout.immunization_fragment_layout, container, false);

		titleText = (TextView) rootView.findViewById(R.id.nt_title);
		titleText.setText("Immunizations");

		listView = (ExpandableListView) rootView.findViewById(R.id.expandable_list_view);
		initImmunizationView(listView);

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	}

	private void initImmunizationView(final ExpandableListView v) {
		mAdapter = new ExpandableAdapter(Nuesoft.getCurrentPatient());
		mAdapter.init();
		v.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {
		private LayoutInflater mInflater;
		private ArrayList<Immunization> list;
		private final PatientObj mPatient;

		public ExpandableAdapter(final PatientObj patient) {
			mPatient = patient;
		}

		private void init() {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			list = mPatient.getIMMUNIZATIONS();

			Log.d(TAG, "NCC - List SIZE: " + list.size());
		}

		@Override
		public Immunization getChild(int groupPos, int childPos) {
			return list.get(groupPos);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
		        ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.immunization_list_child_view, parent, false);
			}

			TextView mTitleText = (TextView) convertView.findViewById(R.id.nt_text);
			mTitleText.setText(list.get(groupPosition).manufacturerName);

			return convertView;
		}

		@Override
		public int getChildrenCount(int pos) {
			return 1;
		}

		@Override
		public String getGroup(int pos) {
			return list.get(pos).GetDisplayName();
		}

		@Override
		public int getGroupCount() {
			return list.size();
		}

		@Override
		public long getGroupId(int pos) {
			return pos;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.expandable_list_parent_item_view, null);
			}

			String mTitle = list.get(groupPosition).displayName;
			TextView titleText = (TextView) convertView.findViewById(R.id.nt_name);
			titleText.setText(mTitle);

			return convertView;

		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return false;
		}

	}

}
