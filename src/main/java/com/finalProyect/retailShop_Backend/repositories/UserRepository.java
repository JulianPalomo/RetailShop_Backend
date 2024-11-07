package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @NonNull
    Optional<UserEntity> findById(@NonNull Long id);
}
