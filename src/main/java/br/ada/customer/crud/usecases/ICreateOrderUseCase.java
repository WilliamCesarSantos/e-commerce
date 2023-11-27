package br.ada.customer.crud.usecases;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.model.Order;

public interface ICreateOrderUseCase {

    Order create(Customer customer);

}
