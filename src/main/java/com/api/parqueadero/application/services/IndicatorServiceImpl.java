package com.api.parqueadero.application.services;

import com.api.parqueadero.application.dtos.response.AssociateTopVehicleResponseDto;
import com.api.parqueadero.application.dtos.response.EarningResponseDto;
import com.api.parqueadero.application.dtos.response.IndicatorVehicleResponse;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;
import com.api.parqueadero.application.mapper.EarningDtoMapper;
import com.api.parqueadero.application.mapper.ParkingDtoMapper;
import com.api.parqueadero.application.mapper.RegistryDtoMapper;
import com.api.parqueadero.domain.ports.in.IndicatorServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {

    private final IndicatorServicePort indicatorServicePort;
    private final RegistryDtoMapper registryDtoMapper;
    private final EarningDtoMapper earningDtoMapper;
    private final ParkingDtoMapper parkingDtoMapper;

    @Override
    public List<IndicatorVehicleResponse> getTop10Vehicles(Long parkingId) {
        return registryDtoMapper.toIndicatorVehicleResponseDto
                (indicatorServicePort.getTop10Vehicles(parkingId));
    }

    @Override
    public List<IndicatorVehicleResponse> getFirstTimeVehicle(Long parkingId) {
        return registryDtoMapper.toIndicatorVehicleResponseDto(
                indicatorServicePort.getFirstTimeVehicle(parkingId));
    }

    @Override
    public EarningResponseDto getEarnings(Long parkingId) {
        return earningDtoMapper.toEarningResponseDto(
                indicatorServicePort.getEarnings(parkingId)
        );
    }

    @Override
    public List<AssociateTopVehicleResponseDto> getAssociateTopVehicles() {
        return earningDtoMapper.toAssociateTopVehicleResponse(
                indicatorServicePort.associateTop()
        );
    }

    @Override
    public List<ParkingResponseDto> getTopParking() {
        return parkingDtoMapper.parkingToDto(
                indicatorServicePort.getTop3ParkingEarning()
        );
    }
}
