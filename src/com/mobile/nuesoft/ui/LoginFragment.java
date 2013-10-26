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
import android.widget.TextView;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.NuesoftUser;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.NuesoftRegisteredUserEvent;
import com.mobile.nuesoft.jobs.NuesoftUserLoginEvent;
import com.mobile.nuesoft.jobs.RegisterUserJob;
import com.mobile.nuesoft.jobs.UserLoginJob;

public class LoginFragment extends NuesoftFragment implements OnClickListener {

	View rootView;

	EditText userName;
	EditText password;

	Button btnLogin;
	TextView btnRegister;

	OnLoginEventListener loginEventListener = new OnLoginEventListener();
	OnNuesoftRegisteredEventListener registeredEventListener = new OnNuesoftRegisteredEventListener();

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
		registeredEventListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		loginEventListener.register();
		registeredEventListener.register();
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
		btnLogin = (Button) rootView.findViewById(R.id.btn_login);
		btnRegister = (TextView) rootView.findViewById(R.id.btn_register);

		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		
		((MainActivity) getActivity()).closeAndLockDrawer();

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

				NuesoftUser mUser = new NuesoftUser();
				mUser.setUserName(userName.getText().toString());

				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ObjectOutputStream objOutStream;

				try {
					objOutStream = new ObjectOutputStream(outStream);
					objOutStream.writeUTF(password.getText().toString());
					objOutStream.flush();

					mUser.setString64PSWRD(Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT));

					RegisterUserJob registerJob = new RegisterUserJob();
					registerJob.execute(mUser);
				} catch (IOException e) {
					e.printStackTrace();
				}

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

			Log.d(TAG, "LOGIN IN SUCCESS? : " + mSuccess);
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
			Log.d(TAG, "onReceive HIT");

			Bundle result = intent.getExtras();

			boolean mSuccess = result.getBoolean(NuesoftRegisteredUserEvent.RESULT_KEY);

			Log.d(TAG, "REGISTER SUCCESS? : " + mSuccess);
		}
	}
}
