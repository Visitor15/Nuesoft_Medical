package com.mobile.nuesoft.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.DecryptionJob;

public class DecryptDocDialog extends DialogFragment implements OnClickListener {

	public static final String TAG = "DecryptDocDialog";

	private Uri docUri;

	private View rootView;

	private TextView mTitle;

	private Button btnCancel;

	private Button btnUnlock;

	private EditText etPin;

	public DecryptDocDialog() {
		super();
		init();
	}

	public DecryptDocDialog(final Uri mUri) {
		init();
		docUri = mUri;
	}

	private void init() {
		this.setStyle(STYLE_NO_FRAME, 0);
		this.setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(arg0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.decrypt_doc_dialog_layout, container, false);

		etPin = (EditText) rootView.findViewById(R.id.et_pswrd1);
		btnUnlock = (Button) rootView.findViewById(R.id.btn_unlock);
		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
		mTitle = (TextView) rootView.findViewById(R.id.nt_filename);
		
		mTitle.setText(docUri.getLastPathSegment());

		btnUnlock.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

//	private String decodeBase64String(final String encodedStr) {
//		String decodedStr = "";
//
//		final byte[] byteVal = Base64.decode(DecryptionJob.ENC_PIECE_1.concat(encodedStr), Base64.DEFAULT);
//		final ByteArrayInputStream is = new ByteArrayInputStream(byteVal);
//		final ObjectInputStream in;
//
//		try {
//			in = new ObjectInputStream(is);
//			decodedStr = in.readUTF();
//		} catch (StreamCorruptedException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return decodedStr;
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_unlock: {
				DecryptionJob job = new DecryptionJob();
				job.execute(new String[] { docUri.getPath(), "0000".concat(etPin.getText().toString()) });
				break;
			}
		}

		dismiss();
	}
}
