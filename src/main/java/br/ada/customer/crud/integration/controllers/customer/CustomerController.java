package br.ada.customer.crud.integration.controllers.customer;

import br.ada.customer.crud.model.Customer;
import br.ada.customer.crud.usecases.ICustomerUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private ICustomerUseCase customerUseCase;

    public CustomerController(ICustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @PostMapping
    public CustomerDto create(@Valid @RequestBody CustomerDto dto) {
        Customer customer = fromDto(dto);
        customerUseCase.create(customer);
        return toDto(customer);
    }

    @GetMapping
    public List<CustomerDto> list() {
        return customerUseCase.list().stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    private Customer fromDto(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setDocument(dto.getDocument());
        customer.setBirthDate(dto.getBirthDate());
        customer.setTelephone(dto.getTelephone());
        customer.setEmail(dto.getEmail());
        return customer;
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setDocument(customer.getDocument());
        dto.setBirthDate(customer.getBirthDate());
        dto.setTelephone(customer.getTelephone());
        dto.setEmail(customer.getEmail());
        return dto;
    }
}
