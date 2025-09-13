package com.wasim.buildbridge.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.mapper.UserMapper;
import com.wasim.buildbridge.model.Projects;
import com.wasim.buildbridge.repository.ProjectRepository;
import com.wasim.buildbridge.requestDTO.AddProjectDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.ProjectDTO;
import com.wasim.buildbridge.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponseDTO addProject(String username,AddProjectDTO projectDTO) {
        try {
            Projects project = userMapper.mapToProject(username, projectDTO);
            Projects savedProject = projectRepository.save(project);
            ProjectDTO projectResponse = userMapper.mapToProjectDTO(savedProject);

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "Project saved successfully",
                    projectResponse);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error: while saving project");
        }
    }


    @Override
    public ApiResponseDTO getAllProject(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProject'");
    }

    @Override
    public ApiResponseDTO getProjectById(long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectById'");
    }

    @Override
    public ApiResponseDTO updateProject(long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProject'");
    }

    @Override
    public ApiResponseDTO deleteProject(long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProject'");
    }

}
