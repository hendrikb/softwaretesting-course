/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import exercise2.addressbook.controller.AddressBookController;
import exercise2.addressbook.model.AddressBookAccess;
import exercise2.addressbook.model.Entry;

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
	private TableModel model;
	
	/**
	 * Ctor.
	 */
	public SwingAddressBookView() {
		// Initialisation done in initialize
	}


	private class AddressBookTableModel extends AbstractTableModel {
		
		// keep java happy
		private static final long serialVersionUID = -6117615767886851210L;
		private AddressBookAccess addressBook;
		
		AddressBookTableModel(AddressBookAccess addressBook) {
			this.addressBook = addressBook;
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 4;
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return this.addressBook.getEntries().size();
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			final Vector<Entry> entries = new Vector<Entry>(this.addressBook.getEntries());
			final Entry e = entries.get(rowIndex);
			switch(columnIndex) {
				case 0: return e.getFirstName();
				case 1: return e.getSurName();
				case 2: return e.isMale()? "M" : "F";
				case 3: return e.getContactInformation().toString();
				default: return null;
			}
		}
			
	}
		
	
	/* (non-Javadoc)
	 * @see exercise2.addressbook.view.AddressBookView#initialize(exercise2.addressbook.controller.TableModelSupplier, exercise2.addressbook.controller.AddressBookController)
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
		table.setBounds(6, 26, 588, 300);
		table.getTableHeader().setBounds(6, 6, 588, 20);
		table.getColumnModel().getColumn(0).setHeaderValue("First Name");
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setHeaderValue("Last Name");
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setHeaderValue("Gender");
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setHeaderValue("Contact");
		table.getColumnModel().getColumn(3).setPreferredWidth(250);
		frame.getContentPane().add(table.getTableHeader());
		frame.getContentPane().add(table);
		
		buttonLoad = new JButton("Load");
		buttonLoad.addActionListener(this);
		buttonLoad.setBounds(434, 343, 81, 29);
		frame.getContentPane().add(buttonLoad);
		
		buttonSave = new JButton("Save");
		buttonSave.addActionListener(this);
		buttonSave.setBounds(513, 343, 81, 29);
		frame.getContentPane().add(buttonSave);
		
		buttonErase = new JButton("Erase");
		buttonErase.addActionListener(this); 
		buttonErase.setBounds(353, 343, 81, 29);
		frame.getContentPane().add(buttonErase);
		
		buttonRemove = new JButton("Remove");
		buttonRemove.addActionListener(this);
		buttonRemove.setBounds(272, 343, 81, 29);
		frame.getContentPane().add(buttonRemove);
		
		buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(this);
		buttonAdd.setBounds(112, 343, 81, 29);
		frame.getContentPane().add(buttonAdd);
		
		buttonEdit = new JButton("Edit");
		buttonEdit.addActionListener(this);
		buttonEdit.setBounds(192, 343, 81, 29);
		frame.getContentPane().add(buttonEdit);
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see exercise2.addressbook.view.AddressBookView#refresh()
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
					if (dialog.rdbtnPhone.isSelected()) {
						// Add with phone contact
						this.controller.add(firstName, lastName, gender, dialog.txtContactInformation.getText(), null);	
					} else {
						// Add with email contact
						this.controller.add(firstName, lastName, gender, null, dialog.txtContactInformation.getText());
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
					try {
						if (dialog.rdbtnPhone.isSelected()) {
							// Then add with phone contact
							this.controller.add(firstName, lastName, gender, dialog.txtContactInformation.getText(), null);	
						} else {
							// Then add with email contact
							this.controller.add(firstName, lastName, gender, null, dialog.txtContactInformation.getText());
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
		}
	}
}
