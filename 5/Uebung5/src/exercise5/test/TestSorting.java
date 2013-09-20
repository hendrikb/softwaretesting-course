/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise5.test;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.NameMatcher;
import abbot.tester.JButtonTester;
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
		this.textTester = new JTextComponentTester();
		this.content = ((JTable) getFinder().find(new NameMatcher("viewTable"))).getModel();
	}
	
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
		enterDataIntoForm("John", "Doe", "M", "999999", "1.1.1111");
	    assertRowEntry(0, "John", "Doe", "M", "999999", "1.1.1111");
    
		enterDataIntoForm("Jane", "Muster", "F", "999999", "2.1.1111");
		
		assertRowEntry(0, "John", "Doe", "M", "999999", "1.1.1111");
		assertRowEntry(1, "Jane", "Muster", "F", "999999", "2.1.1111");
		
		enterDataIntoForm("Berta", "Bergmann", "F", "999999", "3.1.1111");
		
		assertRowEntry(0, "Berta", "Bergmann", "F", "999999", "3.1.1111");
		assertRowEntry(1, "John", "Doe", "M", "999999", "1.1.1111");
		assertRowEntry(2, "Jane", "Muster", "F", "999999", "2.1.1111");
		
		enterDataIntoForm("Adam", "Äpfel", "M", "999999", "4.1.1111");
		
		assertRowEntry(0, "Adam", "Äpfel", "M", "999999", "4.1.1111");
		assertRowEntry(1, "Berta", "Bergmann", "F", "999999", "3.1.1111");
		assertRowEntry(2, "John", "Doe", "M", "999999", "1.1.1111");
		assertRowEntry(3, "Jane", "Muster", "F", "999999", "2.1.1111");    
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
		
		buttonTester.actionClick(getFinder().find(new NameMatcher("addButton")));
	    textTester.actionEnterText(getFinder().find(new NameMatcher("firstNameTextfield")), firstName);
	    textTester.actionEnterText(getFinder().find(new NameMatcher("lastNameTextfield")), lastName);
	    buttonTester.actionClick(getFinder().find(new NameMatcher(sexButtonIdentifier+"Radiobutton")));
	    buttonTester.actionClick(getFinder().find(new NameMatcher("phoneRadiobutton")));
	    textTester.actionEnterText(getFinder().find(new NameMatcher("contactInformationTextfield")), phone);
	    textTester.actionEnterText(getFinder().find(new NameMatcher("dateOfBirthTextfield")), dob);
	    buttonTester.actionClick(getFinder().find(new NameMatcher("okButton")));
	}
	
}
