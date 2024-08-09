package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.RideRequestStatus;
import com.saurabhorg.uber.uberApllication.entities.enums.RideStatus;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.RideRepository;
import com.saurabhorg.uber.uberApllication.services.RideRequestService;
import com.saurabhorg.uber.uberApllication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public RideEntity getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));
    }

    @Override
    public RideEntity createNewRide(RideRequestEntity rideRequestEntity, DriverEntity driver) {
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        RideEntity rideEntity = modelMapper.map(rideRequestEntity, RideEntity.class);
        rideEntity.setRideStatus(RideStatus.CONFIRMED);
        rideEntity.setDriver(driver);
        rideEntity.setOtp(generateRandomOTP());
        rideEntity.setId(null);

        rideRequestService.update(rideRequestEntity);
        return rideRepository.save(rideEntity);
    }

    @Override
    public RideEntity updateRideStatus(RideEntity ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<RideEntity> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<RideEntity> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000); // Range: 0 to 9999
        return String.format("%04d", otpInt);      // 14 to 0014, 1 to 0001
    }
}
