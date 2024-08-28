package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.RideDTO;
import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.RideRequestStatus;
import com.saurabhorg.uber.uberApllication.entities.enums.RideStatus;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.DriverRepository;
import com.saurabhorg.uber.uberApllication.services.DriverService;
import com.saurabhorg.uber.uberApllication.services.PaymentService;
import com.saurabhorg.uber.uberApllication.services.RideRequestService;
import com.saurabhorg.uber.uberApllication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    private final RideService rideService;
    private final PaymentService paymentService;

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

        currentDriver.setVehicleId("Honda-Car");
        DriverEntity savedDriverEntity = updateDriverAvailability(currentDriver, false);

        RideEntity rideEntity = rideService.createNewRide(rideRequestEntity, savedDriverEntity);
        return modelMapper.map(rideEntity, RideDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        RideEntity ride = rideService.getRideById(rideId);

        DriverEntity driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);

        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    @Transactional
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

        paymentService.createNewPayment(savedRideEntity);

        return modelMapper.map(savedRideEntity, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride status is not ONGOING hence cannot be ended, status: "+ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        RideEntity savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public DriverDTO getMyProfile() {
        DriverEntity currentDriverEntity = getCurrentDriver();
        return modelMapper.map(currentDriverEntity, DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        DriverEntity currentDriverEntity = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriverEntity, pageRequest)
                .map(ride -> modelMapper.map(ride, RideDTO.class));
    }

    @Override
    public DriverEntity updateDriverAvailability(DriverEntity driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public DriverEntity getCurrentDriver() {
        // return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Driver not found with " + "id " + 2));

        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(userEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with " +
                        "id "+userEntity.getUserId()));
    }

    @Override
    public DriverEntity createNewDriver(DriverEntity driver) {
        return driverRepository.save(driver);
    }
}
