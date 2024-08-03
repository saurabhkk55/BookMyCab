package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.dto.RideDTO;
import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.RideRequestStatus;
import com.saurabhorg.uber.uberApllication.entities.enums.RideStatus;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.DriverRepository;
import com.saurabhorg.uber.uberApllication.services.DriverService;
import com.saurabhorg.uber.uberApllication.services.RideRequestService;
import com.saurabhorg.uber.uberApllication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    private final RideService rideService;

    @Override
    public RideDTO acceptRide(Long rideRequestId) {
        System.out.println("START");
        RideRequestEntity rideRequestEntity = rideRequestService.findRideRequestById(rideRequestId);

        if (!rideRequestEntity.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("RideRequest cannot be accepted, status is " + rideRequestEntity.getRideRequestStatus());
        }

        DriverEntity currentDriver = getCurrentDriver();
        if (!currentDriver.getAvailable()) {
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }

        currentDriver.setAvailable(false);
        currentDriver.setVehicleId("Honda-Car");
        DriverEntity savedDriverEntity = driverRepository.save(currentDriver);
        System.out.println("done1");
        RideEntity rideEntity = rideService.createNewRide(rideRequestEntity, savedDriverEntity);
        System.out.println("done2");
        return modelMapper.map(rideEntity, RideDTO.class);
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {
        RideEntity rideEntity = rideService.getRideById(rideId);
        DriverEntity driverEntity = getCurrentDriver();

        if (!driverEntity.equals(rideEntity.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if (!rideEntity.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started, status: " + rideEntity.getRideStatus());
        }

        if (!otp.equals(rideEntity.getOtp())) {
            throw new RuntimeException("Otp is not valid, otp: " + otp);
        }

        rideEntity.setStartedAt(LocalDateTime.now());
        RideEntity savedRideEntity = rideService.updateRideStatus(rideEntity, RideStatus.ONGOING);

        return modelMapper.map(savedRideEntity, RideDTO.class);
    }

    @Override
    public DriverEntity getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Driver not found with " + "id " + 2));
    }
}
