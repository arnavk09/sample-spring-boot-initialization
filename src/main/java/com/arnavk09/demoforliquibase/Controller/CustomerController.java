package com.arnavk09.demoforliquibase.Controller;


import com.arnavk09.demoforliquibase.Entity.Customer;
import com.arnavk09.demoforliquibase.Repository.CustomerRepository;
import com.arnavk09.demoforliquibase.Service.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @GetMapping(value = "/customer/all", produces = "application/json")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("Called /customer/all->getAllCustomers() method");
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @PostMapping(value = "/savecustomer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        logger.info("called /savecustomer->saveCustomer() method");
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
    @GetMapping(value = "/customer/{id}",produces = "application/json")
    public ResponseEntity<Optional<Customer>>getCustomerById(@PathVariable Integer id){
        logger.info("called /customer/{id}->getCustomerById() method");
        Optional<Customer> customerById=customerService.getCustomerById(id);
        if (customerById.isPresent()) {
            return new ResponseEntity<>(customerById, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
