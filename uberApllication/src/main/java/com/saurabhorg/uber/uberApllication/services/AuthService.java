package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.SignUpDTO;
import com.saurabhorg.uber.uberApllication.dto.UserDTO;

public interface AuthService {
    UserDTO singUp(SignUpDTO signUpDTO);

    DriverDTO onboardNewDriver(Long userId, String vehicleId);
}
