package dev.kerem.library_app.service;

import dev.kerem.library_app.dto.CustomerDto;
import dev.kerem.library_app.entity.Customer;
import dev.kerem.library_app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerDto(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail(), savedCustomer.getPhoneNumber());
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber());
        }
        return null;
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return new CustomerDto(updatedCustomer.getId(), updatedCustomer.getName(), updatedCustomer.getEmail(), updatedCustomer.getPhoneNumber());
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}