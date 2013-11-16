package com.mobile.nuesoft;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.util.Base64;
import android.util.Log;

/*
 * Simple class used to represent a user during registration or login.
 * This is also the object that gets stored in preferences after a successful registraton.
 */
public class NuesoftUser {
	
	public static final String TAG = "NuesoftUser";

	private String userName = "";
	private String string64PSWRD = "";
	private String profilePicURI = "";

	private byte[] salt;
	private byte[] encryptedPSWRD;

	public NuesoftUser() {}
	
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public void setString64PSWRD(final String string64PSWRD) {
		this.string64PSWRD = string64PSWRD;
	}

	public String getString64PSWRD() {
		return this.string64PSWRD;
	}
	
	public void setProfilePicURI(final String uri) {
		this.profilePicURI = uri;
		Log.d(TAG, "URI IS: " + this.profilePicURI);
	}
	
	public String getProfilePicUri() {
		return this.profilePicURI;
	}

	public void setSalt(final byte[] salt) {
		this.salt = salt;
	}
	
	public byte[] getSalt() {
		return this.salt;
	}

	public void setEncryptedPassword(final byte[] encryptedPSWRD) {
		this.encryptedPSWRD = encryptedPSWRD;
	}
	
	public byte[] getEncryptedPassword() {
		return this.encryptedPSWRD;
	}

	@Override
	public String toString() {
		String val = "";
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutStream;
		
		try {
			objOutStream = new ObjectOutputStream(outStream);
			
			objOutStream.writeInt(salt.length);
			objOutStream.write(salt, 0, salt.length);
			objOutStream.writeInt(encryptedPSWRD.length);
			objOutStream.write(encryptedPSWRD, 0, encryptedPSWRD.length);
			
			Log.d(TAG, "NCC - WRITING URI: " + getProfilePicUri());
			
			objOutStream.writeUTF(getProfilePicUri());
			
			objOutStream.flush();
			
			val = Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT);
		} catch (IOException e) {
			return val;
		}

		return val;
	}
	
	public static NuesoftUser fromString(final String string) {
		final byte[] byteVal = Base64.decode(string, Base64.DEFAULT);
		final ByteArrayInputStream is = new ByteArrayInputStream(byteVal);
		final ObjectInputStream in;
		int length = 0;
		NuesoftUser user = new NuesoftUser();
		
		try {
	        in = new ObjectInputStream(is);
	        
	        length = in.readInt();
	        user.setSalt(new byte[length]);
	        in.read(user.salt);
	        
	       	length = in.readInt();
	       	user.setEncryptedPassword(new byte[length]);
	       	in.read(user.encryptedPSWRD);
	       	user.setProfilePicURI(in.readUTF());
        } catch (StreamCorruptedException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }
		
		return user;
	}
}
