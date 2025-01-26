package com.api.parqueadero.application.services;

import com.api.parqueadero.application.dtos.request.RegistryRequestDto;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;
import com.api.parqueadero.application.dtos.response.RegistryResponseDto;
import com.api.parqueadero.application.dtos.response.VehicleDetailResponseDto;

import java.util.List;

public interface AssociateService {

    RegistryResponseDto createRegistry(RegistryRequestDto registryRequestDto);
    void departureVehicle(RegistryRequestDto registryRequestDto);
    List<ParkingResponseDto> getAllAssociatedParking();
    List<VehicleDetailResponseDto> getAllAssociatedVehicle();
}
