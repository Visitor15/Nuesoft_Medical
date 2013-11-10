package com.mobile.nuesoft.patient;

public class FamilyHistory {

	private final String familyHistoryItem;
	
	public FamilyHistory(final String historyItem) {
		this.familyHistoryItem = historyItem;
	}
	
	public String getDisplayName() {
		return this.familyHistoryItem;
	}
}
