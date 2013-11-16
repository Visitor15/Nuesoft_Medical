package com.mobile.nuesoft.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.document.Author;
import com.mobile.nuesoft.document.CDADocumentBuilder.CDADocument;
import com.mobile.nuesoft.document.DataEnterer;
import com.mobile.nuesoft.document.LegalAuthenticator;
import com.mobile.nuesoft.document.Participant;
import com.mobile.nuesoft.jobs.CDADocumentUpdateEvent;
import com.mobile.nuesoft.util.Util;

public class DocumentOverviewFragment extends NuesoftFragment {

	private View rootView;
	private TextView titleText;
	private ExpandableListView expandableList;
	private LayoutInflater mInflater;
	private ExpandableAdapter mAdapter;

	private OnCDADocumentUpdateEventListener documentUpdateListener = new OnCDADocumentUpdateEventListener();

	public DocumentOverviewFragment() {
		super();
		TAG = "DocumentOverviewFragment";
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
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
	}

	@Override
	public void onFragmentStart() {
	}

	@Override
	public void onFragmentStop() {
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
		// TODO Auto-generated method stub

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
		titleText.setText("Document Overview");
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
		expandableList.expandGroup(ExpandableAdapter.DOC_ELEMENT_GET_SUMMARY);
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {
		public static final int DOC_ELEMENT_GET_SUMMARY = 0;
		public static final int DOC_ELEMENT_AUTHOR = 1;
		public static final int DOC_ELEMENT_DATA_ENTRY = 2;
		public static final int DOC_ELEMENT_LEGAL_AUTH = 3;
		public static final int DOC_ELEMENT_PARTICIPANTS = 4;

		private ArrayList<Object> parentElements;

		private LayoutInflater mInflater;

		private final CDADocument mDocument;

		public ExpandableAdapter(final CDADocument document) {
			mDocument = document;
			init();
		}

		private void init() {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			parentElements = new ArrayList<Object>();
			parentElements.add(mDocument.getSUMMARY_TITLE());
			parentElements.add(mDocument.getAUTHOR());
			parentElements.add(mDocument.getDATA_ENTERER());
			parentElements.add(mDocument.getLEGAL_AUTHENTICATOR());
			parentElements.add(mDocument.getPARTICIPANTS());
		}

		@Override
		public String getChild(int groupPosition, int childPosition) {

			try {
				switch (groupPosition) {
					case DOC_ELEMENT_GET_SUMMARY: {
						return (String) parentElements.get(groupPosition);
					}
					case DOC_ELEMENT_AUTHOR: {
						return (String) ((ArrayList<Author>) parentElements.get(groupPosition)).get(childPosition)
						        .getAUTHOR().getPRINTABLE_NAME();
					}
					case DOC_ELEMENT_DATA_ENTRY: {
						return (String) ((ArrayList<DataEnterer>) parentElements.get(groupPosition)).get(childPosition)
						        .getENTERER().getPRINTABLE_NAME();
					}
					case DOC_ELEMENT_LEGAL_AUTH: {
						return (String) ((ArrayList<LegalAuthenticator>) parentElements.get(groupPosition))
						        .get(childPosition).getAUTHENTICATOR().getPRINTABLE_NAME();
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
				case DOC_ELEMENT_GET_SUMMARY: {
					iconColor.setBackgroundColor(Color.parseColor("#33B5E5"));
					mTitleText.setText(getChild(groupPosition, childPosition));
					break;
				}
				case DOC_ELEMENT_AUTHOR: {
					iconColor.setBackgroundColor(Color.parseColor("#AA66CC"));
					mTitleText.setText(getChild(groupPosition, childPosition));
					break;
				}
				case DOC_ELEMENT_DATA_ENTRY: {
					iconColor.setBackgroundColor(Color.parseColor("#99CC00"));
					mTitleText.setText(getChild(groupPosition, childPosition));
					break;
				}
				case DOC_ELEMENT_LEGAL_AUTH: {
					iconColor.setBackgroundColor(Color.parseColor("#FFBB33"));
					mTitleText.setText(getChild(groupPosition, childPosition));
					break;
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					iconColor.setBackgroundColor(Color.parseColor("#FF4444"));
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
				case DOC_ELEMENT_GET_SUMMARY: {
					return 1;
				}
				case DOC_ELEMENT_AUTHOR: {
					return ((ArrayList<Author>) parentElements.get(groupPosition)).size();
				}
				case DOC_ELEMENT_DATA_ENTRY: {
					return ((ArrayList<DataEnterer>) parentElements.get(groupPosition)).size();
				}
				case DOC_ELEMENT_LEGAL_AUTH: {
					return ((ArrayList<LegalAuthenticator>) parentElements.get(groupPosition)).size();
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					return ((ArrayList<DataEnterer>) parentElements.get(groupPosition)).size();
				}
				default: {
					return 0;
				}
			}
		}

		@Override
		public String getGroup(int groupPosition) {
			switch (groupPosition) {
				case DOC_ELEMENT_GET_SUMMARY: {
					return "Summary";
				}
				case DOC_ELEMENT_AUTHOR: {
					return "Author";
				}
				case DOC_ELEMENT_DATA_ENTRY: {
					return "Data Entry";
				}
				case DOC_ELEMENT_LEGAL_AUTH: {
					return "Legal Authenticator";
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					return "Other Participants";
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
				case DOC_ELEMENT_GET_SUMMARY: {
					iconColor.setBackgroundColor(Color.parseColor("#0099CC"));

					break;
				}
				case DOC_ELEMENT_AUTHOR: {
					iconColor.setBackgroundColor(Color.parseColor("#9933CC"));

					break;
				}
				case DOC_ELEMENT_DATA_ENTRY: {
					iconColor.setBackgroundColor(Color.parseColor("#669900"));

					break;
				}
				case DOC_ELEMENT_LEGAL_AUTH: {
					iconColor.setBackgroundColor(Color.parseColor("#FF8800"));

					break;
				}
				case DOC_ELEMENT_PARTICIPANTS: {
					iconColor.setBackgroundColor(Color.parseColor("#CC0000"));

					break;
				}
			}

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
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
