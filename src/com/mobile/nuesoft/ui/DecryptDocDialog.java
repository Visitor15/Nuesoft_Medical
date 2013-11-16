package com.mobile.nuesoft.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.nuesoft.R;
import com.mobile.nuesoft.jobs.DecryptionJob;

public class DecryptDocDialog extends DialogFragment implements OnClickListener {
	public static final String TAG = "DecryptDocDialog";
	private static final String URI_KEY = "doc_uri_key";
	
	private Uri docUri;
	private View rootView;
	private TextView mTitle;
	private TextView decodedPIN;
	private Button btnCancel;
	private Button btnUnlock;
	private EditText etInput;
	private String mData;

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
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			docUri = Uri.parse(savedInstanceState.getString(URI_KEY));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle b) {
		super.onSaveInstanceState(b);
		b.putString(URI_KEY, docUri.toString());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.decrypt_doc_dialog_layout, container, false);

		etInput = (EditText) rootView.findViewById(R.id.et_input);
		btnUnlock = (Button) rootView.findViewById(R.id.btn_unlock);
		btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
		mTitle = (TextView) rootView.findViewById(R.id.nt_filename);
		decodedPIN = (TextView) rootView.findViewById(R.id.nt_decoded_pswrd);

		mTitle.setText(docUri.getLastPathSegment());

		initEditTextListener(etInput);
		btnUnlock.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	private void initEditTextListener(final EditText text) {
		text.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable e) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mData = null;
				try {
					if (etInput.length() == 8) {
						mData = decodeBase64String(etInput.getText().toString()).substring(4);
						decodedPIN.setText(mData);
					} else if (etInput.length() == 4) {
						Integer.parseInt(etInput.getText().toString());
						mData = etInput.getText().toString();
					} else {
						decodedPIN.setText("----");
					}
				} catch (final UTFDataFormatException e) {
					mData = null;
				} catch (final NumberFormatException e) {
					mData = null;
				}
			}
		});
	}

	private String decodeBase64String(final String encodedStr) throws UTFDataFormatException {
		String decodedStr = "";
		try {
			final byte[] byteVal = Base64.decode(DecryptionJob.ENC_PIECE_1.concat(encodedStr), Base64.DEFAULT);
			final ByteArrayInputStream is = new ByteArrayInputStream(byteVal);
			final ObjectInputStream in;

			in = new ObjectInputStream(is);
			decodedStr = in.readUTF();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			throw new UTFDataFormatException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new UTFDataFormatException();
		} catch (IllegalArgumentException e) {
			throw new UTFDataFormatException();
		}

		return decodedStr;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_unlock: {
				try {
					DecryptionJob job = new DecryptionJob();
					job.execute(new String[] { docUri.getPath(), "0000".concat(mData) });
					dismiss();
				} catch (final NullPointerException e) {
					// decodedPIN.setText("(ノಠ益ಠ)ノ No!");
					Toast.makeText(getActivity(), "Incorrect PIN", Toast.LENGTH_LONG).show();
				}
				break;
			}
			case R.id.btn_cancel: {
				dismiss();
			}
		}
	}
}
