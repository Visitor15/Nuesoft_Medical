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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobile.nuesoft.ui.FooterFragment;
import com.mobile.nuesoft.ui.FragmentCallbackEvent;
import com.mobile.nuesoft.ui.LoginFragment;
import com.mobile.nuesoft.ui.NuesoftBroadcastReceiver;
import com.mobile.nuesoft.ui.PatientFragment;
import com.mobile.nuesoft.ui.RegistrationFragment;
import com.mobile.nuesoft.ui.SendDocFragment;
import com.mobile.nuesoft.ui.UnlockCDADocFragment;

public class MainActivity extends ActionBarActivity {

	public static final String TAG = "MainActivity";
	private static final String DO_INIT_KEY = "do_init_key";
	private static final String FOOTER_FRAG_VISIBLE = "footer_frag_visbility";

	private LinearLayout mFooterContainer;

	private boolean do_init = true;
	private boolean showFooter = false;

	private FooterFragment mFooter = new FooterFragment();
	private OnFragmentCallbackListener fragCallbackListener = new OnFragmentCallbackListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mFooterContainer = (LinearLayout) findViewById(R.id.ll_footer_container);

		if (savedInstanceState != null) {
			do_init = savedInstanceState.getBoolean(DO_INIT_KEY);
			showFooter = savedInstanceState.getBoolean(FOOTER_FRAG_VISIBLE);
		}
		if (do_init) {
			init();
		}
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
		if (showFooter) {
			this.showFooter();
		}
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
		do_init = false;

		this.getSupportFragmentManager().beginTransaction().add(R.id.ll_footer_container, mFooter, FooterFragment.TAG)
		        .commit();
		Fragment frag = new LoginFragment();
		this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, frag, LoginFragment.TAG).commit();
	}

	public void hideFooter() {
		showFooter = false;
		Animation outAnim;
		outAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		outAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation anim) {
				mFooterContainer.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation anim) {
			}

			@Override
			public void onAnimationStart(Animation anim) {
			}
		});

		if (mFooterContainer.isShown()) {
			mFooterContainer.startAnimation(outAnim);
		}
	}

	public FooterFragment getFooter() {
		return mFooter;
	}

	public void showFooter() {
		showFooter = true;
		mFooterContainer.setVisibility(View.VISIBLE);
		Animation outAnim;
		outAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		outAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation anim) {
				mFooterContainer.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation anim) {
			}

			@Override
			public void onAnimationStart(Animation anim) {
			}

		});

		mFooterContainer.startAnimation(outAnim);
	}

	public void replaceMainContent(final NuesoftFragment frag) {
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
						mFooter = new FooterFragment();
						this.getSupportFragmentManager().beginTransaction()
						        .replace(R.id.ll_footer_container, mFooter, FooterFragment.TAG).commit();
						replaceMainContent(new PatientFragment());
						break;
					}

					// Registration fragment
					case 1: {
						replaceMainContent(new RegistrationFragment());
						break;
					}
					// Login fragment
					case 2: {
						replaceMainContent(new LoginFragment());
						break;
					}
					case 3: {
						// Unlock CDA Document fragment
						replaceMainContent(new UnlockCDADocFragment());
						break;
					}
					case 4: {
						// Send CDA Document fragment
						replaceMainContent(new SendDocFragment());
						break;
					}
				}
				break;
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putBoolean(DO_INIT_KEY, do_init);
		outState.putBoolean(FOOTER_FRAG_VISIBLE, showFooter);
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
			onHandleFragmentCallback(intent.getExtras());
		}
	}
}
