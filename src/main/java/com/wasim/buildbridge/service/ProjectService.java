package com.wasim.buildbridge.service;

import com.wasim.buildbridge.requestDTO.AddProjectDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface ProjectService {

    ApiResponseDTO addProject(String username, AddProjectDTO projectDTO);

    ApiResponseDTO getAllProject(String username);

    ApiResponseDTO getProjectById(long projectId);

    ApiResponseDTO updateProject(long projectId);

    ApiResponseDTO deleteProject(long projectId);
    
}
