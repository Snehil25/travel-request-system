package com.tx.travel_ms.travel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class TravelDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;
    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private LocalDate returnDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    @JsonBackReference
    private Request request;
}
