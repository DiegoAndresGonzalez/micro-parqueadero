package com.api.parqueadero.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "parqueadero")
public class ParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private Integer maxAmount;
    private Integer costPerHour;

    @OneToMany(mappedBy = "parkingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistryEntity> registryEntities;

    @OneToMany(mappedBy = "parkingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserParkingEntity> userParking;

}
