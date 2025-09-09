package com.wasim.buildbridge.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionsDTO {
    private String sender;
    private String reciever;
}
