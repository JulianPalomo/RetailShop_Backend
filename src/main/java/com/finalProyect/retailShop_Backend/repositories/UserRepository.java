package com.finalProyect.retailShop_Backend.repositories;

import com.finalProyect.retailShop_Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    List<UserEntity> findByName(String name);
}
