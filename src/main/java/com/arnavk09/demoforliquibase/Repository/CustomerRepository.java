package com.arnavk09.demoforliquibase.Repository;

import com.arnavk09.demoforliquibase.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
