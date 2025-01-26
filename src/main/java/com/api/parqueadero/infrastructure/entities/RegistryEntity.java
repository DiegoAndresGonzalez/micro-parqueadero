package com.api.parqueadero.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "historial")
public class RegistryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
    private LocalDateTime entry;
    private LocalDateTime departure;
    private Integer registrationCount;

    @ManyToOne
    @JoinColumn(name = "id_parqueadero")
    private ParkingEntity parkingEntity;

    @ManyToOne
    @JoinColumn(name = "id_socio")
    private UserEntity userEntity;

}