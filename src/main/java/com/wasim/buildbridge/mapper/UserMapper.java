package com.wasim.buildbridge.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.model.Comment;
import com.wasim.buildbridge.model.Like;
import com.wasim.buildbridge.model.Post;
import com.wasim.buildbridge.model.ProjectContributors;
import com.wasim.buildbridge.model.Projects;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.model.UserConnections;
import com.wasim.buildbridge.model.enums.ProjectContributorRole;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.requestDTO.AddProjectDTO;
import com.wasim.buildbridge.requestDTO.PostRequestDTO;
import com.wasim.buildbridge.responseDTO.CommentDTO;
import com.wasim.buildbridge.responseDTO.ConnectionDTO;
import com.wasim.buildbridge.responseDTO.ConnectionPreviewDTO;
import com.wasim.buildbridge.responseDTO.LikeDTO;
import com.wasim.buildbridge.responseDTO.PostDTO;
import com.wasim.buildbridge.responseDTO.ProjectContributorDTO;
import com.wasim.buildbridge.responseDTO.ProjectDTO;
import com.wasim.buildbridge.responseDTO.UserDTO;

@Service
public class UserMapper {
    @Autowired
    private UserRepository userRepository;

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setProfileImgUrl(user.getProfileImgUrl());
        userDTO.setBio(user.getBio());
        userDTO.setSkills(user.getSkills());
        userDTO.setProjects(mapToProjectDTO(user.getProjects()));
        userDTO.setPosts(mapToPostDTO(user.getPosts()));
        userDTO.setConnections(mapToConnectionDTO(user.getSentConnections(), user.getReceivedConnections()));
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    public List<ProjectDTO> mapToProjectDTO(List<Projects> projects) {
        List<ProjectDTO> projectDTO = new ArrayList<>();
        for (Projects project : projects) {
            ProjectDTO dto = mapToProjectDTO(project);
            projectDTO.add(dto);
        }

        return projectDTO;
    }

    public ProjectDTO mapToProjectDTO(Projects project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setRepoUrl(project.getRepoUrl());
        dto.setImages(project.getImages());
        dto.setLikes(mapToLikeDTO(project.getLikes()));
        dto.setComments(mapToCommentDTO(project.getComments()));
        dto.setContributors(mapToContributors(project.getContributors()));
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());

        return dto;
    }

    public List<ProjectContributorDTO> mapToContributors(List<ProjectContributors> contributors) {
        if (contributors == null)
            return new ArrayList<>();
        List<ProjectContributorDTO> dtoList = new ArrayList<>();
        for (ProjectContributors pc : contributors) {
            ProjectContributorDTO dto = new ProjectContributorDTO();
            dto.setUserId(pc.getUser().getId());
            dto.setUsername(pc.getUser().getUsername());
            dto.setProfileImgUrl(pc.getUser().getProfileImgUrl());
            dto.setRole(pc.getRole());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<CommentDTO> mapToCommentDTO(List<Comment> comments) {
        if (comments == null)
            return new ArrayList<>();
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment c : comments) {
            CommentDTO dto = new CommentDTO();
            dto.setId(c.getId());
            dto.setComment(c.getComment());
            dto.setUsername(c.getUser().getUsername());
            dto.setCommentedAt(c.getCommentedAt());
            commentDTOs.add(dto);
        }
        return commentDTOs;
    }

    public List<LikeDTO> mapToLikeDTO(List<Like> likes) {
        if (likes == null)
            return new ArrayList<>();
        List<LikeDTO> likeDTOs = new ArrayList<>();
        for (Like l : likes) {
            LikeDTO dto = new LikeDTO();
            dto.setId(l.getId());
            dto.setUsername(l.getUser().getUsername());
            dto.setProfileImgUrl(l.getUser().getProfileImgUrl());
            likeDTOs.add(dto);
        }
        return likeDTOs;
    }

    public ConnectionDTO mapToConnectionDTO(List<UserConnections> sentConnections,
            List<UserConnections> receivedConnections) {
        List<ConnectionPreviewDTO> preview = new ArrayList<>();

        if (sentConnections != null) {
            for (UserConnections uc : sentConnections) {
                if (uc.getStatus().name().equals("ACCEPTED")) {
                    User r = uc.getReceiver();
                    preview.add(new ConnectionPreviewDTO(r.getId(), r.getUsername(), r.getProfileImgUrl()));
                }
            }
        }

        if (receivedConnections != null) {
            for (UserConnections uc : receivedConnections) {
                if (uc.getStatus().name().equals("ACCEPTED")) {
                    User s = uc.getSender();
                    preview.add(new ConnectionPreviewDTO(s.getId(), s.getUsername(), s.getProfileImgUrl()));
                }
            }
        }

        return new ConnectionDTO(preview.size(), preview);
    }

    public ConnectionDTO mapToConnectionDTO(List<UserConnections> connections) {
        List<ConnectionPreviewDTO> preview = new ArrayList<>();

        if (connections != null) {
            for (UserConnections uc : connections) {
                User s = uc.getSender();
                preview.add(new ConnectionPreviewDTO(s.getId(), s.getUsername(), s.getProfileImgUrl()));
            }
        }

        return new ConnectionDTO(preview.size(), preview);
    }

    public PostDTO mapToPostDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setDescription(post.getDescription());
        dto.setImages(post.getImages());
        dto.setLikes(mapToLikeDTO(post.getLikes()));
        dto.setComments(mapToCommentDTO(post.getComments()));
        dto.setCreatedAt(post.getPostedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        return dto;
    }

    public List<PostDTO> mapToPostDTO(List<Post> posts) {
        if (posts == null)
            return new ArrayList<>();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO dto = mapToPostDTO(post);
            postDTOs.add(dto);
        }
        return postDTOs;
    }

    public Projects mapToProject(String username, AddProjectDTO projectDTO) {
        User owner = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Projects project = new Projects();
        project.setOwner(owner);
        project.setTitle(projectDTO.getTitle());
        project.setImages(projectDTO.getImages());
        project.setDescription(projectDTO.getDescription());
        project.setRepoUrl(projectDTO.getRepoUrl());
        project.setLikes(new ArrayList<>());
        project.setComments(new ArrayList<>());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());

        if (project.getContributors() == null) {
            project.setContributors(new ArrayList<>());
        }

        ProjectContributors ownerContributor = new ProjectContributors();
        ownerContributor.setUser(owner);
        ownerContributor.setProject(project);
        ownerContributor.setRole(ProjectContributorRole.OWNER);
        project.getContributors().add(ownerContributor);

        if (projectDTO.getContributors() != null) {
            for (String contributorUsername : projectDTO.getContributors()) {

                if (contributorUsername.equals(username)) {
                    continue;
                }

                User contributorUser = userRepository.findByUsernameOrEmail(contributorUsername)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found with contributor username: " + contributorUsername));

                boolean alreadyExists = project.getContributors().stream()
                        .anyMatch(c -> c.getUser().getId() == contributorUser.getId());

                if (!alreadyExists) {
                    ProjectContributors collaborator = new ProjectContributors();
                    collaborator.setUser(contributorUser);
                    collaborator.setProject(project);
                    collaborator.setRole(ProjectContributorRole.COLLABORATOR);
                    project.getContributors().add(collaborator);
                }
            }
        }

        return project;
    }

    public Post mapToPost(PostRequestDTO postRequest, String username) {
        User owner = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Post post = new Post();
        post.setDescription(postRequest.getDescription());
        post.setImages(postRequest.getImages());
        post.setOwner(owner);
        post.setPostedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setLikes(new ArrayList<>());
        post.setComments(new ArrayList<>());
        return post;
    }
}