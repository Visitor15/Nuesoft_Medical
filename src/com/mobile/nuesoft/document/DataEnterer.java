package com.mobile.nuesoft.document;

public class DataEnterer {

	private final Personnel ENTERER;
	private final Organization ORGANIZATION;

	public DataEnterer(final Personnel ENTERER, final Organization ORGANIZATION) {
		this.ENTERER = ENTERER;
		this.ORGANIZATION = ORGANIZATION;
	}

	public Personnel getENTERER() {
		return ENTERER;
	}

	public Organization getORGANIZATION() {
		return ORGANIZATION;
	}
	
	@Override
	public String toString() {
		String val = "";
		
		val += "DATA ENTERER\n";
		val += this.getENTERER().toString() + "\n";
		val += this.getORGANIZATION().toString();
		
		return val;
	}
}
