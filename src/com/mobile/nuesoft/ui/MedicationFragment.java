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
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Medication;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.util.Util;

public class MedicationFragment extends NuesoftFragment {
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
		
		if(Nuesoft.getCurrentPatient() == null) {
			showNoDataView(v);
		}
		else {
			hideNoDataView(v);
		}
		
		return v;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		
	}
	
//	public void setPatientObj(final PatientObj patient) {
//		this.mPatient = patient;
//	}
//
//	public PatientObj getPatient() {
//		return mPatient;
//	}
	
	public void showNoDataView(final View v) {
//		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();
		
		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.no_data_layout, null);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		
		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(view);
	}
	
	public void hideNoDataView(final View v) {
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();
		ExpandableListView addedView = (ExpandableListView) mInflater.inflate(R.layout.medication_fragment_meds_layout, null);
		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(addedView);
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) addedView
		        .getLayoutParams();
		

		int mMargin = Util.convertDpToPixel(8f, getActivity());
		mlp.setMargins(mMargin, 0, mMargin, 0);
		
		initMedsView(v);
	}
	
	private void initMedsView(final View v) {
		listView = (ExpandableListView) v.findViewById(R.id.expandable_list_view);
		
		mAdapter = new ExpandableAdapter(Nuesoft.getCurrentPatient());
		listView.setAdapter(mAdapter);
		mAdapter.init();
	}
	
	private class ExpandableAdapter extends BaseExpandableListAdapter {

		private LayoutInflater mInflater;
		
		private HashMap<String, ArrayList<Medication>> map = new HashMap<String, ArrayList<Medication>>();
		
		private final PatientObj mPatient;
		
		
		public ExpandableAdapter(final PatientObj patient) {
			mPatient = patient;
		}
		
		private void init() {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			
			map.put("Previous Medications", mPatient.getMEDICATION_PREVIOUS());
			map.put("Current Medications", mPatient.getMEDICATION_CURRENT());
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
			
			String key =  (String) map.keySet().toArray()[groupPosition];
	        Medication medObj = map.get(key).get(childPosition);
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.medication_list_child_view, null);
			}
			
			TextView mTitleText = (TextView) convertView.findViewById(R.id.nt_text);
			mTitleText.setText(medObj.getTITLE());
			
			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);
			
			switch(groupPosition) {
				case 0: {
					iconColor.setBackgroundColor(Color.parseColor("#33B5E5"));
					break;
				}
				case 1: {
					iconColor.setBackgroundColor(Color.parseColor("#AA66CC"));
					break;
				}
			}
			
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
	        
			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);
			
			switch(groupPosition) {
				case 0: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));
					break;
				}
				case 1: {
					iconColor.setBackgroundColor(Color.parseColor("#9933CC"));
					break;
				}
			}
	        
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
