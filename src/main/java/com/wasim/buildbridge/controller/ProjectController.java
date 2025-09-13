package com.wasim.buildbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.requestDTO.AddProjectDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.ProjectService;

@RestController
@RequestMapping("/api/v1/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("projects")
    public ResponseEntity<ApiResponseDTO> addNewProject(@RequestBody AddProjectDTO projectDTO,Authentication authentication){

        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = projectService.addProject(username,projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("users/{username}/projects")
    public ResponseEntity<ApiResponseDTO> getAllProject(@PathVariable("username") String username){
        ApiResponseDTO response = projectService.getAllProject(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<ApiResponseDTO> getProjectById(@PathVariable("id") long projectId){
        ApiResponseDTO response = projectService.getProjectById(projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("projects/{id}")
    public ResponseEntity<ApiResponseDTO> updateProject(@PathVariable("id") long projectId){
        ApiResponseDTO response = projectService.updateProject(projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("projects/{id}")
    public ResponseEntity<ApiResponseDTO> deleteProject(@PathVariable("id") long projectId){
        ApiResponseDTO response = projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
