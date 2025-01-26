package com.api.parqueadero.infrastructure.repository;

import com.api.parqueadero.infrastructure.entities.RegistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface RegistryEntityRepository extends JpaRepository<RegistryEntity, Long> {


    Boolean existsByPlateAndDepartureIsNull(String plate);

    List<RegistryEntity> findByParkingEntityIdAndDepartureIsNull(Long id);

    List<RegistryEntity> findByPlateAndDepartureIsNullAndParkingEntityId(String plate, Long parkingId);

    Optional<RegistryEntity> findRegistryEntityByPlateAndParkingEntityIdAndUserEntityIdAndDepartureIsNull
            (String plate, Long parkingId, Long userId);

    @Query("SELECT r FROM historial r WHERE r.parkingEntity.id IN :parkingIds AND r.departure IS NULL")
    List<RegistryEntity> findParkedVehiclesByParkingIds(@Param("parkingIds") List<Long> parkingIds);

    @Query("SELECT COUNT(r) FROM historial r WHERE r.parkingEntity.id = :parkingId AND r.departure IS NULL")
    long countActiveRegistriesByParkingId(@Param("parkingId") Long parkingId);

    @Query("SELECT r FROM historial r " +
            "WHERE r.registrationCount = (" +
            "  SELECT MAX(r2.registrationCount) " +
            "  FROM historial r2 " +
            "  WHERE r2.plate = r.plate AND r2.parkingEntity.id = :parkingId" +
            ") " +
            "AND r.parkingEntity.id = :parkingId " +
            "ORDER BY r.registrationCount DESC " +
            "LIMIT 10")
    List<RegistryEntity> findDistinctByPlateWithMaxRegistrationCount(@Param("parkingId") Long parkingId);

    @Query("SELECT r FROM historial r " +
            "WHERE r.parkingEntity.id = :parkingId " +
            "AND r.departure IS NULL " +
            "AND (SELECT COUNT(r2) FROM historial r2 " +
            "     WHERE r2.plate = r.plate AND r2.parkingEntity.id = :parkingId) = 1")
    List<RegistryEntity> findFirstTimeParkedVehicles(@Param("parkingId") Long parkingId);

    List<RegistryEntity> findAllByParkingEntityId(Long parkingId);

    @Query("SELECT r FROM historial r WHERE r.entry BETWEEN :startDate AND :endDate")
    List<RegistryEntity> findByParkingEntityIdAndEntryBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
