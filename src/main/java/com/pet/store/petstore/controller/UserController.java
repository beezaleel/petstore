package com.pet.store.petstore.controller;

import com.pet.store.petstore.exception.PetStoreException;
import com.pet.store.petstore.request.UserRequestDTO;
import com.pet.store.petstore.response.UserResponseDTO;
import com.pet.store.petstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user) throws PetStoreException {
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(
            @Valid @RequestBody UserRequestDTO user,
            @RequestParam(name = "email") String email) throws PetStoreException {
        return ResponseEntity.ok(userService.update(user, email));
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUser(@RequestParam(name = "email") String email) throws PetStoreException {
        return ResponseEntity.ok(userService.get(email));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<UserResponseDTO>> getAll() throws PetStoreException {
        return ResponseEntity.ok(userService.all());
    }

    @DeleteMapping
    public ResponseEntity<UserResponseDTO> delete(@RequestParam(name = "email") String email) throws PetStoreException {
        return ResponseEntity.ok(userService.delete(email));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}