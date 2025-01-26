package com.api.parqueadero.application.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateParkingRequestDto {

    private String name;
    private String location;
    private Integer maxAmount;
    private Integer costPerHour;

}
