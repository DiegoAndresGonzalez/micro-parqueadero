package com.api.parqueadero.application.mapper;

import com.api.parqueadero.application.dtos.response.AssociateTopVehicleResponseDto;
import com.api.parqueadero.application.dtos.response.EarningResponseDto;
import com.api.parqueadero.domain.model.EarningModel;
import com.api.parqueadero.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EarningDtoMapper {

    EarningResponseDto toEarningResponseDto(EarningModel earningModel);

    @Mapping(source = "userModel.id", target = "userId")
    AssociateTopVehicleResponseDto toAssociateTopVehicleResponseDto(UserModel userModel);
    List<AssociateTopVehicleResponseDto> toAssociateTopVehicleResponse(List<UserModel> userModel);

}
