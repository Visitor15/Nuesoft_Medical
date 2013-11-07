package com.mobile.nuesoft;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobile.nuesoft.ui.FooterFragment;
import com.mobile.nuesoft.ui.FragmentCallbackEvent;
import com.mobile.nuesoft.ui.LoginFragment;
import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;
import com.mobile.nuesoft.ui.PatientFragment;
import com.mobile.nuesoft.ui.RegistrationFragment;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	public static final String TAG = "MainActivity";

	private RelativeLayout mainContainer;
	
	private LinearLayout mFooterContainer;
	
	private FooterFragment mFooter = new FooterFragment();

//	private DrawerLayout navDrawer;
	
//	private ImageView navHandle;

	private OnFragmentCallbackListener fragCallbackListener = new OnFragmentCallbackListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

//		navDrawer = (DrawerLayout) findViewById(R.id.nav_drawer);
//		navHandle = (ImageView) findViewById(R.id.nav_handle);
		mainContainer = (RelativeLayout) findViewById(R.id.content_frame);
		mFooterContainer = (LinearLayout) findViewById(R.id.ll_footer_container);

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
//		navDrawer.setScrimColor(Color.parseColor("#AA282828"));
//		navHandle.setOnClickListener(this);
		
		this.getSupportFragmentManager().beginTransaction().add(R.id.ll_footer_container, mFooter, LoginFragment.TAG)
        .commit();
		
		Fragment frag = new LoginFragment();
		this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, frag, LoginFragment.TAG)
		        .commit();
		
//		Fragment frag = new RegistrationFragment();
//		this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, frag, RegistrationFragment.TAG)
//		        .commit();
		
//		Fragment frag = new PatientFragment();
//		this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, frag, PatientFragment.TAG)
//		        .commit();
	}
	
	public void closeAndLockDrawer() {
		
		mFooterContainer.setVisibility(View.GONE);
		
//		navDrawer.closeDrawers();
//		navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//		navHandle.setVisibility(View.GONE);
	}
	
	public void unlockDrawer() {
		
		mFooterContainer.setVisibility(View.VISIBLE);
		
		
//		navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//		navHandle.setVisibility(View.VISIBLE);
	}
	
	public FooterFragment getFooter() {
		return mFooter;
	}

	private void replaceMainContent(final NuesoftFragment frag) {
		this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag, NuesoftFragment.TAG)
		        .addToBackStack(NuesoftFragment.TAG).commit();
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
						unlockDrawer();
						replaceMainContent(new PatientFragment());
						break;
					}
					
					// Registration fragment
					case 1: {
						this.closeAndLockDrawer();
						replaceMainContent(new RegistrationFragment());
						break;
					}
					// Login fragment
					case 2: {
						this.closeAndLockDrawer();
						replaceMainContent(new LoginFragment());
						break;
					}
				}
				break;
			}
		}
	}
	
	@Override
    public void onClick(View v) {
		switch(v.getId()) {
//			case R.id.nav_handle: {
//				
//				navDrawer.openDrawer(Gravity.LEFT);
//				
//				break;
//			}
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
		}
	}
}
