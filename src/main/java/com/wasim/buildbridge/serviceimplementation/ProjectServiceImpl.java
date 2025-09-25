package com.wasim.buildbridge.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.exception.ProjectNotFoundException;
import com.wasim.buildbridge.mapper.UserMapper;
import com.wasim.buildbridge.model.Comment;
import com.wasim.buildbridge.model.Like;
import com.wasim.buildbridge.model.Projects;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.repository.CommentRepository;
import com.wasim.buildbridge.repository.LikeRepository;
import com.wasim.buildbridge.repository.ProjectRepository;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.requestDTO.AddProjectDTO;
import com.wasim.buildbridge.requestDTO.CommentRequestDTO;
import com.wasim.buildbridge.requestDTO.UpdateProjectDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.CommentDTO;
import com.wasim.buildbridge.responseDTO.LikeDTO;
import com.wasim.buildbridge.responseDTO.ProjectDTO;
import com.wasim.buildbridge.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ApiResponseDTO addProject(String username, AddProjectDTO projectDTO) {
        Projects project = userMapper.mapToProject(username, projectDTO);
        Projects savedProject = projectRepository.save(project);
        ProjectDTO projectResponse = userMapper.mapToProjectDTO(savedProject);
        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Project saved successfully",
                projectResponse);
        return response;
    }

    @Override
    public ApiResponseDTO getAllProject(String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with username: " + username);
        });

        List<ProjectDTO> projects = userMapper.mapToProjectDTO(user.getProjects());

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "All projects of user " + username + " has been fetched",
                projects);
        return response;
    }

    @Override
    public ApiResponseDTO getProjectById(long projectId) {
        Projects project = projectRepository.findById(projectId).orElseThrow(() -> {
            throw new ProjectNotFoundException("project not found with id: " + projectId);
        });

        ProjectDTO projectResponse = userMapper.mapToProjectDTO(project);

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "project fetched with id: " + projectId,
                projectResponse);

        return response;
    }

    @Override
    public ApiResponseDTO updateProject(long projectId, UpdateProjectDTO updateProject, String currentUser) {
        Projects project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));

        if (!project.getOwner().getUsername().equals(currentUser)) {
            throw new RuntimeException("You are not authorized to update this project");
        }

        project.setTitle(updateProject.getTitle());
        project.setDescription(updateProject.getDescription());
        project.setImages(updateProject.getImages());
        project.setRepoUrl(updateProject.getRepoUrl());
        project.setUpdatedAt(LocalDateTime.now());

        projectRepository.save(project);
        return new ApiResponseDTO(true, "Project updated", userMapper.mapToProjectDTO(project));
    }

    @Override
    public ApiResponseDTO deleteProject(long projectId, String currentUser) {
        Projects project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));

        if (!project.getOwner().getUsername().equals(currentUser)) {
            throw new RuntimeException("You are not authorized to delete this project");
        }

        projectRepository.delete(project);
        return new ApiResponseDTO(true, "Project deleted", null);
    }

    @Override
    public ApiResponseDTO likeProject(long projectId, String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with username: " + username);
        });

        Projects project = projectRepository.findById(projectId).orElseThrow(() -> {
            throw new ProjectNotFoundException("Project not found with id: " + projectId);
        });
        Like like = new Like();
        like.setUser(user);
        like.setProject(project);

        Like savedLike = likeRepository.save(like);
        LikeDTO likeDTO = new LikeDTO(savedLike.getId(), user.getUsername(), user.getProfileImgUrl());

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Like has been saved",
                likeDTO);
        return response;
    }

    @Override
    public ApiResponseDTO commentProject(long projectId, CommentRequestDTO commentRequest, String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with username: " + username);
        });

        Projects project = projectRepository.findById(projectId).orElseThrow(() -> {
            throw new ProjectNotFoundException("Project not found with id: " + projectId);
        });

        Comment comment = new Comment();
        comment.setComment(commentRequest.getComment());
        comment.setUser(user);
        comment.setProject(project);
        comment.setCommentedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

        CommentDTO commentDTO = new CommentDTO(savedComment.getId(), savedComment.getComment(), user.getUsername(),
                savedComment.getCommentedAt());

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Commented Successfully",
                commentDTO);

        return response;
    }

}
