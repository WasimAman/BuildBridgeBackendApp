package com.wasim.buildbridge.responseDTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String description;
    private List<String> images;

    private List<LikeDTO> likes;
    private List<CommentDTO> comments;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
