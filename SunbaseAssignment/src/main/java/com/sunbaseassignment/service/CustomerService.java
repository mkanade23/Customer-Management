package com.sunbaseassignment.service;

import com.sunbaseassignment.dao.CustomerRepository;
import com.sunbaseassignment.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    public void createCustomer(Customer customer){
        customerRepository.save(customer);
    }
    
    public void removeCustomerById(String cId){
        customerRepository.deleteById(cId);
    }
    
    public Customer updateCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    
    public List<Customer> viewAllCustomer(){
        return  customerRepository.findAll();
    }

    public Page<Customer> getAllCustomers(Pageable pageable) { return customerRepository.findAll(pageable);}

    public List<Customer> searchCustomers(String searchField, String searchValue) {
        switch (searchField) {
            case "all":
                return customerRepository.findAll();
            case "firstName":
                return customerRepository.findByFirstName(searchValue);
            case "lastName":
                return customerRepository.findByLastName(searchValue);
            case "street":
                return customerRepository.findByStreet(searchValue);
            case "address":
                return customerRepository.findByAddress(searchValue);
            case "city":
                return customerRepository.findByCity(searchValue);
            case "state":
                return customerRepository.findByState(searchValue);
            case "email":
                return customerRepository.findByEmail(searchValue);
            case "phone":
                return customerRepository.findByPhone(searchValue);



            // Add more cases for other fields as needed
            default:
                throw new IllegalArgumentException("Invalid search field: " + searchField);
        }
    }

    public List<Customer> getAllCustomersSorted(String sortField, String sortDir) {
        // Perform server-side sorting based on the provided field and direction
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortField);
        return customerRepository.findAll(sort);
    }

    public void syncWithCustomerList(List<Customer> customerList) {
        for (Customer customer : customerList) {
            // Check if the user already exists in the database based on some identifier (e.g., email)
            Customer existingCustomer = customerRepository.findById(customer.getCustomerId()).orElse(null);

            if (existingCustomer != null) {
                // Update existing user
                existingCustomer.setCustomerId(customer.getCustomerId());
                existingCustomer.setEmail(customer.getEmail());
                existingCustomer.setCity(customer.getCity());
                existingCustomer.setAddress(customer.getAddress());
                existingCustomer.setPhone(customer.getPhone());
                existingCustomer.setState(customer.getState());
                existingCustomer.setStreet(customer.getStreet());
                existingCustomer.setFirstName(customer.getFirstName());
                existingCustomer.setLastName(customer.getLastName());
                // Update other fields as needed
                customerRepository.save(existingCustomer);
            } else {
                // Create a new user
                Customer newUser = new Customer();
                newUser.setCustomerId(customer.getCustomerId());
                newUser.setEmail(customer.getEmail());
                newUser.setCity(customer.getCity());
                newUser.setAddress(customer.getAddress());
                newUser.setPhone(customer.getPhone());
                newUser.setState(customer.getState());
                newUser.setStreet(customer.getStreet());
                newUser.setFirstName(customer.getFirstName());
                newUser.setLastName(customer.getLastName());

                customerRepository.save(newUser);
            }
        }
    }
}
