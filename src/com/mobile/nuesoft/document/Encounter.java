package com.mobile.nuesoft.document;

import java.util.ArrayList;

public class Encounter {

	private final String EFFECTIVE_DATE_LOW;
	private final String EFFECTIVE_DATE_HIGH;
	private final EncounterLocation LOCATION;

	private ArrayList<String> REASON_FOR_VISIT = new ArrayList<String>();
	private ArrayList<String> INSTRUCTIONS = new ArrayList<String>();

	public Encounter(final String EFFECTIVE_DATE_LOW, final String EFFECTIVE_DATE_HIGH, final EncounterLocation LOCATION) {
		this.EFFECTIVE_DATE_LOW = EFFECTIVE_DATE_LOW;
		this.EFFECTIVE_DATE_HIGH = EFFECTIVE_DATE_HIGH;
		this.LOCATION = LOCATION;
	}

	public ArrayList<String> getREASON_FOR_VISIT() {
		return REASON_FOR_VISIT;
	}

	public void addREASON_FOR_VISIT(final String reason) {
		this.REASON_FOR_VISIT.add(reason);
	}

	public ArrayList<String> getINSTRUCTIONS() {
		return INSTRUCTIONS;
	}

	public void addINSTRUCTION(final String instruction) {
		this.INSTRUCTIONS.add(instruction);
	}

	public String getEFFECTIVE_DATE_LOW() {
		return EFFECTIVE_DATE_LOW;
	}

	public String getEFFECTIVE_DATE_HIGH() {
		return EFFECTIVE_DATE_HIGH;
	}

	public EncounterLocation getLOCATION() {
		return LOCATION;
	}
}
