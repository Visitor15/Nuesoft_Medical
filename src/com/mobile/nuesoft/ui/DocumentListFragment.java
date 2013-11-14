package com.mobile.nuesoft.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.DecryptionJob;
import com.mobile.nuesoft.jobs.ParseCDADocumentJob;
import com.mobile.nuesoft.jobs.PatientUpdateEvent;
import com.mobile.nuesoft.patient.Medication;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class DocumentListFragment extends NuesoftFragment {

	private View rootView;

	private ExpandableListView expandableList;

	private ExpandableAdapter mAdapter;

	private OnPatientUpdatedListener patientEventListener = new OnPatientUpdatedListener();

	public DocumentListFragment() {

	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		TAG = "DocumentListFragment";
	}

	@Override
	public void onFragmentPaused() {
		patientEventListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		// if (Nuesoft.getCurrentCDADocument() == null) {
		// ((MainActivity)
		// getActivity()).getFooter().setTitleText("No document loaded");
		// }
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
		rootView = inflater.inflate(R.layout.document_list_frag_layout, container, false);

		expandableList = (ExpandableListView) rootView.findViewById(R.id.expandable_list_view);
		mAdapter = new ExpandableAdapter();

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		expandableList.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		expandableList.expandGroup(0);
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter implements OnClickListener {

		static final int GROUP_ONE = 0;
		static final int GROUP_TWO = 1;

		private LayoutInflater mInflater;

		private ArrayList<DocFile> openDocs;
		private ArrayList<DocFile> encryptedDocs;

		private File mFile;

		public ExpandableAdapter() {
			mInflater = LayoutInflater.from(Nuesoft.getReference());
			openDocs = new ArrayList<DocFile>();
			encryptedDocs = new ArrayList<DocFile>();
			init();
		}

		private void init() {
			mFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

			if (mFile.exists()) {
				File[] children = mFile.listFiles();

				for (int i = 0; i < children.length; i++) {
					File tempFile = children[i];

					if (!tempFile.isDirectory()) {
						String fileName = tempFile.getName();
						String exten = fileName.substring(fileName.length() - 3);
						if (exten.equalsIgnoreCase("xml")) {
							openDocs.add(new DocFile(fileName, Uri.fromFile(tempFile)));
						} else if (exten.equalsIgnoreCase("ncc")) {
							encryptedDocs.add(new DocFile(fileName, Uri.fromFile(tempFile)));
						}
					}
				}
			}
		}

		@Override
		public DocFile getChild(int groupPosition, int childPosition) {
			DocFile mDocFile;
			switch (groupPosition) {
				case GROUP_ONE: {
					mDocFile = openDocs.get(childPosition);
					break;
				}
				case GROUP_TWO: {
					mDocFile = encryptedDocs.get(childPosition);
					break;
				}
				default: {
					mDocFile = new DocFile(null, null);
				}
			}

			return mDocFile;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
		        ViewGroup parent) {

			DocFile mDocFile = null;

			switch (groupPosition) {
				case GROUP_ONE: {
					if (openDocs.size() > 0) {
						mDocFile = openDocs.get(childPosition);
					}
					break;
				}
				case GROUP_TWO: {
					if (encryptedDocs.size() > 0) {
						mDocFile = encryptedDocs.get(childPosition);
					}
					break;
				}
			}

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.document_item_view, parent, false);
			}

			TextView textView = (TextView) convertView.findViewById(R.id.nt_text);

			if (mDocFile != null) {
				RelativeLayout mContainer = (RelativeLayout) convertView.findViewById(R.id.rl_container);
				mContainer.setTag(mDocFile);

				if (Nuesoft.getCurrentCDADocument() != null) {
					if (mDocFile.mUri.equals(Nuesoft.getCurrentCDADocument().getDOC_URI())) {
						mContainer.setSelected(true);
					} else {
						mContainer.setSelected(false);
					}
				}

				textView.setText(mDocFile.name);

				TextView dateTextView = (TextView) convertView.findViewById(R.id.nt_date_value);

				File data = new File(mDocFile.mUri.getPath());
				if (data.exists()) {
					CharSequence mDate = DateFormat.format("MM-dd-yyyy", data.lastModified());
					dateTextView.setText(mDate);
				}

				mContainer.setOnClickListener(ExpandableAdapter.this);
			} else {
				textView.setText("No documents found");
			}

			FrameLayout iconColor = (FrameLayout) convertView.findViewById(R.id.iv_icon);

			switch (groupPosition) {
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
			int count;
			switch (groupPosition) {
				case GROUP_ONE: {
					count = openDocs.size();
					break;
				}
				case GROUP_TWO: {
					count = encryptedDocs.size();
					break;
				}
				default: {
					count = 0;
				}
			}

			if (count == 0) {
				count = 1;
			}

			return count;
		}

		@Override
		public String getGroup(int groupPosition) {
			String mTitle;
			switch (groupPosition) {
				case GROUP_ONE: {
					mTitle = "Open";
					break;
				}
				case GROUP_TWO: {
					mTitle = "Encrypted";
					break;
				}
				default: {
					mTitle = "Error";
				}
			}

			return mTitle;
		}

		@Override
		public int getGroupCount() {
			return 2;
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

			String mTitle;

			switch (groupPosition) {
				case GROUP_ONE: {
					mTitle = "Open";
					break;
				}
				case GROUP_TWO: {
					mTitle = "Encrypted";
					break;
				}
				default: {
					mTitle = "Error";
				}
			}

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

		@Override
		public void onClick(View v) {
			DocFile mDocFile = (DocFile) v.getTag();
			Uri mUri = mDocFile.mUri;

			String extension = mUri.getLastPathSegment().substring(mUri.getLastPathSegment().length() - 3);
			if (extension.equalsIgnoreCase("ncc")) {
				Log.d(TAG, "NCC - GOT PATH: " + mUri.getPath());
				Toast.makeText(getActivity(), "Encrypted File", Toast.LENGTH_LONG).show();

				DecryptDocDialog mDialog = new DecryptDocDialog(mUri);
				mDialog.show(getActivity().getSupportFragmentManager(), DecryptDocDialog.TAG);
			} else {
				ParseCDADocumentJob job = new ParseCDADocumentJob();
				job.execute(mUri.getPath());
			}

			int i;
			for (i = 0; i < openDocs.size(); i++) {
				openDocs.get(i).isSelected = false;
			}
			for (i = 0; i < encryptedDocs.size(); i++) {
				encryptedDocs.get(i).isSelected = false;
			}

			mDocFile.isSelected = true;

			patientEventListener.register();
		}

	}

	public class DocFile {
		public Uri mUri;
		public String name;
		public boolean isSelected = false;

		public DocFile(final String name, final Uri mUri) {
			this.name = name;
			this.mUri = mUri;
		}
	}

	// @Override
	// public void onClick(View v) {
	// Uri mUri = Uri.parse((String) v.getTag());
	//
	// String extension =
	// mUri.getLastPathSegment().substring(mUri.getLastPathSegment().length() -
	// 3);
	// if (extension.equalsIgnoreCase("ncc")) {
	// Log.d(TAG, "NCC - GOT PATH: " + mUri.getPath());
	// Toast.makeText(getActivity(), "Encrypted File",
	// Toast.LENGTH_LONG).show();
	// // DecryptionJob job = new DecryptionJob();
	// // job.execute(new String[] { mUri.getPath(), "11111111" });
	// } else {
	// // EncryptionJob job = new EncryptionJob();
	// // job.execute(new String[] { mUri.getPath(), "11111111" });
	// ParseCDADocumentJob job = new ParseCDADocumentJob();
	// job.execute(mUri.getPath());
	// }
	//
	// //
	// //
	// // Log.d(TAG, "CLICKED URI: " + mUri);
	// //
	// // Bundle b = new Bundle();
	// // b.putInt(FragmentCallbackEvent.ACTION_KEY,
	// // FragmentCallbackEvent.ACTIONS.SHOW_FRAGMENT_IN_PAGER.ordinal());
	// // b.putInt(FragmentCallbackEvent.FRAGMENT, 1);
	// // FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
	//
	//
	//
	// patientEventListener.register();
	// }

	public class OnPatientUpdatedListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = PatientUpdateEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			if (b != null) {
				if (b.containsKey(ParseCDADocumentJob.IS_FINISHED_KEY)) {
					boolean isFinished = b.getBoolean(ParseCDADocumentJob.IS_FINISHED_KEY);
					if (isFinished) {
						((MainActivity) getActivity()).showFooter();

						b = new Bundle();
						b.putInt(FragmentCallbackEvent.ACTION_KEY,
						        FragmentCallbackEvent.ACTIONS.SHOW_FRAGMENT_IN_PAGER.ordinal());
						b.putInt(FragmentCallbackEvent.FRAGMENT, 1);
						FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
					}
				}
			}

			mAdapter.notifyDataSetChanged();
		}
	}
}
