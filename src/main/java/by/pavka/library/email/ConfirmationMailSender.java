package by.pavka.library.email;

import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * ConfirmationMailSender
 * <p>
 * The only static method of this class id responsible for generation a random password and sendin an email with it to
 * a user's email box.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ConfirmationMailSender {
  private static final String mailSender = "pavellibrarytest@gmail.com";
  private static final String password = "pavellibrarypass";

  /**
   *
   * @param mailReceiver
   * @return is used for adding a hashCode of it to the database
   */
  public static String sendInvitation(String mailReceiver) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session =
        Session.getInstance(
            props,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender, password);
              }
            });
    String readerPassword = RandomStringUtils.randomAlphanumeric(8);

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(mailSender));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailReceiver));
      message.setSubject("Pavel Library");
      message.setText(
          String.format(
              "Dear %s," + "\n\n Welcome to Pavel Library! Your password is %s",
              "User", readerPassword));

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return readerPassword;
  }
}
