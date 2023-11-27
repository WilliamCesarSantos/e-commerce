package br.ada.customer.crud.usecases.repository;

import br.ada.customer.crud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByDescription(String description);

    Product findByBarcode(String barcode);

}
