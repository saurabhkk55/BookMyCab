package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.RideDTO;
import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public interface DriverService {
    RideDTO acceptRide(Long rideRequestId);

    RideDTO cancelRide(Long rideId);

    RideDTO startRide(Long rideId, String otp);

    DriverDTO getMyProfile();

    @Transactional
    RideDTO endRide(Long rideId);

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    DriverEntity updateDriverAvailability(DriverEntity driver, boolean available);

    DriverEntity getCurrentDriver();

    DriverEntity createNewDriver(DriverEntity driverEntity);
}
