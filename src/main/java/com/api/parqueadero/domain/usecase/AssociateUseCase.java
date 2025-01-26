package com.api.parqueadero.domain.usecase;

import com.api.parqueadero.domain.exceptions.BadUserRequestException;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;
import com.api.parqueadero.domain.ports.in.AssociateServicePort;
import com.api.parqueadero.domain.ports.out.AssociatePersistencePort;
import com.api.parqueadero.domain.utils.Constants;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class AssociateUseCase implements AssociateServicePort {

    private final AssociatePersistencePort associatePersistencePort;

    @Override
    public RegistryModel createRegistry(RegistryModel registryModel) {
        validatePlateAndParkingId(registryModel);
        UserModel actualUser = new UserModel();
        actualUser.setId(associatePersistencePort.getLoggedAssociateId());
        List<ParkingModel> associatedParking = associatePersistencePort
                .getAssociatedParking(actualUser.getId());
        validateUserHasAssociatedParking(associatedParking);
        validateUserIsAssociatedToParking(registryModel, associatedParking);
        validateParkingIsNotFull(associatePersistencePort
                .getParkingById(registryModel.getParkingModel().getId()));

        List<RegistryModel> actualRegistries = associatePersistencePort.getAssociatedRegistries();
        validateNoActiveRegistryWithSamePlate(actualRegistries, registryModel);
        validatePlateNotInOtherParking(registryModel);

        assert actualRegistries != null;
        long registrationCount = getRegistrationCount(actualRegistries, registryModel);

        registryModel.setUserModel(actualUser);
        registryModel.setEntry(LocalDateTime.parse
                (LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER),
                Constants.DATE_TIME_FORMATTER));
        registryModel.setRegistrationCount((int) registrationCount + Constants.NUMBER_ONE);
        return associatePersistencePort.createRegistry(registryModel);
    }

    @Override
    public void departureVehicle(RegistryModel registryModel) {
        UserModel actualUser = new UserModel();
        actualUser.setId(associatePersistencePort.getLoggedAssociateId());
        Optional<RegistryModel> actualRegistry = associatePersistencePort
                .findActiveRegistryByPlateAndParkingAndUser(registryModel.getPlate(),
                        registryModel.getParkingModel().getId(), actualUser.getId());
        if (actualRegistry.isPresent()) {
            associatePersistencePort.departureVehicle(registryModel);
        } else {
            throw new BadUserRequestException(Constants.NOT_AUTHORIZED_PARKING);
        }
    }

    @Override
    public List<ParkingModel> getAssociatedParking() {
        return validateHasAnyParking();
    }

    @Override
    public List<RegistryModel> getAssociatedVehicle() {
        List<ParkingModel> associatedParking = validateHasAnyParking();
        List<Long> parkingIds = associatedParking.stream()
                .map(ParkingModel::getId)
                .toList();
        List<RegistryModel> parkedVehicles = associatePersistencePort.getAssociatedVehicles(parkingIds);
        if (parkedVehicles.isEmpty()) {
            throw new BadUserRequestException(Constants.NONE_VEHICLE_REGISTERED);
        }
        return parkedVehicles;
    }

    private List<ParkingModel> validateHasAnyParking() {
        List<ParkingModel> associatedParking = associatePersistencePort
                .getAssociatedParking(associatePersistencePort.getLoggedAssociateId());
        if (associatedParking == null || associatedParking.isEmpty()) {
            throw new BadUserRequestException(Constants.NONE_ASSIGNED);
        }
        return associatedParking;
    }

    private void validateUserHasAssociatedParking(List<ParkingModel> associatedParking) {
        if (associatedParking == null || associatedParking.isEmpty()) {
            throw new BadUserRequestException(Constants.NOT_ASSIGNED_NOT_FOUND);
        }
    }

    private void validateUserIsAssociatedToParking(RegistryModel registryModel, List<ParkingModel> associatedParking) {
        boolean isAssociated = associatedParking.stream()
                .anyMatch(parkingModel -> parkingModel.getId()
                        .equals(registryModel.getParkingModel().getId()));
        if (!isAssociated) {
            throw new BadUserRequestException(Constants.NOT_ASSIGNED);
        }
    }

    private void validateNoActiveRegistryWithSamePlate(List<RegistryModel> actualRegistries, RegistryModel registryModel) {
        if (actualRegistries != null && !actualRegistries.isEmpty()) {
            boolean hasActiveRegistry = actualRegistries.stream()
                    .anyMatch(registry -> registry.getPlate().equals(registryModel.getPlate())
                            && registry.getDeparture() == null);

            if (Boolean.TRUE.equals(hasActiveRegistry)) {
                throw new BadUserRequestException(Constants.ALREADY_REGISTERED);
            }
        }
    }

    private void validatePlateNotInOtherParking(RegistryModel registryModel) {
        boolean plateExistsInOtherParking = associatePersistencePort.checkExistingPlate(registryModel.getPlate());
        if (Boolean.TRUE.equals(plateExistsInOtherParking)) {
            throw new BadUserRequestException(Constants.ALREADY_PARKED);
        }
    }

    private long getRegistrationCount(List<RegistryModel> actualRegistries, RegistryModel registryModel) {
        if (actualRegistries == null) {
            return 0;
        }
        return actualRegistries.stream()
                .filter(registry -> registry.getPlate().equals(registryModel.getPlate())
                        && registry.getParkingModel().getId().equals(registryModel.getParkingModel().getId()))
                .count();

    }

    private void validateParkingIsNotFull(ParkingModel parkingModel) {
        long currentVehicleCount = associatePersistencePort
                .countActiveRegistriesByParkingId(parkingModel.getId());

        if (currentVehicleCount >= parkingModel.getMaxAmount()) {
            throw new BadUserRequestException(Constants.PARKING_FULL);
        }
    }

    private void validatePlateAndParkingId(RegistryModel registryModel) {
        if (registryModel.getPlate() == null || registryModel.getParkingModel().getId() == null) {
            throw new BadUserRequestException(Constants.EMPTY_REGISTRY);
        }

        if (!isValidPlate(registryModel.getPlate())) {
            throw new BadUserRequestException(Constants.INVALID_PLATE);
        }
    }

    private boolean isValidPlate(String plate) {
        Pattern pattern = Pattern.compile(Constants.PLATE_REGEX);
        Matcher matcher = pattern.matcher(plate);
        return matcher.matches();
    }

}