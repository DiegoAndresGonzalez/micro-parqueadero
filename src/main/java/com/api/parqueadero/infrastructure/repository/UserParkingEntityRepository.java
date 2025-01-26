package com.api.parqueadero.infrastructure.repository;

import com.api.parqueadero.infrastructure.entities.ParkingEntity;
import com.api.parqueadero.infrastructure.entities.UserEntity;
import com.api.parqueadero.infrastructure.entities.UserParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserParkingEntityRepository extends JpaRepository<UserParkingEntity, Long> {

    List<UserParkingEntity> findByUserEntityId(Long userId);

    @Modifying
    @Query("UPDATE usuario_parqueadero up SET up.userEntity = :userEntity WHERE up.parkingEntity.id = :parkingId")
    Boolean existsByUserEntityAndParkingEntity(UserEntity userEntity, ParkingEntity parkingEntity);
    Boolean existsByUserEntityIdAndParkingEntityId(Long userEntityId, Long parkingId) ;
}
