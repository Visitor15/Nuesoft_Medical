package com.mobile.nuesoft.patient;

public class Problem {
	private final String actStatusCode;
	private final String effectiveTimeLow;
	private final String actCodeDisplayName;
	private final String entryReferenceId;
	private final String entryStatusCode;
	private final String entryCodeDisplayName;
	private final String entryValueType;
	private final String entryValueDisplayName;
	private final String entryValueCodeSystemName;
	
	public Problem(final String actStatusCode, final String effectiveTimeLow, final String actCodeDisplayName, final String entryReferenceId, 
			final String entryStatusCode, final String entryCodeDisplayName, final String entryValueType, final String entryValueDisplayName, 
			final String entryValueCodeSystemName){
		this.actStatusCode = actStatusCode;
		this.effectiveTimeLow = actStatusCode;
		this.actCodeDisplayName = actStatusCode;
		this.entryReferenceId = entryReferenceId;
		this.entryStatusCode = entryStatusCode;
		this.entryCodeDisplayName = entryCodeDisplayName;
		this.entryValueType = entryValueType;
		this.entryValueDisplayName = entryValueDisplayName;
		this.entryValueCodeSystemName = entryValueCodeSystemName;
	}
}
