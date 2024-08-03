package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.dto.SignUpDTO;
import com.saurabhorg.uber.uberApllication.dto.UserDTO;

public interface AuthService {
    UserDTO singUp(SignUpDTO signUpDTO);
}
