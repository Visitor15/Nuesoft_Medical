package com.mobile.nuesoft.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Allergy;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.patient.VitalSign;
import com.mobile.nuesoft.util.Util;

public class PatientVitalsFragment extends NuesoftFragment {

	private LayoutInflater mInflater;
	
	private View rootView;

	private ListViewAdapter mAdapter;

	private ListView listView;

	public PatientVitalsFragment() {
		TAG = "PatientVitalsFragment";
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

		rootView = mInflater.inflate(R.layout.patient_vitals_fragment_layout, container, false);

		if (Nuesoft.getCurrentPatient() == null) {
			showNoDataView(rootView);
		} else {
			hideNoDataView(rootView);
		}

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	public void showNoDataView(final View v) {
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();

		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.no_data_layout, null);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		        LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);

		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(view);
	}

	public void hideNoDataView(final View v) {
		RelativeLayout mContainer = (RelativeLayout) v.findViewById(R.id.rl_container);
		mContainer.removeAllViews();
		listView = (ListView) mInflater.inflate(R.layout.vital_sign_fragment_list_layout, null);
		mContainer.addView(listView);
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();

		int mMargin = Util.convertDpToPixel(4f, getActivity());
		mlp.setMargins(mMargin, 0, mMargin, 0);

		initVitalsView(mContainer);
	}

	private void initVitalsView(final View v) {
		listView = (ListView) v.findViewById(R.id.list_view);

		mAdapter = new ListViewAdapter(Nuesoft.getCurrentPatient());
		listView.setAdapter(mAdapter);
	}

	public class ListViewAdapter extends BaseAdapter {

		private ArrayList<VitalSign> dataList;

		public ListViewAdapter(final PatientObj patient) {
			dataList = patient.getVITAL_SIGNS();
			init();
		}

		private void init() {

		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public VitalSign getItem(int pos) {
			return dataList.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup container) {
			final VitalSign mVitalSign = dataList.get(pos);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.allergy_item_layout, container, false);
			}

			RelativeLayout mContainer = (RelativeLayout) convertView.findViewById(R.id.rl_container);

			TextView textView = (TextView) convertView.findViewById(R.id.nt_text);
			textView.setText(mVitalSign.getDisplayName());

			return convertView;
		}
	}
}
