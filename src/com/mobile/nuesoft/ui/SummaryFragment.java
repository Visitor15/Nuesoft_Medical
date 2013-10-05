package com.mobile.nuesoft.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.PatientBuilder;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class SummaryFragment extends NuesoftFragment {
	
	private PatientObj mPatient;
	
	private LayoutInflater mInflater;
	
	private TextView mTitleText;
	
	SummaryFragment() {
		
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
		View v = mInflater.inflate(R.layout.patient_summary_fragment_layout, container, false);
		
		mTitleText = (TextView) v.findViewById(R.id.nt_title);
		mTitleText.setText("Patient Summary");
		
//		if(mPatient == null) {
//			showNoDataView(v);
//		}
//		else {
//			hideNoDataView(v);
//		}
		
		return v;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPatientObj(final PatientObj patient) {
		this.mPatient = patient;
	}

	public PatientObj getPatient() {
		return mPatient;
	}
	
	public void showNoDataView(final View v) {
		((LinearLayout) v.findViewById(R.id.ll_container)).removeAllViews();
		
		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.no_data_layout, null);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		
		((LinearLayout) v.findViewById(R.id.ll_container)).addView(view);
	}
	
	public void hideNoDataView(final View v) {
		((LinearLayout) v.findViewById(R.id.ll_container)).removeAllViews();
		((LinearLayout) v.findViewById(R.id.ll_container)).addView(mInflater.inflate(R.layout.medication_fragment_meds_layout, null));
		
		initSummaryView();
	}
	
	private void initSummaryView() {
		
	}
}
