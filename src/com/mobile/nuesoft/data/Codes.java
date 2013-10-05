package com.mobile.nuesoft.data;

import java.util.HashMap;

public class Codes {
	
	private static Codes mInstance;
	
	public static final String ALLERGY_KEY = "48765-2";
	public static final String ADVANCED_DIRECTIVES_KEY = "42348-3";
	public static final String REASON_FOR_VISIT_KEY = "29299-5";
	public static final String FAMILY_HISTORY_KEY = "10157-6";
	public static final String FUNCTIONAL_COGNITIVE_STATUS_KEY = "47420-5";
	public static final String IMMUNIZATIONS_KEY = "11369-6";
	public static final String INSTRUCTIONS_KEY = "69730-0";
	public static final String MEDICATIONS_KEY = "10160-0";
	public static final String PLAN_OF_CARE_KEY = "18776-5";
	public static final String PROBLEM_LIST_KEY = "11450-4";
	public static final String PROCEDURES_KEY = "47519-4";
	public static final String REASON_FOR_REFERRAL_KEY = "42349-1";
	public static final String TEST_RESULTS_KEY = "30954-2";
	public static final String SOCIAL_HISTORY_KEY = "29762-2";
	public static final String VITAL_SIGNS_KEY = "8716-3";
	
	public static final int ADVANCED_DIRECTIVES_ID = 0;
	public static final int ALLERGY_ID = 1;
	public static final int REASON_FOR_VISIT_ID = 2;
	public static final int FAMILY_HISTORY_ID = 3;
	public static final int FUNCTIONAL_COGNITIVE_STATUS_ID = 4;
	public static final int IMMUNIZATIONS_ID = 5;
	public static final int INSTRUCTIONS_ID = 6;
	public static final int MEDICATIONS_ID = 7;
	public static final int PLAN_OF_CARE_ID = 8;
	public static final int PROBLEM_LIST_ID = 9;
	public static final int PROCEDURES_ID = 10;
	public static final int REASON_FOR_REFERRAL_ID = 11;
	public static final int TEST_RESULTS_ID = 12;
	public static final int SOCIAL_HISTORY_ID = 13;
	public static final int VITAL_SIGNS_ID = 14;

	public HashMap<String, Integer> codeMap;
	
	private Codes() {
		init();
	}
	
	public static Codes getInstance() {
		if(mInstance == null) {
			mInstance = new Codes();
		}
		
		return mInstance;
	}
	
	private void init() {
		codeMap = new HashMap<String, Integer>();
		
		codeMap.put(ALLERGY_KEY, ALLERGY_ID);
		codeMap.put(ADVANCED_DIRECTIVES_KEY, ADVANCED_DIRECTIVES_ID);
		codeMap.put(REASON_FOR_VISIT_KEY, REASON_FOR_VISIT_ID);
		codeMap.put(FAMILY_HISTORY_KEY, FAMILY_HISTORY_ID);
		codeMap.put(FUNCTIONAL_COGNITIVE_STATUS_KEY, FUNCTIONAL_COGNITIVE_STATUS_ID);
		codeMap.put(IMMUNIZATIONS_KEY, IMMUNIZATIONS_ID);
		codeMap.put(INSTRUCTIONS_KEY, INSTRUCTIONS_ID);
		codeMap.put(MEDICATIONS_KEY, MEDICATIONS_ID);
		codeMap.put(PLAN_OF_CARE_KEY, PLAN_OF_CARE_ID);
		codeMap.put(PROBLEM_LIST_KEY, PROBLEM_LIST_ID);
		codeMap.put(PROCEDURES_KEY, PROCEDURES_ID);
		codeMap.put(REASON_FOR_REFERRAL_KEY, REASON_FOR_REFERRAL_ID);
		codeMap.put(TEST_RESULTS_KEY, TEST_RESULTS_ID);
		codeMap.put(SOCIAL_HISTORY_KEY, SOCIAL_HISTORY_ID);
		codeMap.put(VITAL_SIGNS_KEY, VITAL_SIGNS_ID);
	}
}
