/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise5.addressbook.view;

import static exercise5.addressbook.view.AddressBookFilter.NONE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import exercise5.addressbook.controller.AddressBookController;
import exercise5.addressbook.model.AddressBookAccess;
import exercise5.addressbook.model.Entry;



/**
 * A GUI element that allows to display and alter address book entries.
 * @author Edzard Hoefig
 */
public class SwingAddressBookView implements ActionListener, AddressBookView {

	// GUI elements
	private JFrame frame;
	private JTable table;
	private AddressBookController controller; 
	
	private JButton buttonAdd, buttonEdit, buttonRemove, buttonErase, buttonLoad, buttonSave;
	
	private JComboBox filterComboBox;
	private AddressBookTableModel model;
	
	/**
	 * Ctor.
	 */
	public SwingAddressBookView() {
		// Initialisation done in initialize
	}

	/**
	 * Wraps the address book model to provide a table-compatible model that can be filtered. 
	 * @author Edzard Hoefig
	 * @see JTable
	 */
	private class AddressBookTableModel extends AbstractTableModel {
		
		// keep java happy
		private static final long serialVersionUID = -6117615767886851210L;
		
		// The data source
		private AddressBookAccess addressBook;
		
		// A filter that restricts the data to display
		private AddressBookFilter currentFilter;
		
		/**
		 * Parametrised Ctor.
		 * @param addressBook The address book to use for data
		 */
		AddressBookTableModel(AddressBookAccess addressBook) {
			this.addressBook = addressBook;
			this.currentFilter = NONE;
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 5;
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return currentFilter.filter(this.addressBook.getEntries()).size();
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			final Vector<Entry> entries = new Vector<Entry>(currentFilter.filter(this.addressBook.getEntries()));
			final Entry e = entries.get(rowIndex);
			switch(columnIndex) {
				case 0: return e.getFirstName();
				case 1: return e.getSurName();
				case 2: return e.isMale()? "M" : "F";
				case 3: return e.getContactInformation().toString();
				case 4: return Entry.dateFormatter.print(e.getDateOfBirth());
				default: return null;
			}
		}

