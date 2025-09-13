package com.wasim.buildbridge.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionPreviewDTO {
    private Long id;
    private String username;
    private String profileImgUrl;
}
