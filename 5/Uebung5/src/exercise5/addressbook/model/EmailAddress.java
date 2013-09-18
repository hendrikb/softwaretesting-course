/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise5.addressbook.model;

/**
 * Stores an email address.
 * @author Edzard Hoefig
 */
public class EmailAddress implements Contact {
	
	// Holds the email address
	private String address;
	
	/**
	 * Ctor.
	 */
	public EmailAddress() {
	}
	
	/**
	 * Parametrised Ctor.
	 * @param address The email address to use for initialisation
	 */
	public EmailAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Retrieve the stored email address.
	 * @return The email address
	 */
	public String getEmailAddress() {
		return this.address;
	}
	
	/**
	 * Set the phone number.
	 * @param number The phone number to store
	 */
	public void setEmailAddress(String address) {
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.address;
	}
}
