package com.api.parqueadero.domain.ports.out;

import com.api.parqueadero.domain.model.EmailModel;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;

import java.util.List;

public interface AdminPersistencePort {

    void createAssociate(UserModel user);
    UserModel getUserByEmail(String email);
    void createParking(ParkingModel parking);
    List<RegistryModel> findVehicleByParkingId(Long parkingId);
    void associateParking(Long parkingId, Long userId);
    void sendEmail(EmailModel email);
    ParkingModel findParkingById(Long parkingId);
    Boolean findVehicleByPlate(String plate);
    ParkingModel findParkingByName(String name);
    UserModel findUserById(Long userId);
    void deleteParking(Long parkingId);
    void deleteUser(Long userId);
    void updateParking(ParkingModel parking);
    void updateUser(UserModel user);
}
