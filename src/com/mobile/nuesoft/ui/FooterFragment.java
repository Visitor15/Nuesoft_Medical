package com.mobile.nuesoft.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.ParseCDADocumentJob;
import com.mobile.nuesoft.jobs.PatientUpdateEvent;

public class FooterFragment extends NuesoftFragment implements OnClickListener {

	private View rootView;
	private ImageView btnLeft;
	private ImageView btnRight;
	private TextView mTitleText;

	OnPatientUpdatedListener patientEventListener = new OnPatientUpdatedListener();

	public FooterFragment() {
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
	}

	@Override
	public void onFragmentPaused() {
		patientEventListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		patientEventListener.register();

		if (Nuesoft.getCurrentCDADocument() != null) {
			setTitleText(Nuesoft.getCurrentCDADocument().getDOC_URI().getLastPathSegment());
		}
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
		rootView = inflater.inflate(R.layout.footer_layout, container, false);

		mTitleText = (TextView) rootView.findViewById(R.id.nt_title_text);
		btnLeft = (ImageView) rootView.findViewById(R.id.btn_cancel);
		btnRight = (ImageView) rootView.findViewById(R.id.btn_send_now);

		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	public void setTitleText(final String str) {
		this.mTitleText.setText(str);
	}

	public void refreshTitle() {
		if (Nuesoft.getCurrentCDADocument() != null) {
			setTitleText(Nuesoft.getCurrentCDADocument().getDOC_URI().getLastPathSegment());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_cancel: {
				Nuesoft.getReference().setCurrentCDADocument(null);

				((MainActivity) getActivity()).hideFooter();

				Bundle b = new Bundle();
				b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
				b.putInt(FragmentCallbackEvent.FRAGMENT, FragmentCallbackEvent.FRAGMENTS.PATIENT_FRAGMENT.ordinal());
				FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
				break;
			}

			case R.id.btn_send_now: {
				Bundle b = new Bundle();
				b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
				b.putInt(FragmentCallbackEvent.FRAGMENT, FragmentCallbackEvent.FRAGMENTS.SEND_DOC_FRAGMENT.ordinal());
				FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
				break;
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
			Bundle b = intent.getExtras();
			if (b != null) {
				if (b.containsKey(ParseCDADocumentJob.IS_FINISHED_KEY)) {
					boolean isFinished = b.getBoolean(ParseCDADocumentJob.IS_FINISHED_KEY);
					if (isFinished) {
						refreshTitle();
					}
				}
			}
		}
	}
}
