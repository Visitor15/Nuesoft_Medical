package com.mobile.nuesoft.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;

public class EncryptionJobEvent {
	private static final String ACTION_UPDATE= "com.mobile.nuesoft.action.ENCRYPTION_UPDATE";
	public static final String PATIENT_OBJ_KEY = "com.mobile.nuesoft.ENCRYPTION_KEY";

	public static IntentFilter createFilter() {
		return new IntentFilter(EncryptionJobEvent.ACTION_UPDATE);
	}

	public static void broadcast(final Context context, final Bundle b) {
		final Intent intent = new Intent(ACTION_UPDATE);
		intent.putExtras(b);
		NuesoftBroadcastReceiver.sendLocalBroadcast(context, intent);
	}

	private EncryptionJobEvent() {
		throw new UnsupportedOperationException();
	}
}