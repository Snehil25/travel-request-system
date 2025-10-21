package com.tx.travel_ms.travel.dto;

import lombok.Data;


@Data
public class UpdateRequestDto {
    private String purpose;
    private TravelDetailsDto travelDetails;
    private HotelBookingDto hotelBooking;
}
