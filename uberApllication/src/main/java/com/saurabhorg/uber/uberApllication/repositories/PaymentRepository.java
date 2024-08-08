package com.saurabhorg.uber.uberApllication.repositories;

import com.saurabhorg.uber.uberApllication.entities.PaymentEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByRide(RideEntity ride);
}
