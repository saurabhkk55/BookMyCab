package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.dto.DriverDTO;
import com.saurabhorg.uber.uberApllication.dto.SignUpDTO;
import com.saurabhorg.uber.uberApllication.dto.UserDTO;
import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.Role;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.exceptions.RuntimeConflictException;
import com.saurabhorg.uber.uberApllication.repositories.UserRepository;
import com.saurabhorg.uber.uberApllication.security.JWTService;
import com.saurabhorg.uber.uberApllication.services.AuthService;
import com.saurabhorg.uber.uberApllication.services.DriverService;
import com.saurabhorg.uber.uberApllication.services.RiderService;
import com.saurabhorg.uber.uberApllication.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.saurabhorg.uber.uberApllication.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    @Transactional
    public UserDTO singUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if (userEntity.isPresent()) {
            throw new RuntimeConflictException("SignUp can't be done as user with " + userEntity.get().getEmail() + " already exist.");
        }

        UserEntity toBeCreatedNewUserEntity = modelMapper.map(signUpDTO, UserEntity.class);
        toBeCreatedNewUserEntity.setRoles(Set.of(Role.RIDER));
        toBeCreatedNewUserEntity.setPassword(passwordEncoder.encode(toBeCreatedNewUserEntity.getPassword()));
        UserEntity savedUserEntity = userRepository.save(toBeCreatedNewUserEntity);

        // create user related entities
        riderService.createNewRider(savedUserEntity);
        walletService.createNewWallet(savedUserEntity);

        return modelMapper.map(savedUserEntity, UserDTO.class);
    }

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }

    @Override
    public DriverDTO onboardNewDriver(Long userId, String vehicleId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));

        if(userEntity.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("User with id "+userId+" is already a Driver");

        DriverEntity createDriverEntity = DriverEntity.builder()
                .user(userEntity)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        userEntity.getRoles().add(DRIVER);
        userRepository.save(userEntity);
        DriverEntity savedDriver = driverService.createNewDriver(createDriverEntity);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }
}
