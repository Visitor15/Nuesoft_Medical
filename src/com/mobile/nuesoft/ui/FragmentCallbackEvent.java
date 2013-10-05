package com.mobile.nuesoft.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class FragmentCallbackEvent {
	public static final String ACTION_CALLBACK= "com.mobile.nuesoft.action.ACTION_CALLBACK";
	public static final String ACTION_KEY = "com.mobile.nuesoft.action.ACTION_KEY";
	public static final String DATA = "com.mobile.nuesoft.DATA";
	public static final String FRAGMENT = "com.mobile.nuesoft.FRAGMENT";
	
	public static enum FRAGMENTS {
		PATIENT_FRAGMENT
	};
	
	public static enum ACTIONS {
		REPLACE_MAIN_CONTENT
	};

	public static IntentFilter createFilter() {
		return new IntentFilter(FragmentCallbackEvent.ACTION_CALLBACK);
	}

	public static void broadcast(final Context context, final Bundle b) {
		final Intent intent = new Intent(ACTION_CALLBACK);
		intent.putExtras(b);
		NuesoftBroadcastReceiver.sendLocalBroadcast(context, intent);
	}

	private FragmentCallbackEvent() {
		throw new UnsupportedOperationException();
	}
}