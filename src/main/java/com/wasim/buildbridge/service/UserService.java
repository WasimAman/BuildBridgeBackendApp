package com.wasim.buildbridge.service;

import java.util.Set;

import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface UserService {

    ApiResponseDTO getUserProfile(String email);

    ApiResponseDTO getUserProjects(String email);

    ApiResponseDTO getUserConnectinos(String email);

    ApiResponseDTO updateUserSkills(String email, Set<String> skills);

    ApiResponseDTO updateUserProfile(String email, String imageUrl);
    
}
