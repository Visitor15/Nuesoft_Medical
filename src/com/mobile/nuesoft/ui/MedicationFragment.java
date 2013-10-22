package com.mobile.nuesoft.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Medication;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class MedicationFragment extends NuesoftFragment {
	
	private PatientObj mPatient;
	private LayoutInflater mInflater;
	private ExpandableAdapter mAdapter;
	
	private ExpandableListView listView;

	private TextView titleText;
	
	public MedicationFragment() {
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
		View v = mInflater.inflate(R.layout.medication_fragment_layout, container, false);
		
		titleText = (TextView) v.findViewById(R.id.nt_title);
		titleText.setText("Medications");
		
		listView = (ExpandableListView) v.findViewById(R.id.expandable_list_view);
		
		mAdapter = new ExpandableAdapter(Nuesoft.getCurrentPatient());
		listView.setAdapter(mAdapter);
		
		mAdapter.notifyDataSetChanged();
		
		if(mPatient == null) {
			showNoDataView(v);
		}
		else {
			hideNoDataView(v);
		}
		
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
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();
		
		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.no_data_layout, null);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		
		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(view);
	}
	
	public void hideNoDataView(final View v) {
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();
		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(mInflater.inflate(R.layout.medication_fragment_meds_layout, null));
		
		initMedsView();
	}
	
	private void initMedsView() {
	}
	
	private class ExpandableAdapter extends BaseExpandableListAdapter {

		private LayoutInflater mInflater;
		
		private HashMap<String, ArrayList<Medication>> map = new HashMap<String, ArrayList<Medication>>();
		
		private final PatientObj mPatient;
		
		
		public ExpandableAdapter(final PatientObj patient) {
			mPatient = patient;
			
			init();
		}
		
		private void init() {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			
			map.put("Current Medications", mPatient.getMEDICATION_CURRENT());
			map.put("Previous Medications", mPatient.getMEDICATION_PREVIOUS());
		}
		
		@Override
        public Medication getChild(int groupPosition, int childPosition) {
			return map.get(map.keySet().toArray()[groupPosition]).get(childPosition);
        }

		@Override
        public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
        }

		@Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                ViewGroup parent) {
	        Medication medObj = map.get(groupPosition).get(childPosition);
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.document_item_view, null);
			}
			
			TextView mTitleText = (TextView) convertView.findViewById(R.id.nt_text);
			mTitleText.setText(medObj.getTITLE());
			
			Log.d("ExpandableAdapter", "MED TITLE: " + medObj.getTITLE());
			
			return convertView;
        }

		@Override
        public int getChildrenCount(int groupPosition) {
	        return map.get(map.keySet().toArray()[groupPosition]).size();
        }

		@Override
        public String getGroup(int groupPosition) {
	        return (String) map.keySet().toArray()[groupPosition];
        }

		@Override
        public int getGroupCount() {
			return map.keySet().size();
        }

		@Override
        public long getGroupId(int groupPosition) {
	        return groupPosition;
        }

		@Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        if(convertView == null) {
	        	convertView = mInflater.inflate(R.layout.expandable_list_parent_item_view, null);
	        }
	        
	        String mTitle = (String) map.keySet().toArray()[groupPosition];
	        TextView titleText = (TextView) convertView.findViewById(R.id.nt_name);
	        titleText.setText(mTitle);
	        
	        return convertView;
        }

		@Override
        public boolean hasStableIds() {
	        // TODO Auto-generated method stub
	        return false;
        }

		@Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
	        // TODO Auto-generated method stub
	        return false;
        }
		
	}
}
