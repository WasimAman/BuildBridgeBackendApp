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
        try {
            System.out.println("One");
            Projects project = userMapper.mapToProject(username, projectDTO);
            System.out.println("two");
            Projects savedProject = projectRepository.save(project);
            System.out.println("three");
            ProjectDTO projectResponse = userMapper.mapToProjectDTO(savedProject);
            System.out.println("four");
            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "Project saved successfully",
                    projectResponse);
            System.out.println("five");
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error: while saving project");
        }
    }

    @Override
    public ApiResponseDTO getAllProject(String username) {
        try {
            User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found with username: " + username);
            });

            List<ProjectDTO> projects = userMapper.mapToProjectDTO(user.getProjects());

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "All projects of user " + username + " has been fetched",
                    projects);
            return response;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Error: while fetching projects");
        }
    }

    @Override
    public ApiResponseDTO getProjectById(long projectId) {
        try {
            Projects project = projectRepository.findById(projectId).orElseThrow(() -> {
                throw new ProjectNotFoundException("project not found with id: " + projectId);
            });

            ProjectDTO projectResponse = userMapper.mapToProjectDTO(project);

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "project fetched with id: " + projectId,
                    projectResponse);

            return response;
        } catch (ProjectNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error: while fetching project");
        }
    }

    @Override
    public ApiResponseDTO updateProject(long projectId, UpdateProjectDTO updateProject) {
        try {
            Projects project = projectRepository.findById(projectId).orElseThrow(() -> {
                throw new ProjectNotFoundException("project not found with id: " + projectId);
            });

            project.setTitle(updateProject.getTitle());
            project.setDescription(updateProject.getDescription());
            project.setImages(updateProject.getImages());
            project.setRepoUrl(updateProject.getRepoUrl());

            projectRepository.save(project);
            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "project updated successfully",
                    null);
            return response;
        } catch (ProjectNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error: while deleting project");
        }
    }

    @Override
    public ApiResponseDTO deleteProject(long projectId) {
        try {
            Projects project = projectRepository.findById(projectId).orElseThrow(() -> {
                throw new ProjectNotFoundException("project not found with id: " + projectId);
            });

            projectRepository.delete(project);

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "project deleted successfully",
                    null);
            return response;
        } catch (ProjectNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error: while deleting project");
        }
    }

    @Override
    public ApiResponseDTO likeProject(long projectId, String username) {
        try {
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
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (ProjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: while saving like");
        }
    }

    @Override
    public ApiResponseDTO commentProject(long projectId, CommentRequestDTO commentRequest) {
        try {
            User user = userRepository.findByUsernameOrEmail(commentRequest.getUsername()).orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found with username: " + commentRequest.getUsername());
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
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (ProjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: while saving like");
        }
    }

}
