package com.mobile.nuesoft.ui;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.NuesoftFragment;
import com.mobile.nuesoft.R;

public class DocumentListFragment extends NuesoftFragment implements OnClickListener {

	private View rootView;

	private ListView listView;

	private ListViewAdapter mAdapter;

	public DocumentListFragment() {

	}

	@Override
	public void onFragmentCreate(Bundle savedInstanceState) {
		TAG = "DocumentListFragment";
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
		rootView = inflater.inflate(R.layout.document_list_frag_layout, container, false);

		listView = (ListView) rootView.findViewById(R.id.list);

		listView.setBackgroundResource(R.color.light_grey);

		mAdapter = new ListViewAdapter(Nuesoft.getReference(), Uri.parse("storage/sdcard0/Download"));

		return rootView;
	}

	@Override
	public void onFragmentViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		listView.setAdapter(mAdapter);
	}

	public class ListViewAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		private Uri rootUri;

		private ArrayList<DocFile> dataList = new ArrayList<DocFile>();

		public ListViewAdapter(final Context c, final Uri rootUri) {
			mInflater = LayoutInflater.from(c);
			this.rootUri = rootUri;
			init();
		}

		private void init() {
			File f = new File(rootUri.getPath());

			if (f.exists()) {
				File[] children = f.listFiles();

				for (int i = 0; i < children.length; i++) {
					File tempFile = children[i];

					if (!tempFile.isDirectory()) {
						String fileName = tempFile.getName();
						String exten = fileName.substring(fileName.length() - 3);
						if (exten.equalsIgnoreCase("xml")) {
							dataList.add(new DocFile(fileName, Uri.fromFile(tempFile)));
						}
					}
				}
			}
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public DocFile getItem(int pos) {
			return dataList.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup container) {
			final DocFile mDocFile = dataList.get(pos);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.document_item_view, container, false);
			}
			
			RelativeLayout mContainer = (RelativeLayout) convertView.findViewById(R.id.rl_container);
			mContainer.setTag(mDocFile.mUri.toString());

			TextView textView = (TextView) convertView.findViewById(R.id.nt_text);
			textView.setText(mDocFile.name);
			
			mContainer.setOnClickListener(DocumentListFragment.this);

			return convertView;
		}
	}

	public class DocFile {
		public Uri mUri;
		public String name;

		public DocFile(final String name, final Uri mUri) {
			this.name = name;
			this.mUri = mUri;
		}
	}

	@Override
    public void onClick(View v) {
	    Uri mUri = Uri.parse((String) v.getTag());
	    
	    Log.d(TAG, "CLICKED URI: " + mUri);
	    
	    Bundle b = new Bundle();
		b.putInt(FragmentCallbackEvent.ACTION_KEY, FragmentCallbackEvent.ACTIONS.REPLACE_MAIN_CONTENT.ordinal());
		b.putInt(FragmentCallbackEvent.FRAGMENT, FragmentCallbackEvent.FRAGMENTS.PATIENT_FRAGMENT.ordinal());
		b.putString(FragmentCallbackEvent.DATA, mUri.toString());
		FragmentCallbackEvent.broadcast(Nuesoft.getReference(), b);
    }
}
