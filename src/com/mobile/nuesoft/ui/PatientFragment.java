package com.mobile.nuesoft.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.ParseCDADocumentJob;
import com.mobile.nuesoft.jobs.PatientUpdateEvent;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class PatientFragment extends NuesoftFragment implements OnPatientObjUpdated {

	public static final int NUM_OF_CARDS = 5;

	private ViewPager mPager;

	private ScreenSlidePagerAdapter mPagerAdapter;

	private TextView mPatientTitleName;
	
	private ProfilePicImageView profileIcon;

	private PatientObj mPatient;

	private ParseCDADocumentJob docParseJob;

	private boolean shouldParse = true;

	private String docPath = "mnt/sdcard0/download";

	private Uri parseUri;

	private LinearLayout activeReferralContainer;

	private OnPatientUpdatedListener onPatientUpdatedListener = new OnPatientUpdatedListener();

	public PatientFragment() {
		TAG = "PatientFragment";
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
	}

	@Override
	public void onFragmentPaused() {
		onPatientUpdatedListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		onPatientUpdatedListener.register();
	}

	@Override
	public void onSave(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFragmentStart() {
//		if (docParseJob == null || shouldParse) {
//			docParseJob = new ParseCDADocumentJob();
//
//			if (parseUri != null) {
//				docParseJob.execute(parseUri.getPath());
//			} else if (docPath != null || docPath.trim().length() > 0) {
//				docParseJob.execute(docPath);
//				
//				shouldParse = false;
//			} else {
//				Toast.makeText(getActivity(), "A parse error has occurred: No file found", Toast.LENGTH_LONG).show();
//			}
//		}
	}

	@Override
	public void onFragmentStop() {
		// TODO Auto-generated method stub
	}

	@Override
	public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.profile_frag_layout, null);
		
		mPatientTitleName = (TextView) v.findViewById(R.id.nt_name);
		profileIcon = (ProfilePicImageView) v.findViewById(R.id.nt_profile_pic);
		
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) v.findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), getActivity());
		mPager.setAdapter(mPagerAdapter);
		
		return v;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		((ActionBarActivity) getActivity()).getSupportActionBar().setIcon(profileIcon.getDrawable());
	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects,
	 * in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		private ArrayList<Fragment> dataList = new ArrayList<Fragment>();

		private ArrayList<String> categoryList = new ArrayList<String>();

		public ScreenSlidePagerAdapter(final FragmentManager fm, final Context c) {
			super(fm);
			categoryList.add(c.getResources().getString(R.string.patient_summary));

			String[] tempList = c.getResources().getStringArray(R.array.profile_categories);
			for (String s : tempList) {
				categoryList.add(s);
			}

			dataList.add(new DocumentListFragment());
			dataList.add(new DocumentOverviewFragment());
			dataList.add(new AllergyFragment());
			dataList.add(new MedicationFragment());
			dataList.add(new ScreenSlidePageFragment("Family History"));
			dataList.add(new ScreenSlidePageFragment("Patient History"));
		}

		@Override
		public Fragment getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public int getCount() {
			return dataList.size();
		}
	}

	public class ScreenSlidePageFragment extends Fragment {

		private String mTitle = "";

		ScreenSlidePageFragment(final String title) {
			this.mTitle = title;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile_card_layout, container, false);

			((TextView) rootView.findViewById(R.id.nt_title)).setText(mTitle);

			return rootView;
		}
	}

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
			onPatientObjUpdated(intent.getExtras());
		}
	}

	@Override
	public void onPatientObjUpdated(Bundle b) {
		if (b != null) {
			if (b.containsKey(PatientUpdateEvent.PATIENT_OBJ_KEY)) {
				mPatient = (PatientObj) b.getSerializable(PatientUpdateEvent.PATIENT_OBJ_KEY);

				if (b.containsKey(ParseCDADocumentJob.IS_FINISHED_KEY)) {
					if ((b.getBoolean(ParseCDADocumentJob.IS_FINISHED_KEY))) {

						mPatientTitleName.setText(mPatient.getIDENTIFIER().getFIRST_NAME() + " "
						        + mPatient.getIDENTIFIER().getLAST_NAME());

						// Log.d(TAG, "PATIENTE UPDATED: " +
						// mPatient.toString());

						return;
					}
				}
				Log.d(TAG, "JOB FINISHED");
			}
		}
	}
}
