package com.api.parqueadero.infrastructure.endpoints;

import com.api.parqueadero.application.dtos.request.RegistryRequestDto;
import com.api.parqueadero.application.dtos.response.GenericResponseDto;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;
import com.api.parqueadero.application.dtos.response.RegistryResponseDto;
import com.api.parqueadero.application.dtos.response.VehicleDetailResponseDto;
import com.api.parqueadero.application.services.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/socio")
public class AssociateController {

    private final AssociateService associateService;

    @PreAuthorize("hasRole('SOCIO')")
    @PostMapping("/registro")
    public ResponseEntity<RegistryResponseDto> createRegistry(@RequestBody RegistryRequestDto registryRequestDto) {
        RegistryResponseDto newRegistry = associateService.createRegistry(registryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistry);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @PostMapping("/salida")
    public ResponseEntity<GenericResponseDto> registerDeparture(@RequestBody RegistryRequestDto registryRequestDto) {
        associateService.departureVehicle(registryRequestDto);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Salida registrada");
        return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseDto);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/parqueadero")
    public ResponseEntity<List<ParkingResponseDto>> createRegistry() {
        List<ParkingResponseDto> parkingResponseDto = associateService.getAllAssociatedParking();
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingResponseDto);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/vehiculo")
    public ResponseEntity<List<VehicleDetailResponseDto>> getAssociatedVehicle() {
        List<VehicleDetailResponseDto> vehicleResponseDto = associateService.getAllAssociatedVehicle();
        return ResponseEntity.status(HttpStatus.OK).body(vehicleResponseDto);
    }

}
