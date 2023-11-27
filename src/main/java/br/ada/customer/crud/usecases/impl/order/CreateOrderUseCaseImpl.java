package br.ada.customer.crud.usecases.impl.order;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.order.ICreateOrderUseCase;
import br.ada.customer.crud.usecases.repository.ICustomerRepository;
import br.ada.customer.crud.usecases.repository.IOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CreateOrderUseCaseImpl implements ICreateOrderUseCase {

    private IOrderRepository repository;
    private ICustomerRepository customerRepository;

    public CreateOrderUseCaseImpl(IOrderRepository repository, ICustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Order create(Customer customer) {
        validCustomer(customer);
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(new ArrayList<>());
        order.setStatus(OrderStatus.OPEN);
        order.setShippingAddress("Minha casa sempre");
        order.setOrderedAt(LocalDateTime.now());
        repository.save(order);
        return order;
    }

    private void validCustomer(Customer customer) {
        Customer found = customerRepository.findByDocument(customer.getDocument());
        if (found == null) {
            throw new IllegalStateException("Cliente não encontrado");
        }
    }

}
