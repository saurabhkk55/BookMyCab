package com.saurabhorg.uber.uberApllication.controllers;

import com.saurabhorg.uber.uberApllication.dto.RideRequestDTO;
import com.saurabhorg.uber.uberApllication.services.RiderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rider")
@AllArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping
    public RideRequestDTO requestRide(@RequestBody RideRequestDTO rideRequestDTO) {
        return riderService.requestRide(rideRequestDTO);
    }
}
