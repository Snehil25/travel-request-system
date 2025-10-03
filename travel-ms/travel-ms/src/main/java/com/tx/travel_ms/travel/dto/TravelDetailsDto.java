package com.tx.travel_ms.travel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelDetailsDto {
    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private LocalDate returnDate;
}
