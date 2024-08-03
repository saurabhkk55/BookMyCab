package com.saurabhorg.uber.uberApllication.strategies;

import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequestEntity rideRequest);
}
