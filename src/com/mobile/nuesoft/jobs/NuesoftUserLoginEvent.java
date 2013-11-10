package com.mobile.nuesoft.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;

public class NuesoftUserLoginEvent {
	private static final String ACTION_LOGIN = "com.mobile.nuesoft.action.USER_LOGIN";
	
	public static final String RESULT_KEY = "com.mobile.nuesoft.RESULT";
	public static final String USER_KEY = "com.mobile.nuesoft.USER_KEY";
	
	public static IntentFilter createFilter() {
		return new IntentFilter(NuesoftUserLoginEvent.ACTION_LOGIN);
	}

	public static void broadcast(final Context context, final Bundle b) {
		final Intent intent = new Intent(ACTION_LOGIN);
		intent.putExtras(b);
		NuesoftBroadcastReceiver.sendLocalBroadcast(context, intent);
	}

	private NuesoftUserLoginEvent() {
		throw new UnsupportedOperationException();
	}
}