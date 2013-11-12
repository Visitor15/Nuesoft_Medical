package com.mobile.nuesoft.patient;

import com.mobile.nuesoft.document.Personnel;

public class Procedure {

	private String TITLE;
	private String DISPLAY_TITLE;
	private String EFFECTIVE_DATE_LOW;
	private String EFFECTIVE_DATE_HIGH;
	private String STATUS;
	private Personnel PERFORMER;

	public Procedure(final String TITLE, final String EFFECTIVE_DATE_LOW, final String EFFECTIVE_DATE_HIGH,
	        final String STATUS, final String DISPLAY_TITLE, final Personnel PERFORMER) {
		this.TITLE = TITLE;
		this.EFFECTIVE_DATE_LOW = EFFECTIVE_DATE_LOW;
		this.EFFECTIVE_DATE_HIGH = EFFECTIVE_DATE_HIGH;
		this.STATUS = STATUS;
		this.DISPLAY_TITLE = DISPLAY_TITLE;
		this.PERFORMER = PERFORMER;
	}
	
	public Procedure() {
		
	}

	public String getTITLE() {
		return TITLE;
	}

	public String getEFFECTIVE_DATE_LOW() {
		return EFFECTIVE_DATE_LOW;
	}

	public String getEFFECTIVE_DATE_HIGH() {
		return EFFECTIVE_DATE_HIGH;
	}
	
	public String getSTATUS() {
		return STATUS;
	}

	public Personnel getPERFORMER() {
		return PERFORMER;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public void setEFFECTIVE_DATE_LOW(String eFFECTIVE_DATE_LOW) {
		EFFECTIVE_DATE_LOW = eFFECTIVE_DATE_LOW;
	}

	public void setEFFECTIVE_DATE_HIGH(String eFFECTIVE_DATE_HIGH) {
		EFFECTIVE_DATE_HIGH = eFFECTIVE_DATE_HIGH;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public void setPERFORMER(Personnel pERFORMER) {
		PERFORMER = pERFORMER;
	}

	public String getDISPLAY_TITLE() {
		return DISPLAY_TITLE;
	}

	public void setDISPLAY_TITLE(String dISPLAY_TITLE) {
		DISPLAY_TITLE = dISPLAY_TITLE;
	}
	
}
