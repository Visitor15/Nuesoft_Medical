package com.mobile.nuesoft.patient;

public class Gender {

	private final String mGender;
	private final String mGenderCode;
	
	public Gender(final String mGender, final String mGenderCode) {
		this.mGender = mGender;
		this.mGenderCode = mGenderCode;
	}
	
	@Override
	public String toString() {
		return mGender;
	}
}
