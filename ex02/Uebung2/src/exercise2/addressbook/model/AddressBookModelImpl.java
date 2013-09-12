/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Serves as a repository for address book entries
 * @author Edzard Hoefig
 */
public class AddressBookModelImpl implements AddressBookModel {

	// Access to persistent storage
	private File dataFile;
	
	// Keeps entries sorted by name
	private SortedSet<Entry> entries;
	
	// Defines the maximum number of entries that can be stored in the address book
	public final static int sizeLimit = 10;
	
	/**
	 * Parametrised Ctor.
	 * @param persistentStorageFile A file used for persistent storage
	 */
	public AddressBookModelImpl(File persistentStorageFile) {
		this.dataFile = persistentStorageFile;
		this.entries = new TreeSet<Entry>();
	}
	
	/* (non-Javadoc)
	 * @see exercise2.addressbook.model.AddressBookAccess#addEntry(exercise2.addressbook.model.Entry)
	 */
	@Override
	public boolean addEntry(Entry entry) throws SizeLimitReachedException {
		
		// Check size limit
		if (entries.size() >= sizeLimit) 
			throw new SizeLimitReachedException("Limit is " + sizeLimit);
		
		// Does entry exist?
		if (entries.contains(entry)) {
			// Does exist
			return false;
		} else {
			// Store entry
			entries.add(entry);
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see exercise2.addressbook.model.AddressBookAccess#getEntry(java.lang.String, java.lang.String)
	 */
	@Override
	public Entry getEntry(String surName, String firstName) {
		// This look-up strategy is sub-optimal
		for (Entry e: entries) {
            if (e.getSurName().equals(surName) && e.getFirstName().equals(firstName)) {
				// Found the requested entry
				return e;
			}
		}
		// Did not find the entry
		return null;
	}
	
	/* (non-Javadoc)
	 * @see exercise2.addressbook.model.AddressBookAccess#deleteEntry(exercise2.addressbook.model.Entry)
	 */
	@Override
	public boolean deleteEntry(Entry entry) {
		
		// Check if entry exists
		if (entries.contains(entry)) {
			// Exists, remove entry
			entries.remove(entry);
			return true;
		} else {
			// No such entry
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see exercise2.addressbook.model.AddressBookAccess#erase()
	 */
	@Override
	public void erase() {
		entries.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.model.AddressBookAccess#getEntries()
	 */
	@Override
	public Collection<Entry> getEntries() {
		return entries;
	}
	
	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.model.PersistenceAccess#load()
	 */
	public void load() throws IOException {
		
		// Read data from given file into DOM document
		final FileInputStream in = new FileInputStream(dataFile);
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.close();
		
		// Put document data in address book
		// Get AddressBookAccess
		Element ab = null;
		if (document.getElementsByTagName("AddressBook").getLength() == 1) {
			ab = (Element) document.getElementsByTagName("AddressBook").item(0);
		} else {
			// Parse error
			throw new IOException("No single AddressBookAccess element found");
		}
			
		// Read and store entries
		NodeList list = ab.getChildNodes();
		for (int i=0; i < list.getLength(); ++i) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				
				
				Entry entry = new Entry();
				Element el = (Element)list.item(i);
				
				// Personal data
				entry.setName(el.getAttribute("FirstName"), el.getAttribute("SurName"));
				entry.setGender(el.getAttribute("Gender").equalsIgnoreCase("M")? Gender.Male : Gender.Female);
				
				// Contact Information
				if (el.getElementsByTagName("Contact").getLength() > 0) {
					Element contactEl = (Element)el.getElementsByTagName("Contact").item(0);
					if (contactEl.getAttribute("type").equalsIgnoreCase("phone")) {
						entry.setContactPhoneNumber(Integer.parseInt(contactEl.getTextContent()));
					} else if (contactEl.getAttribute("type").equalsIgnoreCase("email")) {
						entry.setContactEmailAddress(contactEl.getTextContent());
					}
				}
				
				try {
					addEntry(entry);
				} catch (SizeLimitReachedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see exercise2.addressbook.model.PersistenceAccess#save()
	 */
	public void save() throws IOException {
		
		// Create new DOM document
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Put content to document
		final Element ab = document.createElement("AddressBook");
		document.appendChild(ab);
		for (Entry e: entries) {
			
			// Create new Entry element in address book element
			final Element entry = document.createElement("Entry");
			ab.appendChild(entry);
			
			// Add name and gender
			entry.setAttribute("FirstName", e.getFirstName());
			entry.setAttribute("SurName", e.getSurName());
			entry.setAttribute("Gender", e.isMale()? "M":"F");
			
			// Add contact information
			final Element contact = document.createElement("Contact");
			if (e.getContactInformation() instanceof PhoneNumber) {
				contact.setAttribute("type", "phone");
			} else if (e.getContactInformation() instanceof EmailAddress) {
				contact.setAttribute("type", "email");
			}
			contact.setTextContent(e.getContactInformation().toString());
			entry.appendChild(contact);
		}
		
		// Write document to file
		final FileOutputStream out = new FileOutputStream(dataFile);
		try {
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");	// Platform specific...
			transformer.transform(new DOMSource(document), new StreamResult(out));	
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.close();
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		// Look up the platform dependent new line symbol
		final String newline = System.getProperty("line.separator");
		
		// Create pretty representation of the address
		final StringBuffer sb = new StringBuffer();
		for (Entry e: entries) {
			sb.append(e).append(newline);
		}
		return sb.toString();
	}
}
