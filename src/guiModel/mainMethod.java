package guiModel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import OneTimePassword.GetOTP;
import SendMailNotification.JavaMailUtil;

public class mainMethod extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GetOTP gotp = new GetOTP();
	String OTP;
	
	JButton submitButton;
	JTextField usernameTextfield;
	JPasswordField passwordTextfield;
	JLabel message;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//credcontent.showContent();
		
		mainMethod loginform = new mainMethod();
		loginform.loginForm();

	}

	public void loginForm() {

		JLabel username = new JLabel("Username ");
		JLabel password = new JLabel("Password ");
		message = new JLabel();

		usernameTextfield = new JTextField(20);
		passwordTextfield = new JPasswordField(20);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);

		add(username);
		add(usernameTextfield);
		add(password);
		add(passwordTextfield);
		add(submitButton);
		add(message);

		setLayout(new FlowLayout());
		setSize(500, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		String usernameText = usernameTextfield.getText();
		String passwordText = passwordTextfield.getText();

		//add mail notification service for confirmation
		
		if (usernameText.contentEquals("root") && passwordText.contentEquals("toor")) {
			try {
				OTP = gotp.getOTP();
				JavaMailUtil.sendMail("anshul.uberride@gmail.com", OTP);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			usernameTextfield.setText("");
			passwordTextfield.setText("");
			message.setText("Successfull!!!");
			this.dispose();
			try {
				gotp.OTPValidation(OTP);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			message.setText("Invalid username or password.");
			usernameTextfield.setText("");
			passwordTextfield.setText("");
		}

	}

}
