package com.example.game_dialogue_generator.dto;

// same as fastapi pydantic, validate object from request body
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;

}