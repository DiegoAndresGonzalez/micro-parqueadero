package com.api.parqueadero.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailModel {

    private String email;
    private String plate;
    private String message;
    private String parkingName;
    private Long parkingId;

}
