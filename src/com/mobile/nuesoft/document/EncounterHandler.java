package com.mobile.nuesoft.document;


public class EncounterHandler {

	private final String ID;
	private final String CODE;
	private final String CODE_SYSTEM;
	private final String CODE_SYSTEM_NAME;
	private final Personnel PERSON;

	public EncounterHandler(final String ID, final String CODE, final String CODE_SYSTEM,
	        final String CODE_SYSTEM_NAME, final Personnel PERSON) {
		this.ID = ID;
		this.CODE = CODE;
		this.CODE_SYSTEM = CODE_SYSTEM;
		this.CODE_SYSTEM_NAME = CODE_SYSTEM_NAME;
		this.PERSON = PERSON;
	}

	public String getID() {
		return ID;
	}

	public String getCODE() {
		return CODE;
	}

	public String getCODE_SYSTEM() {
		return CODE_SYSTEM;
	}

	public String getCODE_SYSTEM_NAME() {
		return CODE_SYSTEM_NAME;
	}

	public Personnel getPERSON() {
		return PERSON;
	}
	
}
