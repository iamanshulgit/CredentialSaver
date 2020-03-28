package OneTimePassword;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import guiModel.CredentialContent;

public class GetOTP extends JFrame implements ActionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static CredentialContent credcontent = new CredentialContent();
	
	JButton submitButton;
	JTextField otpTextfield;
	JLabel message;
	String tempOTP;
	
	public String getOTP() {
		String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String otp = "";
		Random random = new Random();
		
		while(otp.length() < 5) {
			char temp = charSet.charAt(random.nextInt(charSet.length()));
			otp = otp + temp;
		}
		return otp;
	}
	
	public void OTPValidation(String OTP) {

		tempOTP = OTP;
		JLabel otp = new JLabel("OTP ");
		message = new JLabel();

		otpTextfield = new JTextField(10);

		submitButton = new JButton("Submit");
		submitButton.addActionListener( this);

		add(otp);
		add(otpTextfield);

		add(submitButton);
		add(message);

		setLayout(new FlowLayout());
		setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		String otpText = otpTextfield.getText();

		//add mail notification service for confirmation
		
		if (otpText.contentEquals(tempOTP)) {

			otpTextfield.setText("");

			message.setText("Successfull!!!");
			this.dispose();
			try {
				credcontent.showContent();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			message.setText("Invalid OTP.");
			otpTextfield.setText("");
		}

	}
}
