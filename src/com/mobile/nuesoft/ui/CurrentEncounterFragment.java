package com.mobile.nuesoft.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.document.Author;
import com.mobile.nuesoft.document.CDADocumentBuilder.CDADocument;
import com.mobile.nuesoft.document.DataEnterer;
import com.mobile.nuesoft.document.Participant;
import com.mobile.nuesoft.jobs.CDADocumentUpdateEvent;
import com.mobile.nuesoft.patient.PlanOfCare;
import com.mobile.nuesoft.patient.VitalSign;
import com.mobile.nuesoft.util.Util;

public class CurrentEncounterFragment extends NuesoftFragment {

	private View rootView;
	private TextView titleText;
	private ExpandableListView expandableList;
	private LayoutInflater mInflater;
	private ExpandableAdapter mAdapter;

	private OnCDADocumentUpdateEventListener documentUpdateListener = new OnCDADocumentUpdateEventListener();

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFragmentPaused() {
		documentUpdateListener.unregister();

	}

	@Override
	public void onFragmentResume() {
		documentUpdateListener.register();

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
		rootView = mInflater.inflate(R.layout.document_overview_frag_layout, container, false);

		titleText = (TextView) rootView.findViewById(R.id.nt_title);

		if (Nuesoft.getCurrentCDADocument() == null) {
			showNoDataView(rootView);
		} else {
			hideNoDataView(rootView);
		}
		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	}

