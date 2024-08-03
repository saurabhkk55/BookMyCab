package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.dto.RideDTO;
import com.saurabhorg.uber.uberApllication.entities.DriverEntity;

public interface DriverService {
    RideDTO acceptRide(Long rideRequestId);

    RideDTO startRide(Long rideId, String otp);

    DriverEntity getCurrentDriver();
}
