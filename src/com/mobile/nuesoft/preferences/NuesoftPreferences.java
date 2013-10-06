package com.mobile.nuesoft.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class NuesoftPreferences {

	public static final String PREFERENCES = "com.mobile.nuesoft.preferences";
	
	
	public static SharedPreferences getPreferences(final Context c) {
		return c.getSharedPreferences(PREFERENCES, 0);
	}
}
