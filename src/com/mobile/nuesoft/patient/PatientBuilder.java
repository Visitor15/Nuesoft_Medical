package com.mobile.nuesoft.patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mobile.nuesoft.document.Encounter;
import com.mobile.nuesoft.patient.IdentifierBuilder.PatientIdentifier;

public class PatientBuilder {
	private PatientIdentifier id;
	private String birthTime;
	private Gender gender;
	private String race;
	private String religion;
	private String ethnicGroup;
	private String reasonForReferral;
	private String reasonForVisit;
	private String instructions;
	private Marital.STATUS maritalStatus;
	private ArrayList<VitalSign> vitalSigns = new ArrayList<VitalSign>();
	private ArrayList<Language> languages = new ArrayList<Language>();
	private ArrayList<Medication> medicationCurrent = new ArrayList<Medication>();
	private ArrayList<Medication> medicationPrevious = new ArrayList<Medication>();
	private ArrayList<Encounter> medicalEncounters = new ArrayList<Encounter>();
	private ArrayList<Allergy> allergies = new ArrayList<Allergy>();
	private ArrayList<FamilyHistory> familyHistory = new ArrayList<FamilyHistory>();
	private ArrayList<PlanOfCare> planOfCare = new ArrayList<PlanOfCare>();
	private ArrayList<Immunization> immunizations = new ArrayList<Immunization>();
	private ArrayList<SocialHistory> socialHistory = new ArrayList<SocialHistory>();
	private ArrayList<PatientTest> tests = new ArrayList<PatientTest>();
	private ArrayList<Procedure> procedures = new ArrayList<Procedure>();
	private ArrayList<Problem> problem = new ArrayList<Problem>();

