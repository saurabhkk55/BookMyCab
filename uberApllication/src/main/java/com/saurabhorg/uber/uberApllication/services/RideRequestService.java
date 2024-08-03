package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;

public interface RideRequestService {
    RideRequestEntity findRideRequestById(Long rideRequestId);

    void update(RideRequestEntity rideRequest);
}
