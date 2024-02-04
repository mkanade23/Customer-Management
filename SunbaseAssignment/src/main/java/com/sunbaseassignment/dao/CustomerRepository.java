package com.sunbaseassignment.dao;

import com.sunbaseassignment.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
    List<Customer> findByStreet(String street);
    List<Customer> findByAddress(String address);
    List<Customer> findByCity(String city);
    List<Customer> findByState(String state);
    List<Customer> findByEmail(String email);
    List<Customer> findByPhone(String phone);

}