		/**
		 * Set a filter.
		 * This impacts the data available from the table model.
		 * @param filter The filter type to use
		 */
		public void setFilter(AddressBookFilter filter) {
			this.currentFilter = filter;
		}
	}
		
	
	/* (non-Javadoc)
	 * @see addressbook.view.AddressBookView#initialize(addressbook.controller.TableModelSupplier, addressbook.controller.AddressBookController)
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void create(AddressBookAccess model, AddressBookController controller) {
		
		this.controller = controller;
		this.model = new AddressBookTableModel(model);
		
		// Create gfx
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Address Book");
		lblNewLabel.setBounds(16, 342, 97, 29);
		frame.getContentPane().add(lblNewLabel);
		
		// The table
		table = new JTable(this.model);
		table.setName("viewTable");
		table.setBounds(6, 26, 588, 275);
		table.getTableHeader().setBounds(6, 6, 588, 20);
		table.getColumnModel().getColumn(0).setHeaderValue("First Name");
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setHeaderValue("Last Name");
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setHeaderValue("Gender");
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setHeaderValue("Contact");
		table.getColumnModel().getColumn(3).setPreferredWidth(250);
		table.getColumnModel().getColumn(4).setHeaderValue("Date of Birth");
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		frame.getContentPane().add(table.getTableHeader());
		frame.getContentPane().add(table);
		
		buttonLoad = new JButton("Load");
		buttonLoad.setName("loadButton");
		buttonLoad.addActionListener(this);
		buttonLoad.setBounds(434, 343, 81, 29);
		frame.getContentPane().add(buttonLoad);
		
		buttonSave = new JButton("Save");
		buttonSave.setName("saveButton");
		buttonSave.addActionListener(this);
		buttonSave.setBounds(513, 343, 81, 29);
		frame.getContentPane().add(buttonSave);
		
		buttonErase = new JButton("Erase");
		buttonErase.setName("eraseButton");
		buttonErase.addActionListener(this); 
		buttonErase.setBounds(353, 343, 81, 29);
		frame.getContentPane().add(buttonErase);
		
		buttonRemove = new JButton("Remove");
		buttonRemove.setName("removeButton");
		buttonRemove.addActionListener(this);
		buttonRemove.setBounds(272, 343, 81, 29);
		frame.getContentPane().add(buttonRemove);
		
		buttonAdd = new JButton("Add");
		buttonAdd.setName("addButton");
		buttonAdd.addActionListener(this);
		buttonAdd.setBounds(112, 343, 81, 29);
		frame.getContentPane().add(buttonAdd);
		
		buttonEdit = new JButton("Edit");
		buttonEdit.setName("editButton");
		buttonEdit.addActionListener(this);
		buttonEdit.setBounds(192, 343, 81, 29);
		frame.getContentPane().add(buttonEdit);
		
		filterComboBox = new JComboBox(AddressBookFilter.values());
		filterComboBox.setName("filterComboBox");
		filterComboBox.addActionListener(this);
		filterComboBox.setBounds(192, 313, 402, 27);
		frame.getContentPane().add(filterComboBox);
		
		JLabel lblFilter = new JLabel("Filter");
		lblFilter.setBounds(145, 317, 32, 16);
		frame.getContentPane().add(lblFilter);
		
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see addressbook.view.AddressBookView#refresh()
	 */
	@Override
	public void refresh() {
		this.table.updateUI();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Add an entry?
		if (e.getSource() == buttonAdd) {
			try {
				// Display add entry dialog
				SwingAddEntryDialog dialog = new SwingAddEntryDialog(this.frame);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				if (dialog.ok) {  
					// Dialog was OK'ed
					final String firstName = dialog.txtFirstName.getText();
					final String lastName = dialog.txtLastName.getText();
					final String gender = dialog.rdbtnFemale.isSelected()? "F" : "M";
					final String dateOfBirth = dialog.txtDateOfBirth.getText();
					if (dialog.rdbtnPhone.isSelected()) {
						// Add with phone contact
						this.controller.add(firstName, lastName, gender, dateOfBirth, dialog.txtContactInformation.getText(), null);	
					} else {
						// Add with email contact
						this.controller.add(firstName, lastName, gender, dateOfBirth, null, dialog.txtContactInformation.getText());
					}
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		// Edit an entry
		} else if (e.getSource() == buttonEdit) {
			int row = this.table.getSelectedRow();
			if (row != -1) {
				// Found selected row, show Edit Dialog
				SwingAddEntryDialog dialog = new SwingAddEntryDialog(this.frame, row, this.model);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				if (dialog.ok) {  
					// Dialog was OK'ed
					this.controller.remove(row);	// First remove entry
					final String firstName = dialog.txtFirstName.getText();
					final String lastName = dialog.txtLastName.getText();
					final String gender = dialog.rdbtnFemale.isSelected()? "F" : "M";
					final String dateOfBirth = dialog.txtDateOfBirth.getText();
					try {
						if (dialog.rdbtnPhone.isSelected()) {
							// Then add with phone contact
							this.controller.add(firstName, lastName, gender, dateOfBirth, dialog.txtContactInformation.getText(), null);	
						} else {
							// Then add with email contact
							this.controller.add(firstName, lastName, gender, dateOfBirth, null, dialog.txtContactInformation.getText());
						}
					} catch (Exception ex) {
						System.err.println("A Problem while adding an entry: " + ex.getMessage());
					}
				}
				this.table.clearSelection();
			}
			
		// Remove a selected row?
		} else if (e.getSource() == buttonRemove) {
			int row = this.table.getSelectedRow();
			if (row != -1) {
				// Found selected row, remove
				this.controller.remove(row);
				this.table.clearSelection();
			}
		
		// Erase the address book?
		} else if (e.getSource() == buttonErase) {
			// Clear all entries
			while(this.table.getRowCount() != 0) {
				this.controller.remove(0);
			}
			
		// Load data?
		} else if (e.getSource() == buttonLoad) {
			try {
				controller.load();
			} catch (IOException ex) {
				System.err.println("Unable to read address book data file (" + ex.getMessage() + ")");
				System.exit(-1);
			}
		
		// Save data?
		} else if (e.getSource() == buttonSave) {
			try {
				controller.save();
			} catch (IOException ex) {
				System.err.println("Unable to save address book data file (" + ex.getMessage() + ")");
				System.exit(-1);
			}
				
		// Change filter
		} else if (e.getSource() == filterComboBox) {
			this.model.setFilter((AddressBookFilter) this.filterComboBox.getSelectedItem());
			this.table.updateUI();
		}
	}
}
