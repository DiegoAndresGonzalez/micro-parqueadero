package com.api.parqueadero.domain.ports.in;

import com.api.parqueadero.domain.model.EarningModel;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;

import java.util.List;

public interface IndicatorServicePort {

    List<RegistryModel> getTop10Vehicles(Long parkingId);
    List<RegistryModel> getFirstTimeVehicle(Long parkingId);
    EarningModel getEarnings(Long parkingId);
    List<UserModel> associateTop();
    List<ParkingModel> getTop3ParkingEarning();

}
