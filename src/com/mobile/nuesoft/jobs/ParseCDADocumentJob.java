package com.mobile.nuesoft.jobs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.data.Codes;
import com.mobile.nuesoft.document.Author;
import com.mobile.nuesoft.document.CDADocumentBuilder;
import com.mobile.nuesoft.document.CDADocumentBuilder.CDADocument;
import com.mobile.nuesoft.document.Custodian;
import com.mobile.nuesoft.document.DataEnterer;
import com.mobile.nuesoft.document.DocRecipient;
import com.mobile.nuesoft.document.Encounter;
import com.mobile.nuesoft.document.LegalAuthenticator;
import com.mobile.nuesoft.document.Organization;
import com.mobile.nuesoft.document.Participant;
import com.mobile.nuesoft.document.Personnel;
import com.mobile.nuesoft.document.ServiceEvent;
import com.mobile.nuesoft.document.ServicePerformer;
import com.mobile.nuesoft.patient.Address;
import com.mobile.nuesoft.patient.Allergy;
import com.mobile.nuesoft.patient.Allergy.STATUS;
import com.mobile.nuesoft.patient.AllergyReaction;
import com.mobile.nuesoft.patient.AllergyType;
import com.mobile.nuesoft.patient.Drug;
import com.mobile.nuesoft.patient.FamilyHistory;
import com.mobile.nuesoft.patient.Gender;
import com.mobile.nuesoft.patient.IdentifierBuilder;
import com.mobile.nuesoft.patient.Immunization;
import com.mobile.nuesoft.patient.Language;
import com.mobile.nuesoft.patient.Marital;
import com.mobile.nuesoft.patient.Medication;
import com.mobile.nuesoft.patient.PatientBuilder;
import com.mobile.nuesoft.patient.PatientBuilder.PatientObj;
import com.mobile.nuesoft.patient.PlanOfCare;
import com.mobile.nuesoft.patient.Procedure;
import com.mobile.nuesoft.patient.Problem;
import com.mobile.nuesoft.patient.Severity;
import com.mobile.nuesoft.patient.SocialHistory;
import com.mobile.nuesoft.patient.Telephone;
import com.mobile.nuesoft.patient.VitalSign;
import com.mobile.nuesoft.util.XMLParserUtil;

public class ParseCDADocumentJob extends AsyncTask<String, PatientObj, CDADocument> {
	public static final String TAG = "ParseCDADocumentJob";
	public static final String IS_FINISHED_KEY = "com.mobile.nuesoft.job.FINISHED_KEY";

	private Bundle updateBundle;

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private File docFile;

	private ArrayList<Node> componentNodeList;

	private CDADocumentBuilder docBuilder;
	private CDADocument mDocument;
	private PatientBuilder patBuilder;
	private PatientObj patientObj;
	private IdentifierBuilder patIdBuilder;

	public ParseCDADocumentJob() {
	}

