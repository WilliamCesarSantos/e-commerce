package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.usecases.ICustomerUseCase;
import br.ada.customer.crud.usecases.INotifierUserCase;
import br.ada.customer.crud.usecases.repository.ICustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUseCaseImpl implements ICustomerUseCase {

    private ICustomerRepository repository;
    private INotifierUserCase<Customer> notifier;

    public CustomerUseCaseImpl(ICustomerRepository repository, INotifierUserCase<Customer> notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    @Override
    @Transactional
    public void create(Customer customer) {
        repository.save(customer);
        notifier.registered(customer);
    }

    @Override
    public List<Customer> list() {
        return repository.findAll();
    }

    @Override
    public Customer findByDocument(String document) {
        Customer found = null;
        if (document != null) {
            found = repository.findByDocument(document);
        }
        return found;
    }

    @Override
    public List<Customer> findByName(String name) {
        List<Customer> found = new ArrayList<>();
        if (name != null) {
            found = repository.findByName(name);
        }
        return found;
    }

    @Override
    @Transactional
    public void update(Customer customer) {
        repository.save(customer);
        notifier.updated(customer);
    }

    @Override
    @Transactional
    public Customer delete(Long id) {
        Customer customer = repository.findById(id).orElse(null);
        if (customer != null) {
            repository.delete(customer);
            notifier.deleted(customer);
        }
        return customer;
    }

}
