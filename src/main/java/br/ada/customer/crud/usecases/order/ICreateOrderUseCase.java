package br.ada.customer.crud.usecases.order;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.model.Order;

public interface ICreateOrderUseCase {

    Order create(Customer customer);

}
