package com.mobile.nuesoft.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;

public class FooterFragment extends NuesoftFragment implements OnClickListener {
	
	private View rootView;
	
	private ImageView btnLeft;
	private ImageView btnRight;
	
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
	    
	    mTitleText = (TextView) rootView.findViewById(R.id.nt_title_text);
	    btnLeft = (ImageView) rootView.findViewById(R.id.btn_cancel);
	    btnRight = (ImageView) rootView.findViewById(R.id.btn_send_now);
	    
	    btnLeft.setOnClickListener(this);
	    btnRight.setOnClickListener(this);
	    
	    return rootView;
    }

	@Override
    public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    
    }
	
	public void setTitleText(final String str) {
		this.mTitleText.setText(str);
	}

	@Override
    public void onClick(View v) {
	    switch(v.getId()) {
	    	case R.id.btn_cancel: {
	    		
	    		break;
	    	}
	    	
	    	case R.id.btn_send_now: {
	    		
	    		break;
	    	}
	    }
    }

}
