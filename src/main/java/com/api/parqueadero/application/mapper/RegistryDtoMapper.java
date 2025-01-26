package com.api.parqueadero.application.mapper;

import com.api.parqueadero.application.dtos.request.RegistryRequestDto;
import com.api.parqueadero.application.dtos.response.IndicatorVehicleResponse;
import com.api.parqueadero.application.dtos.response.RegistryResponseDto;
import com.api.parqueadero.application.dtos.response.RegistryVehicleResponseDto;
import com.api.parqueadero.application.dtos.response.VehicleDetailResponseDto;
import com.api.parqueadero.domain.model.RegistryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RegistryDtoMapper {

    @Mapping(source = "parkingId", target = "parkingModel.id")
    RegistryModel toRegistryModel(RegistryRequestDto registryRequestDto);
    RegistryResponseDto toRegistryResponseDto(RegistryModel registryModel);

    @Mapping(source = "id", target = "registryId")
    @Mapping(source = "userModel.id", target = "userId")
    RegistryVehicleResponseDto toRegistryVehicleResponseDto(RegistryModel registryModel);
    List<RegistryVehicleResponseDto> toRegistryVehicleResponseDto(List<RegistryModel> registryModel);

    @Mapping(source = "id", target = "registryId")
    VehicleDetailResponseDto toVehicleDetailResponseDto(RegistryModel registryModel);
    List<VehicleDetailResponseDto> toVehicleDetailResponseDto(List<RegistryModel> registryModel);

    List<IndicatorVehicleResponse> toIndicatorVehicleResponseDto(List<RegistryModel> registryModel);
}
