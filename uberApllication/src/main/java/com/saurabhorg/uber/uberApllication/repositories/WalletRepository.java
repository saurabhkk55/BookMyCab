package com.saurabhorg.uber.uberApllication.repositories;

import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import com.saurabhorg.uber.uberApllication.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    Optional<WalletEntity> findByUser(UserEntity user);
}
