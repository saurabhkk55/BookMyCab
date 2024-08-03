package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.RideStatus;

public interface RideService {
    RideEntity getRideById(Long rideId);

    RideEntity createNewRide(RideRequestEntity rideRequestEntity, DriverEntity driver);

    RideEntity updateRideStatus(RideEntity ride, RideStatus rideStatus);
}
