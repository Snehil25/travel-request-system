package com.tx.employee_ms.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRequestDto {
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
    private String department;
    private String position;
    private String level;
    private String contact;
    private String emergencyContact;
    private String projectName;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    private Long reportingManagerId;
}
