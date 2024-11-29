package com.pet.store.petstore.service.impl;

import com.pet.store.petstore.entity.User;
import com.pet.store.petstore.enums.UserStatus;
import com.pet.store.petstore.exception.NotFoundException;
import com.pet.store.petstore.exception.PetStoreException;
import com.pet.store.petstore.repository.UserRepository;
import com.pet.store.petstore.request.UserRequestDTO;
import com.pet.store.petstore.response.UserResponseDTO;
import com.pet.store.petstore.service.UserService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO userRequest) {
        val email = userRequest.getEmail();

        if (userRepository.findByEmail(email) != null) {
            throw new PetStoreException("Email " + email + " already exists.");
        }

        val data = new User();
        data.setEmail(userRequest.getEmail());
        data.setPhone((userRequest.getPhone()));
        data.setUsername(userRequest.getUsername());
        data.setStatus(userRequest.getStatus());
        data.setFirstName(userRequest.getFirstName());
        data.setLastName(userRequest.getLastName());
        data.setPassword(userRequest.getPassword());

        val response = userRepository.save(data);

        return entityToUserMapper(response);
    }

    @Override
    public UserResponseDTO update(UserRequestDTO userRequest, String email) throws NotFoundException, PetStoreException {
        val user = userRepository.findByEmail(email);

        if (user == null) {
            throw new NotFoundException("User with email " + email + "does not exist");
        }

        user.setStatus(userRequest.getStatus());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());

        val response = userRepository.save(user);

        return entityToUserMapper(response);
    }

    @Override
    public UserResponseDTO get(String email) {
        val user = userRepository.findByEmail(email);

        if (user == null) {
            throw new PetStoreException("Email " + email + " does not exist.");
        }

        return entityToUserMapper(user);
    }

    @Override
    public Iterable<UserResponseDTO> all() {
        val allUsers = new ArrayList<UserResponseDTO>();

        val users = userRepository.findAll();
        for (User user : users) {
            allUsers.add(entityToUserMapper(user));
        }

        return allUsers;
    }

    @Override
    public UserResponseDTO delete(String email) {
        val user = userRepository.findByEmail(email);

        if (user == null) {
            throw new PetStoreException("Email " + email + " does not exist.");
        }

        val userResponseDTO = entityToUserMapper(user);
        userRepository.delete(user);

        return userResponseDTO;
    }

    private UserResponseDTO entityToUserMapper(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId().toString());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setStatus(user.getStatus());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setUsername(user.getUsername());

        return userResponseDTO;
    }
}
