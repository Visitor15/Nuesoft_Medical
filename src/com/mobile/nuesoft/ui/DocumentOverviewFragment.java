package com.mobile.nuesoft.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.document.CDADocumentBuilder.CDADocument;

public class DocumentOverviewFragment extends NuesoftFragment {
	
	private View rootView;
	
	private ExpandableListView expandableList;
	
	private HashMap<Integer, String> expandableListData = new HashMap<Integer, String>();

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
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    
    }
	
	private class ExpandableAdapter extends BaseExpandableListAdapter {

		private final CDADocument mDocument;
		
		
		
		public ExpandableAdapter(final CDADocument document) {
			mDocument = document;
		}
		
		private void init() {
			mDocument.getSERVICE_EVENT();
		}
		
		@Override
        public Object getChild(int groupPosition, int childPosition) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public long getChildId(int groupPosition, int childPosition) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                ViewGroup parent) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public int getChildrenCount(int groupPosition) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public Object getGroup(int groupPosition) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public int getGroupCount() {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public long getGroupId(int groupPosition) {
	        // TODO Auto-generated method stub
	        return 0;
        }

		@Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	        return null;
        }

		@Override
        public boolean hasStableIds() {
	        // TODO Auto-generated method stub
	        return false;
        }

		@Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
	        // TODO Auto-generated method stub
	        return false;
        }
		
	}

}
