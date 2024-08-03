package com.saurabhorg.uber.uberApllication.dto;

import com.saurabhorg.uber.uberApllication.entities.enums.PaymentMethod;
import com.saurabhorg.uber.uberApllication.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideRequestDTO {
    private Long id;

    private PointDTO pickUpLocation;

    private PointDTO dropOffLocation;

    private LocalDateTime requestedTime;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;

    private Double fare;

    private RiderDTO rider;
}
