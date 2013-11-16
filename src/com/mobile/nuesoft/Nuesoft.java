package com.mobile.nuesoft;

import android.app.Application;

import com.mobile.nuesoft.document.CDADocumentBuilder.CDADocument;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.preferences.NuesoftPreferences;

public class Nuesoft extends Application {

	private static Nuesoft singleton;
	private static NuesoftUser currentUser;
	private static PatientObj currentPatient;
	private static CDADocument currentCDADocument;

	public static final Nuesoft getReference() {
		return Nuesoft.singleton;
	}

	public static PatientObj getCurrentPatient() {
		return Nuesoft.currentPatient;
	}

	public static CDADocument getCurrentCDADocument() {
		return Nuesoft.currentCDADocument;
	}

	public void setCurrentCDADocument(final CDADocument document) {
		Nuesoft.currentCDADocument = document;

		if (document != null) {
			Nuesoft.currentPatient = document.getPATIENT();
		} else {
			Nuesoft.currentPatient = null;
		}
	}

	public static NuesoftUser getCurrentUser() {
		return Nuesoft.currentUser;
	}

	public void setCurrentUser(final NuesoftUser user) {
		Nuesoft.currentUser = user;
	}

	public void saveCurrentUser() {
		NuesoftPreferences.getInstance().updateRegisteredUser(getCurrentUser());
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
