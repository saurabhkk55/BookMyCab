package com.saurabhorg.uber.uberApllication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDTO {
    private Long id;

    private Double rating;

    private Boolean available;

    @JsonProperty("vehicle_id")
    private String vehicleId;

    PointDTO currentLocation;

    private UserDTO user;
}
