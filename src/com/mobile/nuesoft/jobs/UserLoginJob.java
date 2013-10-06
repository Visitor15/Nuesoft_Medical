package com.mobile.nuesoft.jobs;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import android.os.AsyncTask;
import android.os.Bundle;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftUser;
import com.mobile.nuesoft.preferences.NuesoftPreferences;
import com.mobile.nuesoft.util.Util;

public class UserLoginJob extends AsyncTask<NuesoftUser, Void, Boolean> {

	public static final String TAG = "NuesoftLoginJob";

	private NuesoftUser registeredUser
	;

	private boolean result = false;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		Bundle b = new Bundle();
		
		b.putBoolean(NuesoftUserLoginEvent.RESULT_KEY, result);
		b.putString(NuesoftUserLoginEvent.USER_KEY, registeredUser.toString());
		
		NuesoftUserLoginEvent.broadcast(Nuesoft.getReference(), b);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(NuesoftUser... user) {

		try {
	        result = authorizeUser(user[0]);
        } catch (NoSuchAlgorithmException e) {
        	result = false;
	        e.printStackTrace();
        } catch (InvalidKeySpecException e) {
        	result = false;
	        e.printStackTrace();
        }
		
		return result;
	}

	private boolean authorizeUser(final NuesoftUser attemptingUser) throws NoSuchAlgorithmException,
	        InvalidKeySpecException {
		registeredUser = NuesoftPreferences.getInstance().getNuesoftUser(attemptingUser.getUserName());
		
		if (registeredUser != null) {
			// Ensuring usernames match. This is a case-sensitive operation!
			if (!(attemptingUser.getUserName().equals(registeredUser.getUserName()))) {
				return false;
			}
			byte[] byteVal = Util.getEncryptedPassword(attemptingUser.getString64PSWRD(), registeredUser.getSalt());

			return Arrays.equals(byteVal, registeredUser.getEncryptedPassword());
		}

		return false;
	}
}
