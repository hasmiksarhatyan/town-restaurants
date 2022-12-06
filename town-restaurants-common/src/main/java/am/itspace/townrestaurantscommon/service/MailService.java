package am.itspace.townrestaurantscommon.service;


import javax.mail.MessagingException;

public interface MailService {

    void sendEmail(String to, String subject, String text) throws MessagingException;
}