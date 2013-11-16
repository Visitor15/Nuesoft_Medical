package com.mobile.nuesoft.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.mobile.nuesoft.Nuesoft;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DecryptionJob extends AsyncTask<String, Void, Boolean> {
	public static final String TAG = "DecryptionJob";
	public static final String ENC_PIECE_1 = "rO0ABXcKAAgwMDAw";
	private static final String ALGORITHM = "DES";
	private static final String FULL_ALGORITHM = "DES/ECB/PKCS5Padding";

	private FileInputStream in;
	private FileOutputStream out;
	private CipherOutputStream ciphStream;
	private Cipher mEncrypt;
	private SecretKeySpec mKeySpec;

	private byte[] mBuf;

	private File mEncryptedFile;

	private File mFile;

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

		if (result) {
			ParseCDADocumentJob parseJob = new ParseCDADocumentJob();
			parseJob.execute(mFile.getPath());
		} else {
			Toast.makeText(Nuesoft.getReference(), "Failed to unlock document", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected Boolean doInBackground(String... param) {
		String mPath = param[0];
		String mPswrd = param[1];
		mEncryptedFile = new File(mPath);
		if (mEncryptedFile.exists()) {
			try {
				byte[] seedKey = mPswrd.getBytes();
				in = new FileInputStream(mEncryptedFile);

				String encryptFileName = mEncryptedFile.getName();
				String decryptFileName = encryptFileName.substring(0, encryptFileName.length() - 8).concat("_UNLOCKED.xml");

				String mTmpPath = mEncryptedFile.getParentFile().getPath().concat("/").concat(decryptFileName);
				mFile = new File(mTmpPath);
				out = new FileOutputStream(mFile);
				mKeySpec = new SecretKeySpec(seedKey, ALGORITHM);
				mEncrypt = Cipher.getInstance(FULL_ALGORITHM);
				mEncrypt.init(Cipher.DECRYPT_MODE, mKeySpec);
				ciphStream = new CipherOutputStream(out, mEncrypt);

				mBuf = new byte[512];
				int bytesRead = 0;
				while ((bytesRead = in.read(mBuf)) != -1) {
					ciphStream.write(mBuf, 0, bytesRead);
				}
				in.close();
				mEncryptedFile.delete();
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
