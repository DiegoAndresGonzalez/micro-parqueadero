package com.api.parqueadero.infrastructure.repository;

import com.api.parqueadero.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u.id FROM usuario u WHERE u.email = :email")
    Long findIdByEmail(@Param("email") String email);

}
