package com.saurabhorg.uber.uberApllication.repositories;

import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<RiderEntity, Long> {
    Optional<RiderEntity> findByUser(UserEntity userEntity);
}
