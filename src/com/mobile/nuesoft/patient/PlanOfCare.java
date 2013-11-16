package com.mobile.nuesoft.patient;

public class PlanOfCare {

	private final String planOfCareItem;
	
	public PlanOfCare(final String planOfCareItem){
		this.planOfCareItem = planOfCareItem;
	}
	
	public String getDisplayName() {
		return planOfCareItem;
	}
}
