package com.wasim.buildbridge.requestDTO;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 8, max = 20, message = "Username must be between 8 to 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 to 20 characters")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 25, message = "Name must be beetween 5 to 25 characters")
    private String fullName;

    private String profileImgUrl;

    @NotBlank(message = "Bio is required")
    @Size(min = 50, max = 500, message = "Bio must be between 50 to 500 characters")
    private String bio;

    @Size(min = 1, message = "At least one skill is required")
    private Set<@NotBlank(message = "Skill name cannot be blank") String> skills;
}
