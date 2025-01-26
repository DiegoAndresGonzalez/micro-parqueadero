package com.api.parqueadero.domain.usecase;

import com.api.parqueadero.domain.exceptions.BadUserRequestException;
import com.api.parqueadero.domain.exceptions.AlreadyExistsException;
import com.api.parqueadero.domain.model.EmailModel;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;
import com.api.parqueadero.domain.ports.in.AdminServicePort;
import com.api.parqueadero.domain.ports.out.AdminPersistencePort;
import com.api.parqueadero.domain.utils.Constants;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminUseCase implements AdminServicePort {

    private final AdminPersistencePort adminPersistencePort;

    @Override
    public void createAssociate(UserModel user) {
        validateUser(user);
        user.setRole(Constants.ASSOCIATE_ROLE);
        adminPersistencePort.createAssociate(user);
    }

    @Override
    public void createParking(ParkingModel parking) {
        validateParking(parking);
        ParkingModel actualParking = adminPersistencePort.findParkingByName(parking.getName());
        if (actualParking == null) {
            adminPersistencePort.createParking(parking);
        } else {
            throw new AlreadyExistsException(Constants.PARKING_ALREADY_EXISTS);
        }
    }

    @Override
    public List<RegistryModel> findVehicleByParkingId(Long parkingId) {
        return adminPersistencePort.findVehicleByParkingId(parkingId);
    }

    @Override
    public void associateUserToParking(Long parkingId, Long userId) {
        adminPersistencePort.associateParking(parkingId,userId);
    }

    @Override
    public void sendEmail(EmailModel emailModel) {
        ParkingModel parkingModel = adminPersistencePort.findParkingById(emailModel.getParkingId());
        if (Boolean.TRUE.equals(adminPersistencePort.findVehicleByPlate
                (emailModel.getPlate())) && parkingModel !=null) {
            if (isValidEmail(emailModel.getEmail())) {
                throw new BadUserRequestException(Constants.WRONG_EMAIL);
            }
            emailModel.setParkingName(parkingModel.getName());
            adminPersistencePort.sendEmail(emailModel);

        }
        else if (parkingModel == null){
            throw new BadUserRequestException(Constants.PARKING_NOT_FOUND);
        } else {
            throw new BadUserRequestException(Constants.VEHICLE_NOT_FOUND);
        }

    }

    @Override
    public void deleteParking(Long parkingId) {
        ParkingModel parkingModel = adminPersistencePort.findParkingById(parkingId);
        if (parkingModel != null) {
            adminPersistencePort.deleteParking(parkingId);
        }
        else {
            throw new BadUserRequestException(Constants.PARKING_NOT_FOUND);
        }
    }

    @Override
    public void updateParking(ParkingModel parking) {
        ParkingModel actualParking = adminPersistencePort.findParkingById(parking.getId());
        adminPersistencePort.updateParking(validateParkingUpdate(parking,actualParking));
    }

    @Override
    public void deleteUser(Long userId) {
        UserModel actualUser = adminPersistencePort.findUserById(userId);
        if (actualUser != null) {
            adminPersistencePort.deleteUser(userId);
        }
        else {
            throw new BadUserRequestException(Constants.USER_NOT_FOUND);
        }
    }

    @Override
    public void updateUser(UserModel user) {
        UserModel actualUser = adminPersistencePort.findUserById(user.getId());
        adminPersistencePort.updateUser(validateUserUpdate(user,actualUser));
    }

    public UserModel validateUserUpdate(UserModel user, UserModel actualUser) {

        if (actualUser.getName() == null && actualUser.getEmail() == null && actualUser.getPassword() == null) {
            throw new BadUserRequestException(Constants.EMPTY_USER_UPDATE);
        }

        if (user.getName() != null) {
            actualUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (isValidEmail(user.getEmail())) {
                throw new BadUserRequestException(Constants.WRONG_EMAIL);
            }
            actualUser.setEmail(user.getEmail());
        }
        return actualUser;
    }

    private boolean isValidEmail(String email) {
        return !email.matches(Constants.EMAIL_REGEX);
    }

    private void validateUser(UserModel user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new BadUserRequestException(Constants.EMPTY_EMAIL);
        }
        if (isValidEmail(user.getEmail())) {
            throw new BadUserRequestException(Constants.WRONG_EMAIL);
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new BadUserRequestException(Constants.EMPTY_NAME);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BadUserRequestException(Constants.EMPTY_PASSWORD);
        }
        if (adminPersistencePort.getUserByEmail(user.getEmail()) != null) {
            throw new AlreadyExistsException(Constants.USER_ALREADY_EXISTS);
        }
    }

    private void validateParking(ParkingModel parking) {
        if (parking.getName() == null || parking.getName().isEmpty()) {
            throw new BadUserRequestException(Constants.EMPTY_NAME);
        }
        if (parking.getLocation() == null || parking.getLocation().isEmpty()) {
            throw new BadUserRequestException(Constants.EMPTY_LOCATION);
        }
        if (parking.getMaxAmount() == null) {
            throw new BadUserRequestException(Constants.EMPTY_MAX_VEHICLE_AMOUNT);
        }
        if (parking.getCostPerHour() == null) {
            throw new BadUserRequestException(Constants.EMPTY_COST);
        }
    }

    private ParkingModel validateParkingUpdate(ParkingModel parking, ParkingModel actualParking){
        if (actualParking == null) {
            throw new BadUserRequestException(Constants.PARKING_NOT_FOUND);
        }
        if (parking.getName() != null) {
            actualParking.setName(parking.getName());
        }
        if (parking.getLocation() != null) {
            actualParking.setLocation(parking.getLocation());
        }
        if (parking.getMaxAmount() != null) {
            actualParking.setMaxAmount(parking.getMaxAmount());
        }
        if (parking.getCostPerHour() != null) {
            actualParking.setCostPerHour(parking.getCostPerHour());
        }
        return actualParking;
    }

}
