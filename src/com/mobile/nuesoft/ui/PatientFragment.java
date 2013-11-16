package com.mobile.nuesoft.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

	private OnPatientUpdatedListener onPatientUpdatedListener = new OnPatientUpdatedListener();
	private OnFragmentCallbackListener onFragmentCallbackListener = new OnFragmentCallbackListener();

	public PatientFragment() {
		TAG = "PatientFragment";
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
	}

	@Override
	public void onFragmentPaused() {
		onPatientUpdatedListener.unregister();
		onFragmentCallbackListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		onPatientUpdatedListener.register();
		onFragmentCallbackListener.register();
	}

	@Override
	public void onSave(Bundle outState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFragmentStart() {

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
		
		mPager = (ViewPager) v.findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), getActivity());
		mPagerAdapter.init();
		mPager.setAdapter(mPagerAdapter);

		return v;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {

		try {
			Bitmap bitmap;
			Uri docUri = Uri.parse(Nuesoft.getCurrentUser().getProfilePicUri());
			InputStream stream;
			stream = getActivity().getContentResolver().openInputStream(docUri);
			bitmap = BitmapFactory.decodeStream(stream);
			stream.close();

			if (profileIcon != null) {
				profileIcon.setImageBitmap(bitmap);
				profileIcon.initWithNewImage();
			} else {
				profileIcon = new ProfilePicImageView(getActivity());
				profileIcon.setImageBitmap(bitmap);
				profileIcon.initWithNewImage();
				((ActionBarActivity) getActivity()).getSupportActionBar().setIcon(profileIcon.getDrawable());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		}

		private void init() {
			dataList.clear();
			dataList.add(new DocumentListFragment());
			dataList.add(new CurrentEncounterFragment());

			if (Nuesoft.getCurrentCDADocument() != null) {
				dataList.add(new PatientOverviewFragment());
				dataList.add(new DocumentOverviewFragment());
			}
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

	public static class ScreenSlidePageFragment extends Fragment {

		private String mTitle = "";

		public ScreenSlidePageFragment() {
		}

		public ScreenSlidePageFragment(final String title) {
			this.mTitle = title;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile_card_layout, container, false);

			((TextView) rootView.findViewById(R.id.nt_title)).setText(mTitle);

			return rootView;
		}
	}

	@Override
	public void onPatientObjUpdated(Bundle b) {
		if (b != null) {
			if (b.containsKey(PatientUpdateEvent.PATIENT_OBJ_KEY)) {
				mPatient = (PatientObj) b.getSerializable(PatientUpdateEvent.PATIENT_OBJ_KEY);

				if (b.containsKey(ParseCDADocumentJob.IS_FINISHED_KEY)) {
					if ((b.getBoolean(ParseCDADocumentJob.IS_FINISHED_KEY))) {

						if (mPatientTitleName != null) {
							mPatientTitleName.setText(mPatient.getIDENTIFIER().getFIRST_NAME() + " "
							        + mPatient.getIDENTIFIER().getLAST_NAME());
						} else {
							((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
							        mPatient.getIDENTIFIER().getFIRST_NAME() + " "
							                + mPatient.getIDENTIFIER().getLAST_NAME());
						}

						mPagerAdapter.init();
						mPagerAdapter.notifyDataSetChanged();

						return;
					}
				}
				Log.d(TAG, "JOB FINISHED");
			}
		}
	}

	public void onHandleFragmentCallback(Bundle b) {
		if (b != null) {
			if (b.containsKey(FragmentCallbackEvent.ACTION_KEY)) {
				int mAction = b.getInt(FragmentCallbackEvent.ACTION_KEY);

				switch (mAction) {
					case 1: {
						if (b.containsKey(FragmentCallbackEvent.FRAGMENT)) {
							int mFragIndex = b.getInt(FragmentCallbackEvent.FRAGMENT);

							if (mPager != null) {
								mPager.setCurrentItem(mFragIndex, true);
							}
						}

						break;
					}
				}
			}
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

	public class OnFragmentCallbackListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = FragmentCallbackEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive HIT");
			onHandleFragmentCallback(intent.getExtras());
		}
	}
}
