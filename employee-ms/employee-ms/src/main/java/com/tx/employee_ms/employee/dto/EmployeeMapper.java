package com.tx.employee_ms.employee.dto;

import com.tx.employee_ms.employee.Employee;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee){
        if(employee==null){
            return null;
        }
        EmployeeDto dto=new EmployeeDto();
        dto.setEmployee_id(employee.getEmployee_id());
        dto.setFirstName(employee.getFirstName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setLastName(employee.getLastName());
        dto.setDepartment(employee.getDepartment());
        dto.setPosition(employee.getPosition());
        dto.setLevel(employee.getLevel());
        dto.setContact(employee.getContact());
        dto.setEmergencyContact(employee.getEmergencyContact());
        dto.setProjectName(employee.getProjectName());
        dto.setEmail(employee.getEmail());
        dto.setReportingManagerId(employee.getReportingManagerId());
        return dto;
    }
    public static Employee toEntity(CreateRequestDto dto){
        if(dto==null){
            return null;
        }
        Employee employee=new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setMiddleName(dto.getMiddleName());
        employee.setLastName(dto.getLastName());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        employee.setLevel(dto.getLevel());
        employee.setContact(dto.getContact());
        employee.setEmergencyContact(dto.getEmergencyContact());
        employee.setProjectName(dto.getProjectName());
        employee.setEmail(dto.getEmail());
        employee.setReportingManagerId(dto.getReportingManagerId());
        return employee;
    }
}
