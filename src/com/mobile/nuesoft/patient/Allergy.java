package com.mobile.nuesoft.patient;

import java.util.ArrayList;
import java.util.List;

public class Allergy {

	private final String code;
	private final AllergyType allergyType;
	private final String narrativeDisplayName;
	private final String displayName;
	private final String codeSystem;
	private final String codeSystemName;
	private final AllergyReaction reaction;
	private final Severity severity;
	private final long effectiveDateLow;
	private final long effectiveDateHigh;
	private final STATUS currentStatus;
	private ArrayList<Drug> allergicDrugList = new ArrayList<Drug>();

	public enum STATUS {
		ACTIVE, UNACTIVE
	};

	public Allergy(final String displayName, final String narrativeDisplayName, final String code,
	        final String codeSystem, final String codeSystemName, final AllergyReaction reaction,
	        final Severity severity, final long effectiveDateLow, final long effectiveDateHigh,
	        final AllergyType allergyType, final STATUS currentStatus) {
		this.code = code;
		this.displayName = displayName;
		this.narrativeDisplayName = narrativeDisplayName;
		this.effectiveDateLow = effectiveDateLow;
		this.effectiveDateHigh = effectiveDateHigh;
		this.currentStatus = currentStatus;
		this.codeSystem = codeSystem;
		this.codeSystemName = codeSystemName;
		this.reaction = reaction;
		this.severity = severity;
		this.allergyType = allergyType;
	}

	public ArrayList<Drug> getAllergicDrugList() {
		return (ArrayList<Drug>) allergicDrugList.clone();
	}

	public void addAllergicDrug(final Drug d) {
		this.allergicDrugList.add(d);
	}

	public void addAllergicDruglist(final List<Drug> drugList) {
		this.allergicDrugList.addAll(allergicDrugList);
	}

	public String getNarrativeDisplayName() {
		return narrativeDisplayName;
	}

	public String getCodeSystem() {
		return codeSystem;
	}

	public String getCodeSystemName() {
		return codeSystemName;
	}

	public AllergyType getAllergyType() {
		return allergyType;
	}

	public AllergyReaction getReaction() {
		return reaction;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public long getEffectiveDateLow() {
		return effectiveDateLow;
	}

	public long getEffectiveDateHigh() {
		return effectiveDateHigh;
	}

	public STATUS getCurrentStatus() {
		return currentStatus;
	}

	@Override
	public String toString() {
		String val = "";
		
		val += "Name: " + this.getDisplayName() + "\n";
		val += "Code: " + this.getCode() + "\n";
		val += "Allergy Type: " + this.getAllergyType().toString() + "\n";
		val += "Severity: " + this.getSeverity().toString() + "\n";

		for (int i = 0; i < this.getAllergicDrugList().size(); i++) {
			val += "Drug: " + this.getAllergicDrugList().get(i).toString() + "\n";
		}
		
		val += "Reaction: " + this.getReaction().toString() + "\n";
		val += "Status: " + this.getCurrentStatus().name() + "\n";
		
		return val;
	}
}
