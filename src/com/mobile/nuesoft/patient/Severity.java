package com.mobile.nuesoft.patient;

public class Severity {
	private final String code;
	private final String displayName;
	private final String codeSystem;
	private final String codeSystemName;

	public Severity(final String displayName, final String code, final String codeSystem, final String codeSystemName) {
		this.code = code;
		this.displayName = displayName;
		this.codeSystem = codeSystem;
		this.codeSystemName = codeSystemName;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
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