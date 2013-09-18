/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import exercise2.addressbook.model.AddressBookModel;
import exercise2.addressbook.model.AddressBookModelImpl;
import exercise2.addressbook.model.Entry;
import exercise2.addressbook.model.Gender;
import exercise2.addressbook.model.SizeLimitReachedException;
import exercise2.addressbook.view.AddressBookView;


/**
 * Controller class for the address book example.
 * Bootstraps and manages the application's components.
 * @author Edzard Hoefig
 */
public class AddressBookControllerImpl implements AddressBookController {

	// Access to the application's data
	private AddressBookModel model;
	
	// Enables interaction with users
	private AddressBookView view;
	
	/**
	 * Ctor.
	 * Creates components for the application and establishes internal observer relationship.
	 * @param view 
	 * @param model 
	 */
	public AddressBookControllerImpl(AddressBookModel model, AddressBookView view) {
		// Instantiate and wire components
		this.model = model;
		this.view = view;
		this.view.create(this.model, this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.controller.AddressBookController#load()
	 */
	@Override
	public void load() throws IOException {
		this.model.load();
		this.view.refresh();
	}

	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.controller.AddressBookController#save()
	 */
	@Override
	public void save() throws IOException {
		this.model.save();
	}
	
	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.controller.AddressBookController#add(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void add(String firstName, String lastName, String gender,
			String phoneContactInformation, String emailContactInformation) throws ParameterException, SizeLimitReachedException {
		
		// Check parameters
		if (firstName == null) throw new ParameterException("No first name");
		if (lastName == null) throw new ParameterException("No last name");
		if (!(gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"))) throw new ParameterException("Only \"M\" and \"F\" allowed as gender");
		if ((phoneContactInformation != null) && (emailContactInformation != null)) throw new ParameterException("Only one sort of contact information can be set at one time");
		
		// Create entry
		Entry entry = new Entry();
		entry.setName(firstName, lastName);
		entry.setGender(gender.equalsIgnoreCase("F")? Gender.Female : Gender.Male);
		if (phoneContactInformation != null) entry.setContactPhoneNumber(Integer.parseInt(phoneContactInformation));
		if (emailContactInformation != null) entry.setContactEmailAddress(emailContactInformation);
		try {
			// Add entry
			this.model.addEntry(entry);
		} catch (SizeLimitReachedException e) {
			System.err.println("Couldn't add! Size limitation is " + AddressBookModelImpl.sizeLimit);
		}
		this.view.refresh();
	}
	
	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.controller.AddressBookController#remove(int)
	 */
	@Override
	public void remove(int row) {
		final Vector<Entry> entries = new Vector<Entry>(this.model.getEntries());
		this.model.deleteEntry(entries.get(row));
		this.view.refresh();
	}	

	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.controller.AddressBookController#erase()
	 */
	@Override
	public void erase() {
		// Delete all entries
		Iterator<Entry> entryIt = this.model.getEntries().iterator();
		while (!entryIt.hasNext()) {
			this.model.deleteEntry(entryIt.next());
		}
		this.view.refresh();
	}
}
