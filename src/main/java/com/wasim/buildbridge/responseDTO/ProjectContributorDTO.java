package com.wasim.buildbridge.responseDTO;

import com.wasim.buildbridge.model.enums.ProjectContributorRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectContributorDTO {
    private Long userId;
    private String username;
    private String profileImgUrl;
    private ProjectContributorRole role;
}
