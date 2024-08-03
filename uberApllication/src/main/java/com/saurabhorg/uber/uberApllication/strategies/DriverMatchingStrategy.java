package com.saurabhorg.uber.uberApllication.strategies;

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;

import java.util.List;

public interface DriverMatchingStrategy {
    List<DriverEntity> findMatchingDriver(RideRequestEntity rideRequest);
}
