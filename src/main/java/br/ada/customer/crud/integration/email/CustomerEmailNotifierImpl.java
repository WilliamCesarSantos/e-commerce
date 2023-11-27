package br.ada.customer.crud.integration.email;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.usecases.INotifierUserCase;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class CustomerEmailNotifierImpl implements INotifierUserCase<Customer> {

    private SendEmail sendEmail;

    public CustomerEmailNotifierImpl(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public void registered(Customer customer) {
        try {
            if (hasEmail(customer)) {
                sendEmail.send("comunicado@ada.com.br", customer.getEmail(), "Bem vindo.", "Bem vindo. Click no link abaixo para confirmar seu cadastro.");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updated(Customer customer) {
        try {
            if (hasEmail(customer)) {
                sendEmail.send("comunicado@ada.com.br", customer.getEmail(), "Confira as últimas atualizações", "Suas informações foram atualizadas. Caso tenha sido você mesmo, pode ignorar esse email.");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleted(Customer customer) {
        try {
            if (hasEmail(customer)) {
                sendEmail.send("comunicado@ada.com.br", customer.getEmail(), "Estamos tristes agora. Espero que seja um até logo", "Sentiremos sua falta e esperamos seu retorno logo.");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasEmail(Customer customer) {
        return customer.getEmail() != null && !customer.getEmail().isEmpty();
    }
}
