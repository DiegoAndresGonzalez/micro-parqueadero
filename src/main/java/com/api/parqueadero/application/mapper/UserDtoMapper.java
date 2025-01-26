package com.api.parqueadero.application.mapper;

import com.api.parqueadero.application.dtos.request.UpdateUserRequestDto;
import com.api.parqueadero.application.dtos.request.UserRequestDto;
import com.api.parqueadero.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {

    UserModel toUserModel (UserRequestDto userRequestDto);
    UserModel toUpdate (UpdateUserRequestDto updateUserRequestDto);
}
