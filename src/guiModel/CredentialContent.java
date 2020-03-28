package guiModel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Model.databaseModel;
import database.dbConnect;

import java.util.ArrayList;
import javax.swing.border.BevelBorder;

public class CredentialContent extends JFrame implements ActionListener {
	public CredentialContent() {
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);

		model.setColumnIdentifiers(headerTable);
		table.setModel(model);
		table.setBackground(Color.PINK);
		table.setForeground(Color.black);
		table.setSelectionBackground(Color.DARK_GRAY);
		table.setSelectionForeground(Color.white);

		getContentPane().add(table);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton addNewItem, removeAnItem;
	private JTable table;
	private String[] headerTable = { "Website", "Username", "Password" };
	private DefaultTableModel model = new DefaultTableModel();
	JTextField websiteTextField, usernameTextField, passwordTextField;

	dbConnect dbc = new dbConnect();

	public void showContent() throws Exception {

		JLabel website, username, password;
		website = new JLabel("Website");
		username = new JLabel("Username");
		password = new JLabel("Password");

		websiteTextField = new JTextField(20);
		usernameTextField = new JTextField(20);
		passwordTextField = new JTextField(20);

		addNewItem = new JButton("Add New Item");
		addNewItem.addActionListener(this);
		removeAnItem = new JButton("Remove an Item");
		removeAnItem.addActionListener(this);

		getContentPane().add(website);
		getContentPane().add(websiteTextField);
		getContentPane().add(username);
		getContentPane().add(usernameTextField);
		getContentPane().add(password);
		getContentPane().add(passwordTextField);
		getContentPane().add(addNewItem);
		getContentPane().add(removeAnItem);
		getContentPane().add(new JScrollPane(table));

		showContentTable();

		getContentPane().setLayout(new FlowLayout());
		setSize(700, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == addNewItem) {
			table.setModel(new DefaultTableModel(null, headerTable));
			try {

				String website, username, password;
				website = new String (encryptData(websiteTextField.getText()));
				username = new String (encryptData(usernameTextField.getText()));
				password = new String (encryptData(passwordTextField.getText()));

				dbc.executingNewUser(website, username, password);

				// model.insertRow(model.getRowCount(), new Object[] {
				// websiteTextField.getText(),
				// usernameTextField.getText(),passwordTextField.getText() }); //insert into a
				// new line in the table
				showContentTable();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == removeAnItem) {
			table.setModel(new DefaultTableModel(null, headerTable));
			try {
				
				String website, username;
				website = new String (encryptData(websiteTextField.getText()));
				username = new String (encryptData(usernameTextField.getText()));

				dbc.deleteEntry(website, username);

				// model.insertRow(model.getRowCount(), new Object[] {
				// websiteTextField.getText(),
				// usernameTextField.getText(),passwordTextField.getText() }); //insert into a
				// new line in the table
				showContentTable();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void showContentTable() throws Exception {
		ArrayList<databaseModel> dbmlist = new ArrayList<databaseModel>();

		model = (DefaultTableModel) table.getModel();

		dbmlist = dbc.gettingResult("select * from credential");
		for (databaseModel dbm : dbmlist) {
			String website, username, password;
			website = new String (decryptData(dbm.getWebsite()));
			username = new String (decryptData(dbm.getUsername()));
			password = new String (decryptData(dbm.getPassword()));
			model.insertRow(model.getRowCount(), new Object[] { website, username, password });
		}
	}

	public byte[] encryptData(String data) {
		byte[] tempVar = data.getBytes();
		byte[] enc = new byte[data.length()];
		for (int i = 0; i < tempVar.length; i++) {
			enc[i] = (byte) ((i % 2 == 0) ? tempVar[i] + 7 : tempVar[i] - 11);
		}
		return enc;
	}

	public byte[] decryptData(String data) {
		byte[] tempVar = data.getBytes();
		byte[] decry = new byte[data.length()];
		for(int i = 0; i < tempVar.length; i++)
		{
			decry[i] = (byte) ((i % 2 == 0) ? tempVar[i] - 7 : tempVar[i] + 11);
		}

		return decry;
	}
}
