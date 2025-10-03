package com.tx.employee_ms.employee.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long employee_id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String department;
    private String position;
    private String level;
    private String contact;
    private String emergencyContact;
    private String projectName;
    private String email;
    private Long reportingManagerId;
}
