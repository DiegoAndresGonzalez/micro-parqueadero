package com.api.parqueadero.application.services;

import com.api.parqueadero.application.dtos.request.*;
import com.api.parqueadero.application.dtos.response.ParkingVehicleResponseDto;

public interface AdminService {

    void createAssociate(UserRequestDto userRequestDto);
    void createParking(CreateParkingRequestDto createParkingRequestDto);
    ParkingVehicleResponseDto findVehicleByParkingId(Long id);
    void associateUserToParking(Long parkingId, Long userId);
    void sendEmail(EmailRequestDto emailRequestDto);
    void deleteParking(Long parkingId);
    void updateParking(UpdateParkingRequestDto updateParkingRequestDto);
    void deleteUser(Long userId);
    void updateUser(UpdateUserRequestDto updateUserRequestDto);

}
