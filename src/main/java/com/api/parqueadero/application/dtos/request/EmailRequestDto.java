package com.api.parqueadero.application.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {

    private String email;
    private String plate;
    private String message;
    private Long parkingId;
    private String parkingName;

}
