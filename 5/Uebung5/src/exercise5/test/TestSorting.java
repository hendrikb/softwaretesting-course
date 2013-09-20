/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise5.test;

import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.NameMatcher;
import abbot.tester.JButtonTester;
import abbot.tester.JTableLocation;
import abbot.tester.JTableTester;
import abbot.tester.JTextComponentTester;

/**
 * Uebung 5 - Black Box Test
 * GUI testing
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
public class TestSorting extends ComponentTestFixture {

	// Tester for (radio) button components
	private JButtonTester buttonTester;

	// Tester for table components
	private JTableTester tableTester;

	// Tester for text field components
	private JTextComponentTester textTester;
	
	private TableModel content;

	
	/**
	 * Creates test fixture
	 */
	@Before
	public void setUp() throws Exception {

		// Start the application
		exercise5.addressbook.Manager.main(null);
		
		// Setup some test instrumentation
		this.buttonTester = new JButtonTester();
		this.tableTester = new JTableTester();
		this.textTester = new JTextComponentTester();
		this.content = ((JTable) getFinder().find(new NameMatcher("viewTable"))).getModel();
	}

	/**
	 * Removes test fixture
	 */
	@After
	public void tearDown() throws Exception {
		// Nothing to do
	}
	
	/**
	 * Test editing of an entry.
	 * This test case serves as a small tutorial on using the Abbot GUI testing framework. Please remove it before submitting the exercise.
	 * @throws ComponentNotFoundException When a GUI component was not available
	 * @throws MultipleComponentsFoundException When there is an ambiguous resolution of GUI components 
	 * @throws IOException In case the address book data file could not be read 
	 */
