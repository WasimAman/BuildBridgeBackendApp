package com.wasim.buildbridge.service;

import com.wasim.buildbridge.requestDTO.AddProjectDTO;
import com.wasim.buildbridge.requestDTO.CommentRequestDTO;
import com.wasim.buildbridge.requestDTO.UpdateProjectDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface ProjectService {

    ApiResponseDTO addProject(String username, AddProjectDTO projectDTO);

    ApiResponseDTO getAllProject(String username);

    ApiResponseDTO getProjectById(long projectId);

    ApiResponseDTO updateProject(long projectId,UpdateProjectDTO updateProject,String currentUser);

    ApiResponseDTO deleteProject(long projectId,String currentUser);

    ApiResponseDTO likeProject(long projectId, String username);

    ApiResponseDTO commentProject(long projectId, CommentRequestDTO comment,String username);
    
}
