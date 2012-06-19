package com.tmobile.reallyme.mduploadsvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;

public class PhoneBookManager implements iPropertyManager {
	Cursor cursor;
	static Context phonecontext = null;
	String TAG = "HereNow";
	String[] dblist;
	int contactID = 0;
	static PhoneBookManager phonemgr = null;
	private Vector contactItemVector = new Vector();
	private Hashtable prevContacts = new Hashtable();
	private Vector addedContactsVector = new Vector();
	private ContactItem contactItem = null;
	int rows = 0;
	// Contact first name
	private String firstName = "";
	// Contact last name
	private String lastName = "";
	// Contact home phone
	private String homePhone = "";
	// Contact mobile phone
	private String mobilePhone = "";
	// Contact work phone
	private String workPhone = "";

	// Contact home email
	private String homeEmail;
	// Contact work email
	private String workEmail;
	// Contact other email
	private String otherEmail;

	public void setContext(Context context) {
		this.phonecontext = context;
	}

	private PhoneBookManager() {

	}

	public static PhoneBookManager getInstance() {
		if (phonemgr == null) {
			phonemgr = new PhoneBookManager();
		}
		return phonemgr;
	}

	public int getNoOfContact() {
		int contactCount = 0;
		try {
			cursor = phonecontext.getContentResolver().query(
					android.provider.Contacts.Phones.CONTENT_URI, null, null,
					null, null);
		} catch (Exception e) {

		}
		if (cursor != null) {
			contactCount = cursor.getCount();
		}
		return contactCount;

	}

	public String processData() {
		// Log.v(TAG,"PhoneBook----->");
		return "";
	}

	public Vector getNewlyAddedContacts() {
		return addedContactsVector;
	}

	public String getFormattedContactXML() {
		String xmlForm = new String();
		Cursor contactcur = this.phonecontext.getContentResolver().query(
				android.provider.Contacts.People.CONTENT_URI, null, null, null,
				null);
		int nameColumn = contactcur.getColumnIndex("name");
		int numberColumn = contactcur.getColumnIndex("number");
		int idColumn = contactcur.getColumnIndex("_id");
		int emailCol = contactcur.getColumnIndex("primary_email");
		int typeCol = contactcur.getColumnIndex("type");
		xmlForm = xmlForm
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><contacts>";
		if (contactcur != null && contactcur.getCount() > 0) {
			contactcur.moveToFirst();

			do {
				int id = contactcur.getInt(idColumn);
				String number = contactcur.getString(numberColumn);
				String name = contactcur.getString(nameColumn);
				splitCombinedName(name);
				//Log.i(TAG,"Name =====>"+name + "Number --->"+number);
				String email = getEmailContacts(id);
				getPhoneNumbers(id);
				
				if (number != null && !"null".equalsIgnoreCase(number)) {
					switch (contactcur.getInt(contactcur
							.getColumnIndexOrThrow(Contacts.Phones.TYPE))) {
					case Contacts.PhonesColumns.TYPE_HOME:
						homePhone = number;
						break;

					case Contacts.PhonesColumns.TYPE_WORK:
						workPhone = number;
						break;

					case Contacts.PhonesColumns.TYPE_MOBILE:
						mobilePhone = number;
						break;

					}
				}

				xmlForm = xmlForm + "<identity id=\"" + id + "\">";
				xmlForm = xmlForm + "<profile><item value=\"" + firstName
						+ "\" name=\"firstName\"/>";
				xmlForm = xmlForm + "<item value=\"" + lastName
						+ "\" name=\"lastName\"/>";
				xmlForm = xmlForm + "<item value=\"" + homePhone
						+ "\" name=\"homePhome\"/>";
				xmlForm = xmlForm + "<item value=\"" + mobilePhone
						+ "\" name=\"mobilePhone\"/>";
				xmlForm = xmlForm + "<item value=\"" + workPhone
						+ "\" name=\"workPhone\"/>";
				xmlForm = xmlForm + "<item value=\"" + email
						+ "\" name=\"email\"/>";
				xmlForm = xmlForm + "</profile></identity>";

			} while (contactcur.moveToNext());

		}
		contactcur.close();
		xmlForm = xmlForm + "</contacts>";
		//Log.d(TAG,"Contact xmlForm :: "+xmlForm );
		return xmlForm;
	}

