package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantsrest.exception.SendEmailException;
import am.itspace.townrestaurantsrest.serviceRest.MailServiceRest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import static am.itspace.townrestaurantsrest.exception.Error.SEND_EMAIL_FAILED;

@Service
@RequiredArgsConstructor
public class MailServiceRestImpl implements MailServiceRest {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SendEmailException(SEND_EMAIL_FAILED);
        }
    }
}
