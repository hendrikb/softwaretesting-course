/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook;

import java.io.File;

import exercise2.addressbook.controller.AddressBookControllerImpl;
import exercise2.addressbook.model.AddressBookModel;
import exercise2.addressbook.model.AddressBookModelImpl;
import exercise2.addressbook.view.AddressBookView;
import exercise2.addressbook.view.SwingAddressBookView;


/**
 * Bootstraps and wires the application's components.
 * @author Edzard Hoefig
 */
public class Manager  {
	
	// Location of the address book file
	private static final File addressBookFile = new File("contacts.xml");
	
	/**
	 * The bootstrap method.
	 * Creates the application and starts with program execution.
	 * @param args Optional arguments for program execution. Currently not in use.
	 */
	public static void main(String[] args) {
		// Instantiate and wire components
		final AddressBookModel model = new AddressBookModelImpl(addressBookFile);
		final AddressBookView view = new SwingAddressBookView();
		new AddressBookControllerImpl(model, view);	
	}
}
