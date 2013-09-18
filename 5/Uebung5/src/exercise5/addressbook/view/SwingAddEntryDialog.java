/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker and Edzard Hoefig
 * Freie Universitaet Berlin, SS 2013
 */
package exercise5.addressbook.view;

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
import javax.swing.JLabel;

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
	JTextField txtDateOfBirth;
	JTextField txtContactInformation;

	private ButtonGroup genderGroup, contactGroup;
	JRadioButton rdbtnFemale, rdbtnMale, rdbtnPhone, rdbtnEmail;
	
	private JButton okButton, cancelButton;
	
	// Determines if the dialog operation was successful
	boolean ok;
	private JLabel lblDateOfBirth;
	private JLabel lblGender;
	
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
	 * @wbp.parser.constructor
	 */
	public SwingAddEntryDialog(JFrame owner, int row, TableModel tableModel) {
		super(owner, "Enter Contact Data", true);
		
		// Create gfx
		setBounds(100, 100, 450, 208);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtFirstName = new JTextField("First Name");
		txtFirstName.setName("firstNameTextfield");
		txtFirstName.setBounds(6, 6, 221, 28);
		contentPanel.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField("Last Name");
		txtLastName.setName("lastNameTextfield");
		txtLastName.setBounds(229, 6, 215, 28);
		contentPanel.add(txtLastName);
		txtLastName.setColumns(10);
		
		genderGroup = new ButtonGroup();
		

		JPanel contactPanel = new JPanel();
		contactPanel.setBounds(6, 76, 438, 60);
		contentPanel.add(contactPanel);
		contactPanel.setLayout(null);
		
		rdbtnPhone = new JRadioButton("Phone");
		rdbtnPhone.setName("phoneRadiobutton");
		rdbtnPhone.setBounds(0, 6, 70, 23);
		contactPanel.add(rdbtnPhone);
		
		rdbtnEmail = new JRadioButton("Email");
		rdbtnEmail.setName("emailRadiobutton");
		rdbtnEmail.setBounds(0, 31, 70, 23);
		rdbtnEmail.setSelected(true);
		contactPanel.add(rdbtnEmail);
		
		contactGroup = new ButtonGroup();
		contactGroup.add(rdbtnPhone);
		contactGroup.add(rdbtnEmail);
		
		txtContactInformation = new JTextField();
		txtContactInformation.setText("Contact Information");
		txtContactInformation.setName("contactInformationTextfield");
		txtContactInformation.setBounds(82, 16, 350, 28);
		contactPanel.add(txtContactInformation);
		txtContactInformation.setColumns(10);
		
		lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(255, 45, 85, 16);
		contentPanel.add(lblDateOfBirth);
		
		txtDateOfBirth = new JTextField();
		txtDateOfBirth.setName("dateOfBirthTextfield");
		txtDateOfBirth.setText("1.1.1970");
		txtDateOfBirth.setBounds(343, 39, 101, 28);
		contentPanel.add(txtDateOfBirth);
		txtDateOfBirth.setColumns(10);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setName("femaleRadiobutton");
		rdbtnFemale.setBounds(68, 41, 76, 23);
		contentPanel.add(rdbtnFemale);
		rdbtnFemale.setSelected(true);
		genderGroup.add(rdbtnFemale);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setName("maleRadiobutton");
		rdbtnMale.setBounds(141, 41, 67, 23);
		contentPanel.add(rdbtnMale);
		genderGroup.add(rdbtnMale);
		
		lblGender = new JLabel("Gender");
		lblGender.setBounds(16, 45, 61, 16);
		contentPanel.add(lblGender);
		
		
		// OK / Cancel buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		okButton = new JButton("OK");
		okButton.setName("okButton");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setName("cancelButton");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		// Initialize?
		if (tableModel != null) {
			this.txtFirstName.setText(tableModel.getValueAt(row, 0).toString());
			this.txtLastName.setText(tableModel.getValueAt(row, 1).toString());
			this.txtDateOfBirth.setText(tableModel.getValueAt(row, 4).toString());
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
