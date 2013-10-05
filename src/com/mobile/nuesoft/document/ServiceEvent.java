package com.mobile.nuesoft.document;

import java.util.ArrayList;
import java.util.List;

public class ServiceEvent {

	public static final String TAG = "ServiceEvent";

	private final String SERVICE_CODE;
	private final String EFFECTIVE_DATE_LOW;
	private final String EFFECTIVE_DATE_HIGH;

	private ArrayList<ServicePerformer> SERVICE_PERFORMERS = new ArrayList<ServicePerformer>();

	public ServiceEvent(final String SERVICE_CODE, final String EFFECTIVE_DATE_LOW, final String EFFECTIVE_DATE_HIGH) {
		this.SERVICE_CODE = SERVICE_CODE;
		this.EFFECTIVE_DATE_LOW = EFFECTIVE_DATE_LOW;
		this.EFFECTIVE_DATE_HIGH = EFFECTIVE_DATE_HIGH;
	}

	public ArrayList<ServicePerformer> getSERVICE_PERFORMERS() {
		return (ArrayList<ServicePerformer>) SERVICE_PERFORMERS.clone();
	}

	public void addSERVICE_PERFORMERS(final ServicePerformer performer) {
		this.SERVICE_PERFORMERS.add(performer);
	}
	
	public void addSERVICE_PERFORMERS_LIST(final List<ServicePerformer> list) {
		this.SERVICE_PERFORMERS.addAll(list);
	}

	public String getEFFECTIVE_DATE_LOW() {
		return EFFECTIVE_DATE_LOW;
	}

	public String getEFFECTIVE_DATE_HIGH() {
		return EFFECTIVE_DATE_HIGH;
	}

	public String getSERVICE_CODE() {
		return SERVICE_CODE;
	}

	@Override
	public String toString() {
		String val = "";
		
		val += "Service performed (" + this.getEFFECTIVE_DATE_LOW() + " - " + this.getEFFECTIVE_DATE_HIGH() + ")\n";
		
		for(int i = 0; i < this.SERVICE_PERFORMERS.size(); i++) {
			val += "By: " + this.SERVICE_PERFORMERS.get(i).getPERFORMER().toString() + "\n";
		}
		
		return val;
	}
}