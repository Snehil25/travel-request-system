package com.tx.travel_ms.travel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    private Long requestId;
    private Long employeeId;
    private String purpose;
    private LocalDate requestDate;
    private RequestStatus approvalStatus;
    private Long approvedBy;
    private TravelDetailsDto travelDetails;
    private HotelBookingDto hotelBooking;
}
