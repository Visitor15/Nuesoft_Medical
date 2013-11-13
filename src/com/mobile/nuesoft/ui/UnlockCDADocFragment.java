package com.mobile.nuesoft.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.DecryptionJob;

public class UnlockCDADocFragment extends NuesoftFragment implements OnClickListener {

	public static final String TAG = "UnlockCDADocuFragment";

	private View rootView;

	private EditText encodedPswrd;

	private TextView decodedPswrd;

	private Button btnCancel;
	private Button btnDecode;

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFragmentPaused() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFragmentResume() {
		// TODO Auto-generated method stub

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
		rootView = inflater.inflate(R.layout.unlock_doc_frag_layout, container, false);

		decodedPswrd = (TextView) rootView.findViewById(R.id.nt_decoded_pswrd);
		encodedPswrd = (EditText) rootView.findViewById(R.id.et_encoded_pswrd);
		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
		btnDecode = (Button) rootView.findViewById(R.id.btn_decode);

		btnCancel.setOnClickListener(this);
		btnDecode.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	private String decodeBase64String(final String encodedStr) {
		String decodedStr = "";

		final byte[] byteVal = Base64.decode(DecryptionJob.ENC_PIECE_1.concat(encodedStr), Base64.DEFAULT);
		final ByteArrayInputStream is = new ByteArrayInputStream(byteVal);
		final ObjectInputStream in;

		try {
			in = new ObjectInputStream(is);
			decodedStr = in.readUTF();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return decodedStr;
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
			case R.id.btn_decode: {

				if (encodedPswrd.getText().length() > 0) {
					decodedPswrd.setText(decodeBase64String(encodedPswrd.getText().toString()).substring(4));
				}

				break;
			}
			case R.id.btn_cancel: {
				getActivity().getSupportFragmentManager().popBackStackImmediate();
				break;
			}
		}
	}
}
