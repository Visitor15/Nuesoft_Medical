package com.mobile.nuesoft.patient;

public class Immunization {
	public final String displayName;
	public final String codeSystem;
	public final String effectiveTime;
	public final String statusCode;
	public final String codeSystemName;
	public final String manufacturedClassCode;
	public final String manufacturerName;
	public final String substanceAdministrationMoodCode;
	public final String substanceAdministrationClassCode;
	
	public Immunization(String displayName, String codeSystem, String effectiveTime, String statusCode, String codeSystemName, 
			String manufacturedClassCode, String manufacturerName, String substanceAdministrationMoodCode, String substanceAdministrationClassCode){
		this.displayName = displayName;
		this.effectiveTime = effectiveTime;
		this.statusCode = statusCode;
		this.codeSystem = codeSystem;
		this.codeSystemName = codeSystemName;
		this.manufacturedClassCode = manufacturedClassCode;
		this.manufacturerName = manufacturerName;
		this.substanceAdministrationMoodCode = substanceAdministrationMoodCode;
		this.substanceAdministrationClassCode = substanceAdministrationClassCode;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public String getEffectiveTime(){
		return effectiveTime;
	}
	
	public String getCodeSystem(){
		return codeSystem;
	}
	
	public String getCodeSystemName(){
		return codeSystemName;
	}
	
	public String getManufactureredClassCode(){
		return manufacturedClassCode;
	}
	
	public String getManufacturerName(){
		return manufacturerName;
	}
	
	public String getSubstanceAdministrationMoodCode(){
		return substanceAdministrationMoodCode;
	}
	
	public String getSubstanceAdministrationClassCode(){
		return substanceAdministrationClassCode;
	}
	
}
