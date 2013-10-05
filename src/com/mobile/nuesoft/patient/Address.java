package com.mobile.nuesoft.patient;

public class Address {

	private final String STREET_ADDRESS_LINE;
	private final String CITY;
	private final String STATE;
	private final String POSTAL_CODE;
	private final String COUNTRY;
	
	private String DEPARTMENT = "";
	
	public Address(final String STREET_ADDRESS_LINE, final String CITY, final String STATE, final String POSTAL_CODE, final String COUNTRY) {
		this.STREET_ADDRESS_LINE = STREET_ADDRESS_LINE;
		this.CITY = CITY;
		this.STATE = STATE;
		this.POSTAL_CODE = POSTAL_CODE;
		this.COUNTRY = COUNTRY;
	}

	public String getSTREET_ADDRESS_LINE() {
		return STREET_ADDRESS_LINE;
	}

	public String getCITY() {
		return CITY;
	}

	public String getSTATE() {
		return STATE;
	}

	public String getPOSTAL_CODE() {
		return POSTAL_CODE;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}
	
	public void setDepartment(final String department) {
		this.DEPARTMENT = department;
	}
	
	public String getDepartment() {
		return this.DEPARTMENT;
	}
	
	@Override
	public String toString() {
		String results = "";
		
		results += this.getSTREET_ADDRESS_LINE() + "\n";
		results += this.getCITY() + ", " + this.getSTATE() + " " + this.getPOSTAL_CODE() + " " + this.getCOUNTRY();
		
		return results;
	}
}
