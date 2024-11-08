package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
