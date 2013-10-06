package com.mobile.nuesoft.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;

public class NuesoftRegisteredUserEvent {
	private static final String ACTION_REGISTERED = "com.mobile.nuesoft.action.USER_REGISTERED";
	
	public static final String RESULT_KEY = "com.mobile.nuesoft.RESULT";
	public static final String USER_KEY = "com.mobile.nuesoft.USER_KEY";
	
	public static IntentFilter createFilter() {
		return new IntentFilter(NuesoftRegisteredUserEvent.ACTION_REGISTERED);
	}

	public static void broadcast(final Context context, final Bundle b) {
		final Intent intent = new Intent(ACTION_REGISTERED);
		intent.putExtras(b);
		NuesoftBroadcastReceiver.sendLocalBroadcast(context, intent);
	}

	private NuesoftRegisteredUserEvent() {
		throw new UnsupportedOperationException();
	}
}