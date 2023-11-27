package br.ada.customer.crud.integration.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SendEmail {

    private JavaMailSender emailSender;

    public SendEmail(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(String from, List<String> to, String subject, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom(from);
        message.setRecipients(MimeMessage.RecipientType.TO, to.stream().collect(Collectors.joining(",")));
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
    }

}
