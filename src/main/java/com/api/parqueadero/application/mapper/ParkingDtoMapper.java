package com.api.parqueadero.application.mapper;

import com.api.parqueadero.application.dtos.request.CreateParkingRequestDto;
import com.api.parqueadero.application.dtos.request.UpdateParkingRequestDto;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;
import com.api.parqueadero.domain.model.ParkingModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ParkingDtoMapper {

    ParkingModel toModel (CreateParkingRequestDto parkingRequestDto);
    List<ParkingResponseDto> parkingToDto (List<ParkingModel> parkingModel);
    ParkingModel toUpdate (UpdateParkingRequestDto updateParkingRequestDto);
}
