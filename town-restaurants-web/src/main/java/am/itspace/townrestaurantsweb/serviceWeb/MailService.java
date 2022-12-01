package am.itspace.townrestaurantsweb.serviceWeb;


import javax.mail.MessagingException;

public interface MailService {

    void sendEmail(String to, String subject, String text) throws MessagingException;
}