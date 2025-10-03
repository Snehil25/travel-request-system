package com.tx.employee_ms.employee;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "travel-service",url = "http://localhost:8082")
public interface TravelServiceClient {
    @DeleteMapping("/requests/by-employee/{employeeId}")
    void deleteRequestByEmployee(@PathVariable("employeeId") Long employeeId);
}
