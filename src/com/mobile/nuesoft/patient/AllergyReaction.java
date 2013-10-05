package com.mobile.nuesoft.patient;

public class AllergyReaction {
	private final String displayName;
	private final String code;
	private final String codeSystem;
	private final String codeSystemName;
	private String effectiveDateLow = "";
	private String effectiveDateHigh = "";

	public AllergyReaction(final String displayName, final String code, final String codeSystem,
	        final String codeSystemName) {
		this.displayName = displayName;
		this.code = code;
		this.codeSystem = codeSystem;
		this.codeSystemName = codeSystemName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getCode() {
		return code;
	}

	public String getCodeSystem() {
		return codeSystem;
	}

	public String getCodeSystemName() {
		return codeSystemName;
	}

	public String getEffectiveDateLow() {
		return effectiveDateLow;
	}

	public void setEffectiveDateLow(String effectiveDateLow) {
		this.effectiveDateLow = effectiveDateLow;
	}

	public String getEffectiveDateHigh() {
		return effectiveDateHigh;
	}

	public void setEffectiveDateHigh(String effectiveDatehigh) {
		this.effectiveDateHigh = effectiveDatehigh;
	}

	@Override
	public String toString() {
		return this.getDisplayName();
	}
}