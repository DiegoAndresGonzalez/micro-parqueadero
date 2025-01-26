package com.api.parqueadero.infrastructure.endpoints;

import com.api.parqueadero.application.dtos.request.LoginRequestDto;
import com.api.parqueadero.application.dtos.response.LoginResponseDto;
import com.api.parqueadero.infrastructure.configuration.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authRequest);
        String token = jwtUtils.generateToken(auth.getName(),
                auth.getAuthorities().toString());
        LoginResponseDto loginResponseDto = new LoginResponseDto(token);
        return ResponseEntity.ok(loginResponseDto);
    }

}
