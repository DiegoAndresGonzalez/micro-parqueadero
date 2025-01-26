package com.api.parqueadero.infrastructure.mappers;

import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.infrastructure.entities.RegistryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RegistryEntityMapper {

    @Mapping(target = "userEntity.id", source = "userModel.id")
    @Mapping(target = "parkingEntity.id", source = "parkingModel.id")
    RegistryEntity toEntity (RegistryModel registryModel);

    @Mapping(target = "userModel.id", source = "userEntity.id")
    @Mapping(target = "parkingModel.id", source = "parkingEntity.id")
    RegistryModel toRegistryModel (RegistryEntity registryEntity);
    List<RegistryModel> toModelList (List<RegistryEntity> registryEntities);
    default Optional<RegistryModel> toRegistryModelOptional(Optional<RegistryEntity> registryEntity) {
        return registryEntity.map(this::toRegistryModel);
    }

    default Optional<RegistryEntity> toRegistryEntityOptional(Optional<RegistryModel> registryModel) {
        return registryModel.map(this::toEntity);
    }


}
