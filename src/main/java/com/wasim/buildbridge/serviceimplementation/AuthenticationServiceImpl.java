package com.wasim.buildbridge.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wasim.buildbridge.exception.UserAlreadyExistsException;
import com.wasim.buildbridge.jwt.JwtConstant;
import com.wasim.buildbridge.jwt.JwtService;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.requestDTO.SigninRequestDTO;
import com.wasim.buildbridge.requestDTO.SignupRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.AuthResponse;
import com.wasim.buildbridge.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Override
    public ApiResponseDTO signup(SignupRequestDTO request) {
        boolean isExists = userRepository.existsByEmail(request.getEmail());

        if (isExists) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        isExists = userRepository.existsByUsername(request.getUsername());
        if (isExists) {
            throw new UserAlreadyExistsException("User already exists with username: " + request.getUsername());
        }

        User user = mapTOUser(request);
        userRepository.save(user);

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Signup successful",
                null);
        return response;
    }

    @Override
    public ApiResponseDTO signin(SigninRequestDTO request) {

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(),
                        request.getPassword()));

        User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail()).get();
        AuthResponse authResponse = new AuthResponse(
                jwtService.getToken(user),
                JwtConstant.JWT_PREFIX,
                user.getUsername(),
                LocalDateTime.now().plusMinutes(30));

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Signin successful",
                authResponse);
        return response;
    }

    private User mapTOUser(SignupRequestDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setProfileImgUrl(request.getProfileImgUrl());
        user.setBio(request.getBio());
        user.setSkills(request.getSkills());
        user.setProjects(new ArrayList<>());
        user.setSentConnections(new ArrayList<>());
        user.setReceivedConnections(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

}
