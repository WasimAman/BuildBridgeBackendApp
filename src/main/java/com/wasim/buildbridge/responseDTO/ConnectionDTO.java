package com.wasim.buildbridge.responseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDTO {
    private int connectionsCount;
    private List<ConnectionPreviewDTO> preview;
}
