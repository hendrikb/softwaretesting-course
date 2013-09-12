/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.test;

import exercise2.addressbook.controller.AddressBookController;
import exercise2.addressbook.model.AddressBookAccess;
import exercise2.addressbook.view.AddressBookView;

/**
 * Uebung 2 - Komponenten und Integrationstest
 * Mock-Up für den AddressBookView
 * 
 * Bitte Nummer der Gruppe eintragen:
 * 7
 * 
 * Bitte Gruppenmitglieder eintragen:
 * @author Bergunde
 * @author Rahner
 * @author Sydow
 * @author Teich
 */
public class AddressBookViewMockUp implements AddressBookView {

	private boolean hasBeenRefreshed = false;
	private AddressBookAccess model = null;
	private AddressBookController controller = null;

	@Override
	public void create(AddressBookAccess model, AddressBookController controller) {
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void refresh() {
		setHasBeenRefreshed(true);
	}

	public boolean hasBeenRefreshed() {
		return hasBeenRefreshed;
	}

	public void setHasBeenRefreshed(boolean hasBeenRefreshed) {
		this.hasBeenRefreshed = hasBeenRefreshed;
	}
}
