package com.api.parqueadero.application.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateParkingRequestDto {

    private Long id;
    private String name;
    private String location;
    private Integer maxAmount;
    private Integer costPerHour;

}
