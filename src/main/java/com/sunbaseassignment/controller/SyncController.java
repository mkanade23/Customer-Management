package com.sunbaseassignment.controller;

import com.sunbaseassignment.config.AppConfig;
import com.sunbaseassignment.model.AccessTokenRequest;
import com.sunbaseassignment.model.AccessTokenResponse;
import com.sunbaseassignment.model.Customer;
import com.sunbaseassignment.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class SyncController {
    
    private final String BEARER_TOKEN_API = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private final String CUSTOMER_LIST_API = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    AppConfig appConfig;

    @GetMapping("/sync")
    public String sync(Model model) {
        // Make the first API call to get the access token
        ResponseEntity<AccessTokenResponse> accessTokenResponseEntity = restTemplate.postForEntity(
                BEARER_TOKEN_API,
                new AccessTokenRequest(appConfig.getHttpClientUserName(), appConfig.getHttpClientUserPassword()),
                AccessTokenResponse.class);
        List<Customer> customerList = List.of();

        if (accessTokenResponseEntity.getStatusCode() == HttpStatus.OK) {
            // Extract access token from the response
            String accessToken = accessTokenResponseEntity.getBody().getAccessToken();

            // Make the second API call using the access token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Customer>> customerListResponseEntity = restTemplate.exchange(
                    CUSTOMER_LIST_API,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Customer>>() {});

            if (customerListResponseEntity.getStatusCode() == HttpStatus.OK) {
                customerList = customerListResponseEntity.getBody();
            } else {
                model.addAttribute("error", "Error fetching customer list");
            }
        } else {
            model.addAttribute("error", "Error getting access token");
        }

        customerService.syncWithCustomerList(customerList);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        model.addAttribute("customerList", customerPage.getContent());
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("totalPages", customerPage.getTotalPages());

        return "home";
    }
}

