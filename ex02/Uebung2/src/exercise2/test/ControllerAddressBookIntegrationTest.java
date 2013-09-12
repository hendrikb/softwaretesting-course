/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import exercise2.addressbook.controller.AddressBookController;
import exercise2.addressbook.controller.AddressBookControllerImpl;
import exercise2.addressbook.controller.ParameterException;
import exercise2.addressbook.model.AddressBookModel;
import exercise2.addressbook.model.AddressBookModelImpl;
import exercise2.addressbook.model.Entry;
import exercise2.addressbook.model.Gender;
import exercise2.addressbook.model.SizeLimitReachedException;

/**
 * Uebung 2 - Komponenten und Integrationstest
 * Integration Test für Addressbook und Controller.
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
public class ControllerAddressBookIntegrationTest {

	// Location of the address book file
	private static final File addressBookFile = new File("contacts.xml");
		
	/*
	 *  Aufgabe 4
	 *  Programmieren Sie einen Integrationstest für AddressBookModel und AddressBookController.
	 *  Testen Sie ob die Methoden des exercise2.addressbook.controller.AddressBookController Interface zu den erwarteten Resultate im Addressbuch führen.
	 *  Testen Sie intensiv und schreiben Sie MINDESTENS einen Testfall pro Methode des interfaces. Es sind Fehler zu finden.  
	 */
	
	// Model component for the test
	AddressBookModel model;
	
	// View component for the test
	AddressBookViewMockUp view;
	
	// Controller component for the test
	AddressBookController controller;
	
	private void testEntry(String firstName, String lastName, String genderString, String phone, String email)
	{
		int lastEntryCount = model.getEntries().size();
		try {
			controller.add(firstName, lastName, genderString, phone, email);
		} catch (Exception e) {
			fail();
		}
		
		Entry entry = model.getEntry(lastName, firstName);
		assertNotNull( entry );
		assertEquals(firstName, entry.getFirstName());
		assertEquals(lastName, entry.getSurName());
		assertEquals(model.getEntries().size(), lastEntryCount+1);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Instantiate and wire components
		this.model = new AddressBookModelImpl(addressBookFile);
		this.view = new AddressBookViewMockUp();
		this.controller = new AddressBookControllerImpl(model, view);
	}

	@Test
	public void testCorrectEntry()
	{
		testEntry("Donald", "Duck", "M", "12345", null);
	}

	@Test
	public void testEntryCount()
	{
		assertEquals(model.getEntries().size(), 0);
		testEntry("Donald", "Duck", "M", "12345", null);
		testEntry("Rita", "Rührig", "F", null, "ruehrig@duck-enterprises.com");
	}

	@Test
	public void testDoubleEntry()
	{
		testEntry("Donald", "Duck", "M", "12345", null);
		
		try {
			controller.add("Donald", "Duck", "M", "12345", null);
		} catch (Exception e) {
			fail();
		}
		
		Entry entry = model.getEntry("Duck", "Donald");
		assertNotNull( entry );
		assertEquals("Donald", entry.getFirstName());
		assertEquals("Duck", entry.getSurName());

		assertEquals(model.getEntries().size(), 1);
	}

	@Test
	public void testIncorrectEntry()
	{
		boolean hasParameterExceptionBeenThrown = false;
		try {
			controller.add("Donald", "Duck", "abc", "12345", null);
		} catch (ParameterException e) {
			// Expected
			hasParameterExceptionBeenThrown = true;
		} catch (SizeLimitReachedException e) {
			fail();
		}
		
		assertTrue(hasParameterExceptionBeenThrown);
		
		Entry entry = model.getEntry("Duck", "Donald");
		assertNull( entry );
		assertEquals(model.getEntries().size(), 0);
	}

	@Test
	public void testIncorrectSecondEntry()
	{
		// first correct entry:
		testEntry("Rita", "Rührig", "F", null, "ruehrig@duck-enterprises.com");
		
		// second wrong entry:
		boolean hasParameterExceptionBeenThrown = false;
		try {
			controller.add("Donald", "Duck", "abc", "12345", null);
		} catch (ParameterException e) {
			// Expected
			hasParameterExceptionBeenThrown = true;
		} catch (SizeLimitReachedException e) {
			fail();
		}
		
		assertTrue(hasParameterExceptionBeenThrown);
		
		Entry entry = model.getEntry("Duck", "Donald");
		assertNull( entry );
		assertEquals(model.getEntries().size(), 1);
	}
	
	@Test
	public void testMultipleEntries()
	{
		String firstName = "Donald";
		String lastName = "Duck";
		String genderString = "M";
		String phone = "12345";
		String email = null;
		
		for(int i=0; i<AddressBookModelMockUp.SIZE_LIMIT; ++i)
		{
			testEntry(firstName+i, lastName, genderString, phone, email);
		}
	}
	
	@Test
	public void testTooManyEntries()
	{

		String firstName = "Donald";
		String lastName = "Duck";
		String genderString = "M";
		String phone = "12345";
		String email = null;

		for(int i=0; i < AddressBookModelImpl.sizeLimit+1; ++i)
		{
			testEntry(firstName+i, lastName, genderString, phone, email);
		}
		
		try {
			controller.add(firstName, lastName, genderString, phone, email);
		} catch (ParameterException e) {
		} catch (SizeLimitReachedException e) {
			assertEquals(model.getEntries().size(), AddressBookModelImpl.sizeLimit);
			return;
		}
		
		fail();
	}
	
	@Test
	public void testErase()
	{
		String firstName = "Donald";
		String lastName = "Duck";
		String genderString = "M";
		String phone = "12345";
		String email = null;
		
		for(int i=0; i<AddressBookModelMockUp.SIZE_LIMIT; ++i)
		{
			testEntry(firstName+i, lastName, genderString, phone, email);
		}
		
		controller.erase();
		assertEquals(0, model.getEntries().size());
	}
	
	@Test
	public void testEraseOnEmptyAddressBook()
	{
		controller.erase();
		assertEquals(0, model.getEntries().size());
	}
	
	@Test
	public void testRemoveEntry()
	{
		assertEquals(0, model.getEntries().size());
		testEntry("Donald", "Duck", "M", "12345", null);
		
		controller.remove(0);
		
		Entry entry = model.getEntry("Duck", "Donald");
		assertNull( entry );
		assertEquals(0, model.getEntries().size());
	}
	
	
	@Test
	public void testRemoveEntry2()
	{
		assertEquals(0, model.getEntries().size());
		testEntry("Donald", "Duck", "M", "12345", null);
		testEntry("Rita", "Rührig", "F", null, "ruehrig@duck-enterprises.com");
		
		controller.remove(1);
		assertEquals(1, model.getEntries().size());
		
		Entry entry = model.getEntry("Duck", "Donald");
		assertNotNull( entry );

		Entry entry2 = model.getEntry("Rührig", "Rita");
		assertNull( entry2 );
	}	

        @Test
	public void testRemoveNonExistingEntry()
	{
		testEntry("Donald", "Duck", "M", "12345", null);
		testEntry("Rita", "Rührig", "F", null, "ruehrig@duck-enterprises.com");
		
		controller.remove(2);
		assertEquals(2, model.getEntries().size());
		
		Entry entry = model.getEntry("Duck", "Donald");
		assertNotNull( entry );

		Entry entry2 = model.getEntry("Rührig", "Rita");
		assertNotNull( entry2 );
	}
}
