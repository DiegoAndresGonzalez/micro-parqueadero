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
    public void registerAssociate(@RequestBody UserRequestDto userRequestDto) {
        adminService.createAssociate(userRequestDto);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/parqueadero")
    public void createParking(@RequestBody CreateParkingRequestDto createParkingRequestDto) {
        adminService.createParking(createParkingRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/vehiculo/{parkingId}")
    public ResponseEntity<ParkingVehicleResponseDto> getVehicleList(@PathVariable Long parkingId) {
        return ResponseEntity.ok().body(adminService.findVehicleByParkingId(parkingId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/parqueadero/socio")
    public void associateUserToParking
            (@RequestParam Long parkingId, @RequestParam Long userId) {
        adminService.associateUserToParking(parkingId, userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enviar")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        adminService.sendEmail(emailRequestDto);
        return ResponseEntity.ok(Map.of("mensaje", "Correo Enviado"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/parqueadero/{parkingId}")
    public ResponseEntity<GenericResponseDto> deleteParking(@PathVariable Long parkingId) {
        adminService.deleteParking(parkingId);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Se ha eliminado el parqueadero");
        return ResponseEntity.ok(genericResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/socio/{userId}")
    public ResponseEntity<GenericResponseDto> deleteUser(@PathVariable Long userId) {
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
