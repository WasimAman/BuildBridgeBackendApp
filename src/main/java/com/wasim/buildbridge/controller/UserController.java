package com.wasim.buildbridge.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.requestDTO.UpdateProfileImgRequestDTO;
import com.wasim.buildbridge.requestDTO.UpdateSkillsRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("profile") // Url :- http://localhost:8080/api/user/profile
    public ResponseEntity<ApiResponseDTO> userProfile(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.getUserProfile(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("projects") // Url :- http://localhost:8080/api/user/projects
    public ResponseEntity<ApiResponseDTO> userProjects(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.getUserProjects(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("connections") // Url :- http://localhost:8080/api/user/connections
    public ResponseEntity<ApiResponseDTO> userConnections(Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.getUserConnectinos(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("update/profile/skills")    // url :- http://localhost:8080/api/user/update/profile/skills
    public ResponseEntity<ApiResponseDTO> updateSkills(
            @RequestBody UpdateSkillsRequestDTO request,
            Authentication authentication) {

        System.out.println(request.getSkills());
        String email = authentication.getName(); // JWT authenticated user
        ApiResponseDTO response = userService.updateUserSkills(email, new HashSet<>(request.getSkills()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("update/profile/profileimg") // Url :- http://localhost:8080/api/user/update/profileimg
    public ResponseEntity<ApiResponseDTO> updateUserProfileImage(@RequestBody UpdateProfileImgRequestDTO updateProfileImgRequestDTO,
            Authentication authentication) {
        System.out.println(updateProfileImgRequestDTO);
        String email = authentication.getPrincipal().toString();
        ApiResponseDTO response = userService.updateUserProfile(email, updateProfileImgRequestDTO.getImageUrl());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
