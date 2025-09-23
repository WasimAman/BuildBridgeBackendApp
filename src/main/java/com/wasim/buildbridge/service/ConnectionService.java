package com.wasim.buildbridge.service;

import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface ConnectionService {

    public ApiResponseDTO sendRequest(String senderUsername, String receiverUsername);

    public ApiResponseDTO acceptRequest(long connectionId);

    public ApiResponseDTO rejectRequest(long connectionId);

    public ApiResponseDTO getAllPendingRequest(String username);

    public ApiResponseDTO getUserConnection(String username);

    public ApiResponseDTO removeConnection(long connectionId);
    
}