	@Override
	protected void onPostExecute(CDADocument result) {
		super.onPostExecute(result);

		if (result != null) {
			updateBundle = new Bundle();
			updateBundle.putBoolean(ParseCDADocumentJob.IS_FINISHED_KEY, true);
			updateBundle.putSerializable(PatientUpdateEvent.PATIENT_OBJ_KEY, result.getPATIENT());
			updateBundle.putSerializable(CDADocumentUpdateEvent.CDA_DOC_OBJ_KEY, result);

			Nuesoft.getReference().setCurrentCDADocument(result);

			CDADocumentUpdateEvent.broadcast(Nuesoft.getReference(), updateBundle);
			PatientUpdateEvent.broadcast(Nuesoft.getReference(), updateBundle);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		componentNodeList = new ArrayList<Node>();
		patIdBuilder = new IdentifierBuilder();
		patBuilder = new PatientBuilder();
		docBuilder = new CDADocumentBuilder();
	}

	@Override
	protected void onProgressUpdate(PatientObj... values) {
		super.onProgressUpdate(values);
		PatientObj updatedPatient = values[0];

		updateBundle = new Bundle();
		updateBundle.putBoolean(ParseCDADocumentJob.IS_FINISHED_KEY, false);
		updateBundle.putSerializable(PatientUpdateEvent.PATIENT_OBJ_KEY, updatedPatient);

		// Nuesoft.getReference().setPatientToCurrent(updatedPatient);

		PatientUpdateEvent.broadcast(Nuesoft.getReference(), updateBundle);
	}

	@Override
	protected CDADocument doInBackground(String... docPath) {
		try {
			docFile = new File(docPath[0]);

			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(docFile);

			NodeList rootList = doc.getElementsByTagName("ClinicalDocument");
			Node root = XMLParserUtil.getNode("ClinicalDocument", rootList);

			if (docFile.exists()) {
				patientObj = parseForPatientObj(root);
				mDocument = parseForCDADocument(root, patientObj);
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return mDocument;
	}

	private CDADocument parseForCDADocument(final Node node, final PatientObj patient) {
		ArrayList<Author> mAuthors = null;
		ArrayList<Custodian> mCustodians = null;
		ArrayList<DataEnterer> mDataEnterers = null;
		ArrayList<DocRecipient> mRecipients = null;
		ArrayList<LegalAuthenticator> mLegalAuthenticators = null;
		ArrayList<Participant> mParticipants = null;
		ServiceEvent mServiceEvent = null;
		String mID = "";
		String mDisplayTitle = "";
		String mSummaryTitle = "";
		String mCode = "";
		String mCodeSystem = "";
		String mCodeSystemName = "";

		Node dataNode;
		ArrayList<Node> nodeList;

		mID = XMLParserUtil.getNodeAttr("extension", XMLParserUtil.getNode("id", node.getChildNodes()));

		dataNode = XMLParserUtil.getNode("code", node.getChildNodes());
		mCode = XMLParserUtil.getNodeAttr("code", dataNode);
		mCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", dataNode);
		mCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", dataNode);
		mSummaryTitle = XMLParserUtil.getNodeAttr("displayName", dataNode);

		mDisplayTitle = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("title", node.getChildNodes()));

		dataNode = XMLParserUtil.getNode("author", node.getChildNodes());
		nodeList = XMLParserUtil.getNamedNodes("author", node);
		mAuthors = parseForAuthor(nodeList);

		dataNode = XMLParserUtil.getNode("dataEnterer", node.getChildNodes());
		nodeList = XMLParserUtil.getNamedNodes("dataEnterer", node);
		mDataEnterers = parseForDataEnterer(nodeList);

		dataNode = XMLParserUtil.getNode("custodian", node.getChildNodes());
		nodeList = XMLParserUtil.getNamedNodes("custodian", node);
		mCustodians = parseForCustodian(nodeList);

		dataNode = XMLParserUtil.getNode("informationRecipient", node.getChildNodes());
		nodeList = XMLParserUtil.getNamedNodes("informationRecipient", node);
		mRecipients = parseForRecipient(nodeList);

		dataNode = XMLParserUtil.getNode("legalAuthenticator", node.getChildNodes());
		nodeList = XMLParserUtil.getNamedNodes("legalAuthenticator", node);
		mLegalAuthenticators = parseForLegalAuthenticator(nodeList);

		dataNode = XMLParserUtil.getNode("legalAuthenticator", node.getChildNodes());
		nodeList = XMLParserUtil.getNamedNodes("participant", node);
		mParticipants = parseForParticipants(nodeList);

		dataNode = XMLParserUtil.getNode("documentationOf", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("serviceEvent", dataNode.getChildNodes());
		mServiceEvent = parseForServiceEvent(dataNode);

		docBuilder.setDocUri(Uri.fromFile(docFile));
		docBuilder.setmPatient(patient);
		docBuilder.setmAuthor(mAuthors);
		docBuilder.setmCode(mCode);
		docBuilder.setmCodeSystem(mCodeSystem);
		docBuilder.setmCodeSystemName(mCodeSystemName);
		docBuilder.setmCustodian(mCustodians);
		docBuilder.setmDataEnterer(mDataEnterers);
		docBuilder.setmRecipient(mRecipients);
		docBuilder.setmLegalAuthenticator(mLegalAuthenticators);
		docBuilder.setmParticipants(mParticipants);
		docBuilder.setmServiceEvent(mServiceEvent);
		docBuilder.setmDisplayTitle(mDisplayTitle);
		docBuilder.setmSummaryTitle(mSummaryTitle);
		docBuilder.setmID(mID);

		return docBuilder.build();
	}

	private ServiceEvent parseForServiceEvent(final Node root) {
		ArrayList<Node> performerNodes;
		ArrayList<ServicePerformer> servicePerformers = new ArrayList<ServicePerformer>();

		ServicePerformer servicePerformer;
		ServiceEvent serviceEvent;

		String serviceEventCode = "";
		String effectiveTimeLow = "";
		String effectiveTimeHigh = "";
		String performerTypeCode = "";
		String performerId = "";
		String providerTypeCode = "";
		String providerTypeCodeSystem = "";
		String providerTypeCodeSystemName = "";
		String providerDisplayName = "";
		String code = "";
		String codeSystem = "";
		String codeSystemName = "";
		String assignedEntityId = "";
		String assignedEntityCode = "";
		String assignedEntityCodeSystem = "";
		String assignedEntityCodeSystemName = "";
		String functionCode = "";
		String functionDisplayName = "";
		String functionCodeSystem = "";
		String functionCodeSystemName = "";

		Address address = null;
		Telephone telephone = null;
		Personnel performer = null;

		serviceEventCode = XMLParserUtil.getNodeAttr("classCode", root);
		Node dataNode = XMLParserUtil.getNode("effectiveTime", root.getChildNodes());

		int i = 0;
		for (i = 0; i < dataNode.getChildNodes().getLength(); i++) {
			Node n = dataNode.getChildNodes().item(i);

			if (n.getNodeName().equals("low")) {
				effectiveTimeLow = XMLParserUtil.getNodeAttr("value", n);
			} else if (n.getNodeName().equals("high")) {
				effectiveTimeHigh = XMLParserUtil.getNodeAttr("value", n);
			}
		}

		performerNodes = XMLParserUtil.getNamedNodes("performer", root);
		for (i = 0; i < performerNodes.size(); i++) {
			dataNode = performerNodes.get(i);

			performerTypeCode = XMLParserUtil.getNodeAttr("typeCode", dataNode);

			Node functionNode = XMLParserUtil.getNode("functionCode", dataNode.getChildNodes());
			if (functionNode != null) {
				functionCode = XMLParserUtil.getNodeAttr("code", functionNode);
				functionCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", functionNode);
				functionCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", functionNode);
				functionDisplayName = XMLParserUtil.getNodeAttr("displayName", functionNode);
			}

			dataNode = XMLParserUtil.getNode("assignedEntity", dataNode.getChildNodes());
			performerId = XMLParserUtil.getNodeAttr("extension", XMLParserUtil.getNode("id", dataNode.getChildNodes()));

			dataNode = XMLParserUtil.getNode("code", dataNode.getChildNodes());
			providerTypeCode = XMLParserUtil.getNodeAttr("code", dataNode);
			providerTypeCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", dataNode);
			providerTypeCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", dataNode);
			providerDisplayName = XMLParserUtil.getNodeAttr("displayName", dataNode);

			dataNode = performerNodes.get(i);
			dataNode = XMLParserUtil.getNode("assignedEntity", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("addr", dataNode.getChildNodes());
			address = getAddressFromNode(dataNode);

			dataNode = performerNodes.get(i);
			dataNode = XMLParserUtil.getNode("assignedEntity", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("telecom", dataNode.getChildNodes());
			telephone = getTelephoneFromNode(dataNode);

			dataNode = performerNodes.get(i);
			dataNode = XMLParserUtil.getNode("assignedEntity", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("assignedPerson", dataNode.getChildNodes());
			performer = getPersonnelFromNode(dataNode);

			performer.setPERSONNEL_ID(performerId);
			performer.setPERSONNEL_ADDRESS(address);
			performer.setTELEPHONE(telephone);
			performer.setCODE_SYSTEM(providerTypeCode);
			performer.setCODE_SYSTEM(providerTypeCodeSystem);
			performer.setCODE_SYSTEM_NAME(providerTypeCodeSystemName);

			servicePerformer = new ServicePerformer(performerTypeCode, performer);
			servicePerformer.setCODE(providerTypeCode);
			servicePerformer.setCODE_SYSTEM(providerTypeCodeSystem);
			servicePerformer.setCODE_SYSTEM_NAME(providerTypeCodeSystemName);

			if (functionCode.trim().length() > 0) {
				servicePerformer.setFUNCTION_CODE(functionCode);
				servicePerformer.setFUNCTION_CODE_SYSTEM(functionCodeSystem);
				servicePerformer.setFUNCTION_CODE_SYSTEM_NAME(functionCodeSystemName);
				servicePerformer.setFUNCTION_CODE_DISPLAY_NAME(functionDisplayName);
			}

			servicePerformers.add(servicePerformer);
		}

		serviceEvent = new ServiceEvent(serviceEventCode, effectiveTimeLow, effectiveTimeHigh);
		serviceEvent.addSERVICE_PERFORMERS_LIST(servicePerformers);

		return new ServiceEvent(serviceEventCode, effectiveTimeLow, effectiveTimeHigh);
	}

	private ArrayList<Participant> parseForParticipants(final ArrayList<Node> participantNodeList) {
		ArrayList<Participant> participants = new ArrayList<Participant>();

		String role = "";
		String type = "";
		Address address = null;
		Telephone telephone = null;
		Personnel participant = null;

		Node dataNode;
		for (int i = 0; i < participantNodeList.size(); i++) {
			dataNode = participantNodeList.get(i);
			role = XMLParserUtil.getNodeAttr("typeCode", dataNode);

			dataNode = XMLParserUtil.getNode("associatedEntity", dataNode.getChildNodes());
			type = XMLParserUtil.getNodeAttr("classCode", dataNode);
			address = getAddressFromNode(XMLParserUtil.getNode("addr", dataNode.getChildNodes()));
			telephone = getTelephoneFromNode(XMLParserUtil.getNode("telecom", dataNode.getChildNodes()));
			participant = getPersonnelFromNode(XMLParserUtil.getNode("associatedPerson", dataNode.getChildNodes()));

			participant.setPERSONNEL_ADDRESS(address);
			participant.setTELEPHONE(telephone);

			participants.add(new Participant(type, role, participant));
		}

		return participants;
	}

	private ArrayList<LegalAuthenticator> parseForLegalAuthenticator(final ArrayList<Node> nodeList) {
		ArrayList<LegalAuthenticator> authenticators = new ArrayList<LegalAuthenticator>();

		Personnel authenticator = null;
		Organization organization = null;
		Address address = null;
		Telephone telephone = null;

		String authenticationTime = "";
		String signatureCode = "";
		String authenticatorId = "";
		String authenticatorCodeSystem = "";
		String orgCode = "";
		String orgFacilityDisplayName = "";
		String orgCodeSystem = "";
		String orgCodeSystemName = "";

		Node root;
		Node dataNode;
		for (int i = 0; i < nodeList.size(); i++) {
			root = nodeList.get(i);

			authenticationTime = XMLParserUtil
			        .getNodeAttr("value", XMLParserUtil.getNode("time", root.getChildNodes()));
			signatureCode = XMLParserUtil.getNodeAttr("code",
			        XMLParserUtil.getNode("signatureCode", root.getChildNodes()));

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			authenticatorId = XMLParserUtil.getNodeAttr("extension",
			        XMLParserUtil.getNode("id", dataNode.getChildNodes()));
			authenticatorCodeSystem = XMLParserUtil.getNodeAttr("root",
			        XMLParserUtil.getNode("id", dataNode.getChildNodes()));

			dataNode = XMLParserUtil.getNode("code", dataNode.getChildNodes());
			orgCode = XMLParserUtil.getNodeAttr("code", dataNode);
			orgCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", dataNode);
			orgFacilityDisplayName = XMLParserUtil.getNodeAttr("displayName", dataNode);
			orgCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", dataNode);

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("addr", dataNode.getChildNodes());
			address = getAddressFromNode(dataNode);

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("telecom", dataNode.getChildNodes());

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("assignedPerson", dataNode.getChildNodes());
			authenticator = getPersonnelFromNode(dataNode);

			organization = new Organization(orgFacilityDisplayName, address, telephone);
			organization.setCODE(orgCode);
			organization.setCODE_SYSTEM(orgCodeSystem);
			organization.setCODE_SYSTEM_NAME(orgCodeSystemName);

			authenticator.setPERSONNEL_ID(authenticatorId);
			authenticator.setPERSONNEL_DEPARTMENT(orgFacilityDisplayName);
			authenticator.setCODE_SYSTEM(authenticatorCodeSystem);
			authenticator.setPERSONNEL_ADDRESS(address);
			authenticator.setPERSONNEL_ORGANIZATION(organization);

			authenticators.add(new LegalAuthenticator(authenticationTime, signatureCode, authenticator));
		}

		return authenticators;
	}

	private ArrayList<DocRecipient> parseForRecipient(final ArrayList<Node> nodeList) {
		ArrayList<DocRecipient> recipients = new ArrayList<DocRecipient>();

		Personnel recipient = null;
		Telephone telephone = null;
		Address address = null;
		Organization organization = null;

		String recipientId = "";
		String recipientCodeSystem = "";
		String orgId = "";
		String orgCode = "";
		String orgDisplayName = "";
		String orgFacilityName = "";
		String orgCodeSystem = "";
		String orgCodeSystemName = "";

		Node root;
		Node dataNode;
		for (int i = 0; i < nodeList.size(); i++) {
			root = nodeList.get(i);

			dataNode = XMLParserUtil.getNode("intendedRecipient", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("id", dataNode.getChildNodes());
			recipientId = XMLParserUtil.getNodeAttr("extension", dataNode);
			recipientCodeSystem = XMLParserUtil.getNodeAttr("root", dataNode);

			dataNode = XMLParserUtil.getNode("intendedRecipient", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("informationRecipient", dataNode.getChildNodes());
			recipient = getPersonnelFromNode(dataNode);

			dataNode = XMLParserUtil.getNode("intendedRecipient", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("receivedOrganization", dataNode.getChildNodes());
			orgDisplayName = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("name", dataNode.getChildNodes()));
			telephone = getTelephoneFromNode(XMLParserUtil.getNode("telecom", dataNode.getChildNodes()));
			address = getAddressFromNode(XMLParserUtil.getNode("addr", dataNode.getChildNodes()));

			dataNode = XMLParserUtil.getNode("standardIndustryClassCode", dataNode.getChildNodes());
			orgCode = XMLParserUtil.getNodeAttr("code", dataNode);
			orgId = orgCode;
			orgFacilityName = XMLParserUtil.getNodeAttr("displayName", dataNode);
			orgCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", dataNode);
			orgCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", dataNode);

			organization = new Organization(orgDisplayName, address, telephone);
			organization.setID(orgId);
			organization.setCODE(orgCode);
			organization.setCODE_SYSTEM(orgCodeSystem);
			organization.setCODE_SYSTEM_NAME(orgCodeSystemName);

			recipient.setPERSONNEL_ID(recipientId);
			recipient.setCODE_SYSTEM(recipientCodeSystem);
			recipient.setPERSONNEL_ADDRESS(address);
			recipient.setPERSONNEL_DEPARTMENT(orgFacilityName);
			recipient.setPERSONNEL_ORGANIZATION(organization);

			recipients.add(new DocRecipient(recipient, orgFacilityName));
		}

		return recipients;
	}

	private ArrayList<Custodian> parseForCustodian(final ArrayList<Node> nodeList) {
		ArrayList<Custodian> custodians = new ArrayList<Custodian>();

		Organization organization = null;
		Telephone telephone = null;
		Address address = null;

		String orgId = "";
		String orgCode = "";
		String orgDisplayName = "";
		String codeSystem = "";
		String codeSystemName = "";

		Node root;
		Node dataNode;
		for (int i = 0; i < nodeList.size(); i++) {
			root = nodeList.get(i);
			dataNode = XMLParserUtil.getNode("assignedCustodian", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("representedCustodianOrganization", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("id", dataNode.getChildNodes());
			orgId = XMLParserUtil.getNodeAttr("root", dataNode);
			orgCode = XMLParserUtil.getNodeAttr("root", dataNode);

			dataNode = XMLParserUtil.getNode("assignedCustodian", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("representedCustodianOrganization", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("name", dataNode.getChildNodes());
			orgDisplayName = XMLParserUtil.getNodeValue(dataNode);

			dataNode = XMLParserUtil.getNode("assignedCustodian", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("representedCustodianOrganization", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("telecom", dataNode.getChildNodes());
			telephone = getTelephoneFromNode(dataNode);

			dataNode = XMLParserUtil.getNode("assignedCustodian", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("representedCustodianOrganization", dataNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("addr", dataNode.getChildNodes());
			address = getAddressFromNode(dataNode);

			organization = new Organization(orgDisplayName, address, telephone);
			organization.setCODE(orgCode);
			organization.setID(orgId);
			organization.setCODE_SYSTEM(codeSystem);
			organization.setCODE_SYSTEM_NAME(codeSystemName);

			custodians.add(new Custodian(organization));
		}

		return custodians;
	}

	private ArrayList<DataEnterer> parseForDataEnterer(final ArrayList<Node> nodeList) {
		ArrayList<DataEnterer> dataEnterers = new ArrayList<DataEnterer>();

		Personnel person = null;
		Organization organization = null;
		Address address = null;
		Telephone telephone = null;

		String entererId = "";
		String orgId = "";
		String orgCode = "";
		String orgDisplayName = "";
		String codeSystem = "";
		String codeSystemName = "";

		Node root;
		Node dataNode;
		for (int i = 0; i < nodeList.size(); i++) {
			root = nodeList.get(i);
			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("id", dataNode.getChildNodes());
			entererId = XMLParserUtil.getNodeAttr("extension", dataNode);
			orgId = XMLParserUtil.getNodeAttr("root", dataNode);

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("code", dataNode.getChildNodes());
			orgCode = XMLParserUtil.getNodeAttr("code", dataNode);
			codeSystem = XMLParserUtil.getNodeAttr("codeSystem", dataNode);
			codeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", dataNode);
			orgDisplayName = XMLParserUtil.getNodeAttr("displayName", dataNode);

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("addr", dataNode.getChildNodes());
			address = getAddressFromNode(dataNode);

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("telecom", dataNode.getChildNodes());
			telephone = getTelephoneFromNode(dataNode);

			dataNode = XMLParserUtil.getNode("assignedEntity", root.getChildNodes());
			dataNode = XMLParserUtil.getNode("assignedPerson", dataNode.getChildNodes());
			person = getPersonnelFromNode(dataNode);

			person.setCODE_SYSTEM(codeSystem);
			person.setCODE_SYSTEM_NAME(codeSystemName);
			person.setPERSONNEL_ADDRESS(address);
			person.setPERSONNEL_ID(entererId);

			organization = new Organization(orgDisplayName, address, telephone);
			organization.setCODE_SYSTEM(codeSystem);
			organization.setCODE_SYSTEM_NAME(codeSystemName);
			organization.setCODE(orgCode);
			organization.setID(orgId);

			dataEnterers.add(new DataEnterer(person, organization));
		}
		return dataEnterers;
	}

	private ArrayList<Author> parseForAuthor(final ArrayList<Node> nodeList) {
		ArrayList<Author> authorList = new ArrayList<Author>();

		Address mAuthorAddress = null;
		Telephone mAuthorTelephone = null;
		Personnel person = null;
		Organization organization = null;

		String authorID = "";
		String codeSystem = "";
		String codeSystemName = "";
		String timeAuthored = "";
		String orgCode = "";
		String orgDepartment = "";

		Node rootNode;
		Node dataNode;
		for (int i = 0; i < nodeList.size(); i++) {
			rootNode = nodeList.get(i);
			dataNode = XMLParserUtil.getNode("time", rootNode.getChildNodes());
			timeAuthored = XMLParserUtil.getNodeAttr("value", dataNode);

			dataNode = XMLParserUtil.getNode("assignedAuthor", rootNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("id", dataNode.getChildNodes());
			authorID = XMLParserUtil.getNodeAttr("extension", dataNode);

			dataNode = XMLParserUtil.getNode("assignedAuthor", rootNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("code", dataNode.getChildNodes());
			orgCode = XMLParserUtil.getNodeAttr("code", dataNode);
			orgDepartment = XMLParserUtil.getNodeAttr("displayName", dataNode);
			codeSystem = XMLParserUtil.getNodeAttr("codeSystem", dataNode);
			codeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", dataNode);

			dataNode = XMLParserUtil.getNode("assignedAuthor", rootNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("addr", dataNode.getChildNodes());
			mAuthorAddress = getAddressFromNode(dataNode);

			dataNode = XMLParserUtil.getNode("assignedAuthor", rootNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("telecom", dataNode.getChildNodes());
			mAuthorTelephone = this.getTelephoneFromNode(dataNode);

			organization = new Organization(orgDepartment, mAuthorAddress, mAuthorTelephone);
			organization.setCODE(orgCode);
			organization.setCODE_SYSTEM(codeSystem);
			organization.setCODE_SYSTEM_NAME(codeSystemName);

			dataNode = XMLParserUtil.getNode("assignedAuthor", rootNode.getChildNodes());
			dataNode = XMLParserUtil.getNode("assignedPerson", dataNode.getChildNodes());
			person = getPersonnelFromNode(dataNode);

			person.setPERSONNEL_ADDRESS(mAuthorAddress);
			person.setPERSONNEL_ID(authorID);
			person.setPERSONNEL_DEPARTMENT(orgDepartment);

			authorList.add(new Author(person, organization, timeAuthored));
		}
		return authorList;
	}

	private Personnel getPersonnelFromNode(final Node root) {
		String givenName = "";
		String familyName = "";
		String suffix = "";

		Node dataNode = XMLParserUtil.getNode("name", root.getChildNodes());

		givenName = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("given", dataNode.getChildNodes()));
		familyName = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("family", dataNode.getChildNodes()));

		Node suffixNode = XMLParserUtil.getNode("suffix", dataNode.getChildNodes());
		if (suffixNode != null) {
			suffix = XMLParserUtil.getNodeValue(suffixNode);
		} else {
			suffix = "N/A";
		}

		return new Personnel(givenName, familyName, suffix);
	}

	private PatientObj parseForPatientObj(final Node node) throws SAXException, IOException,
	        ParserConfigurationException {
		Node root = node;
		Node record = XMLParserUtil.getNode("recordTarget", root.getChildNodes());
		root = XMLParserUtil.getCDADocumentBodySection(root);
		componentNodeList = XMLParserUtil.getComponentNodesFromBody(root);

		parsePatientGeneralInfo(record, patBuilder);

		for (Node n : componentNodeList) {
			Node sectionNode = XMLParserUtil.getNode("section", n.getChildNodes());
			Node code = XMLParserUtil.getNode("code", sectionNode.getChildNodes());

			int id = Codes.getInstance().codeMap.get(XMLParserUtil.getNodeAttr("code", code));
			switch (id) {
				case Codes.ADVANCED_DIRECTIVES_ID: {
					parsePatientAdvancedDirectivesFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.ALLERGY_ID: {
					parsePatientAllergiesFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.FAMILY_HISTORY_ID: {
					parsePatientFamilyHistoryFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.FUNCTIONAL_COGNITIVE_STATUS_ID: {
					parsePatientFunctionalCognitiveStatusFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.IMMUNIZATIONS_ID: {
					parsePatientImmunizationsFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.INSTRUCTIONS_ID: {
					parsePatientInstructionsFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.MEDICATIONS_ID: {
					parsePatientMedicationsFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.PLAN_OF_CARE_ID: {
					parsePatientPlanOfCareFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.PROBLEM_LIST_ID: {
					parsePatientProblemsFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.PROCEDURES_ID: {
					parsePatientProceduresFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.REASON_FOR_REFERRAL_ID: {
					parsePatientReasonForReferralFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.REASON_FOR_VISIT_ID: {
					parsePatientReasonForVisitFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.SOCIAL_HISTORY_ID: {
					parsePatientSocialHistoryFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.TEST_RESULTS_ID: {
					parsePatientTestResultsFromNode(sectionNode, patBuilder);
					break;
				}
				case Codes.VITAL_SIGNS_ID: {
					parsePatientVitalSignsFromNode(sectionNode, patBuilder);
					break;
				}
				default: {
				}
			}
		}

		return patBuilder.build();
	}

	private void parsePatientVitalSignsFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		Node dataNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		dataNode = XMLParserUtil.getNode("list", dataNode.getChildNodes());

		String val;
		Node tempNode;
		for (int i = 0; i < dataNode.getChildNodes().getLength(); i++) {
			tempNode = dataNode.getChildNodes().item(i);
			if (tempNode.getNodeName().equalsIgnoreCase("item")) {
				tempNode = XMLParserUtil.getNode("content", tempNode.getChildNodes());
				val = XMLParserUtil.getNodeValue(tempNode);
				VitalSign vital = new VitalSign(val);
				patBuilder.addVitalSign(vital);
			}
		}
	}

	private void parsePatientTestResultsFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		// TODO Auto-generated method stub

	}

	private void parsePatientSocialHistoryFromNode(final Node sectionNode, final PatientBuilder patientBuilder) {
		/*
		 * Notes: code 8517006 = former smoker
		 */

		String templateCode = "";
		String templateDisplayName = "";

		String observationClassCode = "";
		String observationMoodCode = "";
		String codeCode = "";
		String codeCodeSystem = "";
		String statusCode = "";
		String effectiveDateLow = "";
		String effectiveDateHigh = "";
		String observationValueType = "";
		String observationValueCode = "";
		String observationDisplayName = "";
		String observationCodeSystem = "";
		String observationCodeSystemName = "";

		ArrayList<SocialHistory> socialHistoryList = new ArrayList<SocialHistory>();
		ArrayList<Node> itemList;
		ArrayList<Node> entryList;

		Node dataNode = XMLParserUtil.getNode("code", sectionNode.getChildNodes());
		templateCode = XMLParserUtil.getNodeAttr("code", dataNode);
		templateDisplayName = XMLParserUtil.getNodeAttr("displayName", dataNode);

		entryList = XMLParserUtil.getNamedNodes("entry", sectionNode);

		Node content;
		Node tempNode;

		for (int i = 0; i < entryList.size(); i++) {
			SocialHistory socialHistory = null;
			observationClassCode = "";
			observationMoodCode = "";
			codeCode = "";
			codeCodeSystem = "";
			statusCode = "";
			effectiveDateLow = "";
			effectiveDateHigh = "";
			observationValueType = "";
			observationValueCode = "";
			observationDisplayName = "";
			observationCodeSystem = "";
			observationCodeSystemName = "";

			content = entryList.get(i);
			content = XMLParserUtil.getNode("observation", content.getChildNodes());

			tempNode = XMLParserUtil.getNode("code", content.getChildNodes());
			codeCode = XMLParserUtil.getNodeAttr("code", tempNode);
			codeCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempNode);

			tempNode = XMLParserUtil.getNode("statusCode", content.getChildNodes());
			statusCode = XMLParserUtil.getNodeAttr("code", tempNode);

			tempNode = XMLParserUtil.getNode("effectiveTime", content.getChildNodes());
			tempNode = XMLParserUtil.getNode("low", tempNode.getChildNodes());
			effectiveDateLow = XMLParserUtil.getNodeAttr("value", tempNode);

			tempNode = XMLParserUtil.getNode("effectiveTime", content.getChildNodes());
			tempNode = XMLParserUtil.getNode("high", tempNode.getChildNodes());
			effectiveDateHigh = XMLParserUtil.getNodeAttr("value", tempNode);

			tempNode = XMLParserUtil.getNode("value", content.getChildNodes());
			observationValueType = XMLParserUtil.getNodeAttr("xsi:type", tempNode);
			observationValueCode = XMLParserUtil.getNodeAttr("code", tempNode);
			observationDisplayName = XMLParserUtil.getNodeAttr("displayName", tempNode);
			observationCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempNode);
			observationCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempNode);

			socialHistory = new SocialHistory(observationClassCode, observationMoodCode, codeCode, codeCodeSystem,
			        statusCode, effectiveDateLow, effectiveDateHigh, observationValueType, observationValueCode,
			        observationDisplayName, observationCodeSystem, observationCodeSystemName);

			socialHistoryList.add(socialHistory);
		}

		patientBuilder.setSocialHistory(socialHistoryList);

	}

	private void parsePatientReasonForVisitFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		Node dataNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		dataNode = XMLParserUtil.getNode("paragraph", dataNode.getChildNodes());
		String val = XMLParserUtil.getNodeValue(dataNode);

		patBuilder.setReasonForVisit(val);
	}

	private void parsePatientReasonForReferralFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		Node dataNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		dataNode = XMLParserUtil.getNode("paragraph", dataNode.getChildNodes());
		String val = XMLParserUtil.getNodeValue(dataNode);

		patBuilder.setReasonForReferral(val);
	}

	private void parsePatientProceduresFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		ArrayList<Procedure> procedureList = new ArrayList<Procedure>();

		Node tempNode;
		tempNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		tempNode = XMLParserUtil.getNode("list", tempNode.getChildNodes());

		int i = 0;
		Node dataNode;
		for (i = 0; i < tempNode.getChildNodes().getLength(); i++) {
			dataNode = tempNode.getChildNodes().item(i);
			if (dataNode.getNodeName().equalsIgnoreCase("item")) {
				dataNode = XMLParserUtil.getNode("content", dataNode.getChildNodes());
				Procedure mProcedure = new Procedure();
				mProcedure.setDISPLAY_TITLE(XMLParserUtil.getNodeValue(dataNode));
				mProcedure.setSTATUS(XMLParserUtil.getNodeAttr("ID", dataNode));
				procedureList.add(mProcedure);
			}
		}

		for (i = 0; i < sectionNode.getChildNodes().getLength(); i++) {
			if (sectionNode.getChildNodes().item(i).getNodeName().equalsIgnoreCase("entry")) {
				dataNode = sectionNode.getChildNodes().item(i);
				dataNode = XMLParserUtil.getNode("procedure", dataNode.getChildNodes());
				tempNode = XMLParserUtil.getNode("code", dataNode.getChildNodes());

				dataNode = XMLParserUtil.getNode("originalText", tempNode.getChildNodes());
				String mID = XMLParserUtil.getNodeAttr("value",
				        XMLParserUtil.getNode("reference", dataNode.getChildNodes()));
				mID = mID.substring(1);

				for (int p = 0; p < procedureList.size(); p++) {
					Procedure curProcedure = procedureList.get(p);
					if (curProcedure.getSTATUS().equalsIgnoreCase(mID)) {
						dataNode = sectionNode.getChildNodes().item(i);
						dataNode = XMLParserUtil.getNode("procedure", dataNode.getChildNodes());
						tempNode = XMLParserUtil.getNode("statusCode", dataNode.getChildNodes());

						String mStatusCode = XMLParserUtil.getNodeAttr("code", tempNode);
						curProcedure.setSTATUS(mStatusCode);

						String effectiveDateLow = "";
						String effectiveDateHigh = "";

						tempNode = XMLParserUtil.getNode("effectiveTime", dataNode.getChildNodes());
						tempNode = XMLParserUtil.getNode("low", tempNode.getChildNodes());

						if (tempNode != null) {
							effectiveDateLow = XMLParserUtil.getNodeAttr("value", tempNode);
						}
						tempNode = XMLParserUtil.getNode("effectiveTime", dataNode.getChildNodes());
						tempNode = XMLParserUtil.getNode("high", tempNode.getChildNodes());
						if (tempNode != null) {
							effectiveDateHigh = XMLParserUtil.getNodeAttr("value", tempNode);
						}

						tempNode = XMLParserUtil.getNode("effectiveTime", dataNode.getChildNodes());
						tempNode = XMLParserUtil.getNode("center", tempNode.getChildNodes());
						if (tempNode != null) {
							effectiveDateLow = XMLParserUtil.getNodeAttr("value", tempNode);
						}

						curProcedure.setEFFECTIVE_DATE_LOW(effectiveDateLow);
						curProcedure.setEFFECTIVE_DATE_HIGH(effectiveDateHigh);
						tempNode = XMLParserUtil.getNode("performer", dataNode.getChildNodes());

						if (tempNode != null) {
							tempNode = XMLParserUtil.getNode("assignedEntity", tempNode.getChildNodes());

							dataNode = XMLParserUtil.getNode("assignedPerson", tempNode.getChildNodes());
							dataNode = XMLParserUtil.getNode("name", dataNode.getChildNodes());

							String mGiveName = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("given",
							        dataNode.getChildNodes()));
							String mFamilyName = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("family",
							        dataNode.getChildNodes()));
							String mSuffixName = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("suffix",
							        dataNode.getChildNodes()));

							Personnel mPerformer = new Personnel(mGiveName, mFamilyName, mSuffixName);

							dataNode = XMLParserUtil.getNode("representedOrganization", tempNode.getChildNodes());

							String mDisplayTitle = XMLParserUtil.getNodeValue(XMLParserUtil.getNode("name",
							        dataNode.getChildNodes()));
							Telephone mTelephone = getTelephoneFromNode(XMLParserUtil.getNode("telecom",
							        dataNode.getChildNodes()));
							Address mAddress = getAddressFromNode(XMLParserUtil.getNode("addr",
							        dataNode.getChildNodes()));
							Organization mRepresentedOrg = new Organization(mDisplayTitle, mAddress, mTelephone);

							mPerformer.setPERSONNEL_ORGANIZATION(mRepresentedOrg);

							dataNode = sectionNode.getChildNodes().item(i);
							dataNode = XMLParserUtil.getNode("procedure", dataNode.getChildNodes());
							tempNode = XMLParserUtil.getNode("statusCode", dataNode.getChildNodes());

							String personnelType = XMLParserUtil.getNodeAttr("displayName", tempNode);
							mPerformer.setPERSONNEL_TYPE(personnelType);

							curProcedure.setPERFORMER(mPerformer);
						}

						patBuilder.addProcedure(curProcedure);
					}
				}
			}
		}
	}

	private void parsePatientProblemsFromNode(final Node sectionNode, final PatientBuilder patientBuilder) {
		String actStatusCode = "";
		String effectiveTimeLow = "";
		String actCodeDisplayName = "";
		String entryReferenceId = "";
		String entryStatusCode = "";
		String entryCodeDisplayName = "";
		String entryValueType = "";
		String entryValueDisplayName = "";
		String entryValueCodeSystemName = "";

		Problem problem = null;

		ArrayList<Node> entryList = XMLParserUtil.getNamedNodes("entry", sectionNode);

		Node rootNode;
		Node tempNode;
		Node effectiveTimeNode;

		for (int i = 0; i < entryList.size(); i++) {
			actStatusCode = "";
			effectiveTimeLow = "";
			actCodeDisplayName = "";

			entryReferenceId = "";

			entryStatusCode = "";
			entryCodeDisplayName = "";
			entryValueType = "";
			entryValueDisplayName = "";
			entryValueCodeSystemName = "";

			rootNode = XMLParserUtil.getNode("act", entryList.get(i).getChildNodes());

			tempNode = XMLParserUtil.getNode("code", rootNode.getChildNodes());
			actCodeDisplayName = XMLParserUtil.getNodeAttr("displayName", tempNode);

			tempNode = XMLParserUtil.getNode("statusCode", rootNode.getChildNodes());
			actStatusCode = XMLParserUtil.getNodeAttr("code", tempNode);

			tempNode = XMLParserUtil.getNode("effectiveTime", rootNode.getChildNodes());
			effectiveTimeNode = XMLParserUtil.getNode("low", tempNode.getChildNodes());
			effectiveTimeLow = XMLParserUtil.getNodeAttr("value", effectiveTimeNode);

			tempNode = XMLParserUtil.getNode("entryRelationship", rootNode.getChildNodes());
			rootNode = XMLParserUtil.getNode("observation", tempNode.getChildNodes());

			tempNode = XMLParserUtil.getNode("code", rootNode.getChildNodes());
			entryStatusCode = XMLParserUtil.getNodeAttr("code", tempNode);
			entryCodeDisplayName = XMLParserUtil.getNodeAttr("code", tempNode);

			tempNode = XMLParserUtil.getNode("value", rootNode.getChildNodes());
			entryValueType = XMLParserUtil.getNodeAttr("xsi:type", tempNode);
			entryValueDisplayName = XMLParserUtil.getNodeAttr("displayName", tempNode);
			entryValueCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempNode);

			tempNode = XMLParserUtil.getNode("text", rootNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("reference", tempNode.getChildNodes());
			entryReferenceId = XMLParserUtil.getNodeAttr("value", tempNode);

			problem = new Problem(actStatusCode, effectiveTimeLow, actCodeDisplayName, entryReferenceId,
			        entryStatusCode, entryCodeDisplayName, entryValueType, entryValueDisplayName,
			        entryValueCodeSystemName);
			patientBuilder.addProblem(problem);
		}
	}

	private void parsePatientPlanOfCareFromNode(final Node sectionNode, final PatientBuilder patientBuilder) {
		String planOfCareItem = "";
		PlanOfCare planOfCare;

		Node content = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		Node rootNode;

		for (int i = 0; i < content.getChildNodes().getLength(); i++) {
			rootNode = content.getChildNodes().item(i);

			if (rootNode.getNodeName().equalsIgnoreCase("paragraph")) {
				planOfCareItem = XMLParserUtil.getNodeValue(rootNode);
				planOfCare = new PlanOfCare(planOfCareItem);
				patientBuilder.addPlanOfCareItem(planOfCare);
			}
		}
	}

	private void parsePatientInstructionsFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		String instructions = "";

		Node dataNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		dataNode = XMLParserUtil.getNode("paragraph", dataNode.getChildNodes());

		instructions = XMLParserUtil.getNodeValue(dataNode);

		patBuilder.setInstructions(instructions);
	}

	private void parsePatientImmunizationsFromNode(final Node sectionNode, final PatientBuilder patientBuilder) {
		Immunization mImmunization = null;
		ArrayList<Immunization> mImmunizationList = new ArrayList<Immunization>();

		String mDisplayName = "";
		String mCodeSystem = "";
		String mCodeSystemName = "";
		String mEffectiveTime = "";
		String mStatusCode = "";
		String mManufacturedClassCode = "";
		String mManufacturerName = "";
		String mSubstanceAdministrationMoodCode = "";
		String mSubstanceAdministrationClassCode = "";

		ArrayList<Node> itemList;
		ArrayList<Node> entryList;

		Node dataNode = XMLParserUtil.getNode("code", sectionNode.getChildNodes());
		String code = XMLParserUtil.getNodeAttr("code", dataNode);

		ArrayList<String> medicationNarrativeName = new ArrayList<String>();
		dataNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());
		dataNode = XMLParserUtil.getNode("list", dataNode.getChildNodes());
		itemList = XMLParserUtil.getNamedNodes("item", dataNode);

		Node tempNode;
		for (Node n : itemList) {
			tempNode = XMLParserUtil.getNode("content", n.getChildNodes());
			medicationNarrativeName.add(XMLParserUtil.getNodeValue(tempNode));
		}

		entryList = XMLParserUtil.getNamedNodes("entry", sectionNode);
		Node mRootNode;

		for (int i = 0; i < entryList.size(); i++) {
			mDisplayName = "";
			mCodeSystem = "";
			mCodeSystemName = "";
			mEffectiveTime = "";
			mStatusCode = "";
			mManufacturedClassCode = "";
			mManufacturerName = "";
			mSubstanceAdministrationMoodCode = "";
			mSubstanceAdministrationClassCode = "";

			mRootNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());
			mSubstanceAdministrationMoodCode = XMLParserUtil.getNodeAttr("moodCode", mRootNode);
			mSubstanceAdministrationClassCode = XMLParserUtil.getNodeAttr("classCode", mRootNode);

			tempNode = XMLParserUtil.getNode("statusCode", mRootNode.getChildNodes());
			mStatusCode = XMLParserUtil.getNodeAttr("code", tempNode);

			tempNode = XMLParserUtil.getNode("effectiveTime", mRootNode.getChildNodes());
			mEffectiveTime = XMLParserUtil.getNodeAttr("value", tempNode);

			tempNode = XMLParserUtil.getNode("consumable", mRootNode.getChildNodes());
			mManufacturedClassCode = XMLParserUtil.getNodeAttr("classCode", tempNode);

			mRootNode = XMLParserUtil.getNode("manufacturedProduct", tempNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("manufacturedMaterial", mRootNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("code", tempNode.getChildNodes());
			mDisplayName = XMLParserUtil.getNodeAttr("displayName", tempNode);
			mCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempNode);
			mCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempNode);

			tempNode = XMLParserUtil.getNode("manufacturerOrganization", mRootNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("name", tempNode.getChildNodes());

			mManufacturerName = XMLParserUtil.getNodeValue(tempNode);

			mImmunization = new Immunization(mDisplayName, mCodeSystem, mEffectiveTime, mStatusCode, mCodeSystemName,
			        mManufacturedClassCode, mManufacturerName, mSubstanceAdministrationMoodCode,
			        mSubstanceAdministrationClassCode);
			patientBuilder.addImmunizations(mImmunization);
		}
	}

	private void parsePatientFunctionalCognitiveStatusFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		// TODO Auto-generated method stub

	}

	private void parsePatientFamilyHistoryFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		Node dataNode = XMLParserUtil.getNode("text", sectionNode.getChildNodes());

		String val;
		Node tempNode;
		for (int i = 0; i < dataNode.getChildNodes().getLength(); i++) {
			tempNode = dataNode.getChildNodes().item(i);

			if (tempNode.getNodeName().equalsIgnoreCase("paragraph")) {
				val = XMLParserUtil.getNodeValue(tempNode);
				FamilyHistory famHistory = new FamilyHistory(val);
				patBuilder.addFamilyHistoryItem(famHistory);
			}
		}
	}

	private void parsePatientAdvancedDirectivesFromNode(final Node sectionNode, final PatientBuilder patBuilder) {
		// TODO Auto-generated method stub

	}

	private void parsePatientAllergiesFromNode(final Node root, final PatientBuilder patient) {
		ArrayList<Drug> allergyDrugList = new ArrayList<Drug>();
		ArrayList<String> allergyNames = new ArrayList<String>();
		Drug allergyDrug;
		String displayName = "";
		Allergy mAllergy = null;
		Node codeNode = XMLParserUtil.getNode("code", root.getChildNodes());
		String code = XMLParserUtil.getNodeAttr("code", codeNode);
		if (code.equals(Codes.ALLERGY_KEY)) {

			Node allergyTextNode = XMLParserUtil.getNode("text", root.getChildNodes());
			if (allergyTextNode != null) {
				Node allergyListNode = XMLParserUtil.getNode("list", allergyTextNode.getChildNodes());
				if (allergyListNode != null) {
					if (allergyListNode.hasChildNodes()) {
						ArrayList<Node> itemList = XMLParserUtil.getNamedNodes("item", allergyListNode);

						Node content;
						for (int j = 0; j < itemList.size(); j++) {
							Node item = itemList.get(j);
							if (item.getNodeName().equals("item")) {
								content = XMLParserUtil.getNode("content", item.getChildNodes());
								displayName = XMLParserUtil.getNodeValue(content);
								allergyNames.add(displayName);
							}
						}

						ArrayList<Node> entryList = XMLParserUtil.getNamedNodes("entry", root);
						Node observationNode;
						Node tempRootNode;
						for (int q = 0; q < entryList.size(); q++) {
							content = entryList.get(q);
							content = XMLParserUtil.getNode("act", content.getChildNodes());
							tempRootNode = XMLParserUtil.getNode("statusCode", content.getChildNodes());

							tempRootNode = XMLParserUtil.getNode("effectiveTime", content.getChildNodes());
							tempRootNode = XMLParserUtil.getNode("low", tempRootNode.getChildNodes());

							tempRootNode = XMLParserUtil.getNode("code", content.getChildNodes());
							String allergyCode = XMLParserUtil.getNodeAttr("code", tempRootNode);
							String allergyCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempRootNode);
							String allergyCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempRootNode);
							content = XMLParserUtil.getNode("entryRelationship", content.getChildNodes());
							observationNode = XMLParserUtil.getNode("observation", content.getChildNodes());

							String allergyTypeDisplayName = XMLParserUtil.getNodeAttr("displayName",
							        XMLParserUtil.getNode("value", observationNode.getChildNodes()));

							String allergyTypeCode = XMLParserUtil.getNodeAttr("code",
							        XMLParserUtil.getNode("value", observationNode.getChildNodes()));

							String allergyTypeCodeSystem = XMLParserUtil.getNodeAttr("codeSystem",
							        XMLParserUtil.getNode("value", observationNode.getChildNodes()));

							String allergyTypeCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName",
							        XMLParserUtil.getNode("value", observationNode.getChildNodes()));

							AllergyType allergyType = new AllergyType(allergyTypeDisplayName, allergyTypeCode,
							        allergyTypeCodeSystem, allergyTypeCodeSystemName);

							ArrayList<Node> participantList = XMLParserUtil.getNamedNodes("participant",
							        observationNode);

							for (int k = 0; k < participantList.size(); k++) {
								tempRootNode = XMLParserUtil.getNode("participantRole", participantList.get(k)
								        .getChildNodes());
								tempRootNode = XMLParserUtil.getNode("playingEntity", tempRootNode.getChildNodes());

								String drugCode;
								String drugDisplayName;
								String drugCodeSystem;
								String drugCodeSystemName;

								drugCode = XMLParserUtil.getNodeAttr("code", "code", tempRootNode.getChildNodes());
								drugDisplayName = XMLParserUtil.getNodeAttr("code", "displayName",
								        tempRootNode.getChildNodes());
								drugCodeSystem = XMLParserUtil.getNodeAttr("code", "codeSystem",
								        tempRootNode.getChildNodes());
								drugCodeSystemName = XMLParserUtil.getNodeAttr("code", "codeSystemName",
								        tempRootNode.getChildNodes());

								allergyDrug = new Drug(drugDisplayName, drugCode, drugCodeSystem, drugCodeSystemName);
								allergyDrugList.add(allergyDrug);
							}

							content = XMLParserUtil.getNode("entryRelationship", observationNode.getChildNodes());
							content = XMLParserUtil.getNode("observation", content.getChildNodes());

							String drugReactionEffectiveDateLow = "";
							String drugReactionEffectiveDateHigh = "";
							String drugReactionDisplayName = "";
							String drugReactionCode = "";
							String drugReactionCodeSystem = "";
							String drugReactionCodeSystemName = "";

							tempRootNode = XMLParserUtil.getNode("value", content.getChildNodes());
							drugReactionDisplayName = XMLParserUtil.getNodeAttr("displayName", tempRootNode);
							drugReactionCode = XMLParserUtil.getNodeAttr("code", tempRootNode);
							drugReactionCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempRootNode);
							drugReactionCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempRootNode);

							tempRootNode = XMLParserUtil.getNode("effectiveTime", content.getChildNodes());
							drugReactionEffectiveDateLow = XMLParserUtil.getNodeAttr("low", "value",
							        tempRootNode.getChildNodes());

							AllergyReaction reaction = new AllergyReaction(drugReactionDisplayName, drugReactionCode,
							        drugReactionCodeSystem, drugReactionCodeSystemName);

							if (drugReactionEffectiveDateLow.trim().length() > 0) {
								reaction.setEffectiveDateLow(drugReactionEffectiveDateLow);
							}
							if (drugReactionEffectiveDateHigh.trim().length() > 0) {
								reaction.setEffectiveDateHigh(drugReactionEffectiveDateHigh);
							}

							String reactionSeverityDisplayName;
							String reactionSeverityCode;
							String reactionSeverityCodeSystem;
							String reactionSeverityCodeSystemName;

							tempRootNode = XMLParserUtil.getNode("entryRelationship", content.getChildNodes());
							tempRootNode = XMLParserUtil.getNode("observation", tempRootNode.getChildNodes());
							tempRootNode = XMLParserUtil.getNode("value", tempRootNode.getChildNodes());

							reactionSeverityDisplayName = XMLParserUtil.getNodeAttr("displayName", tempRootNode);
							reactionSeverityCode = XMLParserUtil.getNodeAttr("code", tempRootNode);
							reactionSeverityCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempRootNode);
							reactionSeverityCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempRootNode);

							Severity allergySeverity = new Severity(reactionSeverityDisplayName, reactionSeverityCode,
							        reactionSeverityCodeSystem, reactionSeverityCodeSystemName);

							displayName = allergyNames.get(q);

							mAllergy = new Allergy(displayName, displayName, allergyCode, allergyCodeSystem,
							        allergyCodeSystemName, reaction, allergySeverity, 0L, 0L, allergyType,
							        STATUS.ACTIVE);
							mAllergy.addAllergicDruglist(allergyDrugList);
							patient.addAllergy(mAllergy);
						}
					}
				}
			}
		}
	}

	private void parsePatientMedicationsFromNode(final Node root, final PatientBuilder patient) {
		String mTitle = "";
		String mInstructions = "";
		String mDateLow = "";
		String mDateHigh = "";
		String mStatus = "";
		String mManufacturerCode = "";
		String mManufacturerCodeSystem = "";
		String mManufacturerCodeSystemName = "";
		String mAdministeredType = "";
		String mAdministeredMethod = "";
		String mAdministeredFreq = "";
		String mDosageQuantity = "";

		ArrayList<Node> itemList;
		ArrayList<Node> entryList;

		Node dataNode = XMLParserUtil.getNode("code", root.getChildNodes());
		String code = XMLParserUtil.getNodeAttr("code", dataNode);

		ArrayList<String> medicationNarrativeName = new ArrayList<String>();
		dataNode = XMLParserUtil.getNode("text", root.getChildNodes());
		dataNode = XMLParserUtil.getNode("list", dataNode.getChildNodes());
		itemList = XMLParserUtil.getNamedNodes("item", dataNode);

		Node tempNode;
		for (Node n : itemList) {
			tempNode = XMLParserUtil.getNode("content", n.getChildNodes());
			medicationNarrativeName.add(XMLParserUtil.getNodeValue(tempNode));
		}

		entryList = XMLParserUtil.getNamedNodes("entry", root);

		for (int i = 0; i < entryList.size(); i++) {
			mTitle = "";
			mInstructions = "";
			mDateLow = "";
			mDateHigh = "";
			mStatus = "";
			mManufacturerCode = "";
			mManufacturerCodeSystem = "";
			mManufacturerCodeSystemName = "";
			mAdministeredType = "";
			mAdministeredMethod = "";
			mAdministeredFreq = "";
			mDosageQuantity = "";

			tempNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());

			tempNode = XMLParserUtil.getNode("statusCode", tempNode.getChildNodes());
			mStatus = XMLParserUtil.getNodeAttr("code", tempNode);

			tempNode = entryList.get(i);
			tempNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());

			ArrayList<Node> effectiveTimeNodes = XMLParserUtil.getNamedNodes("effectiveTime", tempNode);

			for (int j = 0; j < effectiveTimeNodes.size(); j++) {
				Node dateNode = effectiveTimeNodes.get(j);

				for (int k = 0; k < dateNode.getChildNodes().getLength(); k++) {
					tempNode = dateNode.getChildNodes().item(k);
					if (tempNode.getNodeName().equals("low")) {
						mDateLow = XMLParserUtil.getNodeAttr("value", tempNode);
					} else if (tempNode.getNodeName().equals("high")) {
						mDateHigh = XMLParserUtil.getNodeAttr("value", tempNode);
					} else if (tempNode.getNodeName().equals("period")) {
						mAdministeredFreq = XMLParserUtil.getNodeAttr("value", tempNode);
						mAdministeredFreq += XMLParserUtil.getNodeAttr("unit", tempNode);
					}
				}
			}

			tempNode = entryList.get(i);
			tempNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());
			tempNode = XMLParserUtil.getNode("routeCode", tempNode.getChildNodes());
			mAdministeredMethod = XMLParserUtil.getNodeAttr("displayName", tempNode);

			tempNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());
			tempNode = XMLParserUtil.getNode("doseQuantity", tempNode.getChildNodes());
			mDosageQuantity = XMLParserUtil.getNodeAttr("value", tempNode);

			tempNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());
			tempNode = XMLParserUtil.getNode("administrationUnitCode", tempNode.getChildNodes());
			mAdministeredType = XMLParserUtil.getNodeAttr("displayName", tempNode);

			tempNode = XMLParserUtil.getNode("substanceAdministration", entryList.get(i).getChildNodes());
			tempNode = XMLParserUtil.getNode("consumable", tempNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("manufacturedProduct", tempNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("manufacturedMaterial", tempNode.getChildNodes());
			tempNode = XMLParserUtil.getNode("code", tempNode.getChildNodes());
			mManufacturerCode = XMLParserUtil.getNodeAttr("code", tempNode);
			mManufacturerCodeSystem = XMLParserUtil.getNodeAttr("codeSystem", tempNode);
			mManufacturerCodeSystemName = XMLParserUtil.getNodeAttr("codeSystemName", tempNode);
			mTitle = XMLParserUtil.getNodeAttr("displayName", tempNode);

			mInstructions = medicationNarrativeName.get(i);

			if (mDateHigh.trim().length() > 0) {
				patient.addMedicationPrevious(new Medication(mTitle, mInstructions, mDateLow, mDateHigh, mStatus,
				        mManufacturerCode, mManufacturerCodeSystem, mManufacturerCodeSystemName, mAdministeredType,
				        mAdministeredMethod, mAdministeredFreq, mDosageQuantity));
			} else {
				patient.addMedicationCurrent(new Medication(mTitle, mInstructions, mDateLow, mDateHigh, mStatus,
				        mManufacturerCode, mManufacturerCodeSystem, mManufacturerCodeSystemName, mAdministeredType,
				        mAdministeredMethod, mAdministeredFreq, mDosageQuantity));
			}
		}
	}

	private void parsePatientGeneralInfo(final Node root, final PatientBuilder patient) {
		Node node = XMLParserUtil.getNode("patientRole", root.getChildNodes());
		Node dataNode = null;
		String data = "";

		patIdBuilder = new IdentifierBuilder();

		dataNode = XMLParserUtil.getNode("id", node.getChildNodes());
		data = XMLParserUtil.getNodeAttr("extension", dataNode);
		patIdBuilder.setSsn(data);

		dataNode = XMLParserUtil.getNode("addr", node.getChildNodes());
		patIdBuilder.setAddress(getAddressFromNode(dataNode));

		dataNode = XMLParserUtil.getNode("telecom", node.getChildNodes());
		patIdBuilder.setTel(getTelephoneFromNode(dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("name", dataNode.getChildNodes());
		data = XMLParserUtil.getNodeValue("given", dataNode.getChildNodes());

		patIdBuilder.setFirstName(data);
		data = XMLParserUtil.getNodeValue("family", dataNode.getChildNodes());

		patIdBuilder.setLastName(data);
		patIdBuilder.setEmail("N/A");

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("administrativeGenderCode", dataNode.getChildNodes());
		patient.setGender(getGenderFromNote(dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("birthTime", dataNode.getChildNodes());
		patient.setBirthTime(XMLParserUtil.getNodeAttr("value", dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("maritalStatusCode", dataNode.getChildNodes());
		patient.setMaritalStatus(getMaritalStatus(dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("religiousAffiliationCode", dataNode.getChildNodes());
		patient.setReligion(XMLParserUtil.getNodeAttr("displayName", dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("raceCode", dataNode.getChildNodes());
		patient.setRace(XMLParserUtil.getNodeAttr("displayName", dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("ethnicGroupCode", dataNode.getChildNodes());
		patient.setEthnicGroup(XMLParserUtil.getNodeAttr("displayName", dataNode));

		dataNode = XMLParserUtil.getNode("patient", node.getChildNodes());
		dataNode = XMLParserUtil.getNode("languageCommunication", dataNode.getChildNodes());
		patient.setLanguages(getLanguagesFromNode(dataNode));

		patient.setId(patIdBuilder.build());
	}

	private ArrayList<Language> getLanguagesFromNode(final Node node) {
		ArrayList<Language> languages = new ArrayList<Language>();

		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node lang = node.getChildNodes().item(i);
			if (lang.getNodeName().equals("languageCommunication")) {
				String data = XMLParserUtil.getNodeAttr("code", lang);

				Language newLang = new Language(data);
				languages.add(newLang);
			}
		}

		return languages;
	}

	private Marital.STATUS getMaritalStatus(final Node node) {
		Marital.STATUS status;

		String statusStr = XMLParserUtil.getNodeAttr("displayName", node);

		status = Marital.STATUS.fromTitle(statusStr);

		return status;
	}

	private Gender getGenderFromNote(final Node node) {
		Gender mGender;
		String genderCode;
		String gender;

		genderCode = XMLParserUtil.getNodeAttr("code", node);
		gender = XMLParserUtil.getNodeAttr("displayName", node);

		mGender = new Gender(gender, genderCode);

		return mGender;
	}

	private Address getAddressFromNode(final Node addrNode) {
		String street = "";
		String city = "";
		String state = "";
		String postal = "";
		String country = "";

		street = XMLParserUtil.getNodeValue("streetAddressLine", addrNode.getChildNodes());
		city = XMLParserUtil.getNodeValue("city", addrNode.getChildNodes());
		state = XMLParserUtil.getNodeValue("state", addrNode.getChildNodes());
		postal = XMLParserUtil.getNodeValue("postalCode", addrNode.getChildNodes());

		Node countryNode = XMLParserUtil.getNode("country", addrNode.getChildNodes());
		if (countryNode != null) {
			country = XMLParserUtil.getNodeValue(countryNode);
		} else {
			country = "";
		}

		Address addr = new Address(street, city, state, postal, country);

		return addr;
	}

	private Telephone getTelephoneFromNode(final Node telNode) {
		String areaCode = "";
		String firstPart = "";
		String secondPart = "";

		String data = XMLParserUtil.getNodeAttr("value", telNode);

		areaCode = data.substring(data.indexOf("+") + 3, data.lastIndexOf(")"));
		firstPart = data.substring(data.lastIndexOf(")") + 1, data.lastIndexOf(")") + 4);
		secondPart = data.substring(data.length() - 4);

		Telephone phone = new Telephone(areaCode, firstPart, secondPart);

		return phone;
	}
}
