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
import com.saurabhorg.uber.uberApllication.services.AuthService;
import com.saurabhorg.uber.uberApllication.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final DriverService driverService;

    @Override
    @Transactional
    public UserDTO singUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if (userEntity.isPresent()) {
            throw new RuntimeConflictException("SignUp can't be done as user with " + userEntity.get().getEmail() + " already exist.");
        }
        UserEntity newUserEntity = modelMapper.map(signUpDTO, UserEntity.class);
        newUserEntity.setRoles(Set.of(Role.RIDER));
        userRepository.save(newUserEntity);
        UserDTO newUserDTO = modelMapper.map(newUserEntity, UserDTO.class);

        // create user related entities
        // riderService.createNewRider(savedUser);
        // TODO add wallet related service here

        return newUserDTO;
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
