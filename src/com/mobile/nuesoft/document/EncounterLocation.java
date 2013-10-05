package com.mobile.nuesoft.document;

public class EncounterLocation {

	private final String DISPLAY_NAME;
	private final HealthCareFacility HEALTH_CARE_FACILITY;
	
	public EncounterLocation(final String DISPLAY_NAME, final HealthCareFacility HEALTH_CARE_FACILITY) {
		this.DISPLAY_NAME = DISPLAY_NAME;
		this.HEALTH_CARE_FACILITY = HEALTH_CARE_FACILITY;
	}

	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}

	public HealthCareFacility getHEALTH_CARE_FACILITY() {
		return HEALTH_CARE_FACILITY;
	}
}
