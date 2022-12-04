package am.itspace.townrestaurantsrest.serviceRest;


import javax.mail.MessagingException;

public interface MailServiceRest {

    void sendEmail(String to, String subject, String text) throws MessagingException;

}