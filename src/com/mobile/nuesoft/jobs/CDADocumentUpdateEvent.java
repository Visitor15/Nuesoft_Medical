package com.mobile.nuesoft.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;

public class CDADocumentUpdateEvent {
	private static final String ACTION_UPDATE = "com.mobile.nuesoft.action.UPDATE";
	public static final String CDA_DOC_OBJ_KEY = "com.mobile.nuesoft.CDA_DOC_OBJ_KEY";

	public static IntentFilter createFilter() {
		return new IntentFilter(CDADocumentUpdateEvent.ACTION_UPDATE);
	}

	public static void broadcast(final Context context, final Bundle b) {
		final Intent intent = new Intent(ACTION_UPDATE);
		intent.putExtras(b);
		NuesoftBroadcastReceiver.sendLocalBroadcast(context, intent);
	}

	private CDADocumentUpdateEvent() {
		throw new UnsupportedOperationException();
	}
}