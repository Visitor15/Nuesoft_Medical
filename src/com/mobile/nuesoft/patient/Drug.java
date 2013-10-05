package com.mobile.nuesoft.patient;

public class Drug {

	final private String displayName;
	final private String code;
	final private String codeSystem;
	final private String codeSystemName;

	public Drug(final String displayName, final String code, final String codeSystem, final String codeSystemName) {
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
