package com.finalProyect.retailShop_Backend.services;

import com.finalProyect.retailShop_Backend.DTO.UserDto;
import com.finalProyect.retailShop_Backend.entities.persons.UserEntity;
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
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public UserDto createUser(UserDto userDTO) {
        UserEntity userEntity = userMapper.toEntity(userDTO);
        ///userEntity.setPassword(encryptPassword(userDTO.getPassword())); // Encripta la contraseña
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toDTO(savedUser);
    }

    public UserDto updateUser(Long id, UserDto updatedUserDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setName(updatedUserDTO.getName());
            user.setEmail(updatedUserDTO.getEmail());
            user.setAdmin(updatedUserDTO.isAdmin());
            UserEntity updatedUser = userRepository.save(user);
            return userMapper.toDTO(updatedUser);
        } else {
            return null; // o lanzar una excepción si prefieres manejar errores
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public UserEntity authenticate(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }





}
