/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exercise2.addressbook.controller.AddressBookController;
import exercise2.addressbook.controller.AddressBookControllerImpl;
import exercise2.addressbook.controller.ParameterException;
import exercise2.addressbook.model.Contact;
import exercise2.addressbook.model.EmailAddress;
import exercise2.addressbook.model.Entry;
import exercise2.addressbook.model.Gender;
import exercise2.addressbook.model.PhoneNumber;
import exercise2.addressbook.model.SizeLimitReachedException;


/**
 * Uebung 2 - Komponenten und Integrationstest
 * Komponententests für den Controller
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
public class AddressBookControllerTest {
	
	/*
	 *  Aufgabe 3
	 *  Führen Sie im Rahmen eines Komponententests der Klasse exercise2.addressbook.controller.AddressBookControllerImpl einen Test der Methode add(...) durch.
	 *  Schreiben Sie für die model und view Komponenten Mock-Up Klassen und verwenden Sie dies im Komponententest der AddressBookController Komponente.
	 *  Testen Sie die add() Methode auf Herz und Nieren - es sind durchaus Fehler zu finden.
	 */
	
	// Model component for the test
	AddressBookModelMockUp model;
	
	// View component for the test
	AddressBookViewMockUp view;
	
	// Controller component for the test
	AddressBookController controller;
	
	/**
	 * Tests if the last entry in the model mockup has these values.
	 */
	private void testEntry(String firstname, String surname, Gender gender)
	{
		Entry entry = model.getLastEntry();
		assertNotNull( entry );
		assertEquals(firstname, entry.getFirstName());
		assertEquals(surname, entry.getSurName());
		assertTrue( ((gender == Gender.Female) && entry.isFemale()) || ((gender == Gender.Male) && entry.isMale()) );
	}
	
	/**
	 * Tries to add a valid entry into address book and tests if the result is as expected.
	 */
	private void testCorrectEntry(String firstName, String lastName, String genderString, Gender gender, String phone, String email)
	{
		try {
			controller.add(firstName, lastName, genderString, phone, email);
		} catch (Exception e) {
			fail();
		}
		
		assertTrue( view.hasBeenRefreshed() );
		testEntry(firstName, lastName, gender);
		if(phone != null) {
			testPhoneNumber( Integer.parseInt( phone ) );
		} else if( email != null ) {
			testEmail(email);
		} else {
			assertNull( model.getLastEntry().getContactInformation() );
		}
	}
	
	/**
	 * Tests if last entry in address book has this email as contact.
	 */
	private void testEmail(String email) {
		Contact contact = model.getLastEntry().getContactInformation();
		assertNotNull( contact );
		if( contact instanceof EmailAddress) {
			assertEquals( ((EmailAddress)contact).getEmailAddress(), email );
		} else {
			fail();
		}
	}

	/**
	 * Tests if last entry in address book has this phone number as contact.
	 */
	private void testPhoneNumber(int phoneNumber) {

		Contact contact = model.getLastEntry().getContactInformation();
		assertNotNull( contact );
		if( contact instanceof PhoneNumber) {
			assertEquals( ((PhoneNumber)contact).getNumber(), phoneNumber );
		} else {
			fail();
		}
		
	}

	/**
	 * Tries to add these value to address book and expects a ParameterException.
	 */
	private void testParameterException(String firstName, String lastName, String genderString, String phone, String email)
	{
		try {
			controller.add(firstName, lastName, genderString, phone, email);
		} catch (ParameterException e) {
			assertFalse( view.hasBeenRefreshed() );
			assertNull( model.getLastEntry() );
			return;
		} catch (SizeLimitReachedException e) {
		}
		
		fail();
	}
	
	/**
	 * Set up test system
	 */
	@Before
	public void setUp() {
		// Instantiate and wire components
		this.model = new AddressBookModelMockUp();
		this.view = new AddressBookViewMockUp();
		this.controller = new AddressBookControllerImpl(model, view);
	}
	
	@Test
	public void testCorrectEntry()
	{
		testCorrectEntry("Donald", "Duck", "M", Gender.Male, "12345", null);
	}
	
	@Test
	public void testFirstnameNull()
	{
		testParameterException(null, "Duck", "M", "12345", null);
	}
	
	@Test
	public void testFirstnameEmpty()
	{
		testCorrectEntry("", "Duck", "M", Gender.Male, "12345", null);
	}
	
	@Test
	public void testLastnameNull()
	{
		testParameterException("Donald", null, "M", "12345", null);
	}
	
	@Test
	public void testLastnameEmpty()
	{
		testCorrectEntry("Donald", "", "M", Gender.Male, "12345", null);
	}
	
	@Test
	public void testFemaleEntry()
	{
		testCorrectEntry("Rita", "Rührig", "F", Gender.Female, null, "ruehrig@duck-enterprises.com");
	}
	
	@Test
	public void testSmallGenderFemaleEntry()
	{
		testCorrectEntry("Rita", "Rührig", "f", Gender.Female, null, "ruehrig@duck-enterprises.com");
	}
	
	@Test
	public void testSmallGenderMaleEntry()
	{
		testCorrectEntry("Donald", "Duck", "m", Gender.Male, "12345", null);
	}
	
	@Test
	public void testIncorrectGenderEntry()
	{
		testParameterException("Donald", "Duck", "abc", "12345", null);
	}
	
	@Test
	public void testEmptyGenderEntry()
	{
		testParameterException("Donald", "Duck", "", "12345", null);
	}
	
	@Test
	public void testNullGenderEntry()
	{
		try {
			controller.add("Donald", "Duck", null, "12345", null);
		} catch (ParameterException e) {
		} catch (SizeLimitReachedException e) {
		} catch (NullPointerException e) {
			assertFalse( view.hasBeenRefreshed() );
			assertNull(model.getLastEntry());
			return;
		}
		
		fail();
	}
	
	@Test
	public void testNegativePhoneEntry()
	{
		testParameterException("Donald", "Duck", "M", "-12345", null);
	}

        @Test
	public void testNonPositivePhoneEntry()
        {
	    testParameterException("Donald", "Duck", "M", "0", null );
	}

	@Test
	public void testNoNumberPhoneEntry()
	{
		testParameterException("Donald", "Duck", "M", "abc", null);
	}
	
	@Test
	public void testPartialNumberPhoneEntry()
	{
		testParameterException("Donald", "Duck", "M", "123e45", null);
	}
	
	@Test
	public void testEmptyPhoneEntry()
	{
		testParameterException("Donald", "Duck", "M", "", null);
	}
	
	@Test
	public void testEmptyEmailEntry()
	{
		testCorrectEntry("Donald", "Duck", "M", Gender.Male, null, "");
	}
	
	@Test
	public void testNoPhoneNoEmailEntry()
	{
		testCorrectEntry("Donald", "Duck", "M", Gender.Male, null, null);
	}
	
	@Test
	public void testPhoneAndEmailEntry()
	{
		testParameterException("Donald", "Duck", "", "12345", "donald@duck.eh");
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
			testCorrectEntry(firstName+i, lastName, genderString, Gender.Male, phone, email);
			view.setHasBeenRefreshed(false);
			model.setLastEntry(null);
		}
	}
	
	@Test
	public void testTooManyEntries()
	{
		String firstName = "Donald";
		String lastName = "Duck";
		String genderString = "M";
		Gender gender = Gender.Male;
		String phone = "12345";
		String email = null;
		
		for(int i=0; i<AddressBookModelMockUp.SIZE_LIMIT+1; ++i)
		{
			try {
				controller.add(firstName, lastName, genderString, phone, email);
			} catch (ParameterException e) {
				fail();
			} catch (SizeLimitReachedException e) {
				if(i >= AddressBookModelMockUp.SIZE_LIMIT) {
					return;
				}
			}
			
			if( i >= AddressBookModelMockUp.SIZE_LIMIT)
			{
				assertTrue( view.hasBeenRefreshed() );
				assertNull(model.getLastEntry());
			} else {
				assertTrue( view.hasBeenRefreshed() );
				testEntry(firstName, lastName, gender);
			}

			view.setHasBeenRefreshed(false);
			model.setLastEntry(null);
		}
		
		fail();
	}
}
