package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.dto.*;
import com.saurabhorg.uber.uberApllication.entities.*;
import com.saurabhorg.uber.uberApllication.entities.enums.RideRequestStatus;
import com.saurabhorg.uber.uberApllication.entities.enums.RideStatus;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.DriverRepository;
import com.saurabhorg.uber.uberApllication.repositories.RideRepository;
import com.saurabhorg.uber.uberApllication.repositories.RideRequestRepository;
import com.saurabhorg.uber.uberApllication.repositories.RiderRepository;
import com.saurabhorg.uber.uberApllication.services.DriverService;
import com.saurabhorg.uber.uberApllication.services.RideService;
import com.saurabhorg.uber.uberApllication.services.RiderService;
import com.saurabhorg.uber.uberApllication.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final RideRequestRepository rideRequestRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRepository rideRepository;
    private final RideService rideService;
    private final DriverService driverService;

    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        RiderEntity currentRider = getCurrentRider();
        RideRequestEntity rideRequestEntity = modelMapper.map(rideRequestDTO, RideRequestEntity.class);
        rideRequestEntity.setRider(currentRider);
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.PENDING);
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequestEntity);
        rideRequestEntity.setFare(fare);
        List<DriverEntity> drivers = rideStrategyManager.driverMatchingStrategy(currentRider.getRating()).findMatchingDriver(rideRequestEntity);
        // TODO : Send notification to all the drivers about this ride request
        RideRequestEntity savedRideRequestEntity = rideRequestRepository.save(rideRequestEntity);
        return modelMapper.map(savedRideRequestEntity, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        RiderEntity rider = getCurrentRider();
        RideEntity ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException(("Rider does not own this ride with id: "+rideId));
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        RideEntity savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(RatingDTO ratingDTO) {
        RideEntity ride = rideService.getRideById(ratingDTO.getRideId());

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            // throw new RuntimeException("Rating option will be enabled once ride status is ENDED. Currently, ride status is " + ride.getRideStatus());
            throw new RuntimeException("Rating option will be enabled once you reached your destination safely 😊." + ride.getRideStatus());
        }

        // Avoid rating again if it is already done // Implement

        DriverEntity driver = ride.getDriver();
        double newAverageRating = ((driver.getRating() * driver.getRatingCount()) + ratingDTO.getRating()) / (driver.getRatingCount() + 1);
        // get the desired rating with one decimal place
        newAverageRating = Math.round(newAverageRating * 10) / 10.0;

        driver.setRating(newAverageRating);
        driver.setRatingCount(driver.getRatingCount() + 1);

        DriverEntity savedDriver = driverRepository.save(driver);

        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public RiderDTO getMyProfile() {
        RiderEntity currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        RiderEntity currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDTO.class)
        );
    }

    @Override
    public RiderEntity createNewRider(UserEntity user) {
        RiderEntity rider = RiderEntity
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    public RiderEntity getCurrentRider() {
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("No rider exist"));
    }
}
