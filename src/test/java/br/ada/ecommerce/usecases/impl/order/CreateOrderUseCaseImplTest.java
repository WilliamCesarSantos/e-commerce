package br.ada.ecommerce.usecases.impl.order;

import br.ada.ecommerce.model.Customer;
import br.ada.ecommerce.model.Order;
import br.ada.ecommerce.model.OrderStatus;
import br.ada.ecommerce.usecases.repository.ICustomerRepository;
import br.ada.ecommerce.usecases.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CreateOrderUseCaseImplTest {

    private Customer customer;

    //1
    private CreateOrderUseCaseImpl useCase;

    //2 objetos
    private IOrderRepository orderRepository;
    private ICustomerRepository customerRepository;

    //3
    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setDocument("unit-test");

        orderRepository = Mockito.mock(IOrderRepository.class);
        customerRepository = Mockito.mock(ICustomerRepository.class);

        useCase = new CreateOrderUseCaseImpl(orderRepository, customerRepository);

        Mockito.when(customerRepository.findByDocument(Mockito.any()))/*Método que será chamado pela execução do programa*/
                .thenReturn(customer);/*Retorno que deve ser feito para essa chamada*/
    }

    @Test
    public void cliente_invalido_deve_retornar_excecao() {
        Mockito.when(customerRepository.findByDocument(Mockito.any()))/*Método que será chamado pela execução do programa*/
                .thenReturn(null);/*Retorno que deve ser feito para essa chamada*/

        Customer customer = new Customer();

        Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.create(customer));//Lambda
    }

    @Test
    public void cadastrar_pedido_para_cliente_valido() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
    }

    @Test
    public void pedido_cadastrado_deve_ter_estado_igual_a_aberto() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
        Assertions.assertEquals(OrderStatus.OPEN, created.getStatus());
    }

    @Test
    public void pedido_deve_ter_endereco() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getShippingAddress());
    }

    @Test
    public void pedido_criado_deve_estar_sem_items() {
        Order created = useCase.create(customer);

        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.getItems().isEmpty());
    }

}
