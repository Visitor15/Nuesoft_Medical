package com.mobile.nuesoft.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Util {

	public static int convertDpToPixel(final float dp, final Context context) {
		final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
	}

	public static byte[] getEncryptedPassword(final String string64PSWRD, final byte[] salt)
	        throws NoSuchAlgorithmException, InvalidKeySpecException {

		String algorithm = "PBKDF2WithHmacSHA1";
		int derivedKeyLength = 160;
		int iterations = 10000;

		KeySpec spec = new PBEKeySpec(string64PSWRD.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);

		return factory.generateSecret(spec).getEncoded();
	}
}
