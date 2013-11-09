package com.mobile.nuesoft.jobs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class EncryptionJob extends AsyncTask<String, Void, Boolean> {

	private static final String TAG = "EncryptionJob";

	private static final String ALGORITHM = "DES";
	private static final String FULL_ALGORITHM = "DES/ECB/PKCS5Padding";

	private FileInputStream in;
	private FileOutputStream out;
	private CipherOutputStream ciphStream;
	private Cipher mEncrypt;
	private SecretKeySpec mKeySpec;

	private byte[] mBuf;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		Log.d(TAG, "ENCRYPTION: " + result);
	}

	@Override
	protected Boolean doInBackground(String... param) {
		String mPath = param[0];
		String mPswrd;
		File mFile = new File(mPath);
		if (mFile.exists()) {
			try {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ObjectOutputStream objOutStream;
				objOutStream = new ObjectOutputStream(outStream);
				objOutStream.writeUTF(param[1]);
				objOutStream.flush();

				mPswrd = Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT);

				byte[] seedKey = mPswrd.getBytes();
				in = new FileInputStream(mFile);
				mFile = new File(mFile.getAbsolutePath().concat(".ncc"));
				out = new FileOutputStream(mFile);
				mKeySpec = new SecretKeySpec(seedKey, ALGORITHM);
				mEncrypt = Cipher.getInstance(FULL_ALGORITHM);
				mEncrypt.init(Cipher.ENCRYPT_MODE, mKeySpec);
				ciphStream = new CipherOutputStream(out, mEncrypt);

				mBuf = new byte[512];
				int bytesRead = 0;
				while ((bytesRead = in.read(mBuf)) != -1) {
					ciphStream.write(mBuf, 0, bytesRead);
				}
				in.close();
				ciphStream.flush();
				ciphStream.close();

				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
