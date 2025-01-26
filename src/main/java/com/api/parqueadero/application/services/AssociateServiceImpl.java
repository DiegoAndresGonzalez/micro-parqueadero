package com.api.parqueadero.application.services;

import com.api.parqueadero.application.dtos.request.RegistryRequestDto;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;
import com.api.parqueadero.application.dtos.response.RegistryResponseDto;
import com.api.parqueadero.application.dtos.response.VehicleDetailResponseDto;
import com.api.parqueadero.application.mapper.ParkingDtoMapper;
import com.api.parqueadero.application.mapper.RegistryDtoMapper;
import com.api.parqueadero.domain.ports.in.AssociateServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociateServiceImpl implements AssociateService {

    private final AssociateServicePort associateServicePort;
    private final RegistryDtoMapper registryDtoMapper;
    private final ParkingDtoMapper parkingDtoMapper;

    @Override
    public RegistryResponseDto createRegistry(RegistryRequestDto registryRequestDto) {
        return registryDtoMapper.toRegistryResponseDto(
                associateServicePort.createRegistry(
                        registryDtoMapper.toRegistryModel(registryRequestDto)));
    }

    @Override
    public void departureVehicle(RegistryRequestDto registryRequestDto) {
        associateServicePort.departureVehicle(registryDtoMapper
                .toRegistryModel(registryRequestDto));
    }

    @Override
    public List<ParkingResponseDto> getAllAssociatedParking() {
        return parkingDtoMapper.parkingToDto(associateServicePort
                .getAssociatedParking());
    }

    @Override
    public List<VehicleDetailResponseDto> getAllAssociatedVehicle() {
        return registryDtoMapper.toVehicleDetailResponseDto(
                associateServicePort.getAssociatedVehicle());
    }
}