	public PatientBuilder() {
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(String birthTime) {
		this.birthTime = birthTime;
	}

	public Marital.STATUS getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Marital.STATUS maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public void setInstructions(final String instructions) {
		this.instructions = instructions;
	}
	
	public String getInstructions() {
		return instructions;
	}

	public ArrayList<Allergy> getAllergies() {
		return allergies;
	}

	public void addAllergy(final Allergy allergy) {
		this.allergies.add(allergy);
	}

	public void setAllergies(ArrayList<Allergy> allergies) {
		this.allergies = allergies;
	}
	
	public void addFamilyHistoryItem(final FamilyHistory famHistory) {
		this.familyHistory.add(famHistory);
	}
	
	public void setFamilyHistory(final ArrayList<FamilyHistory> familyHistory) {
		this.familyHistory = familyHistory;
	}
	
	public void addPlanOfCareItem(final PlanOfCare planOfCareItem){
		this.planOfCare.add(planOfCareItem);
	}
	
	public void setPlanOfCare(final ArrayList<PlanOfCare> planOfCare){
		this.planOfCare = planOfCare;
	}
	
	public void setSocialHistory(final ArrayList<SocialHistory> socialHistory){
		this.socialHistory = socialHistory;
	}
	
	public ArrayList<Immunization> getImmunizations(){
		return this.immunizations;
	}
	
	public void addImmunizations(final Immunization immunizations){
		this.immunizations.add(immunizations);
	}
	
	public void setImmunizations(final ArrayList<Immunization> immunizations){
		this.immunizations = immunizations;
	}
	
	public void addProblem(final Problem problem){
		this.problem.add(problem);
	}
	
	public void setProblems(final ArrayList<Problem> problem){
		this.problem = problem;
	}
	
	public ArrayList<FamilyHistory> getFamilyHistory() {
		return this.familyHistory;
	}
	
	public ArrayList<VitalSign> getVitalSigns() {
		return vitalSigns;
	}
	
	public void addVitalSign(final VitalSign vitalSign) {
		this.vitalSigns.add(vitalSign);
	}
	
	public void setVitalSigns(final ArrayList<VitalSign> vitalSigns) {
		this.vitalSigns = vitalSigns;
	}

	public List<Medication> getMedicationCurrent() {
		return medicationCurrent;
	}

	public void setMedicationCurrent(ArrayList<Medication> medicationCurrent) {
		this.medicationCurrent = medicationCurrent;
	}

	public void addMedicationCurrent(final Medication med) {
		this.medicationCurrent.add(med);
	}

	public void addMedicationPrevious(final Medication med) {
		this.medicationPrevious.add(med);
	}

	public List<Medication> getMedicationPrevious() {
		return medicationPrevious;
	}

	public void setMedicationPrevious(ArrayList<Medication> medicationPrevious) {
		this.medicationPrevious = medicationPrevious;
	}

	public List<Encounter> getMedicalEncounters() {
		return medicalEncounters;
	}

	public void setMedicalEncounters(ArrayList<Encounter> medicalEncounters) {
		this.medicalEncounters = medicalEncounters;
	}

	public void addMedicalEncounter(final Encounter encounter) {
		this.medicalEncounters.add(encounter);
	}

	public List<PatientTest> getTests() {
		return tests;
	}

	public void setTests(ArrayList<PatientTest> tests) {
		this.tests = tests;
	}

	public void addTest(final PatientTest t) {
		this.tests.add(t);
	}

	public PatientIdentifier getId() {
		return id;
	}

	public void setId(PatientIdentifier id) {
		this.id = id;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}
	
	public String getReasonForReferral() {
		return reasonForReferral;
	}
	
	public void setReasonForReferral(String reasonForReferral) {
		this.reasonForReferral = reasonForReferral;
	}
	
	public String getReasonForVisit() {
		return reasonForVisit;
	}
	
	public void setReasonForVisit(String reasonForVisit) {
		this.reasonForVisit = reasonForVisit;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(ArrayList<Language> languages) {
		this.languages = languages;
	}
	
	public void addProcedure(final Procedure procedure) {
		this.procedures.add(procedure);
	}

	public PatientObj build() {
		return new PatientObj(id, birthTime, gender, race, reasonForReferral, reasonForVisit, instructions, religion, ethnicGroup, languages, vitalSigns, allergies,
		        medicationCurrent, medicationPrevious, medicalEncounters, tests, maritalStatus, familyHistory, planOfCare, socialHistory, immunizations, procedures, problem);
	}

	public class PatientObj implements Serializable {

		/**
		 *	GENERATED SERIAL ID
		 */
		private static final long serialVersionUID = -5213206329002435084L;

		private final PatientIdentifier IDENTIFIER;
		private final String BIRTH_TIME;
		private final String RACE;
		private final String RELIGION;
		private final String ETHNIC_GROUP;
		private final String REASON_FOR_REFERRAL;
		private final String REASON_FOR_VISIT;
		private final String INSTRUCTIONS;
		private final Gender GENDER;
		private final Marital.STATUS MARITAL;

		private ArrayList<Language> LANGUAGES;
		private ArrayList<VitalSign> VITAL_SIGNS;
		private ArrayList<Allergy> ALLERGIES;
		private ArrayList<Medication> MEDICATION_CURRENT;
		private ArrayList<Medication> MEDICATION_PREVIOUS;
		private ArrayList<Encounter> MEDICAL_ENCOUNTERS;
		private ArrayList<PatientTest> TESTS;
		private ArrayList<FamilyHistory> FAMILY_HISTORY;
		private ArrayList<PlanOfCare> PLAN_OF_CARE;
		private ArrayList<SocialHistory> SOCIAL_HISTORY;
		private ArrayList<Immunization> IMMUNIZATIONS;
		private ArrayList<Procedure> PROCEDURES;
		private ArrayList<Problem> KNOWN_ISSUES;

		private PatientObj(final PatientIdentifier IDENTIFIER, final String BIRTH_TIME, final Gender GENDER,
		        final String RACE, final String REASON_FOR_REFERRAL, final String REASON_FOR_VISIT, final String INSTRUCTIONS, final String RELIGION, final String ETHNIC_GROUP,
		        final ArrayList<Language> LANGUAGES, final ArrayList<VitalSign> VITAL_SIGNS, final ArrayList<Allergy> ALLERGIES,
		        final ArrayList<Medication> MEDICATION_CURRENT, final ArrayList<Medication> MEDICATION_PREVIOUS,
		        final ArrayList<Encounter> MEDICATION_ENCOUNTERS, final ArrayList<PatientTest> TESTS,
		        final Marital.STATUS MARITAL_STATUS, final ArrayList<FamilyHistory> FAMILY_HISTORY, final ArrayList<PlanOfCare> PLAN_OF_CARE, 
		        final ArrayList<SocialHistory> SOCIAL_HISTORY, final ArrayList<Immunization> IMMUNIZATIONS, final ArrayList<Procedure> PROCEDURES, final ArrayList<Problem> KNOWN_ISSUES) {

			this.IDENTIFIER = IDENTIFIER;
			this.BIRTH_TIME = BIRTH_TIME;
			this.GENDER = GENDER;
			this.RACE = RACE;
			this.REASON_FOR_REFERRAL = REASON_FOR_REFERRAL;
			this.REASON_FOR_VISIT = REASON_FOR_VISIT;
			this.INSTRUCTIONS = INSTRUCTIONS;
			this.RELIGION = RELIGION;
			this.ETHNIC_GROUP = ETHNIC_GROUP;
			this.LANGUAGES = LANGUAGES;
			this.VITAL_SIGNS = VITAL_SIGNS;
			this.ALLERGIES = ALLERGIES;
			this.MEDICATION_CURRENT = MEDICATION_CURRENT;
			this.MEDICATION_PREVIOUS = MEDICATION_PREVIOUS;
			this.MEDICAL_ENCOUNTERS = MEDICATION_ENCOUNTERS;
			this.TESTS = TESTS;
			this.MARITAL = MARITAL_STATUS;
			this.FAMILY_HISTORY = FAMILY_HISTORY;
			this.PLAN_OF_CARE = PLAN_OF_CARE;
			this.SOCIAL_HISTORY = SOCIAL_HISTORY;
			this.IMMUNIZATIONS = IMMUNIZATIONS;
			this.PROCEDURES = PROCEDURES;
			this.KNOWN_ISSUES = KNOWN_ISSUES;
		}

		public PatientIdentifier getIDENTIFIER() {
			return IDENTIFIER;
		}

		public String getDisplayName() {
			String val = "";

			val += this.getIDENTIFIER().getLAST_NAME() + ", " + this.getIDENTIFIER().getFIRST_NAME();

			return val;
		}

		public String getBIRTH_TIME() {
			return BIRTH_TIME;
		}
		
		public String getINSTRUCTIONS() {
			return INSTRUCTIONS;
		}
		
		public ArrayList<Immunization> getIMMUNIZATIONS(){
			return (ArrayList<Immunization>) IMMUNIZATIONS.clone();
		}

		public ArrayList<Allergy> getALLERGIES() {
			return (ArrayList<Allergy>) ALLERGIES.clone();
		}

		public void setALLERGIES(ArrayList<Allergy> aLLERGIES) {
			ALLERGIES = aLLERGIES;
		}
		
		public ArrayList<FamilyHistory> getFAMILY_HISTORY() {
			return (ArrayList<FamilyHistory>) FAMILY_HISTORY.clone();
		}
		
		public ArrayList<PlanOfCare> getPLAN_OF_CARE(){
			return (ArrayList<PlanOfCare>) PLAN_OF_CARE.clone();
		}
		
		public ArrayList<SocialHistory> getSOCIAL_HISTORY(){
			return (ArrayList<SocialHistory>) SOCIAL_HISTORY.clone();
		}
		
		public void setVITAL_SIGNS(ArrayList<VitalSign> vITAL_SIGNS) {
			VITAL_SIGNS = vITAL_SIGNS;
		}
		
		public ArrayList<VitalSign> getVITAL_SIGNS() {
			return (ArrayList<VitalSign>) VITAL_SIGNS.clone();
		}

		public ArrayList<Medication> getMEDICATION_CURRENT() {
			return (ArrayList<Medication>) MEDICATION_CURRENT.clone();
		}

		public void setMEDICATION_CURRENT(ArrayList<Medication> mEDICATION_CURRENT) {
			MEDICATION_CURRENT = mEDICATION_CURRENT;
		}

		public ArrayList<Medication> getMEDICATION_PREVIOUS() {
			return (ArrayList<Medication>) MEDICATION_PREVIOUS.clone();
		}

		public void setMEDICATION_PREVIOUS(ArrayList<Medication> mEDICATION_PREVIOUS) {
			MEDICATION_PREVIOUS = mEDICATION_PREVIOUS;
		}

		public ArrayList<Encounter> getMEDICAL_ENCOUNTERS() {
			return (ArrayList<Encounter>) MEDICAL_ENCOUNTERS.clone();
		}

		public void setMEDICAL_ENCOUNTERS(ArrayList<Encounter> mEDICAL_ENCOUNTERS) {
			MEDICAL_ENCOUNTERS = mEDICAL_ENCOUNTERS;
		}

		public ArrayList<PatientTest> getTESTS() {
			return (ArrayList<PatientTest>) TESTS.clone();
		}
		
		public void setPROCEDURES(ArrayList<Procedure> pROCEDURES) {
			PROCEDURES = pROCEDURES;
		}
		
		public ArrayList<Procedure> getPROCEDURES() {
			return (ArrayList<Procedure>) PROCEDURES.clone();
		}
		
		public void setKNOWN_ISSUES(ArrayList<Problem> KNOWN_ISSUES) {
			this.KNOWN_ISSUES = KNOWN_ISSUES;
		}
		
		public ArrayList<Problem> getKNOWN_ISSUES() {
			return this.KNOWN_ISSUES;
		}

		public void setTESTS(ArrayList<PatientTest> tESTS) {
			TESTS = tESTS;
		}

		public Gender getGENDER() {
			return GENDER;
		}

		public Marital.STATUS getMARITAL() {
			return MARITAL;
		}

		public void setLANGUAGES(ArrayList<Language> lANGUAGES) {
			LANGUAGES = lANGUAGES;
		}
		
		public String getREASON_FOR_REFFERAL() {
			return REASON_FOR_REFERRAL;
		}
		
		public String getREASON_FOR_VISIT() {
			return REASON_FOR_VISIT;
		}

		public String getRACE() {
			return RACE;
		}

		public String getRELIGION() {
			return RELIGION;
		}

		public String getETHNIC_GROUP() {
			return ETHNIC_GROUP;
		}

		public ArrayList<Language> getLANGUAGES() {
			return (ArrayList<Language>) LANGUAGES.clone();
		}

		@Override
		public String toString() {
			String results = "";

			results += "Name: " + this.getIDENTIFIER().getFIRST_NAME() + " " + this.getIDENTIFIER().getLAST_NAME()
			        + "\n";
			results += "Gender: " + this.getGENDER().toString() + "\n";
			results += "Marital Status: " + this.getMARITAL().getTitle() + "\n";
			results += "Race: " + this.getRACE() + "\n";
			results += "Religion: " + this.getRELIGION() + "\n";
			results += "Ethnic Group: " + this.getETHNIC_GROUP() + "\n";
			results += "# Langages: " + this.getLANGUAGES().size() + "\n";
			results += "# Allergies: " + this.getALLERGIES().size() + "\n";

			results += "ALLERGIES... \n";

			for (int i = 0; i < this.getALLERGIES().size(); i++) {
				results += i + ". " + this.getALLERGIES().get(i).toString() + "\n";
			}

			results += "# Meds Current: " + this.getMEDICATION_CURRENT().size() + "\n";

			results += "MEDS CURRENT... \n";

			for (int i = 0; i < this.getMEDICATION_CURRENT().size(); i++) {
				results += i + ". " + this.getMEDICATION_CURRENT().get(i).toString() + "\n";
			}

			results += "# Meds Previous: " + this.getMEDICATION_PREVIOUS().size() + "\n";

			results += "MEDS PREVIOUS... \n";

			for (int i = 0; i < this.getMEDICATION_PREVIOUS().size(); i++) {
				results += i + ". " + this.getMEDICATION_PREVIOUS().get(i).toString() + "\n";
			}

			results += "# Medical Events: " + this.getMEDICAL_ENCOUNTERS().size() + "\n";

			return results;
		}
	}
}
