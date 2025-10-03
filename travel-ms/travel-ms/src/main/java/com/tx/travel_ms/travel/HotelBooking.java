package com.tx.travel_ms.travel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class HotelBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String preferredAddress;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    @JsonBackReference
    private Request request;
}
