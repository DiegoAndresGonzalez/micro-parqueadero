package com.api.parqueadero.domain.ports.in;


import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;

import java.util.List;

public interface AssociateServicePort {

    RegistryModel createRegistry(RegistryModel registryModel);
    void departureVehicle(RegistryModel registryModel);
    List<ParkingModel> getAssociatedParking();
    List<RegistryModel> getAssociatedVehicle();
}
