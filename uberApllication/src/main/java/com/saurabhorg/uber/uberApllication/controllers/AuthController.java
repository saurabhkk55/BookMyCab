package com.saurabhorg.uber.uberApllication.controllers;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.OnboardDriverDTO;
import com.saurabhorg.uber.uberApllication.dto.SignUpDTO;
import com.saurabhorg.uber.uberApllication.dto.UserDTO;
import com.saurabhorg.uber.uberApllication.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/signUp")
    public UserDTO singUp(@RequestBody SignUpDTO signUpDTO) {
        return authService.singUp(signUpDTO);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnboardDriverDTO onboardDriverDto) {
        return new ResponseEntity<>(authService.onboardNewDriver(userId, onboardDriverDto.getVehicleId()), HttpStatus.CREATED);
    }
}
