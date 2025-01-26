package com.api.parqueadero.domain.ports.out;

import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;

import java.time.LocalDateTime;
import java.util.List;

public interface IndicatorPersistencePort {

    List<RegistryModel> getTop10Vehicles(Long parkingId);
    List<RegistryModel> getFirstTimeVehicles(Long parkingId);
    List<RegistryModel> calculateEarningsByParkingId(Long parkingId);
    List<RegistryModel> calculateEarningsByEntryBetween(LocalDateTime entry, LocalDateTime departure);
    List<RegistryModel> getTop3ParkingByEarnings(LocalDateTime entry, LocalDateTime departure);

}
