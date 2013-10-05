package com.mobile.nuesoft.patient;

public class Medication {

	private final String TITLE;
	private final String INSTRUCTIONS;
	private final String EFFECTIVE_DATE_LOW;
	private final String EFFECTIVE_DATE_HIGH;
	private final String STATUS;
	private final String MANUFACTURER_CODE;
	private final String MANUFACTURER_CODE_SYSTEM;
	private final String MANUFACTURER_CODE_SYSTEM_NAME;
	private final String DOSAGE_QUANTITY;
	private final String ADMINISTERED_TYPE;
	private final String ADMINISTERED_METHOD;
	private final String ADMINISTRATION_FREQUENCY;

	public Medication(final String TITLE, final String INSTRUCTIONS, final String EFFECTIVE_DATE_LOW,
	        final String EFFECTIVE_DATE_HIGH, final String STATUS, final String MANUFACTURER_CODE,
	        final String MANUFACTURER_CODE_SYSTEM, final String MANUFACTURER_CODE_SYSTEM_NAME,
	        final String ADMINISTERED_TYPE, final String ADMINISTERED_METHOD, final String ADMINISTERED_FREQUENCY, final String DOSAGE_QUANTITY) {
		this.TITLE = TITLE;
		this.INSTRUCTIONS = INSTRUCTIONS;
		this.EFFECTIVE_DATE_LOW = EFFECTIVE_DATE_LOW;
		this.EFFECTIVE_DATE_HIGH = EFFECTIVE_DATE_HIGH;
		this.STATUS = STATUS;
		this.MANUFACTURER_CODE = MANUFACTURER_CODE;
		this.MANUFACTURER_CODE_SYSTEM = MANUFACTURER_CODE_SYSTEM;
		this.MANUFACTURER_CODE_SYSTEM_NAME = MANUFACTURER_CODE_SYSTEM_NAME;
		this.ADMINISTERED_TYPE = ADMINISTERED_TYPE;
		this.ADMINISTERED_METHOD = ADMINISTERED_METHOD;
		this.ADMINISTRATION_FREQUENCY = ADMINISTERED_FREQUENCY;
		this.DOSAGE_QUANTITY = DOSAGE_QUANTITY;
	}

	public String getTITLE() {
		return TITLE;
	}

	public String getINSTRUCTIONS() {
		return INSTRUCTIONS;
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

	public String getMANUFACTURER_CODE() {
		return MANUFACTURER_CODE;
	}

	public String getMANUFACTURER_CODE_SYSTEM() {
		return MANUFACTURER_CODE_SYSTEM;
	}

	public String getMANUFACTURER_CODE_SYSTEM_NAME() {
		return MANUFACTURER_CODE_SYSTEM_NAME;
	}

	public String getADMINISTERED_TYPE() {
		return ADMINISTERED_TYPE;
	}

	public String getADMINISTERED_METHOD() {
		return ADMINISTERED_METHOD;
	}

	public String getADMINISTRATION_FREQUENCY() {
		return ADMINISTRATION_FREQUENCY;
	}

	public String getDOSAGE_QUANTITY() {
		return DOSAGE_QUANTITY;
	}

	@Override
	public String toString() {
		return this.getTITLE();
	}
}
