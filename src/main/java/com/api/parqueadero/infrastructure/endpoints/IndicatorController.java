package com.api.parqueadero.infrastructure.endpoints;

import com.api.parqueadero.application.dtos.response.AssociateTopVehicleResponseDto;
import com.api.parqueadero.application.dtos.response.EarningResponseDto;
import com.api.parqueadero.application.dtos.response.IndicatorVehicleResponse;
import com.api.parqueadero.application.dtos.response.ParkingResponseDto;
import com.api.parqueadero.application.services.IndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/indicador")
public class IndicatorController {

    private final IndicatorService indicatorService;

    @PreAuthorize("hasAnyRole('SOCIO','ADMIN')")
    @GetMapping("/top")
    public ResponseEntity<List<IndicatorVehicleResponse>> getTop10Vehicles(@RequestParam Long parkingId) {
        List<IndicatorVehicleResponse> indicatorResponse = indicatorService.getTop10Vehicles(parkingId);
        return ResponseEntity.status(HttpStatus.OK).body(indicatorResponse);
    }

    @PreAuthorize("hasAnyRole('SOCIO','ADMIN')")
    @GetMapping("/first")
    public ResponseEntity<List<IndicatorVehicleResponse>> getFirstTimeVehicle(@RequestParam Long parkingId) {
        List<IndicatorVehicleResponse> indicatorResponse = indicatorService.getFirstTimeVehicle(parkingId);
        return ResponseEntity.status(HttpStatus.OK).body(indicatorResponse);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/ganancia")
    public ResponseEntity<EarningResponseDto> getEarnings(@RequestParam Long parkingId) {
        EarningResponseDto indicatorResponse = indicatorService.getEarnings(parkingId);
        return ResponseEntity.status(HttpStatus.OK).body(indicatorResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-socio")
    public ResponseEntity<List<AssociateTopVehicleResponseDto>> getTopAssociatedVehicle() {
        List<AssociateTopVehicleResponseDto> indicatorResponse = indicatorService.getAssociateTopVehicles();
        return ResponseEntity.status(HttpStatus.OK).body(indicatorResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-park")
    public ResponseEntity<List<ParkingResponseDto>> getTopParking() {
        List<ParkingResponseDto> indicatorResponse = indicatorService.getTopParking();
        return ResponseEntity.status(HttpStatus.OK).body(indicatorResponse);
    }

}
