/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise5.addressbook.view;

import java.util.ArrayList;
import java.util.Collection;

import exercise5.addressbook.model.Entry;
import exercise5.addressbook.model.PhoneNumber;


/**
 * A collection of filters for address book entries.
 * @author Edzard Hoefig
 */
public enum AddressBookFilter {
	
	/** No Filtering */
	NONE("None"), 
	
	/** All entries with age >= 18 and a phone contact number */
	MALE_ADULTS_PHONE ("Male Adults with phone"),
	
	/** All females with a surname of either Duck or Maus and an age >= 30 */
	FEMALE_DUCK_OR_MAUS_OVER_30 ("Female Duck or Maus over 30"), 
	
	/** People with a last name that starts with the letter D, which are either female or of an age of less than 18 or 65 and more */
	FEMALE_OR_MALE_KIDS_AND_SENIORS_WITH_D ("Females or Male Kids and Seniors starting with D");
	
	// Used as a label in the GUI
	private String text;
	
	/**
	 * Parametrised Ctor.
	 * @param text A label text
	 */
	AddressBookFilter(String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
	
	/**
	 * Filters a collection of entries.
	 * Suppresses all entries that do not fit the filter condition.
	 * @param entries The source entries to filter
	 * @return The filtered entries
	 */
	public Collection<Entry> filter(Collection<Entry> entries) {
		final ArrayList<Entry> result = new ArrayList<Entry>();
		for (Entry e: entries) {
			switch(this.ordinal()) {
			case 1: 
				// Male with an age of 18 years or more and phone number contact information
				if ((e.isMale() && e.getAge() >= 18 && e.getContactInformation() instanceof PhoneNumber)) 
					result.add(e);
				break;
			case 2: 
				// Female with a surname of either "Duck" or "Maus" and an age of 30 or more years
				if (e.isFemale() && (e.getSurName().equalsIgnoreCase("Duck") || e.getSurName().equalsIgnoreCase("Maus") && e.getAge() >= 30)) 
					result.add(e);
				break;
			case 3: 
				// People with a last name that starts with the letter D, which are either female or of an age of less than 18 or 65 and more 
				if ((e.isFemale() || e.getAge() < 18 || e.getAge() >= 65 && e.getSurName().startsWith("D"))) 
					result.add(e);
				break;
			default: return entries;
			}
		}
		return result;
	}
}
