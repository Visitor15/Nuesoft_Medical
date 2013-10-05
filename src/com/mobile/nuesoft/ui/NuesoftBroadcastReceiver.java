package com.mobile.nuesoft.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public abstract class NuesoftBroadcastReceiver extends BroadcastReceiver {

		  public static void sendLocalBroadcast(final Context context, final Intent intent) {
		    final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
		    manager.sendBroadcast(intent);
		  }

		  public static void registerLocalReceiver(final Context context, final BroadcastReceiver receiver,
		      final IntentFilter filter) {
		    final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
		    manager.registerReceiver(receiver, filter);
		  }

		  public static void unregisterLocalReciever(final Context context, final BroadcastReceiver receiver) {
		    final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
		    manager.unregisterReceiver(receiver);
		  }
		  
		  @Override
		  public abstract void onReceive(final Context context, final Intent intent);
}
