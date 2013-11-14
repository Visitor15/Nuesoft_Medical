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
import com.mobile.nuesoft.patient.Medication;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.patient.Problem;
import com.mobile.nuesoft.patient.Procedure;
import com.mobile.nuesoft.patient.SocialHistory;
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

		private static final int ID_CURRENT_MED = 0;
		private static final int ID_PREVIOUS_MED = 1;
		private static final int ID_MED_PROCEDURES = 2;
		private static final int ID_VITAL_SIGNS = 3;
		private static final int ID_KNOWN_ISSUES = 4;
		private static final int ID_ALLERGIES = 5;
		private static final int ID_IMMUNIZATIONS = 6;
		private static final int ID_FAM_HISTORY = 7;
		private static final int ID_SOCIAL_HISTORY = 8;

		private LayoutInflater mInflater;

		private HashMap<String, ArrayList<?>> map = new HashMap<String, ArrayList<?>>();

		private final PatientObj mPatient;

		public ExpandableAdapter(final PatientObj patient) {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			mPatient = patient;
		}

		private void init() {
			map.put("Current Medications", mPatient.getMEDICATION_CURRENT());
			map.put("Previous Medications", mPatient.getMEDICATION_PREVIOUS());
			map.put("Medical Procedures", mPatient.getPROCEDURES());
			map.put("Vital Signs", mPatient.getVITAL_SIGNS());
			map.put("Known Issues", mPatient.getKNOWN_ISSUES());
			map.put("Allergies", mPatient.getALLERGIES());
			map.put("Immunizations", mPatient.getIMMUNIZATIONS());
			map.put("Family History", mPatient.getFAMILY_HISTORY());
			map.put("Social History", mPatient.getSOCIAL_HISTORY());
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			switch (groupPosition) {
				case ID_CURRENT_MED: {
					return ((ArrayList<Medication>) map.get("Current Medications")).get(childPosition);
				}
				case ID_PREVIOUS_MED: {
					return ((ArrayList<Medication>) map.get("Previous Medications")).get(childPosition);
				}
				case ID_MED_PROCEDURES: {
					return ((ArrayList<Procedure>) map.get("Medication Procedures")).get(childPosition);
				}
				case ID_VITAL_SIGNS: {
					return ((ArrayList<VitalSign>) map.get("Vital Signs")).get(childPosition);
				}
				case ID_KNOWN_ISSUES: {
					return ((ArrayList<Problem>) map.get("Known Issues")).get(childPosition);
				}
				case ID_ALLERGIES: {
					return ((ArrayList<Allergy>) map.get("Allergies")).get(childPosition);
				}
				case ID_IMMUNIZATIONS: {
					return ((ArrayList<Immunization>) map.get("Immunizations")).get(childPosition);
				}
				case ID_FAM_HISTORY: {
					return ((ArrayList<FamilyHistory>) map.get("Family History")).get(childPosition);
				}
				case ID_SOCIAL_HISTORY: {
					return ((ArrayList<SocialHistory>) map.get("Social History")).get(childPosition);
				}
			}

			return null;
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
			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);

			switch (groupPosition) {
				case ID_CURRENT_MED: {
					try {
						mTitleText.setText(((ArrayList<Medication>) map.get("Current Medications")).get(childPosition)
						        .getTITLE());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#33B5E5"));

					break;
				}
				case ID_PREVIOUS_MED: {
					try {
						mTitleText.setText(((ArrayList<Medication>) map.get("Previous Medications")).get(childPosition)
						        .getTITLE());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#AA66CC"));

					break;
				}
				case ID_MED_PROCEDURES: {
					try {
						mTitleText.setText(((ArrayList<Procedure>) map.get("Medication Procedures")).get(childPosition)
						        .getDISPLAY_TITLE());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#99CC00"));

					break;
				}
				case ID_VITAL_SIGNS: {
					try {
						mTitleText.setText(((ArrayList<VitalSign>) map.get("Vital Signs")).get(childPosition)
						        .getDisplayName());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#FFBB33"));

					break;
				}
				case ID_KNOWN_ISSUES: {
					try {
						mTitleText.setText(((ArrayList<Problem>) map.get("Known Issues")).get(childPosition)
						        .getDisplayname());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#FF4444"));

					break;
				}
				case ID_ALLERGIES: {
					try {
						mTitleText.setText(((ArrayList<Allergy>) map.get("Allergies")).get(childPosition)
						        .getDisplayName());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#33B5E5"));

					break;
				}
				case ID_IMMUNIZATIONS: {
					try {
						mTitleText.setText(((ArrayList<Immunization>) map.get("Immunizations")).get(childPosition)
						        .getDisplayName());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#AA66CC"));

					break;
				}
				case ID_FAM_HISTORY: {
					try {
						mTitleText.setText(((ArrayList<FamilyHistory>) map.get("Family History")).get(childPosition)
						        .getDisplayName());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#99CC00"));

					break;
				}
				case ID_SOCIAL_HISTORY: {
					try {
						mTitleText.setText(((ArrayList<SocialHistory>) map.get("Social History")).get(childPosition)
						        .getObservationDisplayName());
					} catch (final IndexOutOfBoundsException e) {
						mTitleText.setText("No data found");
					}

					iconColor.setBackgroundColor(Color.parseColor("#FFBB33"));

					break;
				}
			}

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			int count = 0;
			switch (groupPosition) {
				case ID_CURRENT_MED: {
					count = ((ArrayList<Medication>) map.get("Current Medications")).size();
					break;
				}
				case ID_PREVIOUS_MED: {
					count = ((ArrayList<Medication>) map.get("Previous Medications")).size();
					break;
				}
				case ID_MED_PROCEDURES: {
					count = ((ArrayList<Procedure>) map.get("Medication Procedures")).size();
					break;
				}
				case ID_VITAL_SIGNS: {
					count = ((ArrayList<VitalSign>) map.get("Vital Signs")).size();
					break;
				}
				case ID_KNOWN_ISSUES: {
					count = ((ArrayList<Problem>) map.get("Known Issues")).size();
					break;
				}
				case ID_ALLERGIES: {
					count = ((ArrayList<Allergy>) map.get("Allergies")).size();
					break;
				}
				case ID_IMMUNIZATIONS: {
					count = ((ArrayList<Immunization>) map.get("Immunizations")).size();
					break;
				}
				case ID_FAM_HISTORY: {
					count = ((ArrayList<FamilyHistory>) map.get("Family History")).size();
					break;
				}
				case ID_SOCIAL_HISTORY: {
					count = ((ArrayList<SocialHistory>) map.get("Social History")).size();
					break;
				}
			}

			if (count == 0) {
				count = 1;
			}
			return count;
		}

		@Override
		public String getGroup(int groupPosition) {
			String value = "";
			switch (groupPosition) {
				case ID_CURRENT_MED: {
					value = "Current Medications";
					break;
				}
				case ID_PREVIOUS_MED: {
					value = "Prevous Medications";
					break;
				}
				case ID_MED_PROCEDURES: {
					value = "Medical Procedures";
					break;
				}
				case ID_VITAL_SIGNS: {
					value = "Vital Signs";
					break;
				}
				case ID_KNOWN_ISSUES: {
					value = "Known Issues";
					break;
				}
				case ID_ALLERGIES: {
					value = "Allergies";
					break;
				}
				case ID_IMMUNIZATIONS: {
					value = "Immunizations";
					break;
				}
				case ID_FAM_HISTORY: {
					value = "Family History";
					break;
				}
				case ID_SOCIAL_HISTORY: {
					value = "Social History";
					break;
				}
			}

			return value;
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

			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);
			TextView titleText = (TextView) convertView.findViewById(R.id.nt_name);

			switch (groupPosition) {
				case ID_CURRENT_MED: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));
					titleText.setText("Current Medications");
					break;
				}
				case ID_PREVIOUS_MED: {
					iconColor.setBackgroundColor(Color.parseColor("#9933CC"));
					titleText.setText("Prevous Medications");
					break;
				}
				case ID_MED_PROCEDURES: {
					iconColor.setBackgroundColor(Color.parseColor("#669900"));
					titleText.setText("Medical Procedures");
					break;
				}
				case ID_VITAL_SIGNS: {
					iconColor.setBackgroundColor(Color.parseColor("#FF8800"));
					titleText.setText("Vital Signs");
					break;
				}
				case ID_KNOWN_ISSUES: {
					iconColor.setBackgroundColor(Color.parseColor("#CC0000"));
					titleText.setText("Known Issues");
					break;
				}
				case ID_ALLERGIES: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));
					titleText.setText("Allergies");
					break;
				}
				case ID_IMMUNIZATIONS: {
					iconColor.setBackgroundColor(Color.parseColor("#9933CC"));
					titleText.setText("Immunizations");
					break;
				}
				case ID_FAM_HISTORY: {
					iconColor.setBackgroundColor(Color.parseColor("#669900"));
					titleText.setText("Family History");
					break;
				}
				case ID_SOCIAL_HISTORY: {
					iconColor.setBackgroundColor(Color.parseColor("#FF8800"));
					titleText.setText("Social History");
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
