package com.arnavk09.demoforliquibase.Controller;

import com.arnavk09.demoforliquibase.Entity.Customer;
import com.arnavk09.demoforliquibase.Service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(1, "John Doe");
        customer2 = new Customer(2, "Jane Doe");
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<Customer> customerList = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customerList);

        mockMvc.perform(get("/api/v1/customer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(customer1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(customer1.getName())))
                .andExpect(jsonPath("$[1].id", is(customer2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(customer2.getName())));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(1)).thenReturn(Optional.of(customer1));

        mockMvc.perform(get("/api/v1/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(customer1.getName())));

        verify(customerService, times(1)).getCustomerById(1);
    }

    @Test
    void testSaveCustomer() throws Exception {
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer1);

        String customerJson = "{\"id\":1,\"name\":\"John Doe\"}";

        mockMvc.perform(post("/api/v1/savecustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(customer1.getName())));

        verify(customerService, times(1)).saveCustomer(any(Customer.class));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        when(customerService.getCustomerById(3)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/customer/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomerById(3);
    }
}
