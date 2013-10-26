package com.mobile.nuesoft.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.nuesoft.MainActivity;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;

public class RegistrationFragment extends NuesoftFragment {
	
	View rootView;
	private LayoutInflater mInflater;
	
	public RegistrationFragment() {
		TAG = "RegistrationFragment";
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
		rootView = mInflater.inflate(R.layout.registration_fragment_layout, container, false);
		
		((MainActivity) getActivity()).closeAndLockDrawer();
		
		return rootView;
    }

	@Override
    public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    
    }
}
