package com.mobile.nuesoft.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;

public class PatientUpdateEvent {
	private static final String ACTION_UPDATE= "com.mobile.nuesoft.action.UPDATE";
	public static final String PATIENT_OBJ_KEY = "com.mobile.nuesoft.PATIENT_OBJ_KEY";

	public static IntentFilter createFilter() {
		return new IntentFilter(PatientUpdateEvent.ACTION_UPDATE);
	}

	public static void broadcast(final Context context, final Bundle b) {
		final Intent intent = new Intent(ACTION_UPDATE);
		intent.putExtras(b);
		NuesoftBroadcastReceiver.sendLocalBroadcast(context, intent);
	}

	private PatientUpdateEvent() {
		throw new UnsupportedOperationException();
	}
}