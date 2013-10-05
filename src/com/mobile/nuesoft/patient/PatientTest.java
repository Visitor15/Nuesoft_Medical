package com.mobile.nuesoft.patient;

public class PatientTest {

	private String TITLE;
	private String optRESULT;

	public PatientTest(final String TITLE, final String optRESULT) {
		this.TITLE = TITLE;
		this.optRESULT = optRESULT;
	}
	
	public String getRESULT() throws NullPointerException {
		return this.optRESULT;
	}
	
	@Override
	public String toString() {
		return this.TITLE;
	}
}
