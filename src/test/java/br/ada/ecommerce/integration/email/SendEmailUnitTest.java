package br.ada.ecommerce.integration.email;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class SendEmailUnitTest {

    private String from = "unit@test.com";
    private List<String> to = List.of("one@test.com", "two@test.com");
    private String subject = "unit-test";
    private String content = "unit-test";

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private SendEmail sendEmail;

    @BeforeEach
    public void setup() {
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(javaMailSender.createMimeMessage())
                .thenReturn(mimeMessage);
    }

    @Test
    public void envio_de_email_deve_respeitar_o_remetente() throws MessagingException {
        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

        sendEmail.send(from, to, subject, content);

        Mockito.verify(javaMailSender, Mockito.times(1))
                .send(captor.capture());//Captura o parâmetro passado na chamada real

        MimeMessage message = captor.getValue();//Recupera o parâmetro passado
        Mockito.verify(message, Mockito.times(1))
                .setFrom(from);
    }

    @Test
    public void envio_de_email_deve_respeitar_o_assunto() throws MessagingException {
        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

        sendEmail.send(from, to, subject, content);

        Mockito.verify(javaMailSender, Mockito.times(1))
                .send(captor.capture());//Captura o parâmetro passado na chamada real

        MimeMessage message = captor.getValue();//Recupera o parâmetro passado
        Mockito.verify(message, Mockito.times(1))
                .setSubject(subject);
    }

    @Test
    public void envio_de_email_deve_respeitar_o_destinatario() throws MessagingException {
        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);

        sendEmail.send(from, to, subject, content);

        Mockito.verify(javaMailSender, Mockito.times(1))
                .send(captor.capture());//Captura o parâmetro passado na chamada real

        MimeMessage message = captor.getValue();//Recupera o parâmetro passado
        Mockito.verify(message, Mockito.times(1))
                .setRecipients(Message.RecipientType.TO, "one@test.com,two@test.com");
    }

}
