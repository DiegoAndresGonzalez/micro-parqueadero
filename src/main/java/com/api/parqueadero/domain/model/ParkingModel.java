package com.api.parqueadero.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingModel {

    private Long id;
    private String name;
    private String location;
    private Integer maxAmount;
    private Integer costPerHour;
    private List<RegistryModel> registryModels;
    private Integer totalAmount;

}
