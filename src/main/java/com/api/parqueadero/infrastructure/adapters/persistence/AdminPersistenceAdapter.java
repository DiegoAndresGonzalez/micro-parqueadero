package com.api.parqueadero.infrastructure.adapters.persistence;

import com.api.parqueadero.application.mapper.EmailDtoMapper;
import com.api.parqueadero.domain.exceptions.BadUserRequestException;
import com.api.parqueadero.domain.model.EmailModel;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;
import com.api.parqueadero.domain.ports.out.AdminPersistencePort;
import com.api.parqueadero.domain.utils.Constants;
import com.api.parqueadero.infrastructure.adapters.external.feing.EmailClient;
import com.api.parqueadero.infrastructure.entities.ParkingEntity;
import com.api.parqueadero.infrastructure.entities.UserEntity;
import com.api.parqueadero.infrastructure.entities.UserParkingEntity;
import com.api.parqueadero.infrastructure.exceptions.NotFoundException;
import com.api.parqueadero.infrastructure.exceptions.UserAlreadyAssociated;
import com.api.parqueadero.infrastructure.mappers.ParkingEntityMapper;
import com.api.parqueadero.infrastructure.mappers.RegistryEntityMapper;
import com.api.parqueadero.infrastructure.mappers.UserEntityMapper;
import com.api.parqueadero.infrastructure.repository.ParkingEntityRepository;
import com.api.parqueadero.infrastructure.repository.RegistryEntityRepository;
import com.api.parqueadero.infrastructure.repository.UserEntityRepository;
import com.api.parqueadero.infrastructure.repository.UserParkingEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminPersistencePort {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final ParkingEntityRepository parkingEntityRepository;
    private final ParkingEntityMapper parkingEntityMapper;
    private final RegistryEntityMapper registryEntityMapper;
    private final RegistryEntityRepository registryEntityRepository;
    private final UserParkingEntityRepository userParkingEntityRepository;
    private final EmailClient emailClient;
    private final EmailDtoMapper emailDtoMapper;


    @Override
    public void createAssociate(UserModel user) {
        UserEntity newUser = userEntityMapper.toUserEntity(user);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userEntityRepository.save(newUser);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return userEntityMapper.toUserModel(
                userEntityRepository.findByEmail(email).orElse(null));
    }

    @Override
    public void createParking(ParkingModel parking) {
        parkingEntityRepository.save(parkingEntityMapper.toParkingEntity(parking));
    }

    @Override
    public List<RegistryModel> findVehicleByParkingId(Long parkingId) {
        return registryEntityMapper.toModelList(
                registryEntityRepository.findByParkingEntityIdAndDepartureIsNull(parkingId));
    }

    @Override
    public void associateParking(Long parkingId, Long userId) {
        ParkingEntity parkingEntity = parkingEntityRepository.findById(parkingId)
                .orElseThrow(() -> new NotFoundException(Constants.PARKING_NOT_FOUND));
        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
        if (userEntity.getRole().equals(Constants.ADMIN_ROLE)){
            throw new BadUserRequestException(Constants.CANT_ASSOCIATE_ADMIN);
        }
        if (Boolean.FALSE.equals(userParkingEntityRepository
                .existsByUserEntityAndParkingEntity(userEntity, parkingEntity))) {
            UserParkingEntity userParkingEntity = new UserParkingEntity();
            userParkingEntity.setParkingEntity(parkingEntity);
            userParkingEntity.setUserEntity(userEntity);
            userParkingEntityRepository.save(userParkingEntity);
        }
        else{
            throw new UserAlreadyAssociated(Constants.USER_ALREADY_ASSOCIATED);
        }
    }

    @Override
    public void sendEmail(EmailModel emailModel) {
        emailClient.sendMail(emailDtoMapper.toEmailRequestDto(emailModel));
    }

    @Override
    public ParkingModel findParkingById(Long parkingId) {
        return parkingEntityMapper.toParkingModel
                (parkingEntityRepository.findById(parkingId)
                        .orElseThrow(() -> new NotFoundException(Constants.PARKING_NOT_FOUND)));

    }

    @Override
    public Boolean findVehicleByPlate(String plate) {
        return registryEntityRepository.existsByPlateAndDepartureIsNull(plate);
    }

    @Override
    public ParkingModel findParkingByName(String name) {
        return parkingEntityMapper.toParkingModel
                (parkingEntityRepository.findByName(name));
    }

    @Override
    public UserModel findUserById(Long userId) {
        return userEntityMapper.toUserModel(
                userEntityRepository.findById(userId).orElse(null)
        );
    }

    @Override
    public void deleteParking(Long parkingId) {
        parkingEntityRepository.deleteById(parkingId);
    }

    @Override
    public void deleteUser(Long userId) {
        userEntityRepository.deleteById(userId);
    }

    @Override
    public void updateParking(ParkingModel parking) {
        parkingEntityRepository.save
                (parkingEntityMapper.toParkingEntity(parking));
    }

    @Override
    public void updateUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.save(
                userEntityMapper.toUserEntity(user));
    }
}
