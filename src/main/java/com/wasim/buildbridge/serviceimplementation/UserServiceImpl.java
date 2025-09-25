package com.wasim.buildbridge.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.mapper.UserMapper;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.requestDTO.UpdateUserDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.UserDTO;
import com.wasim.buildbridge.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponseDTO getUserProfile(String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with this username: " + username);
        });

        UserDTO userdto = userMapper.mapToUserDTO(user);
        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "User data has been successfully fetched",
                userdto);

        return response;
    }

    @Transactional
    @Modifying
    @Override
    public ApiResponseDTO updateUserProfile(String username, UpdateUserDTO updateRequest) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with this username: " + username);
        });

        user.setFullName(updateRequest.getFullName());
        user.setProfileImgUrl(updateRequest.getProfileImgUrl());
        user.setBio(updateRequest.getBio());
        user.setSkills(updateRequest.getSkills());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "User updated successfully",
                null);
        return response;
    }

    @Transactional
    @Override
    public ApiResponseDTO deleteUserProfile(String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with this username: " + username);
        });

        userRepository.delete(user);
        ApiResponseDTO responseDTO = new ApiResponseDTO(
                true,
                "User deleted seccssfully",
                username);
        return responseDTO;
    }

    @Override
    public ApiResponseDTO searchUserProfile(String query) {
        List<User> users = userRepository.search(query);
        if (users == null || users.size() == 0) {
            return new ApiResponseDTO(
                    true,
                    "Cannot find user",
                    null);
        } else {
            return new ApiResponseDTO(
                    true,
                    "user has been searched",
                    users);
        }
    }
}