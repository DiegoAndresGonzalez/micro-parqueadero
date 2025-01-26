package com.api.parqueadero.application.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponseDto {

    @JsonProperty("mensaje")
    private String message;

}
