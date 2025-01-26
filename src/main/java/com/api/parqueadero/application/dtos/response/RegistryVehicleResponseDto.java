package com.api.parqueadero.application.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistryVehicleResponseDto {

    @JsonProperty("idRegistro")
    private Long registryId;

    @JsonProperty("idSocio")
    private Long userId;

    @JsonProperty("placa")
    private String plate;

    @JsonProperty("fechaIngreso")
    private LocalDateTime entry;

}
