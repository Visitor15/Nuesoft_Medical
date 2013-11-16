package com.mobile.nuesoft.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.EncryptionJob;
import com.mobile.nuesoft.jobs.EncryptionJobEvent;

public class SendDocFragment extends NuesoftFragment implements OnClickListener {

	public static final String TAG = "SendDocDialog";
	private static final int REQUEST_CODE = 121;

	private View rootView;
	private TextView filenameTitle;
	private Button btnCancel;
	private Button btnEncryptAndSend;
	private EditText etEmailAddr;
	private EditText etPinPswrd1;
	private EditText etPinPswrd2;

	OnEncryptionEventListener encryptionEventListener = new OnEncryptionEventListener();

	public SendDocFragment() {
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
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
		rootView = inflater.inflate(R.layout.send_doc_frag_layout, container, false);

		filenameTitle = (TextView) rootView.findViewById(R.id.nt_filename);

		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
		btnEncryptAndSend = (Button) rootView.findViewById(R.id.btn_send);

		etEmailAddr = (EditText) rootView.findViewById(R.id.et_email_addr);
		etPinPswrd1 = (EditText) rootView.findViewById(R.id.et_pswrd1);
		etPinPswrd2 = (EditText) rootView.findViewById(R.id.et_pswrd2);

		btnCancel.setOnClickListener(this);
		btnEncryptAndSend.setOnClickListener(this);

		filenameTitle.setText(Nuesoft.getCurrentCDADocument().getDOC_URI().getLastPathSegment());

		((MainActivity) getActivity()).hideFooter();

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	}

	@Override
	public void onFragmentPaused() {
		encryptionEventListener.unregister();
	}

	@Override
	public void onFragmentResume() {
		encryptionEventListener.register();
	}

	private void beginSending() {
		EncryptionJob job = new EncryptionJob();
		job.execute(new String[] { Nuesoft.getCurrentCDADocument().getDOC_URI().getPath(),
		        "0000".concat(etPinPswrd1.getText().toString()) });
	}

	private void sendDocument() {
		((MainActivity) getActivity()).hideFooter();

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", etEmailAddr.getText().toString(),
		        null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nuesoft Medical Test");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Encoded PIN: ".concat(encodeBase64String(
		        "0000".concat(etPinPswrd1.getText().toString())).substring(16)));
		emailIntent.putExtra(Intent.EXTRA_STREAM,
		        Uri.parse(Nuesoft.getCurrentCDADocument().getDOC_URI().toString().concat(".ncc")));
		startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), REQUEST_CODE);
	}

	private String encodeBase64String(final String str) {
		String encodedVal = "";

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutStream;

		try {
			objOutStream = new ObjectOutputStream(outStream);
			objOutStream.writeUTF(str);
			objOutStream.flush();

			encodedVal = Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedVal;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_send: {
				beginSending();
				break;
			}
			case R.id.btn_cancel: {
				if (Nuesoft.getCurrentCDADocument() != null) {
					((MainActivity) getActivity()).showFooter();
				}
				getActivity().getSupportFragmentManager().popBackStackImmediate();
				break;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			File mFile = new File(Nuesoft.getCurrentCDADocument().getDOC_URI().getPath().concat(".ncc"));
			if (mFile.exists()) {
				mFile.delete();
			}
			((MainActivity) getActivity()).showFooter();
			getActivity().getSupportFragmentManager().popBackStackImmediate();
		}
	}

	public class OnEncryptionEventListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = EncryptionJobEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			if (b != null) {
				if (b.containsKey(EncryptionJob.IS_FINISHED_KEY)) {
					boolean isFinished = b.getBoolean(EncryptionJob.IS_FINISHED_KEY);
					if (isFinished) {
						sendDocument();
					}
				}
			}
		}
	}
}
