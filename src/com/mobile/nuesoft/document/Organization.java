package com.mobile.nuesoft.document;

import com.mobile.nuesoft.patient.Address;
import com.mobile.nuesoft.patient.Telephone;

public class Organization {

	private final String DISPLAY_NAME;
	private final Address ADDRESS;
	private final Telephone TELEPHONE;
	
	public String ID = "";
	public String CODE = "";
	public String CODE_SYSTEM = "";
	public String CODE_SYSTEM_NAME = "";
	
	public Organization(final String DISPLAY_NAME, final Address ADDRESS, final Telephone TELEPHONE) {
		this.DISPLAY_NAME = DISPLAY_NAME;
		this.ADDRESS = ADDRESS;
		this.TELEPHONE = TELEPHONE;
	}

	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}

	public Address getADDRESS() {
		return ADDRESS;
	}

	public Telephone getTELEPHONE() {
		return TELEPHONE;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getCODE_SYSTEM() {
		return CODE_SYSTEM;
	}

	public void setCODE_SYSTEM(String cODE_SYSTEM) {
		CODE_SYSTEM = cODE_SYSTEM;
	}

	public String getCODE_SYSTEM_NAME() {
		return CODE_SYSTEM_NAME;
	}

	public void setCODE_SYSTEM_NAME(String cODE_SYSTEM_NAME) {
		CODE_SYSTEM_NAME = cODE_SYSTEM_NAME;
	}
	
	@Override
	public String toString() {
		String val = "";
		
		val += "ORGANIZATION\n";
		val += this.getDISPLAY_NAME() + "\n";
		val += this.getID() + "\n";
		val += this.getADDRESS().toString() + "\n";
		val += this.getTELEPHONE().toString() + "\n";
		
		return val;
	}
}
