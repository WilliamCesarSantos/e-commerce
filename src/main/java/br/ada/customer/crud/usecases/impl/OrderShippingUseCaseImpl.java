package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.IOrderShippingUseCase;
import br.ada.customer.crud.usecases.IShippingNotifierUseCase;
import br.ada.customer.crud.usecases.repository.IOrderRepository;
import jakarta.transaction.Transactional;

public class OrderShippingUseCaseImpl implements IOrderShippingUseCase {

    private IOrderRepository orderRepository;
    private IShippingNotifierUseCase notifierUseCase;

    public OrderShippingUseCaseImpl(
            IOrderRepository orderRepository,
            IShippingNotifierUseCase notifierUseCase
    ) {
        this.orderRepository = orderRepository;
        this.notifierUseCase = notifierUseCase;
    }

    @Override
    @Transactional
    public void shipping(Order order) {
        if (order.getStatus() != OrderStatus.PAID) {
            throw new RuntimeException("Pedido ainda não pago.");
        }
        order.setStatus(OrderStatus.FINISH);
        orderRepository.save(order);
        notifierUseCase.notify(order);
    }

}
