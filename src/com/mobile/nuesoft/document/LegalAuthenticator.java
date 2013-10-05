package com.mobile.nuesoft.document;

public class LegalAuthenticator {

	private final String TIME_AUTHENTICATED;
	private final Personnel AUTHENTICATOR;
	private final String SIGNATURE_CODE;
	
	public LegalAuthenticator(final String TIME_AUTHENTICATED, final String SIGNATURE_CODE, final Personnel AUTHENTICATOR) {
		this.TIME_AUTHENTICATED = TIME_AUTHENTICATED;
		this.SIGNATURE_CODE = SIGNATURE_CODE;
		this.AUTHENTICATOR = AUTHENTICATOR;
	}

	public Personnel getAUTHENTICATOR() {
		return AUTHENTICATOR;
	}

	public String getTIME_AUTHENTICATED() {
		return TIME_AUTHENTICATED;
	}

	public String getSIGNATURE_CODE() {
		return SIGNATURE_CODE;
	}
}
