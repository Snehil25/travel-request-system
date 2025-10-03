package com.tx.travel_ms.travel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "http://localhost:8081")
public interface EmployeeServiceClient {
    @GetMapping("employees/{id}")
    void getEmployeeById(@PathVariable("id") Long id);
}
