package com.api.parqueadero.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingResponseDto {

    private Long id;
    private String name;
    private String location;
    private Integer totalAmount;

}
