package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.RideDTO;
import com.saurabhorg.uber.uberApllication.dto.RideRequestDTO;
import com.saurabhorg.uber.uberApllication.dto.RiderDTO;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {
    RideRequestDTO requestRide(RideRequestDTO rideRequestDto);

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    RiderEntity createNewRider(UserEntity user);
}
