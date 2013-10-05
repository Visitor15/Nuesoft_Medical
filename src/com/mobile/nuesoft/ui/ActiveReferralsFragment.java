package com.mobile.nuesoft.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;

public class ActiveReferralsFragment extends NuesoftFragment implements OnClickListener {
	
	private LayoutInflater mInflater;
	
	private View btnCancel;
	
	private View btnContinue;

	public ActiveReferralsFragment() {
		super();
	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentPaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSave(Bundle outState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View v = mInflater.inflate(R.layout.active_referral_layout, container, false);
		
		btnCancel = v.findViewById(R.id.iv_nav_left);
		btnContinue = v.findViewById(R.id.iv_nav_right);
		
		btnCancel.setOnClickListener(this);
		btnContinue.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.iv_nav_left: {
			getActivity().getSupportFragmentManager().beginTransaction().remove(ActiveReferralsFragment.this).commit();
			break;
		}
		case R.id.iv_nav_right: {
			
			break;
		}
		}
	}

}
