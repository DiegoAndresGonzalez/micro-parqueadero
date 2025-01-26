package com.api.parqueadero.application.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicatorVehicleResponse {

    private String plate;
    private Integer registrationCount;

}
