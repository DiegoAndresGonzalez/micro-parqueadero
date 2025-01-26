package com.api.parqueadero.domain.ports.in;

import com.api.parqueadero.domain.model.EmailModel;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;

import java.util.List;

public interface AdminServicePort {

    void createAssociate(UserModel user);
    void createParking(ParkingModel parking);
    List<RegistryModel> findVehicleByParkingId(Long parkingId);
    void associateUserToParking(Long parkingId, Long userId);
    void sendEmail(EmailModel email);
    void deleteParking(Long parkingId);
    void updateParking(ParkingModel parking);
    void deleteUser(Long userId);
    void updateUser(UserModel user);
}
