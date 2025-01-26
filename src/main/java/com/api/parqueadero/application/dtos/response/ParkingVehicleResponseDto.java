package com.api.parqueadero.application.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingVehicleResponseDto {

    private List<RegistryVehicleResponseDto> registryVehicleResponseDto;

}
