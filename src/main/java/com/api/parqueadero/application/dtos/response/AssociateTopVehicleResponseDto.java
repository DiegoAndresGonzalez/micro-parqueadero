package com.api.parqueadero.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociateTopVehicleResponseDto {

    private Long userId;
    private String name;
    private Integer vehicleAmount;

}
