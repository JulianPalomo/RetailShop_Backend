package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.UserDto;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
import com.finalProyect.retailShop_Backend.exceptions.NotFoundException;
import com.finalProyect.retailShop_Backend.exceptions.ProductNotFoundException;
import com.finalProyect.retailShop_Backend.mappers.UserMapper;
import com.finalProyect.retailShop_Backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;{

        }
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(UserEntity::isActive)
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public UserDto createUser(UserDto userDTO) {
        Optional<UserEntity> existingUser = userRepository.findByDni(userDTO.getDni());

        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            if (!user.isActive()) {
                user.setName(userDTO.getName());
                user.setEmail(userDTO.getEmail());
                user.setPassword(userDTO.getPassword());
                user.setAdmin(userDTO.isAdmin());
                user.setActive(true); // Reactivar usuario
                UserEntity updatedUser = userRepository.save(user);
                return userMapper.toDTO(updatedUser);
            } else {
                throw new RuntimeException("El usuario con el DNI ya existe y está activo");
            }
        }

        // Si no existe, crear uno nuevo
        UserEntity newUser = userMapper.toEntity(userDTO);
        newUser.setActive(true);
        UserEntity savedUser = userRepository.save(newUser);
        return userMapper.toDTO(savedUser);
    }


    public UserDto updateUser(Long id, UserDto updatedUserDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setName(updatedUserDTO.getName());
            user.setDni(updatedUserDTO.getDni());
            user.setEmail(updatedUserDTO.getEmail());
            user.setAdmin(updatedUserDTO.isAdmin());
            user.setPassword(updatedUserDTO.getPassword());
            UserEntity updatedUser = userRepository.save(user);
            return userMapper.toDTO(updatedUser);
        } else {
            new NotFoundException("User no encontrado" );
        }
        return updatedUserDTO;
    }

    public void deleteUser(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if(userEntityOptional.isPresent())
        {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setActive(false);
            userRepository.save(userEntity);

        }else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }


    public UserDto authenticate(String dni, String password) {
        Optional<UserEntity> user = userRepository.findByDni(dni);
        if (user != null && user.get().getPassword().equals(password)) {
            return userMapper.toDTO(user.get());
        }
        return null;
    }


}