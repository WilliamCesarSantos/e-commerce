package br.ada.customer.crud.usecases.order;

import br.ada.customer.crud.model.Order;

public interface IShippingNotifierUseCase {

    void notify(Order order);

}
