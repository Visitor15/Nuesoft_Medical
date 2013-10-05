package com.mobile.nuesoft.document;

import java.util.ArrayList;

import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;

public class CDADocumentBuilder {

	public static final String TAG = "CDADocumentBuilder";

	private ArrayList<Author> mAuthor = new ArrayList<Author>();
	private ArrayList<Custodian> mCustodian = new ArrayList<Custodian>();
	private ArrayList<DataEnterer> mDataEnterer = new ArrayList<DataEnterer>();
	private ArrayList<DocRecipient> mRecipient = new ArrayList<DocRecipient>();
	private ArrayList<LegalAuthenticator> mLegalAuthenticator = new ArrayList<LegalAuthenticator>();
	private ArrayList<Participant> mParticipants = new ArrayList<Participant>();
	private PatientObj mPatient;
	private ServiceEvent mServiceEvent;
	private Encounter mEncounter;
	private String mID;
	private String mEffectiveTime;
	private String mDisplayTitle;
	private String mSummaryTitle;
	private String mCode;
	private String mCodeSystem;
	private String mCodeSystemName;

	public CDADocumentBuilder() {

	}

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getmEffectiveTime() {
		return mEffectiveTime;
	}

	public void setmEffectiveTime(String mEffectiveTime) {
		this.mEffectiveTime = mEffectiveTime;
	}

	public String getmDisplayTitle() {
		return mDisplayTitle;
	}

	public void setmDisplayTitle(String mDisplayTitle) {
		this.mDisplayTitle = mDisplayTitle;
	}

	public String getmSummaryTitle() {
		return mSummaryTitle;
	}

