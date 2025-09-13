package com.wasim.buildbridge.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private long id;
    private String username;
    private String profileImgUrl;
}
