package com.api.parqueadero.infrastructure.adapters.persistence;

import com.api.parqueadero.domain.exceptions.BadUserRequestException;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.ports.out.AssociatePersistencePort;
import com.api.parqueadero.domain.utils.Constants;
import com.api.parqueadero.infrastructure.configuration.security.jwt.JwtUtils;
import com.api.parqueadero.infrastructure.entities.ParkingEntity;
import com.api.parqueadero.infrastructure.entities.RegistryEntity;
import com.api.parqueadero.infrastructure.entities.UserParkingEntity;
import com.api.parqueadero.infrastructure.exceptions.NotFoundException;
import com.api.parqueadero.infrastructure.mappers.ParkingEntityMapper;
import com.api.parqueadero.infrastructure.mappers.RegistryEntityMapper;
import com.api.parqueadero.infrastructure.repository.ParkingEntityRepository;
import com.api.parqueadero.infrastructure.repository.RegistryEntityRepository;
import com.api.parqueadero.infrastructure.repository.UserEntityRepository;
import com.api.parqueadero.infrastructure.repository.UserParkingEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AssociatePersistenceAdapter implements AssociatePersistencePort {

    private final JwtUtils jwtUtils;
    private final RegistryEntityRepository registryEntityRepository;
    private final RegistryEntityMapper registryEntityMapper;
    private final UserEntityRepository userEntityRepository;
    private final ParkingEntityMapper parkingEntityMapper;
    private final UserParkingEntityRepository userParkingEntityRepository;
    private final ParkingEntityRepository parkingEntityRepository;

    @Override
    public RegistryModel createRegistry(RegistryModel registryModel) {
        return registryEntityMapper.toRegistryModel
                (registryEntityRepository.save(
                        registryEntityMapper.toEntity(registryModel)));
    }

    @Override
    public Long getLoggedAssociateId() {
        String email = jwtUtils.getUsername(SecurityContextHolder
                .getContext().getAuthentication().getCredentials().toString());
        return userEntityRepository.findIdByEmail(email).describeConstable().
                orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
    }

    @Override
    public List<RegistryModel> getAssociatedRegistries() {
        return registryEntityMapper.toModelList(
                registryEntityRepository.findAll()
        );
    }

    @Override
    public Boolean checkExistingPlate(String plate) {
        return registryEntityRepository.existsByPlateAndDepartureIsNull(plate);
    }

    @Override
    public List<ParkingModel> getAssociatedParking(Long userId) {
        List<UserParkingEntity> userParkingEntities = userParkingEntityRepository
                .findByUserEntityId(userId);
        List<ParkingEntity> parkingEntities = userParkingEntities.stream()
                .map(UserParkingEntity::getParkingEntity)
                .toList();
        return parkingEntityMapper.toParkingModelList(parkingEntities);
    }

    @Override
    public void departureVehicle(RegistryModel registryModel) {
        List<RegistryEntity> registries = registryEntityRepository
                .findByPlateAndDepartureIsNullAndParkingEntityId(registryModel.getPlate()
                        ,registryModel.getParkingModel().getId());
        if (registries.isEmpty()) {
            throw new BadUserRequestException(Constants.NOT_PARKED);
        }
        RegistryEntity registryEntity = registries.get(0);
        registryEntity.setDeparture(LocalDateTime.now());
        registryEntityRepository.save(registryEntity);
    }

    @Override
    public Optional<RegistryModel> findActiveRegistryByPlateAndParkingAndUser(String plate,
                                                                             Long parkingId,
                                                                             Long userId) {
        return registryEntityMapper.toRegistryModelOptional(
                registryEntityRepository
                        .findRegistryEntityByPlateAndParkingEntityIdAndUserEntityIdAndDepartureIsNull(
                                plate, parkingId, userId)
        );
    }

    @Override
    public List<RegistryModel> getAssociatedVehicles(List<Long> parkingIds) {
        return registryEntityMapper.toModelList
                (registryEntityRepository.findParkedVehiclesByParkingIds(parkingIds));
    }

    @Override
    public long countActiveRegistriesByParkingId(Long parkingId) {
        return registryEntityRepository.countActiveRegistriesByParkingId(parkingId);
    }

    @Override
    public ParkingModel getParkingById(Long parkingId) {
        return parkingEntityMapper.toParkingModel
                (parkingEntityRepository.findById(parkingId).orElseThrow());
    }

    @Override
    public Boolean checkIfParkingIsAssociated(Long parkingId, Long userId) {
        return userParkingEntityRepository.existsByUserEntityIdAndParkingEntityId(userId, parkingId);
    }
}