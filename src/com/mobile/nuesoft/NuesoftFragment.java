package com.mobile.nuesoft;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class NuesoftFragment extends Fragment {

	public static String TAG = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onFragmentCreate(savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
		
		onFragmentPaused();
	}

	@Override
	public void onResume() {
		super.onResume();
		
		onFragmentResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		onSave(outState);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		onFragmentStart();
	}

	@Override
	public void onStop() {
		super.onStop();
		
		onFragmentStart();
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return onFragmentCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		onFragmentViewCreated(view, savedInstanceState);
	}
	
	public abstract void onFragmentCreate(final Bundle savedInstanceState);
	
	public abstract void onFragmentPaused();
	
	public abstract void onFragmentResume();
	
	public abstract void onSave(final Bundle outState);
	
	public abstract void onFragmentStart();
	
	public abstract void onFragmentStop();
	
	public abstract View onFragmentCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState);
	
	public abstract void onFragmentViewCreated(final View v, final Bundle savedInstanceState);
}
