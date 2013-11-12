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
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Allergy;
import com.mobile.nuesoft.patient.FamilyHistory;
import com.mobile.nuesoft.patient.Immunization;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.patient.Problem;
import com.mobile.nuesoft.patient.Procedure;
import com.mobile.nuesoft.patient.VitalSign;

public class PatientOverviewFragment extends NuesoftFragment {

	private View rootView;

	private TextView mTitle;

	private ExpandableListView expandableList;

	private ExpandableAdapter mAdapter;

	public PatientOverviewFragment() {

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
		rootView = inflater.inflate(R.layout.patient_overview_frag_layout, container, false);

		mTitle = (TextView) rootView.findViewById(R.id.nt_title);
		expandableList = (ExpandableListView) rootView.findViewById(R.id.expandable_list_view);

		mTitle.setText("Patient Overview");

		initExpandableListAdapter();

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	private void initExpandableListAdapter() {
		mAdapter = new ExpandableAdapter(Nuesoft.getCurrentPatient());
		mAdapter.init();
		expandableList.setAdapter(mAdapter);
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {

		private LayoutInflater mInflater;

		private HashMap<String, ArrayList<?>> map = new HashMap<String, ArrayList<?>>();

		private final PatientObj mPatient;

		public ExpandableAdapter(final PatientObj patient) {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			mPatient = patient;
		}

		private void init() {
			map.put("Allergies", mPatient.getALLERGIES());
			map.put("Immunizations", mPatient.getIMMUNIZATIONS());
			map.put("Vital Signs", mPatient.getVITAL_SIGNS());
			map.put("Family History", mPatient.getFAMILY_HISTORY());
			map.put("Medical Procedures", mPatient.getPROCEDURES());
			map.put("Known Issues", mPatient.getKNOWN_ISSUES());
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return map.get(map.keySet().toArray()[groupPosition]).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
		        ViewGroup parent) {

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.medication_list_child_view, parent, false);
			}

			TextView mTitleText = (TextView) convertView.findViewById(R.id.nt_text);

			String key = (String) map.keySet().toArray()[groupPosition];
			if (key.equalsIgnoreCase("vital signs")) {
				mTitleText.setText(((ArrayList<VitalSign>) map.get(key)).get(childPosition).getDisplayName());
			} else if (key.equalsIgnoreCase("allergies")) {
				mTitleText.setText(((ArrayList<Allergy>) map.get(key)).get(childPosition).getDisplayName());
			} else if (key.equalsIgnoreCase("immunizations")) {
				mTitleText.setText(((ArrayList<Immunization>) map.get(key)).get(childPosition).getDisplayName());
			} else if (key.equalsIgnoreCase("family history")) {
				mTitleText.setText(((ArrayList<FamilyHistory>) map.get(key)).get(childPosition).getDisplayName());
			} else if (key.equalsIgnoreCase("Medical Procedures")) {
				mTitleText.setText(((ArrayList<Procedure>) map.get(key)).get(childPosition).getDISPLAY_TITLE());
			} else if(key.equalsIgnoreCase("known issues")) {
				mTitleText.setText(((ArrayList<Problem>) map.get(key)).get(childPosition).getDisplayname());
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
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.expandable_list_parent_item_view, null);
			}

			String mTitle = (String) map.keySet().toArray()[groupPosition];
			TextView titleText = (TextView) convertView.findViewById(R.id.nt_name);
			titleText.setText(mTitle);

			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);

			switch (groupPosition) {
				case 0: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));
					break;
				}
				case 1: {
					iconColor.setBackgroundColor(Color.parseColor("#9933CC"));
					break;
				}
				case 2: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));
					break;
				}
				case 3: {
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
