package com.api.parqueadero.infrastructure.mappers;

import com.api.parqueadero.domain.model.UserModel;
import com.api.parqueadero.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    UserEntity toUserEntity(UserModel userModel);
    UserModel toUserModel(UserEntity userEntity);
}
