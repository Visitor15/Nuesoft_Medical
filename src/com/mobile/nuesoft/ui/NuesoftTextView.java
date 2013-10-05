package com.mobile.nuesoft.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.mobile.nuesoft.R;

public class NuesoftTextView extends TextView {
	
	public static final String TAG = "NuesoftTextView";
	
	public NuesoftTextView(Context context) {
		super(context);
	}
	
	public NuesoftTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public NuesoftTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(final Context c, final AttributeSet attrs) {
		String textPath = "fonts/Roboto-Thin.ttf";
		if(attrs != null) {
			textPath = c.obtainStyledAttributes(attrs, R.styleable.NuesoftTextView).getString(R.styleable.NuesoftTextView_textStyle);
			
			if(textPath == null || textPath.trim().length() < 1) {
				textPath = "fonts/Roboto-Thin.ttf";
			}
		}
		
		Log.d(TAG, "FONT PATH IS: " + textPath);

		this.setTypeface(Typeface.createFromAsset(c.getAssets(), textPath));
	}
}
