package br.ada.customer.crud.usecases.repository;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);

}
