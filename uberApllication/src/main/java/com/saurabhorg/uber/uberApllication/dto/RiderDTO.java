package com.saurabhorg.uber.uberApllication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderDTO {
    private Long id;

    private Double rating;

    private UserDTO user;
}
