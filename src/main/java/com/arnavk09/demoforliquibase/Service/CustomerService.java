package com.arnavk09.demoforliquibase.Service;

import com.arnavk09.demoforliquibase.Entity.Customer;
import com.arnavk09.demoforliquibase.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Optional<Customer> getCustomerById(Integer id){
        return customerRepository.findById(id);
    }

}
