/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.model;

import java.util.Collection;


/**
 * Definition of the address book access methods.
 * @author edzard
 *
 */
public interface AddressBookAccess {

	/**
	 * Store a new entry.
	 * @param entry The entry to store
	 * @return true in case the entry has been stored, false in case that the entry already existed.
	 * @throws SizeLimitReachedException When the address book is full 
	 */
	boolean addEntry(Entry entry) throws SizeLimitReachedException;

	/**
	 * Get a single entry
	 * @param surName The last name of a person's entry 
	 * @param firstName The first name of a person's entry
	 * @return A matching entry, or null if none was found
	 */
	Entry getEntry(String surName, String firstName);

	/**
	 * Retrieve all entries stored in the address book
	 * @return a collection of entries (sorted by name)
	 */
	Collection<Entry> getEntries();
	
	/**
	 * Removes a single entry from the address book.
	 * @param entry The entry to remove
	 * @param return true in case the entry has been found and deleted, false in case there is no such entry
	 */
	boolean deleteEntry(Entry entry);

	/**
	 * Erase all entries in the address book.
	 */
	void erase();

}