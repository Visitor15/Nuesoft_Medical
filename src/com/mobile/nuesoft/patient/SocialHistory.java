package com.mobile.nuesoft.patient;

public class SocialHistory {

	private final String observationClassCode;
	private final String observationMoodCode;
	private final String codeCode;
	private final String codeCodeSystem;
	private final String statusCode;
	private final String effectiveDateLow;
	private final String effectiveDateHigh;
	private final String observationValueType;
	private final String observationValueCode;
	private final String observationDisplayName;
	private final String observationCodeSystem;
	private final String observationCodeSystemName;

	public SocialHistory(final String observationClassCode, final String observationMoodCode, final String codeCode,
	        final String codeCodeSystem, final String statusCode, final String effectiveDateLow,
	        final String effectiveDateHigh, final String observationValueType, final String observationValueCode,
	        final String observationDisplayName, final String observationCodeSystem,
	        final String observationCodeSystemName) {
		this.observationClassCode = observationClassCode;
		this.observationMoodCode = observationMoodCode;
		this.codeCode = codeCode;
		this.codeCodeSystem = codeCodeSystem;
		this.statusCode = statusCode;
		this.effectiveDateLow = effectiveDateLow;
		this.effectiveDateHigh = effectiveDateLow;
		this.observationValueType = observationValueType;
		this.observationValueCode = observationValueCode;
		this.observationDisplayName = observationDisplayName;
		this.observationCodeSystem = observationCodeSystem;
		this.observationCodeSystemName = observationCodeSystemName;
	}

	public String getObservationClassCode() {
		return observationClassCode;
	}

	public String getObservationMoodCode() {
		return observationMoodCode;
	}

	public String getCodeCode() {
		return codeCode;
	}

	public String getCodeCodeSystem() {
		return codeCodeSystem;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getEffectiveDateLow() {
		return effectiveDateLow;
	}

	public String getEffectiveDateHigh() {
		return effectiveDateHigh;
	}

	public String getObservationValueType() {
		return observationValueType;
	}

	public String getObservationValueCode() {
		return observationValueCode;
	}

	public String getObservationDisplayName() {
		return observationDisplayName;
	}

	public String getObservationCodeSystem() {
		return observationCodeSystem;
	}

	public String getObservationCodeSystemName() {
		return observationCodeSystemName;
	}

}
