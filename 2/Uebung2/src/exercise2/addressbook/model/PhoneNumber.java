/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.model;

/**
 * Stores a phone number.
 * @author Edzard Hoefig
 */
public class PhoneNumber implements Contact {
	
	// Holds the number
	private int number;
	
	/**
	 * Ctor.
	 */
	public PhoneNumber() {
		this.number = -1;
	}
	
	/**
	 * Parametrised Ctor.
	 * @param number The phone number to use for initialisation
	 */
	public PhoneNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Retrieve the stored phone number.
	 * @return The phone number
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Set the phone number.
	 * @param number The phone number to store
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toString(this.number);
	}
}
