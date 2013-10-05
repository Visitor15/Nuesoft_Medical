package com.mobile.nuesoft.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Util {
	
	public static int convertDpToPixel(final float dp, final Context context) {
		final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
	}
}
