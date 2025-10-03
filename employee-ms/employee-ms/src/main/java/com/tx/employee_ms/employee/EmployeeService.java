package com.tx.employee_ms.employee;

import com.tx.employee_ms.employee.dto.CreateRequestDto;
import com.tx.employee_ms.employee.dto.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TravelServiceClient travelServiceClient;

    public EmployeeService(EmployeeRepository employeeRepository,TravelServiceClient travelServiceClient){
        this.employeeRepository=employeeRepository;
        this.travelServiceClient=travelServiceClient;
    }
    public Employee createEmployee(CreateRequestDto createRequestDto){
        if (createRequestDto.getReportingManagerId()!=null){
            employeeRepository.findById(createRequestDto.getReportingManagerId())
                    .orElseThrow(()-> new RuntimeException("Invalid Reporting Manager Id : "+ createRequestDto.getReportingManagerId()));
        }
        Employee employee= EmployeeMapper.toEntity(createRequestDto);
        return employeeRepository.save(employee);
    }
    public void deleteEmployeeAndRequests(Long employeeId){
        try {
            travelServiceClient.deleteRequestByEmployee(employeeId);
        }catch (Exception e){
            throw new RuntimeException("Employee and Request not found by Employee ID: "+employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }
}
