package com.wasim.buildbridge.service;

import com.wasim.buildbridge.requestDTO.SigninRequestDTO;
import com.wasim.buildbridge.requestDTO.SignupRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface AuthenticationService {
    public ApiResponseDTO signup(SignupRequestDTO request);
    public ApiResponseDTO signin(SigninRequestDTO request);
}
