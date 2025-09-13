package com.wasim.buildbridge.service;

import com.wasim.buildbridge.requestDTO.UpdateUserDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface UserService {

    ApiResponseDTO getUserProfile(String username);

    ApiResponseDTO updateUserProfile(String username, UpdateUserDTO updateRequest);

    ApiResponseDTO deleteUserProfile(String username);

    ApiResponseDTO searchUserProfile(String query);
    
}
