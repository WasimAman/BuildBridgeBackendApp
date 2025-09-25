package com.wasim.buildbridge.service;

import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface ConnectionService {

    public ApiResponseDTO sendRequest(String senderUsername, String receiverUsername);

    public ApiResponseDTO acceptRequest(long connectionId,String currentUser);

    public ApiResponseDTO rejectRequest(long connectionId,String currentUser);

    public ApiResponseDTO getAllPendingRequest(String username);

    public ApiResponseDTO getUserConnection(String username);

    public ApiResponseDTO removeConnection(long connectionId,String currentUser);
    
}
