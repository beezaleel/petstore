package com.pet.store.petstore.response;

import com.pet.store.petstore.enums.UserStatus;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserStatus status;
    private String username;
}
