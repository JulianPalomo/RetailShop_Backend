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
                userEntity.getEmail(),
                userEntity.isAdmin()
        );
    }

    // Convierte de UserDTO a UserEntity
    public UserEntity toEntity(UserDto userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .isAdmin(userDTO.isAdmin())
                // Nota: No se establece password aquí por seguridad
                .build();
    }
}
