package com.wasim.buildbridge.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveConnectionDTO {
    private String user1;
    private String user2;
}
