package com.wasim.buildbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.ConnectionService;

@RestController
@RequestMapping("/api/v1/")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @PostMapping("connections/send/{receiver}")
    public ResponseEntity<ApiResponseDTO> sendRequest(@PathVariable("receiver") String receiverUsername,Authentication authentication){
        String senderUsername = authentication.getPrincipal().toString();
        ApiResponseDTO response = connectionService.sendRequest(senderUsername,receiverUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("connections/accept/{id}")
    public ResponseEntity<ApiResponseDTO> acceptRequest(@PathVariable("id") long connectionId){
        ApiResponseDTO response = connectionService.acceptRequest(connectionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("connections/reject/{id}")
    public ResponseEntity<ApiResponseDTO> rejectRequest(@PathVariable("id") long connectionId){
        ApiResponseDTO response = connectionService.rejectRequest(connectionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("connections/pending")
    public ResponseEntity<ApiResponseDTO> getAllPendingRequest(Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = connectionService.getAllPendingRequest(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("connections")
    public ResponseEntity<ApiResponseDTO> getUserConnections(Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = connectionService.getUserConnection(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("connections/{id}")
    public ResponseEntity<ApiResponseDTO> removeConnection(){
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
