package com.mobile.nuesoft.document;

public class DocRecipient {

	private final String FACILITY_NAME;
	private final Personnel RECIPIENT;

	private String FACILITY_TYPE_CODE;
	private String REASON_FOR_REFERRAL = "";

	public DocRecipient(final Personnel RECIPIENT, final String FACILITY_NAME) {
		this.RECIPIENT = RECIPIENT;
		this.FACILITY_NAME = FACILITY_NAME;
	}

	public String getREASON_FOR_REFERRAL() {
		return REASON_FOR_REFERRAL;
	}

	public void setREASON_FOR_REFERRAL(String rEASON_FOR_REFERRAL) {
		REASON_FOR_REFERRAL = rEASON_FOR_REFERRAL;
	}

	public String getFACILITY_TYPE_CODE() {
		return FACILITY_TYPE_CODE;
	}

	public void setFACILITY_TYPE_CODE(String fACILITY_TYPE_CODE) {
		FACILITY_TYPE_CODE = fACILITY_TYPE_CODE;
	}

	public String getFACILITY_NAME() {
		return FACILITY_NAME;
	}

	public Personnel getRECIPIENT() {
		return RECIPIENT;
	}

	@Override
	public String toString() {
		String val = "";
		
		val += "DOCUMENT RECIPIENT\n";
		val += "RECIPIENT: " + this.getRECIPIENT().toString() + "\n";
		val += "FACILITY: " + this.getFACILITY_NAME() + "\n";
		val += "REASON FOR REFERRAL: " + this.getREASON_FOR_REFERRAL() + "\n";
		
		return val;
	}
}
