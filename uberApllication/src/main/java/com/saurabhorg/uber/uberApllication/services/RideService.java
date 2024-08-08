package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Driver;

public interface RideService {
    RideEntity getRideById(Long rideId);

    RideEntity createNewRide(RideRequestEntity rideRequestEntity, DriverEntity driver);

    RideEntity updateRideStatus(RideEntity ride, RideStatus rideStatus);

    Page<RideEntity> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest);

    Page<RideEntity> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest);
}
