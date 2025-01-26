package com.api.parqueadero.infrastructure.configuration.beans;

import com.api.parqueadero.application.mapper.EmailDtoMapper;
import com.api.parqueadero.domain.ports.in.AdminServicePort;
import com.api.parqueadero.domain.ports.in.AssociateServicePort;
import com.api.parqueadero.domain.ports.in.IndicatorServicePort;
import com.api.parqueadero.domain.ports.out.AdminPersistencePort;
import com.api.parqueadero.domain.ports.out.AssociatePersistencePort;
import com.api.parqueadero.domain.ports.out.IndicatorPersistencePort;
import com.api.parqueadero.domain.usecase.AdminUseCase;
import com.api.parqueadero.domain.usecase.AssociateUseCase;
import com.api.parqueadero.domain.usecase.IndicatorUseCase;
import com.api.parqueadero.infrastructure.adapters.external.feing.EmailClient;
import com.api.parqueadero.infrastructure.adapters.persistence.AdminPersistenceAdapter;
import com.api.parqueadero.infrastructure.adapters.persistence.AssociatePersistenceAdapter;
import com.api.parqueadero.infrastructure.adapters.persistence.IndicatorPersistenceAdapter;
import com.api.parqueadero.infrastructure.configuration.security.jwt.JwtUtils;
import com.api.parqueadero.infrastructure.mappers.ParkingEntityMapper;
import com.api.parqueadero.infrastructure.mappers.RegistryEntityMapper;
import com.api.parqueadero.infrastructure.mappers.UserEntityMapper;
import com.api.parqueadero.infrastructure.repository.ParkingEntityRepository;
import com.api.parqueadero.infrastructure.repository.RegistryEntityRepository;
import com.api.parqueadero.infrastructure.repository.UserEntityRepository;
import com.api.parqueadero.infrastructure.repository.UserParkingEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public AssociatePersistencePort associatePersistencePort(
            RegistryEntityRepository registryEntityRepository, RegistryEntityMapper registryEntityMapper,
            UserEntityRepository userEntityRepository, JwtUtils jwtUtils,
            UserParkingEntityRepository userParkingEntityRepository, ParkingEntityMapper parkingEntityMapper,
            ParkingEntityRepository parkingEntityRepository) {
        return new AssociatePersistenceAdapter(jwtUtils,registryEntityRepository,registryEntityMapper,
                userEntityRepository,parkingEntityMapper,userParkingEntityRepository, parkingEntityRepository);
    }

    @Bean
    public AssociateServicePort associateServicePort(AssociatePersistencePort associatePersistencePort) {
        return new AssociateUseCase(associatePersistencePort);
    }

    @Bean
    public AdminPersistencePort adminPersistencePort(
            UserEntityRepository userEntityRepository, UserEntityMapper userEntityMapper,
            PasswordEncoder passwordEncoder, RegistryEntityMapper registryEntityMapper,
            RegistryEntityRepository registryEntityRepository, ParkingEntityRepository parkingEntityRepository,
            ParkingEntityMapper parkingEntityMapper, UserParkingEntityRepository userParkingEntityRepository,
            EmailClient emailClient, EmailDtoMapper emailDtoMapper){
        return new AdminPersistenceAdapter(userEntityRepository,userEntityMapper,passwordEncoder,
                parkingEntityRepository,parkingEntityMapper,registryEntityMapper,
                registryEntityRepository,userParkingEntityRepository, emailClient, emailDtoMapper);
    }

    @Bean
    public AdminServicePort adminServicePort(AdminPersistencePort adminPersistencePort) {
        return new AdminUseCase(adminPersistencePort);
    }

    @Bean
    public IndicatorPersistencePort indicatorPersistencePort(RegistryEntityRepository registryEntityRepository,
                                                             RegistryEntityMapper registryEntityMapper) {
        return new IndicatorPersistenceAdapter(registryEntityRepository,registryEntityMapper);
    }

    @Bean
    public IndicatorServicePort indicatorServicePort(IndicatorPersistencePort indicatorPersistencePort,
                                                     AssociatePersistencePort associatePersistencePort,
                                                     AdminPersistencePort adminPersistencePort) {
        return new IndicatorUseCase(indicatorPersistencePort,associatePersistencePort,adminPersistencePort);
    }

}
