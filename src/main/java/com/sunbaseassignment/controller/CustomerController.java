package com.sunbaseassignment.controller;

import com.sunbaseassignment.model.Customer;
import com.sunbaseassignment.service.CustomerService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;


@Controller
public class CustomerController {
    
    private final CustomerService customerService;
    
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @RequestMapping("/home")
    public String home(Model model, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size){

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        model.addAttribute("customerList", customerPage.getContent());
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("totalPages", customerPage.getTotalPages());

        return "home";
    }
    
    @PostMapping("/createCustomer")
    public String createCustomer(){
        return "createCustomer";
    }
    
    @GetMapping("/addCustomer")
    public String addCustomer(Customer customer, Model model){
        customer.setCustomerId(UUID.randomUUID().toString());
        customerService.createCustomer(customer);
        List<Customer> customerList= customerService.viewAllCustomer();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        model.addAttribute("customerList", customerPage.getContent());
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("totalPages", customerPage.getTotalPages());
        return "home";
    }
    
    @PostMapping("/updateCustomer")
    public String updateCustomer(Customer customer, Model model){
        Customer updatedCustomer = customerService.updateCustomer(customer);
        List<Customer> customerList= customerService.viewAllCustomer();
        model.addAttribute("isUpdated", Objects.nonNull(updatedCustomer));
        model.addAttribute("customerList", customerList);
        return "home";
    }
    
    @PostMapping("/removeCustomer")
    public String removeCustomer(@RequestParam String customerId, Model model){
        customerService.removeCustomerById(customerId);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        model.addAttribute("customerList", customerPage.getContent());
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("totalPages", customerPage.getTotalPages());
        return "home";
    }

    @PostMapping("/editCustomer")
    public String editCustomer(@RequestParam  String customerId, Model model){
        List<Customer> customerList= customerService.viewAllCustomer();
        model.addAttribute("customerList", customerList);
        model.addAttribute("customerBeingEdited", customerId);
        return "home";
    }

    @GetMapping("/getCustomer/{page}")
    public ResponseEntity<Page<Customer>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/searchCustomers")
    public String searchCustomers(@RequestParam String searchField, @RequestParam String searchValue, Model model) {
        List<Customer> searchResults = customerService.searchCustomers(searchField, searchValue);
        model.addAttribute("customerList", searchResults);
        return "home";

    }

    @GetMapping("/customerList")
    public String showCustomerList(@RequestParam(name = "sortField", defaultValue = "customerId") String sortField,
                                   @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                   Model model) {
        List<Customer> customers = customerService.getAllCustomersSorted(sortField, sortDir);
        model.addAttribute("customerList", customers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "home";
    }
    
    

}
