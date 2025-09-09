package com.wasim.buildbridge.service;

import com.wasim.buildbridge.requestDTO.RemoveConnectionDTO;
import com.wasim.buildbridge.requestDTO.UserConnectionsDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface ConnectionService {

    ApiResponseDTO makeConnection(UserConnectionsDTO userConnectionsDTO);

    ApiResponseDTO deleteConnection(RemoveConnectionDTO removeConnectionDTO);
    
}
