package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.dto.RideRequestDTO;
import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.RideRequestStatus;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.RideRequestRepository;
import com.saurabhorg.uber.uberApllication.repositories.RiderRepository;
import com.saurabhorg.uber.uberApllication.services.RiderService;
import com.saurabhorg.uber.uberApllication.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;

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

    public RiderEntity getCurrentRider() {
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("No rider exist"));
    }
}
