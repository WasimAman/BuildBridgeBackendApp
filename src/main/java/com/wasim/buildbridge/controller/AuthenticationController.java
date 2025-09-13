package com.wasim.buildbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.requestDTO.SigninRequestDTO;
import com.wasim.buildbridge.requestDTO.SignupRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("signup")  // complete url:- http://localhost:8080/auth/signup
    public ResponseEntity<ApiResponseDTO> signupUser(@Valid @RequestBody SignupRequestDTO request){
        ApiResponseDTO response = authenticationService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("signin")  // complete url:- http://localhost:8080/auth/signin
    public ResponseEntity<ApiResponseDTO> signinUser(@Valid @RequestBody SigninRequestDTO request){
        ApiResponseDTO response = authenticationService.signin(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
