package com.wasim.buildbridge.serviceimplementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.UserDTO;
import com.wasim.buildbridge.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponseDTO getUserProfile(String email) {
        User user = getCurrentUser(email);

        UserDTO userDTO = mapToUserDTO(user);
        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "User profile fetched successfully",
                userDTO);

        return response;
    }

    @Override
    public ApiResponseDTO getUserProjects(String email) {
        User user = getCurrentUser(email);

        return new ApiResponseDTO(
                true,
                "User projects fetched successfully",
                user.getProjects());
    }

    @Override
    public ApiResponseDTO getUserConnectinos(String email) {
        User user = getCurrentUser(email);

        return new ApiResponseDTO(
                true,
                "User connections fetched successfully",
                user.getConnections());
    }

    @Transactional
    @Override
    public ApiResponseDTO updateUserSkills(String email, Set<String> skills) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (skills != null && !skills.isEmpty()) {
            user.setSkills(new HashSet<>(skills));
        }

        User updatedUser = userRepository.save(user);

        return new ApiResponseDTO(true, "Skills updated successfully", updatedUser.getSkills());
    }

    @Override
    public ApiResponseDTO updateUserProfile(String email, String imageUrl) {
        User user = getCurrentUser(email);

        if (imageUrl != null) {
            user.setProfileImgUrl(imageUrl);
        }
        User updatedUser = userRepository.save(user);

        return new ApiResponseDTO(
                true,
                "User profile image updated successfully",
                updatedUser.getProfileImgUrl());
    }

    private User getCurrentUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with email: " + email);
        });
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setBio(user.getBio());
        userDTO.setSkills(user.getSkills());

        return userDTO;
    }
}
