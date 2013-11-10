package com.mobile.nuesoft.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Immunization;
import com.mobile.nuesoft.patient.Medication;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
//import com.mobile.nuesoft.ui.MedicationFragment.ExpandableAdapter;
import com.mobile.nuesoft.util.Util;

public class ImmunizationFragment extends NuesoftFragment {
	private LayoutInflater mInflater;
	private ExpandableAdapter mAdapter;	
	private ExpandableListView listView;
	private TextView titleText;
	
	public ImmunizationFragment(){
		super();
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
	
	public void showNoDataView(final View v){	
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();

		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.no_data_layout, null);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		        LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);

		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(view);
		
	}
	
	//TODO: change out to allergy layout
	public void hideNoDataView(final View v){
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();
		ExpandableListView addedView = (ExpandableListView) mInflater.inflate(R.layout.medication_fragment_meds_layout, null);
		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(addedView);
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) addedView.getLayoutParams();

		int mMargin = Util.convertDpToPixel(8f, getActivity());
		mlp.setMargins(mMargin, 0, mMargin, 0);
		
		initImmunizationView(v);
	}
	
	private void initImmunizationView(final View v){
		listView = (ExpandableListView) v.findViewById(R.id.expandable_list_view);
		
		mAdapter = new ExpandableAdapter(Nuesoft.getCurrentPatient());
		listView.setAdapter(mAdapter);
		
		listView.setAdapter(mAdapter);
		mAdapter.init();
	}
	
	private class ExpandableAdapter extends BaseExpandableListAdapter {
		private LayoutInflater mInflater;
		private ArrayList<Immunization> list = new ArrayList<Immunization>();
		//private HashMap<String, ArrayList<Immunization>> map = new HashMap<String, ArrayList<Immunization>>();
		private final PatientObj mPatient;
		
		public ExpandableAdapter(final PatientObj patient) {
			mPatient = patient;
		}
		
		private void init(){
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			list = mPatient.getIMMUNIZATIONS();
		}

		@Override
        public Object getChild(int arg0, int arg1) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public long getChildId(int arg0, int arg1) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public int getChildrenCount(int arg0) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public Object getGroup(int arg0) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public int getGroupCount() {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public long getGroupId(int arg0) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public boolean hasStableIds() {
	        // TODO Auto-generated method stub
	        return false;
        }

		@Override
        public boolean isChildSelectable(int arg0, int arg1) {
	        // TODO Auto-generated method stub
	        return false;
        }
	
	}

}
