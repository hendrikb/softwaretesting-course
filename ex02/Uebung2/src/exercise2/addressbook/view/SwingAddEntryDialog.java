/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise2.addressbook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

/**
 * A GUI dialog for entering a new address book entry.
 * @author Edzard Hoefig
 */
public class SwingAddEntryDialog extends JDialog implements ActionListener {

	// Keep Java happy
	private static final long serialVersionUID = -4272181738646944687L;
	
	// GUI elements
	private final JPanel contentPanel = new JPanel();
	
	JTextField txtFirstName;
	JTextField txtLastName;
	JTextField txtContactInformation;

	private ButtonGroup genderGroup, contactGroup;
	JRadioButton rdbtnFemale, rdbtnMale, rdbtnPhone, rdbtnEmail;
	
	private JButton okButton, cancelButton;
	
	// Determines if the dialog operation was successful
	boolean ok;
	
	/**
	 * Parametrised Ctor.
	 * @param owner The owning GUI element
	 */
	public SwingAddEntryDialog(JFrame owner) {
		this(owner, 0, null);
	}

	/**
	 * Parametrised Ctor.
	 * @param owner The owning GUI element
	 * @param row A row in a table model for initialisation of the data
	 * @param tableModel The address book data to use
	 */
	public SwingAddEntryDialog(JFrame owner, int row, TableModel tableModel) {
		super(owner, "Enter Contact Data", true);
		
		// Create gfx
		setBounds(100, 100, 450, 176);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtFirstName = new JTextField("First Name");
		txtFirstName.setBounds(6, 6, 221, 28);
		contentPanel.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField("Last Name");
		txtLastName.setBounds(229, 6, 215, 28);
		contentPanel.add(txtLastName);
		txtLastName.setColumns(10);

		JPanel genderPanel = new JPanel();
		genderPanel.setBounds(6, 46, 76, 60);
		contentPanel.add(genderPanel);
		genderPanel.setLayout(null);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(0, 6, 76, 23);
		genderPanel.add(rdbtnFemale);
		rdbtnFemale.setSelected(true);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(0, 31, 67, 23);
		genderPanel.add(rdbtnMale);
		
		genderGroup = new ButtonGroup();
		genderGroup.add(rdbtnMale);
		genderGroup.add(rdbtnFemale);
		

		JPanel contactPanel = new JPanel();
		contactPanel.setBounds(94, 46, 350, 60);
		contentPanel.add(contactPanel);
		contactPanel.setLayout(null);
		
		rdbtnPhone = new JRadioButton("Phone");
		rdbtnPhone.setBounds(0, 6, 70, 23);
		contactPanel.add(rdbtnPhone);
		
		rdbtnEmail = new JRadioButton("Email");
		rdbtnEmail.setBounds(0, 31, 70, 23);
		rdbtnEmail.setSelected(true);
		contactPanel.add(rdbtnEmail);
		
		contactGroup = new ButtonGroup();
		contactGroup.add(rdbtnPhone);
		contactGroup.add(rdbtnEmail);
		
		txtContactInformation = new JTextField();
		txtContactInformation.setText("Contact Information");
		txtContactInformation.setBounds(82, 16, 259, 28);
		contactPanel.add(txtContactInformation);
		txtContactInformation.setColumns(10);
		
		
		// OK / Cancel buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		// Initialize?
		if (tableModel != null) {
			this.txtFirstName.setText(tableModel.getValueAt(row, 0).toString());
			this.txtLastName.setText(tableModel.getValueAt(row, 1).toString());
			if (tableModel.getValueAt(row, 2).toString().equalsIgnoreCase("M")) this.rdbtnMale.setSelected(true);
			else this.rdbtnFemale.setSelected(true);
			this.txtContactInformation.setText(tableModel.getValueAt(row, 3).toString());
			try {
				Integer.parseInt(this.txtContactInformation.getText());
				this.rdbtnPhone.setSelected(true);
			} catch (NumberFormatException ex) {
				// Must be an email address
				this.rdbtnEmail.setSelected(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == okButton) this.ok = true;
		else this.ok = false;
		this.dispose();
	}
}
