package com.mobile.nuesoft.patient;

public class Telephone {
	private final String AREA_CODE;
	private final String FIRST_PART;
	private final String SECOND_PART;
	private String EXTENSION;

	public Telephone() {
		AREA_CODE = "000";
		FIRST_PART = "000";
		SECOND_PART = "0000";
		EXTENSION = "";
	}

	public Telephone(final String areaCode, final String firstPart, final String secondPart) {
		this.AREA_CODE = areaCode;
		this.FIRST_PART = firstPart;
		this.SECOND_PART = secondPart;
	}

	public String getAREA_CODE() {
		return AREA_CODE;
	}

	public String getFIRST_PART() {
		return FIRST_PART;
	}

	public String getSECOND_PART() {
		return SECOND_PART;
	}

	public String getEXTENSION() {
		return EXTENSION;
	}

	public void setEXTENSION(String eXTENSION) {
		EXTENSION = eXTENSION;
	}

	@Override
	public String toString() {
		return "(" + AREA_CODE + ")" + "-" + FIRST_PART + "-" + SECOND_PART;
	}
}