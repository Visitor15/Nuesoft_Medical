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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.NuesoftUser;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.NuesoftUserLoginEvent;
import com.mobile.nuesoft.jobs.UserLoginJob;

public class LoginFragment extends NuesoftFragment implements OnClickListener {

	View rootView;

	EditText userName;
	EditText password;

	private Button btnCancel;

	Button btnLogin;

	LinearLayout btnRegister;
	LinearLayout btnUnlockDoc;

	OnLoginEventListener loginEventListener = new OnLoginEventListener();

	public LoginFragment() {
		super();
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		TAG = "LoginFragment";
	}

	@Override
	public void onFragmentPaused() {
		loginEventListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		loginEventListener.register();
		Nuesoft.getReference().setCurrentCDADocument(null);
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
		rootView = inflater.inflate(R.layout.login_fragment_layout, container, false);

		userName = (EditText) rootView.findViewById(R.id.et_username);
		password = (EditText) rootView.findViewById(R.id.et_password);
		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
		btnLogin = (Button) rootView.findViewById(R.id.btn_login);
		btnRegister = (LinearLayout) rootView.findViewById(R.id.btn_register);
//		btnUnlockDoc = (LinearLayout) rootView.findViewById(R.id.btn_unlock_doc);

		btnCancel.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
//		btnUnlockDoc.setOnClickListener(this);

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
			case R.id.btn_login: {
				UserLoginJob loginJob = new UserLoginJob();

				NuesoftUser mUser = new NuesoftUser();
				mUser.setUserName(userName.getText().toString());

				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ObjectOutputStream objOutStream;

				try {
					objOutStream = new ObjectOutputStream(outStream);
					objOutStream.writeUTF(password.getText().toString());
					objOutStream.flush();

					mUser.setString64PSWRD(Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT));

					loginJob.execute(mUser);
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			}
			case R.id.btn_register: {

				Bundle b = new Bundle();
				b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
				b.putInt(FragmentCallbackEvent.FRAGMENT,
				        FragmentCallbackEvent.FRAGMENTS.REGISTRATION_FRAGMENT.ordinal());
				FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);

				break;
			}
			case R.id.btn_unlock_doc: {
				
				Bundle b = new Bundle();
				b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
				b.putInt(FragmentCallbackEvent.FRAGMENT,
				        FragmentCallbackEvent.FRAGMENTS.UNLOCK_DOC_FRAGMENT.ordinal());
				FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
				
				break;
			}
			case R.id.btn_cancel: {
				getActivity().finish();
				break;
			}
		}
	}

	public class OnLoginEventListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = NuesoftUserLoginEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive HIT");

			Bundle result = intent.getExtras();
			boolean mSuccess = result.getBoolean(NuesoftUserLoginEvent.RESULT_KEY);

			if (mSuccess) {
				Bundle b = new Bundle();
				b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
				b.putInt(FragmentCallbackEvent.FRAGMENT, FragmentCallbackEvent.FRAGMENTS.PATIENT_FRAGMENT.ordinal());
				FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
			} else {
				Toast.makeText(getActivity(), "Error logging in", Toast.LENGTH_LONG).show();
			}
		}
	}
}
