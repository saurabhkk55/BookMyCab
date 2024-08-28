package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.SignUpDTO;
import com.saurabhorg.uber.uberApllication.dto.UserDTO;

public interface AuthService {
    String[] login(String email, String password);

    String refreshToken(String refreshToken);

    UserDTO singUp(SignUpDTO signUpDTO);

    DriverDTO onboardNewDriver(Long userId, String vehicleId);
}