	private void getPhoneNumbers(int personId) {

		homePhone = "";
		workPhone = "";
		mobilePhone = "";
		Cursor contactcur = this.phonecontext.getContentResolver().query(
				android.provider.Contacts.Phones.CONTENT_URI, null,
				"people._id='" + personId + "'", null, null);

		int numberColumn = contactcur.getColumnIndex("number");

		if (contactcur != null && contactcur.getCount() > 0) {
			contactcur.moveToFirst();

			do {

				String number = contactcur.getString(numberColumn);
				
				if (number != null && !"null".equalsIgnoreCase(number)) {
					switch (contactcur.getInt(contactcur
							.getColumnIndexOrThrow(Contacts.Phones.TYPE))) {
					case Contacts.PhonesColumns.TYPE_HOME:
						homePhone = number;
						break;

					case Contacts.PhonesColumns.TYPE_WORK:
						workPhone = number;
						break;

					case Contacts.PhonesColumns.TYPE_MOBILE:
						mobilePhone = number;
						break;

					}
				}

			} while (contactcur.moveToNext());
			contactcur.close();
		}

	}

	/**
	 * Split the combined name into firstName and lastName.
	 */
	private void splitCombinedName(String googleName) {

		// Well known prefixes that belong to the lastname.
		// The list contains the prefix in original writing and alphabetical
		// order.
		final String[] namePrefixes = { "A", "Am", "Aus'm", "D'", "Da", "De",
				"De la", "De las", "Del", "Della", "Den", "Des", "Dos", "Di",
				"Du", "La", "Las", "Le", "Li", "Lo", "op de", "ter", "ten",
				"Ten", "Van", "Van 't", "Van der", "ver", "Vom", "Von",
				"Von der", "z", "Zu", "Zum", "Zur" };

		// Well known suffixes that belong to the lastname.
		final String[] nameSuffixes = { "Filho", "Junior", "Neto", "Netto",
				"Senior", "Sobrinho" };
		firstName = "";
		lastName = "";
		List<String> prefixList = new ArrayList<String>();
		List<String> suffixList = new ArrayList<String>();
		boolean lastNameStart = false;

		for (String prefix : namePrefixes) {
			// A suffix can consist of more than one token but
			// we need only the first one and match with lower case for now.
			prefixList.add(StringUtil.split(prefix.toLowerCase(), " ")[0]);
		}

		for (String suffix : nameSuffixes) {
			suffixList.add(suffix.toLowerCase());
		}

		if (googleName != null) {
			String[] nameFields = StringUtil.split(googleName, " ");

			// If one token is present use it as lastname, else use every but
			// the last
			// as firstname and the last as lastname. If token is a well known
			// prefix, start using it as lastname from there. If next token is a
			// well
			// known suffix, start with current token as lastname.
			for (int i = 0; i < nameFields.length; i++) {
				if (nameFields.length == 1) {
					lastName = nameFields[0];
				} else if (i == nameFields.length - 1) {
					lastName = lastName + " " + nameFields[i];
				} else if (prefixList.contains(nameFields[i].toLowerCase())
						| suffixList.contains(nameFields[i + 1].toLowerCase())
						| lastNameStart) {
					lastNameStart = true;
					lastName = lastName + " " + nameFields[i];
				} else {
					firstName = firstName + " " + nameFields[i];
				}
			}
			firstName = firstName.trim();
			lastName = lastName.trim();
			/*Log.i(TAG, "FirstName ---->" + firstName + "lastName -------->"
					+ lastName);*/
			// don't know if we should set an empty string to null here...
		}
	}

	public String getEmailContacts(int contactId) {
		String contactEmail = "";
		Cursor emailCur = phonecontext.getContentResolver().query(
				android.provider.Contacts.ContactMethods.CONTENT_URI, null,
				"people._id='" + contactId + "'", null, null);
		if (emailCur.moveToFirst()) {
			do {
				String emailid = emailCur
						.getString(emailCur
								.getColumnIndexOrThrow(android.provider.Contacts.ContactMethods.DATA));
				String name = emailCur
						.getString(emailCur
								.getColumnIndexOrThrow(android.provider.Contacts.ContactMethods.NAME));
				int type = emailCur
						.getInt(emailCur
								.getColumnIndexOrThrow(android.provider.Contacts.ContactMethods.TYPE));

				//Log.i(TAG, "EmailId --->" + emailid + "Name ===>" + name);

				switch (type) {
				case android.provider.Contacts.ContactMethods.TYPE_HOME:
					contactEmail = emailid;
					break;
				case android.provider.Contacts.ContactMethods.TYPE_OTHER:
					// Log.i(TAG,"Type is Other");
					break;
				case android.provider.Contacts.ContactMethods.TYPE_WORK:
					// Log.i(TAG,"Type is WORK");
					break;
				}

			} while (emailCur.moveToNext());
			emailCur.close();
		}
		return contactEmail;
	}

	public void clearData() {

	}
}
