package com.mobile.nuesoft.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.NuesoftUser;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.NuesoftRegisteredUserEvent;
import com.mobile.nuesoft.jobs.RegisterUserJob;

public class RegistrationFragmentPartTwo extends NuesoftFragment implements OnClickListener {

	private static final int REQUEST_CODE = 121;

	View rootView;
	private LayoutInflater mInflater;

	EditText userNameET;
	EditText pswrd1ET;
	EditText pswrd2ET;

	Button btnRegister;
	Button btnCancel;

	ProfilePicImageView profilePic;

	ImageView btnMore;

	private Bitmap bitmap;
	private NuesoftUser mUser;

	OnNuesoftRegisteredEventListener registrationEventListener = new OnNuesoftRegisteredEventListener();

	public RegistrationFragmentPartTwo() {
		TAG = "RegistrationFragment";
	}

	public RegistrationFragmentPartTwo(final NuesoftUser mUser) {
		this.mUser = mUser;
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

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
		rootView = mInflater.inflate(R.layout.registration_frag_part_two_layout, container, false);

		userNameET = (EditText) rootView.findViewById(R.id.et_username);
		pswrd1ET = (EditText) rootView.findViewById(R.id.et_password_one);
		pswrd2ET = (EditText) rootView.findViewById(R.id.et_password_two);

		btnRegister = (Button) rootView.findViewById(R.id.btn_register);
		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);

		profilePic = (ProfilePicImageView) rootView.findViewById(R.id.nt_profile_pic);

		btnMore = (ImageView) rootView.findViewById(R.id.more);

		btnRegister.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnMore.setOnClickListener(this);

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
			case R.id.btn_register: {
				RegisterUserJob registerJob = new RegisterUserJob();
				registerJob.execute(mUser);

				break;
			}
			case R.id.more: {

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, REQUEST_CODE);

				break;
			}
			case R.id.btn_cancel: {
				getActivity().getSupportFragmentManager().popBackStackImmediate();
				break;
			}

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
			try {
				InputStream stream = getActivity().getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				stream.close();
				profilePic.setImageBitmap(bitmap);
				profilePic.initWithNewImage();
				mUser.setProfilePicURI(data.getData().toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
