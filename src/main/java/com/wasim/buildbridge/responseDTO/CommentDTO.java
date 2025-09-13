package com.wasim.buildbridge.responseDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private long id;
    private String comment;
    private String username;
    private LocalDateTime commentedAt;
}
