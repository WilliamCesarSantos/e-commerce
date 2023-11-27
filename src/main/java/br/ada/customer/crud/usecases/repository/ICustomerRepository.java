package br.ada.customer.crud.usecases.repository;

import br.ada.customer.crud.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByDocument(String document);

    List<Customer> findByName(String name);

}
