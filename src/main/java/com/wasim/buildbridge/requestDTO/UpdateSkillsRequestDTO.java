package com.wasim.buildbridge.requestDTO;

import java.util.List;

public class UpdateSkillsRequestDTO {
    private List<String> skills;

    public UpdateSkillsRequestDTO() {}

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
