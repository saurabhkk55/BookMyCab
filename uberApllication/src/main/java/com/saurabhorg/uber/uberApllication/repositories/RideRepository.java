package com.saurabhorg.uber.uberApllication.repositories;

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<RideEntity, Long> {

    Page<RideEntity> findByRider(RiderEntity rider, Pageable pageRequest);

    Page<RideEntity> findByDriver(DriverEntity driver, Pageable pageRequest);
}
