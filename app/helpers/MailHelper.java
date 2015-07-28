package helpers;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import play.Logger;
import play.Play;
import play.i18n.Messages;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

public class MailHelper {
	
	private static final String MAIL_FROM = Play.application().configuration().getString("emailFrom");
	private static final String ADD_TO = Play.application().configuration().getString("emailAddTo");

	public static void send(String subject, String email, String message) {

		/**
		 * Set subject, body and sender of mail and send mail
		 */
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setSubject(subject);
			mail.setFrom(MAIL_FROM);
			
			mail.addTo(email);
			mail.setMsg(message);
//			mail.setHtmlMsg(String
//					.format("<html><body><strong> %s </strong>: <p> %s </p> </body></html>",
//							email, message));
			mail.setHostName("smtp.gmail.com");
			mail.setSmtpPort(465);
			//mail.setStartTLSEnabled(true);
			mail.setSSLOnConnect(true);
			mail.setAuthenticator(new DefaultAuthenticator(
					Play.application().configuration().getString("EMAIL_USERNAME_ENV"), 
					Play.application().configuration().getString("EMAIL_PASSWORD_ENV")
					));

			mail.send();

		} catch (EmailException e) {
			Logger.error(e.getMessage());
		}

	}
	

}
