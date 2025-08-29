package com.wasim.buildbridge.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.exception.SigninFailedException;
import com.wasim.buildbridge.exception.SignupFailedException;
import com.wasim.buildbridge.exception.UserAlreadyExistException;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.requestDTO.SigninRequestDTO;
import com.wasim.buildbridge.requestDTO.SignupRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ApiResponseDTO signup(SignupRequestDTO request) {
        try {
            boolean isExists = userRepository.existsByEmail(request.getEmail());

            if (isExists) {
                throw new UserAlreadyExistException("User already exists with email: " + request.getEmail());
            }

            User user = mapTOUser(request);
            userRepository.save(user);

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "Signup successful",
                    null);
            return response;
        }catch(UserAlreadyExistException ex){
            throw ex;
        }
        catch (Exception e) {
            throw new SignupFailedException("Signup failed!");
        }
    }

    @Override
    public ApiResponseDTO signin(SigninRequestDTO request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // will add jwt token letter...

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "Signin successful",
                    null);
            return response;
        }catch(BadCredentialsException ex){
            throw ex;
        }catch(UsernameNotFoundException ex){
            throw ex;
        }catch (Exception e) {
            throw new SigninFailedException("Signin failed!");
        }
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
        user.setConnections(new ArrayList<>());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

}
