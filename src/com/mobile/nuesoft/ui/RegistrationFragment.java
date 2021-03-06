package com.mobile.nuesoft.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.NuesoftUser;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.NuesoftRegisteredUserEvent;

public class RegistrationFragment extends NuesoftFragment implements OnClickListener {

	View rootView;
	private LayoutInflater mInflater;

	EditText userNameET;
	EditText pswrd1ET;
	EditText pswrd2ET;

	Button btnRegister;
	Button btnCancel;

	OnNuesoftRegisteredEventListener registrationEventListener = new OnNuesoftRegisteredEventListener();

	public RegistrationFragment() {
		TAG = "RegistrationFragment";
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
	}

	@Override
	public void onFragmentPaused() {
		registrationEventListener.unregister();

	}

	@Override
	public void onFragmentResume() {
		registrationEventListener.register();

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
		rootView = mInflater.inflate(R.layout.registration_fragment_layout, container, false);

		userNameET = (EditText) rootView.findViewById(R.id.et_username);
		pswrd1ET = (EditText) rootView.findViewById(R.id.et_password_one);
		pswrd2ET = (EditText) rootView.findViewById(R.id.et_password_two);

		btnRegister = (Button) rootView.findViewById(R.id.btn_next);
		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);

		btnRegister.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		((MainActivity) getActivity()).hideFooter();

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_next: {
				NuesoftUser mUser = new NuesoftUser();

				String userName = "";
				String pswrd1 = "";
				String pswrd2 = "";

				try {
					userName = userNameET.getText().toString();
				} catch (final NullPointerException e) {
					Toast.makeText(getActivity(), "Username cannot be empty", Toast.LENGTH_LONG).show();
				}

				try {
					pswrd1 = pswrd1ET.getText().toString();
				} catch (final NullPointerException e) {

				}

				try {
					pswrd2 = pswrd2ET.getText().toString();
				} catch (final NullPointerException e) {

				}

				if ((pswrd1.trim().length() > 0) && (pswrd2.trim().length() > 0) && pswrd1.equalsIgnoreCase(pswrd2)
				        && userName.trim().length() > 0) {

					mUser.setUserName(userName);

					ByteArrayOutputStream outStream = new ByteArrayOutputStream();
					ObjectOutputStream objOutStream;

					try {
						objOutStream = new ObjectOutputStream(outStream);
						objOutStream.writeUTF(pswrd1);
						objOutStream.flush();

						mUser.setString64PSWRD(Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT));

						((MainActivity) getActivity()).replaceMainContent(new RegistrationFragmentPartTwo(mUser));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (userName.trim().length() < 1) {
					Toast.makeText(getActivity(), "Username is empty", Toast.LENGTH_LONG).show();
				} else {

					Toast.makeText(getActivity(), "Passwords do not equal", Toast.LENGTH_LONG).show();
				}

				break;
			}
			case R.id.btn_cancel: {
				getActivity().getSupportFragmentManager().popBackStackImmediate();
				break;
			}
		}
	}

	public class OnNuesoftRegisteredEventListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = NuesoftRegisteredUserEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle result = intent.getExtras();

			boolean mSuccess = result.getBoolean(NuesoftRegisteredUserEvent.RESULT_KEY);

			if (mSuccess) {
				Toast.makeText(getActivity(), "Registration sucessful!", Toast.LENGTH_LONG).show();

				Bundle b = new Bundle();
				b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
				b.putInt(FragmentCallbackEvent.FRAGMENT, FragmentCallbackEvent.FRAGMENTS.LOGIN_FRAGMENT.ordinal());
				FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
			} else {
				Toast.makeText(getActivity(), "Error registering account", Toast.LENGTH_LONG).show();
			}
		}
	}
}
