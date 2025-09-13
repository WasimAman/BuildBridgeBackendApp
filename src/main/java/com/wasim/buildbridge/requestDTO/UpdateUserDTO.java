package com.wasim.buildbridge.requestDTO;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
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
