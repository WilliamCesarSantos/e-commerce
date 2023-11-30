package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.order.IShippingNotifierUseCase;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class OrderShippingUseCaseImplUnitTest {

    private OrderShippingUseCaseImpl useCase;
    private Order order;

    private IOrderRepository repository;
    private IShippingNotifierUseCase notifier;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IOrderRepository.class);
        notifier = Mockito.mock(IShippingNotifierUseCase.class);

        useCase = new OrderShippingUseCaseImpl(repository, notifier);
    }

    @Test
    public void pedido_so_deve_ser_entregue_se_estiver_pago() {
        // Criar dados compatíveis com os testes
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        //Chamar a execução dos testes
        useCase.shipping(order);
    }

    @Test
    public void pedido_com_status_diferente_de_pago_deve_retornar_excecao() {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);

        Assertions.assertThrows(RuntimeException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        useCase.shipping(order);
                    }
                });
    }

    @Test
    public void notificar_cliente_sobre_a_entregue() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.shipping(order);

        Mockito.verify(notifier, Mockito.times(1))
                .notify(order);
    }

    @Test
    public void pedido_entregue_deve_ficar_com_status_de_finalizado() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);

        useCase.shipping(order);

        Assertions.assertEquals(OrderStatus.FINISH, order.getStatus());
        Mockito.verify(repository, Mockito.times(1))
                .save(order);
    }

}
