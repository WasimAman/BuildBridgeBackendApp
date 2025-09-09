package com.wasim.buildbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.requestDTO.RemoveConnectionDTO;
import com.wasim.buildbridge.requestDTO.UserConnectionsDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.ConnectionService;

@RestController
@RequestMapping("/api/connections/")
public class ConnectionController {
    @Autowired
    private ConnectionService connectionService;

    @PostMapping("add") // url :- http://localhost:8080/api/connections/add
    public ResponseEntity<ApiResponseDTO> addConnection(UserConnectionsDTO userConnectionsDTO){
        return null;
    }

    @PostMapping("remove")  // url :- http://localhost:8080/api/connections/remove
    public ResponseEntity<ApiResponseDTO> removeConnection(RemoveConnectionDTO removeConnectionDTO){
        return null;
    }

    @PostMapping("accept")  // url :- http://localhost:8080/api/connections/accept
    public ResponseEntity<ApiResponseDTO> acceptConnection(){
        return null;
    }
}
