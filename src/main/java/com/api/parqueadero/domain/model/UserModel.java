package com.api.parqueadero.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Integer vehicleAmount;
    private List<RegistryModel> registryModels;

}
