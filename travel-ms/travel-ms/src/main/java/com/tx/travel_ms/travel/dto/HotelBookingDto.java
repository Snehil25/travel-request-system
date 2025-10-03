package com.tx.travel_ms.travel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelBookingDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String preferredAddress;
}
