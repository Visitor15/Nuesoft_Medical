package com.mobile.nuesoft.document;

import com.mobile.nuesoft.patient.Address;

public class HealthCareFacility {

	private final String DISPLAY_NAME;
	private final Address ADDRESS;
	private final Organization SERVICE_PROVIDER;
	
	public HealthCareFacility(final String DISPLAY_NAME, final Address ADDRESS, final Organization SERVICE_PROVIDER) {
		this.DISPLAY_NAME = DISPLAY_NAME;
		this.ADDRESS = ADDRESS;
		this.SERVICE_PROVIDER = SERVICE_PROVIDER;
	}

	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}

	public Address getADDRESS() {
		return ADDRESS;
	}

	public Organization getSERVICE_PROVIDER() {
		return SERVICE_PROVIDER;
	}
}
