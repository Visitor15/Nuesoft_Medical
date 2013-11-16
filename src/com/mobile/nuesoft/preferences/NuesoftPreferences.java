package com.mobile.nuesoft.preferences;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftUser;

public class NuesoftPreferences {
	
	public static final String TAG = "NuesoftPreferences";

	public static final String PREFERENCES = "com.mobile.nuesoft.preferences";

	private static NuesoftPreferences mInstance;

	private SharedPreferences mPrefs;

	private NuesoftPreferences() {
		mInstance = this;
		mPrefs = getPreferences(Nuesoft.getReference());
	}

	public static NuesoftPreferences getInstance() {
		if (mInstance == null) {
			new NuesoftPreferences();
		}

		return mInstance;
	}

	private SharedPreferences getPreferences(final Context c) {
		return c.getSharedPreferences(PREFERENCES, 0);
	}

	public NuesoftUser getNuesoftUser(final String userName) {
		String savedUser = mPrefs.getString(userName, null);

		if (savedUser != null) {
			NuesoftUser user = NuesoftUser.fromString(savedUser);
			user.setUserName(userName);
			
			return user;
		}
		return null;
	}

	public boolean saveRegisteredUser(final NuesoftUser user) {
		NuesoftUser savedUser = getNuesoftUser(user.getUserName().toLowerCase(Locale.getDefault()));
		if (savedUser == null) {
			return mPrefs.edit().putString(user.getUserName().toLowerCase(Locale.getDefault()), user.toString()).commit();
		}

		return false;
	}
	
	public boolean updateRegisteredUser(final NuesoftUser user) {
		return mPrefs.edit().putString(user.getUserName().toLowerCase(Locale.getDefault()), user.toString()).commit();
	}
}
