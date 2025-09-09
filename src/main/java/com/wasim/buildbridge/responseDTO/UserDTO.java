package com.wasim.buildbridge.responseDTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String fullName;
    private String bio;
    private Set<String> skills;
}
