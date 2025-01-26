package com.api.parqueadero.infrastructure.endpoints;

import com.api.parqueadero.application.dtos.request.*;
import com.api.parqueadero.application.dtos.response.GenericResponseDto;
import com.api.parqueadero.application.dtos.response.ParkingVehicleResponseDto;
import com.api.parqueadero.application.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registro")
    public ResponseEntity<GenericResponseDto> registerAssociate(@RequestBody UserRequestDto userRequestDto) {
        adminService.createAssociate(userRequestDto);
        GenericResponseDto responseDto = new GenericResponseDto();
        responseDto.setMessage("Se ha registrado con exito el asociado");
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/parqueadero")
    public ResponseEntity<GenericResponseDto> createParking(@RequestBody CreateParkingRequestDto createParkingRequestDto) {
        adminService.createParking(createParkingRequestDto);
        GenericResponseDto responseDto = new GenericResponseDto();
        responseDto.setMessage("Se ha creado con exito el parqueadero");
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/vehiculo")
    public ResponseEntity<ParkingVehicleResponseDto> getVehicleList(@RequestParam Long parkingId) {
        return ResponseEntity.ok().body(adminService.findVehicleByParkingId(parkingId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/parqueadero/socio")
    public ResponseEntity<GenericResponseDto> associateUserToParking
            (@RequestParam Long parkingId, @RequestParam Long userId) {
        adminService.associateUserToParking(parkingId, userId);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Se ha associado con exito el asociado al parqueadero");
        return new ResponseEntity<>(genericResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enviar")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        adminService.sendEmail(emailRequestDto);
        return ResponseEntity.ok(Map.of("mensaje", "Correo Enviado"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/parqueadero")
    public ResponseEntity<GenericResponseDto> deleteParking(@RequestParam Long parkingId) {
        adminService.deleteParking(parkingId);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Se ha eliminado el parqueadero");
        return ResponseEntity.ok(genericResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/socio")
    public ResponseEntity<GenericResponseDto> deleteUser(@RequestParam Long userId) {
        adminService.deleteUser(userId);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Se ha eliminado el usuario");
        return ResponseEntity.status(HttpStatus.OK).body(genericResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/parqueadero")
    public ResponseEntity<GenericResponseDto> updateParking(@RequestBody UpdateParkingRequestDto parkingRequestDto) {
        adminService.updateParking(parkingRequestDto);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Se ha actualizado el parqueadero correctamente");
        return ResponseEntity.status(HttpStatus.OK).body(genericResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/socio")
    public ResponseEntity<GenericResponseDto> updateParking(@RequestBody UpdateUserRequestDto userRequestDto) {
        adminService.updateUser(userRequestDto);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Se ha actualizado al usuario");
        return ResponseEntity.status(HttpStatus.OK).body(genericResponseDto);
    }

}