	public void setmSummaryTitle(String mSummaryTitle) {
		this.mSummaryTitle = mSummaryTitle;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getmCodeSystem() {
		return mCodeSystem;
	}

	public void setmCodeSystem(String mCodeSystem) {
		this.mCodeSystem = mCodeSystem;
	}

	public String getmCodeSystemName() {
		return mCodeSystemName;
	}

	public void setmCodeSystemName(String mCodeSystemName) {
		this.mCodeSystemName = mCodeSystemName;
	}

	public ArrayList<Author> getmAuthor() {
		return mAuthor;
	}

	public void setmAuthor(ArrayList<Author> mAuthor) {
		this.mAuthor = mAuthor;
	}

	public ArrayList<Custodian> getmCustodian() {
		return mCustodian;
	}

	public void setmCustodian(ArrayList<Custodian> mCustodian) {
		this.mCustodian = mCustodian;
	}

	public ArrayList<DataEnterer> getmDataEnterer() {
		return mDataEnterer;
	}

	public void setmDataEnterer(ArrayList<DataEnterer> mDataEnterer) {
		this.mDataEnterer = mDataEnterer;
	}

	public ArrayList<DocRecipient> getmRecipient() {
		return mRecipient;
	}

	public void setmRecipient(ArrayList<DocRecipient> mRecipient) {
		this.mRecipient = mRecipient;
	}

	public ArrayList<LegalAuthenticator> getmLegalAuthenticator() {
		return mLegalAuthenticator;
	}

	public void setmLegalAuthenticator(ArrayList<LegalAuthenticator> mLegalAuthenticator) {
		this.mLegalAuthenticator = mLegalAuthenticator;
	}

	public ArrayList<Participant> getmParticipants() {
		return mParticipants;
	}

	public void setmParticipants(ArrayList<Participant> mParticipants) {
		this.mParticipants = mParticipants;
	}

	public ServiceEvent getmServiceEvent() {
		return mServiceEvent;
	}

	public void setmServiceEvent(ServiceEvent mServiceEvent) {
		this.mServiceEvent = mServiceEvent;
	}

	public Encounter getmEncounter() {
		return mEncounter;
	}

	public void setmEncounter(Encounter mEncounter) {
		this.mEncounter = mEncounter;
	}

	public PatientObj getmPatient() {
		return mPatient;
	}

	public void setmPatient(PatientObj mPatient) {
		this.mPatient = mPatient;
	}
	
	public CDADocument build() {
		return new CDADocument(this);
	}

	public class CDADocument {

		public static final String TAG = "CDADocument";

		private final ArrayList<Author> AUTHOR;
		private final ArrayList<Custodian> CUSTODIAN;
		private final ArrayList<DataEnterer> DATA_ENTERER;
		private final ArrayList<DocRecipient> RECIPIENT;
		private final ArrayList<LegalAuthenticator> LEGAL_AUTHENTICATOR;
		private final ArrayList<Participant> PARTICIPANTS;
		private final PatientObj PATIENT;
		private final ServiceEvent SERVICE_EVENT;
		private final Encounter ENCOUNTER;
		private final String ID;
		private final String EFFECTIVE_TIME;
		private final String DISPLAY_TITLE;
		private final String SUMMARY_TITLE;
		private final String CODE;
		private final String CODE_SYSTEM;
		private final String CODE_SYSTEM_NAME;

		public CDADocument(final CDADocumentBuilder builder) {
			this.PATIENT = builder.getmPatient();
			this.AUTHOR = builder.getmAuthor();
			this.CUSTODIAN = builder.getmCustodian();
			this.DATA_ENTERER = builder.getmDataEnterer();
			this.RECIPIENT = builder.getmRecipient();
			this.LEGAL_AUTHENTICATOR = builder.getmLegalAuthenticator();
			this.PARTICIPANTS = builder.getmParticipants();
			this.SERVICE_EVENT = builder.getmServiceEvent();
			this.ENCOUNTER = builder.getmEncounter();
			this.ID = builder.getmID();
			this.EFFECTIVE_TIME = builder.getmEffectiveTime();
			this.DISPLAY_TITLE = builder.getmDisplayTitle();
			this.SUMMARY_TITLE = builder.getmSummaryTitle();
			this.CODE = builder.getmCode();
			this.CODE_SYSTEM = builder.getmCodeSystem();
			this.CODE_SYSTEM_NAME = builder.getmCodeSystemName();
		}

		public String getID() {
			return ID;
		}

		public String getEFFECTIVE_TIME() {
			return EFFECTIVE_TIME;
		}

		public String getDISPLAY_TITLE() {
			return DISPLAY_TITLE;
		}

		public String getSUMMARY_TITLE() {
			return SUMMARY_TITLE;
		}

		public String getCODE() {
			return CODE;
		}

		public String getCODE_SYSTEM() {
			return CODE_SYSTEM;
		}

		public String getCODE_SYSTEM_NAME() {
			return CODE_SYSTEM_NAME;
		}

		public PatientObj getPATIENT() {
			return PATIENT;
		}

		public ArrayList<Author> getAUTHOR() {
			return AUTHOR;
		}

		public ArrayList<Custodian> getCUSTODIAN() {
			return CUSTODIAN;
		}

		public ArrayList<DataEnterer> getDATA_ENTERER() {
			return DATA_ENTERER;
		}

		public ArrayList<DocRecipient> getRECIPIENT() {
			return RECIPIENT;
		}

		public ArrayList<LegalAuthenticator> getLEGAL_AUTHENTICATOR() {
			return LEGAL_AUTHENTICATOR;
		}

		public ArrayList<Participant> getPARTICIPANTS() {
			return PARTICIPANTS;
		}

		public ServiceEvent getSERVICE_EVENT() {
			return SERVICE_EVENT;
		}

		public Encounter getENCOUNTER() {
			return ENCOUNTER;
		}

		@Override
		public String toString() {
			String val = "";
			int i = 0;

			val += "CDADocument: " + this.getDISPLAY_TITLE() + "\n";
			val += "PATIENT: " + this.getPATIENT().getDisplayName();
			val += "CREATED: " + this.getEFFECTIVE_TIME() + "\n";
			val += "SUMMARY: " + this.getSUMMARY_TITLE() + "\n";
			val += "ID: " + this.getID() + "\n";

			for (i = 0; i < this.getAUTHOR().size(); i++) {
				val += "AUTHOR: " + this.getAUTHOR().get(i).toString() + "\n";
			}

			for (i = 0; i < this.getDATA_ENTERER().size(); i++) {
				val += "DATA ENTERER: " + this.getDATA_ENTERER().get(i).toString() + "\n";
			}

			for (i = 0; i < this.getRECIPIENT().size(); i++) {
				val += "RECIPIENT: " + this.getRECIPIENT().get(i).toString() + "\n";
			}

			for (i = 0; i < this.getLEGAL_AUTHENTICATOR().size(); i++) {
				val += "LEGAL AUTHENTICATOR: " + this.getLEGAL_AUTHENTICATOR().get(i).toString() + "\n";
			}

			for (i = 0; i < this.getCUSTODIAN().size(); i++) {
				val += "CUSTODIAN: " + this.getCUSTODIAN().get(i).toString() + "\n";
			}

			for (i = 0; i < this.getPARTICIPANTS().size(); i++) {
				val += "PARTICIPANTS: " + this.getPARTICIPANTS().get(i).toString() + "\n";
			}

			val += "SERVICE EVENT: " + this.getSERVICE_EVENT().toString() + "\n";

			return val;
		}
	}
}
