package com.amigoscode.fraud;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudController(FraudCheckService fraudCheckService) {

    @GetMapping(path = "{customerId}")
    public FraudCheckReponse isFraudster(@PathVariable("customerId") Integer customerId){
        boolean isFraudulent
                = fraudCheckService.isFraudulentCustomer(customerId);

        return new FraudCheckReponse(isFraudulent);
    }
}
