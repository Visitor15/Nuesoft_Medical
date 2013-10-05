package com.mobile.nuesoft;

import android.app.Application;

import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class Nuesoft  extends Application  {

	private static Nuesoft singleton;
	
	private static PatientObj currentPatient;

	public static final Nuesoft getReference() {
		return Nuesoft.singleton;
	}
	
	public static PatientObj getCurrentPatient() {
		return Nuesoft.currentPatient;
	}
	
	public void setPatientToCurrent(final PatientObj patient) {
		Nuesoft.currentPatient = patient;
	}

	public Nuesoft() {
		super();
	}
	
	@Override
	  public void onCreate() {
	    Nuesoft.singleton = this;
	    super.onCreate();
	}
}
