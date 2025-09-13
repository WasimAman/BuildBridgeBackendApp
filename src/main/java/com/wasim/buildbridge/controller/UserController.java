package com.wasim.buildbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.requestDTO.UpdateUserDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("users/{username}")
    public ResponseEntity<ApiResponseDTO> getUserProfile(@PathVariable("username") String username) {
        ApiResponseDTO response = userService.getUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("users/me")
    public ResponseEntity<ApiResponseDTO> authenticatedUserProfile(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.getUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("users/me")
    public ResponseEntity<ApiResponseDTO> updateUserProfile(@Valid @RequestBody UpdateUserDTO updateRequest,Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.updateUserProfile(username,updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("users/me")
    public ResponseEntity<ApiResponseDTO> deleteUserProfile(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.deleteUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/search")
    public ResponseEntity<ApiResponseDTO> searchUserProfile(@RequestParam("query") String query) {
        ApiResponseDTO response = userService.searchUserProfile(query);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
