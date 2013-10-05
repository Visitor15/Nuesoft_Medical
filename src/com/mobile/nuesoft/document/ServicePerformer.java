package com.mobile.nuesoft.document;

public class ServicePerformer {

	private final String TAG = "ServicePerformer";

	private final String ID;
	private final Personnel PERFORMER;

	private String CODE = "";
	private String CODE_SYSTEM = "";
	private String CODE_SYSTEM_NAME = "";
	private String DISPLAY_NAME = "";

	private String FUNCTION_CODE = "";

	private String FUNCTION_CODE_SYSTEM = "";
	private String FUNCTION_CODE_SYSTEM_NAME = "";
	private String FUNCTION_CODE_DISPLAY_NAME = "";

	public ServicePerformer(String ID, Personnel PERFORMER) {
		this.ID = ID;
		this.PERFORMER = PERFORMER;
	}

	public String getID() {
		return ID;
	}

	public Personnel getPERFORMER() {
		return PERFORMER;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		this.CODE = cODE;
	}

	public String getCODE_SYSTEM() {
		return CODE_SYSTEM;
	}

	public void setCODE_SYSTEM(String cODE_SYSTEM) {
		this.CODE_SYSTEM = cODE_SYSTEM;
	}

	public String getCODE_SYSTEM_NAME() {
		return CODE_SYSTEM_NAME;
	}

	public void setCODE_SYSTEM_NAME(String cODE_SYSTEM_NAME) {
		this.CODE_SYSTEM_NAME = cODE_SYSTEM_NAME;
	}

	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}

	public void setDISPLAY_NAME(String dISPLAY_NAME) {
		this.DISPLAY_NAME = dISPLAY_NAME;
	}

	public String getFUNCTION_CODE() {
		return FUNCTION_CODE;
	}

	public void setFUNCTION_CODE(String fUNCTION_CODE) {
		this.FUNCTION_CODE = fUNCTION_CODE;
	}

	public String getFUNCTION_CODE_SYSTEM() {
		return FUNCTION_CODE_SYSTEM;
	}

	public void setFUNCTION_CODE_SYSTEM(String fUNCTION_CODE_SYSTEM) {
		this.FUNCTION_CODE_SYSTEM = fUNCTION_CODE_SYSTEM;
	}

	public String getFUNCTION_CODE_SYSTEM_NAME() {
		return FUNCTION_CODE_SYSTEM_NAME;
	}

	public void setFUNCTION_CODE_SYSTEM_NAME(String fUNCTION_CODE_SYSTEM_NAME) {
		this.FUNCTION_CODE_SYSTEM_NAME = fUNCTION_CODE_SYSTEM_NAME;
	}

	public String getFUNCTION_CODE_DISPLAY_NAME() {
		return FUNCTION_CODE_DISPLAY_NAME;
	}

	public void setFUNCTION_CODE_DISPLAY_NAME(String FUNCTION_CODE_DISPLAY_NAME) {
		this.FUNCTION_CODE_DISPLAY_NAME = FUNCTION_CODE_DISPLAY_NAME;
	}

	@Override
	public String toString() {
		String val = "Service performed by:\n";
		val += this.getPERFORMER().toString();

		return val;
	}
}
