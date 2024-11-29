package com.pet.store.petstore.service;

import com.pet.store.petstore.request.UserRequestDTO;
import com.pet.store.petstore.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO create(UserRequestDTO userRequest);

    UserResponseDTO update(UserRequestDTO userRequest, String email);

    UserResponseDTO get(String email);

    Iterable<UserResponseDTO> all();

    UserResponseDTO delete(String email);
}
