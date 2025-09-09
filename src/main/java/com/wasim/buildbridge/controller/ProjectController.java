package com.wasim.buildbridge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

@RestController
@RequestMapping("/api/projects/")
public class ProjectController {

    @PostMapping("add") // url :- http://localhost:8080/api/projects/add
    public ResponseEntity<ApiResponseDTO> addProjects(){
        return null;
    }

    @PostMapping("delete")  // url :- http://localhost:8080/api/projects/delete
    public ResponseEntity<ApiResponseDTO> deleteProjects(){
        return null;
    }

    @PostMapping("update")  // url :- http://localhost:8080/api/projects/update
    public ResponseEntity<ApiResponseDTO> updateProjects(){
        return null;
    }
}
