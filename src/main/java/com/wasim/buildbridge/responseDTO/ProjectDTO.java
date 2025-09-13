package com.wasim.buildbridge.responseDTO;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private String repoUrl;
    private List<String> images;

    private List<LikeDTO> likes;
    private List<CommentDTO> comments;

    private List<ProjectContributorDTO> contributors;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

