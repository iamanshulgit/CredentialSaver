package SendMailNotification;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {

	public static void sendMail(String recepient, String OTP) throws Exception {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		
		String eMail = "";		// Your email address
		String password = "";	//Your email password
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(eMail, password);
			}
		});
		
		Message message = prepareMessage(session, eMail, recepient, OTP);
		
		Transport.send(message);
		System.out.print("Message sent successfully");
	}

	private static Message prepareMessage(Session session, String eMail, String recepient, String OTP) {
		// TODO Auto-generated method stub
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(eMail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Credential Saviour Application Notification");
			message.setText("You have just loggon to the Credential Saviour Application. Here is your OTP: " + OTP);
			return message;
		}catch (Exception ex) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	
}
