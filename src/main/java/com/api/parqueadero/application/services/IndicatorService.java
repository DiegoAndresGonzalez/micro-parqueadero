package com.api.parqueadero.application.services;

import com.api.parqueadero.application.dtos.response.AssociateTopVehicleResponseDto;
import com.api.parqueadero.application.dtos.response.EarningResponseDto;
import com.api.parqueadero.application.dtos.response.IndicatorVehicleResponse;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;

import java.util.List;

public interface IndicatorService {

    List<IndicatorVehicleResponse> getTop10Vehicles(Long parkingId);
    List<IndicatorVehicleResponse> getFirstTimeVehicle(Long parkingId);
    EarningResponseDto getEarnings(Long parkingId);
    List<AssociateTopVehicleResponseDto> getAssociateTopVehicles();
    List<ParkingResponseDto> getTopParking();
}
