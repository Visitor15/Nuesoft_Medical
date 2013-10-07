package com.mobile.nuesoft.jobs;

import java.util.ArrayList;

import android.content.Intent;

import com.mobile.nuesoft.Nuesoft;
import com.mobile.nuesoft.document.CDADocumentBuilder.CDADocument;
import com.mobile.nuesoft.document.DocRecipient;

public class SendDocumentAsEmailJob {
	public static final String TAG = "ValidateAndSendDocumentJob";

	public SendDocumentAsEmailJob(final CDADocument document) {
		sendAsEmail(document);
	}

	private void sendAsEmail(final CDADocument document) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		
		ArrayList<DocRecipient> mRecipients = document.getRECIPIENT();
		ArrayList<String> emailList = new ArrayList<String>();
		
		for(DocRecipient recipient : mRecipients) {
			emailList.add(recipient.getRECIPIENT().getEMAIL());
		}
		
		String[] stringArray = emailList.toArray(new String[emailList.size()]);
		String subjectTitle = document.getPATIENT().getDisplayName().concat(" ").concat(document.getDISPLAY_TITLE());
		
		intent.putExtra(Intent.EXTRA_EMAIL, stringArray);
		intent.putExtra(Intent.EXTRA_SUBJECT, subjectTitle);
		intent.putExtra(Intent.EXTRA_TEXT, "See attachment");
		intent.putExtra(Intent.EXTRA_STREAM, document.getDOC_URI());
		Nuesoft.getReference().startActivity(Intent.createChooser(intent, "Send email..."));
	}
}
