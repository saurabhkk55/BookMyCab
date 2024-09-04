package com.saurabhorg.uber.uberApllication.repositories;

import com.saurabhorg.uber.uberApllication.TestContainerConfiguration;
import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import java.util.Optional;
import java.util.Set;

import static com.saurabhorg.uber.uberApllication.entities.enums.Role.RIDER;

@Import(TestContainerConfiguration.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // This is the default behavior
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnUser() {
        // Arrange/Given
        UserEntity testUserEntity = UserEntity.builder()
                .userId(1L)
                .name("test_user")
                .email("testuser@gmail.com")
                .password("testUserPassword")
                .roles(Set.of(RIDER))
                .build();
        userRepository.save(testUserEntity);

        // Act/When
        Optional<UserEntity> foundUserEntity = userRepository.findByEmail(testUserEntity.getEmail());
        log.info("Found user: {}", foundUserEntity);

        // Assert/Then
        assertThat(foundUserEntity)
                .isPresent()
                .isEqualTo(Optional.of(testUserEntity));
    }

    @Test
    void testFindByEmail_whenEmailIsNotPresent_thenReturnEmptyUser() {
        // Arrange/Given
        String email = "emailNotPrsent@gmail.com";

        // Act/When
        Optional<UserEntity> foundUserEntity = userRepository.findByEmail(email);
        log.info("Found user: {}", foundUserEntity);

        // Assert/Then
        assertThat(foundUserEntity)
                .isNotPresent()
                .isEqualTo(Optional.empty());
    }
}