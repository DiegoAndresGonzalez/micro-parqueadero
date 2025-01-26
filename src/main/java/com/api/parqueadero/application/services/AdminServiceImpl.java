package com.api.parqueadero.application.services;

import com.api.parqueadero.application.dtos.request.*;
import com.api.parqueadero.application.dtos.response.ParkingVehicleResponseDto;
import com.api.parqueadero.application.dtos.response.RegistryVehicleResponseDto;
import com.api.parqueadero.application.mapper.*;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.ports.in.AdminServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminServicePort adminServicePort;
    private final UserDtoMapper userDtoMapper;
    private final ParkingDtoMapper parkingDtoMapper;
    private final RegistryDtoMapper registryDtoMapper;
    private final EmailDtoMapper emailDtoMapper;

    @Override
    public void createAssociate(UserRequestDto userRequestDto) {
        adminServicePort.createAssociate(
                userDtoMapper.toUserModel(userRequestDto)
        );
    }

    @Override
    public void createParking(CreateParkingRequestDto createParkingRequestDto) {
        adminServicePort.createParking(parkingDtoMapper.toModel(createParkingRequestDto));
    }

    @Override
    public ParkingVehicleResponseDto findVehicleByParkingId(Long id) {
        List<RegistryModel> registryModels = adminServicePort.findVehicleByParkingId(id);
        List<RegistryVehicleResponseDto> registryVehicleResponseDto = registryDtoMapper
                .toRegistryVehicleResponseDto(registryModels);
        ParkingVehicleResponseDto parkingVehicleResponseDto = new ParkingVehicleResponseDto();
        parkingVehicleResponseDto.setRegistryVehicleResponseDto(registryVehicleResponseDto);
        return parkingVehicleResponseDto;
    }

    @Override
    public void associateUserToParking(Long parkingId, Long userId){
        adminServicePort.associateUserToParking(parkingId, userId);
    }

    @Override
    public void sendEmail(EmailRequestDto emailRequestDto) {
        adminServicePort.sendEmail(
                emailDtoMapper.toEmailModel(emailRequestDto));
    }

    @Override
    public void deleteParking(Long parkingId) {
        adminServicePort.deleteParking(parkingId);
    }

    @Override
    public void updateParking(UpdateParkingRequestDto updateParkingRequestDto) {
        adminServicePort.updateParking(
                parkingDtoMapper.toUpdate(updateParkingRequestDto)
        );
    }

    @Override
    public void deleteUser(Long userId) {
        adminServicePort.deleteUser(userId);
    }

    @Override
    public void updateUser(UpdateUserRequestDto updateUserRequestDto) {
        adminServicePort.updateUser(
                userDtoMapper.toUpdate(updateUserRequestDto)
        );
    }
}
