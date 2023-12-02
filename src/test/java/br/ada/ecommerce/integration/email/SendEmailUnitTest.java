package br.ada.ecommerce.integration.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

public class SendEmailUnitTest {

    private String from = "unit@test.com";
    private List<String> to =  List.of("unit@test.com.br");
    private String subject = "unit-test";
    private String content = "unit-test";

    private SendEmail sendEmail;
    private JavaMailSender javaMailSender;

    @BeforeEach
    public void setup() {
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        javaMailSender = Mockito.mock(JavaMailSender.class);

        sendEmail = new SendEmail(javaMailSender);

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

}
