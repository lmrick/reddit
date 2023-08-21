package com.reddit.app.service;

import com.reddit.app.exceptions.SpringRedditException;
import com.reddit.app.models.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilderService mailContentBuilderService;

    public void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("rykilcc@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilderService.build(notificationEmail.getBody()));
        };
            try {
                mailSender.send(messagePreparator);
                log.info("Activation email sent!!");
            } catch (MailException e) {
                throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
            }
        }

}
