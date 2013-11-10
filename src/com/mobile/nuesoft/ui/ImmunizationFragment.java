package com.mobile.nuesoft.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;
import com.mobile.nuesoft.patient.Immunization;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.util.Util;

public class ImmunizationFragment extends NuesoftFragment {
	private LayoutInflater mInflater;
	private ExpandableAdapter mAdapter;	
	private ExpandableListView listView;
	private TextView titleText;
	
	public ImmunizationFragment(){
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
	    
	    //View v = mInflater.inflate(R.layout., container, false);
	    
	    //View v = mInflater.inflate(R.layout.immunization_fragment_layout, container, false);
	    
	    //R.layout.

	    //View v = mInflater.inflate(R.layout.medication_fragment_layout, container, false);
		
		//titleText = (TextView) v.findViewById(R.id.nt_title);
		//titleText.setText("Medications");
		
		//if(Nuesoft.getCurrentPatient() == null) {
		//	showNoDataView(v);
		//}

		
		return null;
    }

	@Override
    public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub    
    }
	
	public void showNoDataView(final View v){	
	}
	
	public void hideNoDataView(final View v){
	}
	
	private void initImmunizationView(final View v){
	}
	
	private class ExpandableAdapter extends BaseExpandableListAdapter {
		
		public ExpandableAdapter(final PatientObj patient) {
			
		}

		@Override
        public Object getChild(int arg0, int arg1) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public long getChildId(int arg0, int arg1) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public int getChildrenCount(int arg0) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public Object getGroup(int arg0) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public int getGroupCount() {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public long getGroupId(int arg0) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public boolean hasStableIds() {
	        // TODO Auto-generated method stub
	        return false;
        }

		@Override
        public boolean isChildSelectable(int arg0, int arg1) {
	        // TODO Auto-generated method stub
	        return false;
        }
	
	}

}
