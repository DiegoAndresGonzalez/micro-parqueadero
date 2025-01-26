package com.api.parqueadero.infrastructure.adapters.external.feing;


import com.api.parqueadero.application.dtos.request.EmailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "emailSimulationClient", url = "${simulacion.correo.url}")
public interface EmailClient {

    @PostMapping("/enviar")
    Map<String, String> sendMail(@RequestBody EmailRequestDto emailRequestDto);

}
