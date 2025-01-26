package com.api.parqueadero.infrastructure.repository;

import com.api.parqueadero.infrastructure.entities.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingEntityRepository extends JpaRepository<ParkingEntity, Long> {
    ParkingEntity findByName(String parkingName);
}

