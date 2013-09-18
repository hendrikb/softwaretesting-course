/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.test;

import java.io.IOException;
import java.util.Collection;

import exercise2.addressbook.model.AddressBookModel;
import exercise2.addressbook.model.Entry;
import exercise2.addressbook.model.SizeLimitReachedException;

/**
 * Uebung 2 - Komponenten und Integrationstest
 * Mock-Up für das AddressBookModel
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
public class AddressBookModelMockUp implements AddressBookModel {

	public static final int SIZE_LIMIT = 5;
	
	private int entryCount = 0;
	private Entry lastEntry = null;
	private boolean addEntryAnswer = true;
	
	@Override
	public boolean addEntry(Entry entry) throws SizeLimitReachedException {
		if(entryCount >= SIZE_LIMIT) {
			throw new SizeLimitReachedException("");
		}
		
		lastEntry = entry;
		++entryCount;
		
		return addEntryAnswer;
	}

	@Override
	public Entry getEntry(String surName, String firstName) {
		return null;
	}

	@Override
	public Collection<Entry> getEntries() {
		return null;
	}

	@Override
	public boolean deleteEntry(Entry entry) {
		return false;
	}

	@Override
	public void erase() {
	}

	@Override
	public void load() throws IOException {
	}

	@Override
	public void save() throws IOException {
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	public Entry getLastEntry() {
		return lastEntry;
	}

	public void setLastEntry(Entry lastEntry) {
		this.lastEntry = lastEntry;
	}

	public boolean getAddEntryAnswer() {
		return addEntryAnswer;
	}

	public void setAddEntryAnswer(boolean addEntryAnswer) {
		this.addEntryAnswer = addEntryAnswer;
	}	
}
