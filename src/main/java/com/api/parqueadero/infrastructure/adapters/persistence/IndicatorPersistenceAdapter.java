package com.api.parqueadero.infrastructure.adapters.persistence;

import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.ports.out.IndicatorPersistencePort;
import com.api.parqueadero.infrastructure.entities.RegistryEntity;
import com.api.parqueadero.infrastructure.mappers.RegistryEntityMapper;
import com.api.parqueadero.infrastructure.repository.RegistryEntityRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class IndicatorPersistenceAdapter implements IndicatorPersistencePort {

    private final RegistryEntityRepository registryEntityRepository;
    private final RegistryEntityMapper registryEntityMapper;

    @Override
    public List<RegistryModel> getTop10Vehicles(Long parkingId) {
        return registryEntityMapper.toModelList(
                registryEntityRepository
                        .findDistinctByPlateWithMaxRegistrationCount(parkingId)
        ) ;
    }

    @Override
    public List<RegistryModel> getFirstTimeVehicles(Long parkingId) {
        return registryEntityMapper.toModelList(
                registryEntityRepository.findFirstTimeParkedVehicles(parkingId)
        );
    }

    @Override
    public List<RegistryModel> calculateEarningsByParkingId(Long parkingId) {
        return registryEntityMapper.toModelList(registryEntityRepository
                .findAllByParkingEntityId(parkingId));
    }

    @Override
    public List<RegistryModel> calculateEarningsByEntryBetween(LocalDateTime entry, LocalDateTime departure) {
        return registryEntityMapper.toModelList(
                registryEntityRepository.findByParkingEntityIdAndEntryBetween(entry, departure)
        );
    }

    @Override
    public List<RegistryModel> getTop3ParkingByEarnings(LocalDateTime entry, LocalDateTime departure) {
        return registryEntityMapper.toModelList(
                registryEntityRepository.findByParkingEntityIdAndEntryBetween(entry, departure)
        );
    }


}
