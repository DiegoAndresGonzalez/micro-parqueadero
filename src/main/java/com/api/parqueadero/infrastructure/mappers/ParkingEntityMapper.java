package com.api.parqueadero.infrastructure.mappers;

import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.infrastructure.entities.ParkingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ParkingEntityMapper {

    List<ParkingEntity> toParkingEntityList(List<ParkingModel> parkingModelList);
    List<ParkingModel> toParkingModelList(List<ParkingEntity> parkingEntityList);
    ParkingEntity toParkingEntity(ParkingModel parkingModel);
    ParkingModel toParkingModel(ParkingEntity parkingEntity);

}
