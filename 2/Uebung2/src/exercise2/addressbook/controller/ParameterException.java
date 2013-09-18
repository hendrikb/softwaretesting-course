/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.controller;

/**
 * Raised when the some parameter to a method is incorrect.
 * @author Edzard Hoefig
 */
public class ParameterException extends Exception {
	
	// For serialisation purposes (having it keeps java happy)
	private static final long serialVersionUID = -3735718742750537766L;

	/**
	 * Parametrized Ctor.
	 * @param message A message that indications the condition for the raised exception
	 */
	public ParameterException(final String message) {
		super(message);
	}
	
}
