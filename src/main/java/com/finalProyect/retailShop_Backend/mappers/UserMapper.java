package com.finalProyect.retailShop_Backend.mappers;


import com.finalProyect.retailShop_Backend.DTO.UserDto;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Convierte de UserEntity a UserDTO
    public UserDto toDTO(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getDni(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isAdmin(),
                userEntity.isActive()
        );
    }

    // Convierte de UserDTO a UserEntity
    public UserEntity toEntity(UserDto userDTO) {
        return UserEntity.builder()
                .name(userDTO.getName())
                .dni(userDTO.getDni())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .isAdmin(userDTO.isAdmin())
                .isActive(true)
                .build();
    }
}