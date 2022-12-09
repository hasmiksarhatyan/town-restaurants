package am.itspace.townrestaurantscommon.service.impl;


import am.itspace.townrestaurantscommon.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);
        javaMailSender.send(mimeMessage);
        log.info("The email was sent successfully");
    }
}
