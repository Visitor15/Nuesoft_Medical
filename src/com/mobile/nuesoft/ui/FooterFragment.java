package com.mobile.nuesoft.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;

public class FooterFragment extends NuesoftFragment {
	
	private View rootView;
	
	private Button btnLeft;
	private Button btnRight;
	
	private TextView mTitleText;
	
	public FooterFragment() {
		
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
	    rootView = inflater.inflate(R.layout.footer_layout, container, false);
	    
	    
	    return rootView;
    }

	@Override
    public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    
    }

}
