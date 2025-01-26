package com.api.parqueadero.application.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VehicleDetailResponseDto {

    @JsonProperty("idRegistro")
    private Long registryId;

    @JsonProperty("placa")
    private String plate;

    @JsonProperty("fechaIngreso")
    private LocalDateTime entry;

}
