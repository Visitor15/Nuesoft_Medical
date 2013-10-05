package com.mobile.nuesoft.document;

import com.mobile.nuesoft.patient.Address;
import com.mobile.nuesoft.patient.Telephone;

public class Personnel {

	public static final String TAG = "Personnel";

	private final String GIVEN_NAME;
	private final String FAMILY_NAME;
	private final String SUFFIX;

	private String PERSONNEL_ID = "";
	private String PERSONNEL_TYPE = "";
	private String PERSONNEL_DEPARTMENT = "";
	private String CODE_SYSTEM = "";
	private String CODE_SYSTEM_NAME = "";
	private Address PERSONNEL_ADDRESS = null;
	private Telephone TELEPHONE = null;
	private Organization PERSONNEL_ORGANIZATION = null;

	public Personnel(final String firstName, final String lastName, final String suffix) {
		this.GIVEN_NAME = firstName;
		this.FAMILY_NAME = lastName;
		this.SUFFIX = suffix;
	}

	public String getGIVEN_NAME() {
		return GIVEN_NAME;
	}

	public String getFAMILY_NAME() {
		return FAMILY_NAME;
	}

	public String getSUFFIX() {
		return SUFFIX;
	}

	public String getPERSONNEL_TYPE() {
		return PERSONNEL_TYPE;
	}

	public void setPERSONNEL_TYPE(String PERSONNEL_TYPE) {
		this.PERSONNEL_TYPE = PERSONNEL_TYPE;
	}

	public String getPERSONNEL_DEPARTMENT() {
		return PERSONNEL_DEPARTMENT;
	}

	public void setPERSONNEL_DEPARTMENT(String pERSONNEL_DEPARTMENT) {
		PERSONNEL_DEPARTMENT = pERSONNEL_DEPARTMENT;
	}

	public Organization getPERSONNEL_ORGANIZATION() {
		return PERSONNEL_ORGANIZATION;
	}

	public void setPERSONNEL_ORGANIZATION(Organization pERSONNEL_ORGANIZATION) {
		PERSONNEL_ORGANIZATION = pERSONNEL_ORGANIZATION;
	}

	public Telephone getTELEPHONE() {
		return TELEPHONE;
	}

	public void setTELEPHONE(Telephone tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}

	public Address getPERSONNEL_ADDRESS() {
		return PERSONNEL_ADDRESS;
	}

	public void setPERSONNEL_ADDRESS(Address pERSONNEL_ADDRESS) {
		PERSONNEL_ADDRESS = pERSONNEL_ADDRESS;
	}

	public void setPERSONNEL_ID(final String ID) {
		this.PERSONNEL_ID = ID;
	}

	public String getPERSONNEL_ID() {
		return this.PERSONNEL_ID;
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

		val += "PERSONNEL\n";
		if (this.getPERSONNEL_TYPE().trim().length() > 0) {
			val += this.getPERSONNEL_TYPE() + ",\n";
		}

		val += this.getFAMILY_NAME() + ", " + this.getGIVEN_NAME() + " " + this.getSUFFIX();

		if (this.getPERSONNEL_ADDRESS() != null) {
			val += "\n" + this.getPERSONNEL_ADDRESS().toString();
		}
		if(this.getTELEPHONE() != null) {
			val += "\n" + this.getTELEPHONE().toString();
		}

		return val;
	}
}