	public void showNoDataView(final View v) {
		titleText.setText("No Document Loaded");
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();

		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.no_data_layout, null);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		        LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);

		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(view);
	}

	public void hideNoDataView(final View v) {
		titleText.setText("Current Encounter");
		((RelativeLayout) v.findViewById(R.id.rl_container)).removeAllViews();
		expandableList = (ExpandableListView) mInflater.inflate(R.layout.medication_fragment_meds_layout, null);
		((RelativeLayout) v.findViewById(R.id.rl_container)).addView(expandableList);
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) expandableList.getLayoutParams();

		int mMargin = Util.convertDpToPixel(8f, getActivity());
		mlp.setMargins(mMargin, 0, mMargin, 0);

		expandableList.setLayoutParams(mlp);

		initDocumentOverView(v);
	}

	private void initDocumentOverView(final View v) {
		mAdapter = new ExpandableAdapter(Nuesoft.getCurrentCDADocument());
		expandableList.setAdapter(mAdapter);
		mAdapter.init();
		expandableList.expandGroup(ExpandableAdapter.DOC_ELEMENT_INSTRUCTIONS);
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {
		
		public static final int DOC_ELEMENT_INSTRUCTIONS = 0;
		public static final int DOC_ELEMENT_REASON_FOR_REFERRAL = 1;
		public static final int DOC_ELEMENT_REASON_FOR_VISIT = 2;
		public static final int DOC_ELEMENT_PLAN_OF_CARE = 3;
		public static final int DOC_ELEMENT_VITAL_SIGNS = 4;
		public static final int DOC_ELEMENT_PARTICIPANTS = 5;

		private ArrayList<Object> parentElements;

		private LayoutInflater mInflater;

		private final CDADocument mDocument;

		public ExpandableAdapter(final CDADocument document) {
			mDocument = document;
			init();
		}

		private void init() {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			parentElements = new ArrayList<Object>(6);
			parentElements.add(DOC_ELEMENT_INSTRUCTIONS, mDocument.getPATIENT().getINSTRUCTIONS());
			parentElements.add(DOC_ELEMENT_REASON_FOR_REFERRAL, mDocument.getPATIENT().getREASON_FOR_REFFERAL());
			parentElements.add(DOC_ELEMENT_REASON_FOR_VISIT, mDocument.getPATIENT().getREASON_FOR_VISIT());
			parentElements.add(DOC_ELEMENT_PLAN_OF_CARE, mDocument.getPATIENT().getPLAN_OF_CARE());
			parentElements.add(DOC_ELEMENT_VITAL_SIGNS, mDocument.getPATIENT().getVITAL_SIGNS());
			parentElements.add(DOC_ELEMENT_PARTICIPANTS, mDocument.getPARTICIPANTS());
		}

		@Override
		public String getChild(int groupPosition, int childPosition) {

			try {
				switch (groupPosition) {
					
					case DOC_ELEMENT_INSTRUCTIONS: {
						return (String) parentElements.get(groupPosition);
					}
					case DOC_ELEMENT_REASON_FOR_REFERRAL: {
						return (String) parentElements.get(groupPosition);
					}
					case DOC_ELEMENT_REASON_FOR_VISIT: {
						return (String) parentElements.get(groupPosition);
					}
					case DOC_ELEMENT_PLAN_OF_CARE: {
						return (String) ((ArrayList<PlanOfCare>) parentElements.get(groupPosition)).get(childPosition)
						        .getDisplayName();
					}
					case DOC_ELEMENT_VITAL_SIGNS: {
						return (String) ((ArrayList<VitalSign>) parentElements.get(groupPosition)).get(childPosition)
						        .getDisplayName();
					}
					case DOC_ELEMENT_PARTICIPANTS: {
						return (String) ((ArrayList<Participant>) parentElements.get(groupPosition)).get(childPosition)
						        .getPERSON().getPRINTABLE_NAME();
					}
					default: {
						return "Uknown";
					}
				}
			} catch (final NullPointerException e) {
				return "No data present";
			}
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
		        ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.document_overview_list_item_layout, null);
			}

			TextView mTitleText = (TextView) convertView.findViewById(R.id.nt_text);
			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);

			switch (groupPosition) {
				case DOC_ELEMENT_INSTRUCTIONS: {
					iconColor.setBackgroundColor(Color.parseColor("#33B5E5"));
					mTitleText.setText(getChild(groupPosition, childPosition));

					break;
				}
				case DOC_ELEMENT_REASON_FOR_REFERRAL: {
					iconColor.setBackgroundColor(Color.parseColor("#AA66CC"));
					mTitleText.setText(getChild(groupPosition, childPosition));

					break;
				}
				case DOC_ELEMENT_REASON_FOR_VISIT: {
					iconColor.setBackgroundColor(Color.parseColor("#99CC00"));
					mTitleText.setText(getChild(groupPosition, childPosition));

					break;
				}
				case DOC_ELEMENT_PLAN_OF_CARE: {
					iconColor.setBackgroundColor(Color.parseColor("#FFBB33"));
					mTitleText.setText(getChild(groupPosition, childPosition));

					break;
				}

				case DOC_ELEMENT_VITAL_SIGNS: {
					iconColor.setBackgroundColor(Color.parseColor("#FF4444"));
					mTitleText.setText(getChild(groupPosition, childPosition));

					break;
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					iconColor.setBackgroundColor(Color.parseColor("#33B5E5"));
					mTitleText.setText(getChild(groupPosition, childPosition));

					break;
				}
				default: {
					break;
				}
			}

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			switch (groupPosition) {
				case DOC_ELEMENT_INSTRUCTIONS: {
					return 1;
				}
				case DOC_ELEMENT_REASON_FOR_REFERRAL: {
					return 1;
				}
				case DOC_ELEMENT_REASON_FOR_VISIT: {
					return 1;
				}
				case DOC_ELEMENT_PLAN_OF_CARE: {
					return ((ArrayList<Author>) parentElements.get(groupPosition)).size();
				}
				case DOC_ELEMENT_VITAL_SIGNS: {
					return ((ArrayList<DataEnterer>) parentElements.get(groupPosition)).size();
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					return 1;
				}
				default: {
					return 0;
				}
			}
		}

		@Override
		public String getGroup(int groupPosition) {
			switch (groupPosition) {
				case DOC_ELEMENT_INSTRUCTIONS: {
					return "Instructions";
				}
				case DOC_ELEMENT_REASON_FOR_REFERRAL: {
					return "Reason For Referral";
				}
				case DOC_ELEMENT_REASON_FOR_VISIT: {
					return "Reason For Visit";
				}
				case DOC_ELEMENT_PLAN_OF_CARE: {
					return "Plan of Care";
				}
				case DOC_ELEMENT_VITAL_SIGNS: {
					return "Vital Signs";
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					return "Participants";
				}
				default: {
					return "Uknown";
				}
			}
		}

		@Override
		public int getGroupCount() {
			return parentElements.size();
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

			TextView titleText = (TextView) convertView.findViewById(R.id.nt_name);
			titleText.setText(getGroup(groupPosition));

			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);

			switch (groupPosition) {
				case DOC_ELEMENT_INSTRUCTIONS: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));

					break;
				}
				case DOC_ELEMENT_REASON_FOR_REFERRAL: {
					iconColor.setBackgroundColor(Color.parseColor("#9933CC"));

					break;
				}
				
				case DOC_ELEMENT_REASON_FOR_VISIT: {
					iconColor.setBackgroundColor(Color.parseColor("#669900"));

					break;
				}
				case DOC_ELEMENT_PLAN_OF_CARE: {
					iconColor.setBackgroundColor(Color.parseColor("#FF8800"));

					break;
				}
				case DOC_ELEMENT_VITAL_SIGNS: {
					iconColor.setBackgroundColor(Color.parseColor("#CC0000"));

					break;
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));

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

	public class OnCDADocumentUpdateEventListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = CDADocumentUpdateEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if ((Nuesoft.getCurrentCDADocument() == null)) {
					showNoDataView(rootView);
				} else {
					hideNoDataView(rootView);
					((MainActivity) getActivity()).getFooter().setTitleText(
					        Nuesoft.getCurrentCDADocument().getDOC_URI().getLastPathSegment());
				}
				if (mAdapter != null) {
					mAdapter.init();
					mAdapter.notifyDataSetChanged();
				}
			} catch (NullPointerException e) {

			}
		}
	}
}
