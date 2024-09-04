package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.TestContainerConfiguration;
import com.saurabhorg.uber.uberApllication.dto.RideRequestDTO;
import com.saurabhorg.uber.uberApllication.entities.RideRequestEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.repositories.RideRequestRepository;
import com.saurabhorg.uber.uberApllication.services.impl.RiderServiceImpl;
import com.saurabhorg.uber.uberApllication.strategies.RideStrategyManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
class RiderServiceImplTest {

    @InjectMocks
    private RiderServiceImpl riderService;

    @Mock
    private RideRequestRepository rideRequestRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RideStrategyManager rideStrategyManager;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private RiderEntity mockRider;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockRider);
    }

    @Test
    void testRequestRide_whenValidRequestDTOPassed_thenReturnRideRequestDTO() {
        // Setup your mocks for rideRequestRepository, modelMapper, rideStrategyManager, etc.
        // For example:
        RideRequestDTO rideRequestDTO = new RideRequestDTO();
        RideRequestEntity rideRequestEntity = new RideRequestEntity();
        when(modelMapper.map(rideRequestDTO, RideRequestEntity.class)).thenReturn(rideRequestEntity);
        when(rideRequestRepository.save(any(RideRequestEntity.class))).thenReturn(rideRequestEntity);
        // Call the method to test
        riderService.requestRide(rideRequestDTO);
        // Add assertions as needed
    }
}
