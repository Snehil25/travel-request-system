package com.tx.employee_ms.employee;

import com.tx.employee_ms.employee.dto.CreateRequestDto;
import com.tx.employee_ms.employee.dto.EmployeeDto;
import com.tx.employee_ms.employee.dto.EmployeeMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeRepository employeeRepository,EmployeeService employeeService){
        this.employeeRepository=employeeRepository;
        this.employeeService=employeeService;
    }
    @PostMapping
    public EmployeeDto createEmployee(@Valid @RequestBody CreateRequestDto requestDto){
        Employee savedEmployee=employeeService.createEmployee(requestDto);
        return EmployeeMapper.toDto(savedEmployee);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        return employeeRepository.findById(id)
                .map(EmployeeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<EmployeeDto> getAllEmployees(){
        return employeeRepository.findAll()
                .stream().map(EmployeeMapper::toDto).collect(Collectors.toList());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody CreateRequestDto createRequestDto){
        return employeeRepository.findById(id)
                .map(existingEmployee->{
                    existingEmployee.setFirstName(createRequestDto.getFirstName());
                    existingEmployee.setMiddleName(createRequestDto.getMiddleName());
                    existingEmployee.setLastName(createRequestDto.getLastName());
                    existingEmployee.setDepartment(createRequestDto.getDepartment());
                    existingEmployee.setPosition(createRequestDto.getPosition());
                    existingEmployee.setLevel(createRequestDto.getLevel());
                    existingEmployee.setContact(createRequestDto.getContact());
                    existingEmployee.setEmergencyContact(createRequestDto.getEmergencyContact());
                    existingEmployee.setProjectName(createRequestDto.getProjectName());
                    existingEmployee.setEmail(createRequestDto.getEmail());
                    existingEmployee.setReportingManagerId(createRequestDto.getReportingManagerId());
                    Employee updatedEmployee=employeeRepository.save(existingEmployee);
                    return ResponseEntity.ok(EmployeeMapper.toDto(updatedEmployee));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        if(!employeeRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteEmployeeAndRequests(id);
        return ResponseEntity.noContent().build();
    }
}
