package com.api.parqueadero.domain.ports.out;

import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;

import java.util.List;
import java.util.Optional;

public interface AssociatePersistencePort {

    RegistryModel createRegistry(RegistryModel registryModel);
    Long getLoggedAssociateId();
    List<RegistryModel> getAssociatedRegistries();
    Boolean checkExistingPlate(String plate);
    List<ParkingModel> getAssociatedParking(Long userId);
    void departureVehicle(RegistryModel registryModel);
    Optional<RegistryModel> findActiveRegistryByPlateAndParkingAndUser(String plate, Long parkingId, Long userId);
    List<RegistryModel> getAssociatedVehicles(List<Long> parkingIds);
    long countActiveRegistriesByParkingId(Long parkingId);
    ParkingModel getParkingById(Long parkingId);
    Boolean checkIfParkingIsAssociated(Long parkingId, Long userId);
}
