package com.mobile.nuesoft;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobile.nuesoft.ui.DocumentListFragment;
import com.mobile.nuesoft.ui.FragmentCallbackEvent;
import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;
import com.mobile.nuesoft.ui.PatientFragment;

public class MainActivity extends FragmentActivity {

	public static final String TAG = "MainActivity";

	private RelativeLayout mainContainer;

	private DrawerLayout navDrawer;

	private OnFragmentCallbackListener fragCallbackListener = new OnFragmentCallbackListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		navDrawer = (DrawerLayout) findViewById(R.id.nav_drawer);
		mainContainer = (RelativeLayout) findViewById(R.id.content_frame);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onRestart() {
		super.onRestart();
	}

	@Override
	public void onResume() {
		super.onResume();

		fragCallbackListener.register();
	}

	@Override
	public void onPause() {
		super.onPause();

		fragCallbackListener.unregister();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void init() {
		navDrawer.setScrimColor(Color.parseColor("#CC000000"));

		Fragment frag = new DocumentListFragment();
		this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, frag, DocumentListFragment.TAG)
		        .commit();
	}

	private void replaceMainContent(final NuesoftFragment frag) {
		this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag, NuesoftFragment.TAG)
		        .addToBackStack(NuesoftFragment.TAG).commit();
	}

	private void onHandleFragmentCallback(final int actionID) {
		switch (actionID) {

			default: {
				Toast.makeText(getApplicationContext(), "ID is: " + actionID, Toast.LENGTH_LONG).show();
			}
		}
	}

	private void onHandleFragmentCallback(final Bundle b) {
		int mActionID = b.getInt(FragmentCallbackEvent.ACTION_KEY);

		switch (mActionID) {

		// Replace main content with a new fragment
			case 0: {
				int fragmentID = b.getInt(FragmentCallbackEvent.FRAGMENT);
				switch (fragmentID) {

				// Patient fragment
					case 0: {
						Uri mUri = Uri.parse(b.getString(FragmentCallbackEvent.DATA));
						replaceMainContent(new PatientFragment(mUri));
						break;
					}
				}
				break;
			}
		}
	}

	public class OnFragmentCallbackListener extends NuesoftBroadcastReceiver {
		void register() {
			final IntentFilter filter = FragmentCallbackEvent.createFilter();
			registerLocalReceiver(Nuesoft.getReference(), this, filter);
		}

		void unregister() {
			unregisterLocalReciever(Nuesoft.getReference(), this);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive HIT");
			onHandleFragmentCallback(intent.getExtras());
			// onHandleFragmentCallback(intent.getIntExtra(FragmentCallbackEvent.ACTION_KEY,
			// -1));
		}
	}
}
