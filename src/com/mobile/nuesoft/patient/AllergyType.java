package com.mobile.nuesoft.patient;

public class AllergyType {
	private final String displayName;
	private final String code;
	private final String codeSystem;
	private final String codeSystemName;

	public AllergyType(final String displayName, final String code, final String codeSystem, final String codeSystemName) {
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

	@Override
	public String toString() {
		return this.getDisplayName();
	}
}