//	@Test
//	@Ignore
//	public void testEdit() throws ComponentNotFoundException, MultipleComponentsFoundException, IOException {
//		
//	    // Load data into address book
//	    buttonTester.actionClick(getFinder().find(new NameMatcher("loadButton")));
//	    
//	    // Is the correct data in the table?
//	    TableModel content = ((JTable) getFinder().find(new NameMatcher("viewTable"))).getModel();
//	    assertEquals("Dagobert", content.getValueAt(0, 0));
//	    assertEquals("Duck", content.getValueAt(0, 1));
//	    assertEquals("M", content.getValueAt(0, 2));
//	    assertEquals("dagobert@duck-enterprises.com", content.getValueAt(0, 3));
//	    assertEquals("1.10.1911", content.getValueAt(0, 4));
//	    
//	    // Edit first entry in table
//	    tableTester.actionSelectCell(getFinder().find(new NameMatcher("viewTable")), new JTableLocation(0,0));
//	    buttonTester.actionClick(getFinder().find(new NameMatcher("editButton")));
//	   
//	    // Change entry's values
//	    textTester.actionEnterText(getFinder().find(new NameMatcher("firstNameTextfield")), "Foo");
//	    textTester.actionEnterText(getFinder().find(new NameMatcher("lastNameTextfield")), "Bar");
//	    buttonTester.actionClick(getFinder().find(new NameMatcher("femaleRadiobutton")));
//	    buttonTester.actionClick(getFinder().find(new NameMatcher("phoneRadiobutton")));
//	    textTester.actionEnterText(getFinder().find(new NameMatcher("contactInformationTextfield")), "999999");
//	    textTester.actionEnterText(getFinder().find(new NameMatcher("dateOfBirthTextfield")), "1.1.1111");
//	    
//	    // Release dialog
//	    buttonTester.actionClick(getFinder().find(new NameMatcher("okButton")));
//	    
//	    // Did the data change properly in the table?
//	    content = ((JTable) getFinder().find(new NameMatcher("viewTable"))).getModel();
//	    assertEquals("Foo", content.getValueAt(0, 0));
//	    assertEquals("Bar", content.getValueAt(0, 1));
//	    assertEquals("F", content.getValueAt(0, 2));
//	    assertEquals("999999", content.getValueAt(0, 3));
//	    assertEquals("1.1.1111", content.getValueAt(0, 4));
//	}
	
	/*
	 * Aufgabe 4
	 * Verwenden Sie JUnit zur Ueberpruefung der korrekten Sortierreihenfolge beim Hinzufuegen von Eintraegen in das Adressbuch.  
	 * Verwenden Sie das Abbot GUI test framework zur Testdurchfuehrung.
	 * 
	 * Hinweis:
	 * Die aktuelle Version von Abbot (1.3.0) hat auf manchen Systemen (z.B. OS X 10.8) Schwierigkeiten die richtige "Keymap" zu 
	 * erkennen. Als Folge davon werden einige Zeichen nicht richtig in die Textfelder eingetragen (z.B. Sonderzeichen, 
	 * y und z vertauscht...). Bitte ueberpruefen Sie bei Ihren Testfaellen, ob Abbot die richtigen Testdaten eintraegt und waehlen 
	 * Sie ggfs. andere.
	 */
	
	@Test
	public void testSorting() throws Exception {
		enterDataIntoForm("Test", "Entry", "M", "999999", "1.1.1111");
	    assertRowEntry(0, "Test", "Entry", "M", "999999", "1.1.1111");
    
		enterDataIntoForm("Test", "Entryer", "F", "999999", "1.1.1111");
		
	    assertRowEntry(0, "Test", "Entry", "M", "999999", "1.1.1111");
	    assertRowEntry(1, "Test", "Entryer", "F", "999999", "1.1.1111");
	    
	    // add Test Entry2 again
	    enterDataIntoForm("Test", "Entryer", "F", "999999", "1.1.1111");
	    
	    // check, whether entered
	    assertRowEntry(0, "Test", "Entry", "M", "999999", "1.1.1111");
	    assertRowEntry(1, "Test", "Entryer", "F", "999999", "1.1.1111");
	    
	    // add Test Entryboo
	    enterDataIntoForm("Test", "Entryboo", "F", "999999", "1.1.2222");

	    assertRowEntry(0, "Test", "Entry", "M", "999999", "1.1.1111");
	    assertRowEntry(1, "Test", "Entryboo", "F", "999999", "1.1.2222");
	    assertRowEntry(2, "Test", "Entryer", "F", "999999", "1.1.1111");
	    
	    
	    // add Tester Entryboo
	    enterDataIntoForm("Tester", "Entryboo", "M", "999999", "2.2.2222");
	    
	    // check, whether entered
	    assertRowEntry(0, "Test", "Entry", "M", "999999", "1.1.1111");
	    assertRowEntry(1, "Test", "Entryboo", "F", "999999", "1.1.2222");
	    assertRowEntry(2, "Tester", "Entryboo", "M", "999999", "2.2.2222");
	    assertRowEntry(3, "Test", "Entryer", "F", "999999", "1.1.1111");
	    
	    
	 // add Tester Ätest
	    enterDataIntoForm("Tester", "Ätest", "M", "999999", "3.3.3333");
	    
	    assertRowEntry(0, "Tester", "Ätest", "M", "999999", "3.3.3333");
	    assertRowEntry(1, "Test", "Entry", "M", "999999", "1.1.1111");
	    assertRowEntry(2, "Test", "Entryboo", "F", "999999", "1.1.2222");
	    assertRowEntry(3, "Tester", "Entryboo", "M", "999999", "2.2.2222");
	    assertRowEntry(4, "Test", "Entryer", "F", "999999", "1.1.1111");    
	}
	
	public void assertRowEntry(int row, String firstName, String lastName, String sex, String phone, String dob) throws ComponentNotFoundException, MultipleComponentsFoundException {
		content = ((JTable) getFinder().find(new NameMatcher("viewTable"))).getModel();
	    assertEquals(firstName, content.getValueAt(row, 0));
	    assertEquals(lastName, content.getValueAt(row, 1));
	    assertEquals(sex, content.getValueAt(row, 2));
	    assertEquals(phone, content.getValueAt(row, 3));
	    assertEquals(dob, content.getValueAt(row, 4));
	}
	
	public void enterDataIntoForm(String firstName, String lastName, String sex, String phone, String dob) throws Exception {
		buttonTester.actionClick(getFinder().find(new NameMatcher("addButton")));
		
		String sexButtonIdentifier = null;
		
		if (sex == "M") {
			sexButtonIdentifier = "male";
		}
		else if (sex == "F") {
			sexButtonIdentifier = "female";
		}
		else {
			throw new Exception("Wrong test setup: Gender identifier must be either M or F");
		}
		
	    textTester.actionEnterText(getFinder().find(new NameMatcher("firstNameTextfield")), firstName);
	    textTester.actionEnterText(getFinder().find(new NameMatcher("lastNameTextfield")), lastName);
	    buttonTester.actionClick(getFinder().find(new NameMatcher(sexButtonIdentifier+"Radiobutton")));
	    buttonTester.actionClick(getFinder().find(new NameMatcher("phoneRadiobutton")));
	    textTester.actionEnterText(getFinder().find(new NameMatcher("contactInformationTextfield")), phone);
	    textTester.actionEnterText(getFinder().find(new NameMatcher("dateOfBirthTextfield")), dob);
	    buttonTester.actionClick(getFinder().find(new NameMatcher("okButton")));
	}
	
}