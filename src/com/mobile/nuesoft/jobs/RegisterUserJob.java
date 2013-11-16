package com.mobile.nuesoft.jobs;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftUser;
import com.mobile.nuesoft.preferences.NuesoftPreferences;
import com.mobile.nuesoft.util.Util;

public class RegisterUserJob extends AsyncTask<NuesoftUser, Void, Boolean> {

	public static final String TAG = "RegisterUserJob";

	private NuesoftUser mUser;

	private boolean result = false;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		Bundle b = new Bundle();
		b.putBoolean(NuesoftRegisteredUserEvent.RESULT_KEY, result);
		b.putString(NuesoftRegisteredUserEvent.USER_KEY, mUser.toString());

		NuesoftRegisteredUserEvent.broadcast(Nuesoft.getReference(), b);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(NuesoftUser... user) {
		mUser = NuesoftPreferences.getInstance().getNuesoftUser(user[0].getUserName());
		
		if (mUser == null) {
			try {
				mUser = new NuesoftUser();
				mUser.setUserName(user[0].getUserName());
				mUser.setProfilePicURI(user[0].getProfilePicUri());
				mUser.setSalt(generateSalt());
				mUser.setEncryptedPassword(Util.getEncryptedPassword(user[0].getString64PSWRD(), mUser.getSalt()));
				
				Log.d(TAG, "NCC - MORE THINGS: " + mUser.getEncryptedPassword().length);

				// Saving user to preferences
				result = NuesoftPreferences.getInstance().saveRegisteredUser(mUser);
			} catch (final NoSuchAlgorithmException e) {
				Log.d(TAG, "NCC - CAUGHT 1");
				
			} catch (InvalidKeySpecException e) {
				Log.d(TAG, "NCC - CAUGHT 2");
				e.printStackTrace();
			}
		}

		return result;
	}

	private byte[] generateSalt() throws NoSuchAlgorithmException {
		SecureRandom secRandom = SecureRandom.getInstance("SHA1PRNG");

		byte[] salt = new byte[8];
		secRandom.nextBytes(salt);

		return salt;
	}
}
