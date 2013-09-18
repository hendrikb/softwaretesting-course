/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard HÃ¶fig
 * Freie UniversitÃ¤t Berlin
 */
package exercise1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import exercise1.addressbook.model.AddressBook;
import exercise1.addressbook.model.Contact;
import exercise1.addressbook.model.EmailAddress;
import exercise1.addressbook.model.Entry;
import exercise1.addressbook.model.Gender;
import exercise1.addressbook.model.PhoneNumber;
import exercise1.addressbook.model.SizeLimitReachedException;


/**
 * Uebung 1 - Grundlagen des Testens mit JUnit Bitte Nummer der Gruppe
 * eintragen: 7 Bitte Gruppenmitglieder eintragen:
 * 
 * @author Bergunde
 * @author Sydow
 * @author Teich
 * @author Rahner
 */
public class AddressBookFunctionalTest {

	// The component under test
	private AddressBook addressBook;

	/*
	 * Aufgabe 3a) Schreiben Sie eine Methode zum Aufsetzen der Testumgebung
	 * ("Fixture"). Diese Methode soll automatisch vor jedem einzelnen JUnit
	 * Testfall ausgefÃ¼hrt werden. Innerhalb der Methode soll mindestens ein
	 * neues AddressBook Objekt angelegt und im Attribut "addressBook"
	 * gepeichert werden.
	 */

	@Before
	public void setUp() {
		addressBook = new AddressBook();
	}

	/*
	 * Aufgabe 3b) Schreiben Sie einen JUnit Testfall zum Ã¼berprÃ¼fen der
	 * FunktionalitÃ¤t der addEntry() Methode.
	 */
	@Test
	public void testAddEntryInsertsData() {
		try {
			Entry entry = new Entry("Doe", "John", Gender.Male, new Contact() {});
			assertTrue(addressBook.addEntry(entry));
		}
		catch (Exception e) {
			fail("Got an unexpected exception: " + e.getMessage());
		}
	}
	
	/*
	 * ZusÃ¤tzlicher Testfall: ÃœberprÃ¼ft ob addEntry false zurÃ¼ck gibt bei doppelten EintrÃ¤gen
	 * und dann tatsÃ¤chlich nichts einfÃ¼gt.
	 */
	@Test
	public void testAddEntryInsertsOnlyOnce() {
		try {
			Entry entry = new Entry("Doe", "John", Gender.Male, new Contact() {});
			addressBook.addEntry(entry);
			assertEquals(1, addressBook.getEntries().size());
			
			Entry anotherButTheSame = new Entry("Doe", "John", Gender.Male, new Contact() {});
			assertFalse(addressBook.addEntry(anotherButTheSame));
			assertEquals(1, addressBook.getEntries().size());
		}
		catch (Exception e) {
			fail("Got an unexpected exception: " + e.getMessage());
		}
	}

	/*
	 * ZusÃ¤tzlich implementierter Testfall Testet ob nach dem zehnten Eintrag
	 * ein Fehler geworfen wird
	 */
	@Test
	public void testAddEntrySizeLimit() {
		assertTrue(addressBook.sizeLimit > 0);
		for (int i = 0; i <= Integer.MAX_VALUE; i++) {
			Entry entry = new Entry("Doe_" + i, "John", Gender.Male, new Contact() {});
			try {
				addressBook.addEntry(entry);
			}
			catch (SizeLimitReachedException e) {
				assertEquals("fails after the sizelimit's-th entry in the database", addressBook.sizeLimit, i);
				break;
			}
		}
	}

	/*
	 * Aufgabe 3c) Schreiben Sie einen JUnit Testfall zum Ã¼berprÃ¼fen der
	 * FunktionalitÃ¤t der getEntry() Methode.
	 */
	@Test
	public void testGetEntryFindCorrectOne() {
		try {
			Entry entry = new Entry("Doe", "John", Gender.Male, new Contact() {});
			addressBook.addEntry(entry);
		}
		catch (Exception e) {}
		Entry returnedEntry = addressBook.getEntry("Doe", "John");
		assertEquals("John", returnedEntry.getFirstName());
		assertEquals("Doe", returnedEntry.getSurName());
	}

	/*
	 * Weiterer Testfall fÃ¼r getEntry() Stellt sicher, dass null zurÃ¼ck kommt,
	 * falls die Nadel sich nicht im Heuhaufen befindet
	 */
	@Test
	public void testGetEntryFindNoneIfNoneExists() {
		try {
			Entry entry = new Entry("Doe", "John", Gender.Male, new Contact() {});
			addressBook.addEntry(entry);
		}
		catch (Exception e) {}
		assertNull("Does not accidentally find non existing entry", addressBook.getEntry("Normalverbraucher", "Otto"));
	}
}
