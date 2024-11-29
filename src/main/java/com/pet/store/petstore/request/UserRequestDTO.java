package com.pet.store.petstore.request;

import com.pet.store.petstore.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    private String phone;
    private UserStatus status;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "User name is required")
    private String username;
}
