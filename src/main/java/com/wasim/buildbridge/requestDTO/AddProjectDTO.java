package com.wasim.buildbridge.requestDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectDTO {
    private String title;
    private List<String> images;
    private String description;
    private String repoUrl;
    private List<String> contributors;
}
