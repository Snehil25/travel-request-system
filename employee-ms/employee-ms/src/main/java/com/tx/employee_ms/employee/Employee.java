package com.tx.employee_ms.employee;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;
    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    private String department;
    private String position;
    private String level;
    private String contact;
    private String emergencyContact;
    private String projectName;
    @Column(nullable = false,unique = true)
    private String email;
    private Long reportingManagerId;
}
