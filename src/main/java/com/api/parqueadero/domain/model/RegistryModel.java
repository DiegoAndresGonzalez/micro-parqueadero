package com.api.parqueadero.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistryModel {

    private Long id;
    private String plate;
    private LocalDateTime entry;
    private LocalDateTime departure;
    private Integer registrationCount;
    private ParkingModel parkingModel;
    private UserModel userModel;

}
