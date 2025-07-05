package com.amigoscode.fraud.customer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository  customerRepository,
                              RestTemplate restTemplate) {
    public void registerCustomer
            (CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email is valid
        //todo: check if email is not taken
        //todo: check if a customer is fraudster
        customerRepository.save(customer);
        FraudCheckReponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckReponse.class,
                customer.getId()
        );

        if(fraudCheckResponse.isFraudster()){
            new IllegalStateException("Fraudster!");
        }
        //todo: send notification
    }
}
