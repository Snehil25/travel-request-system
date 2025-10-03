package com.tx.travel_ms.travel;

import com.tx.travel_ms.travel.dto.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private Long employeeId;
    private String purpose;
    private LocalDate requestDate;
    @Enumerated(EnumType.STRING)
    private RequestStatus approvalStatus;
    private Long approvedBy;
    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private TravelDetails travelDetails;
    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private HotelBooking hotelBooking;
}
