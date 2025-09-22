package com.wasim.buildbridge.service;

import com.wasim.buildbridge.requestDTO.CommentRequestDTO;
import com.wasim.buildbridge.requestDTO.PostRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;

public interface PostService {

    ApiResponseDTO addPost(String username, PostRequestDTO postRequest);

    ApiResponseDTO getAllPost(String username);

    ApiResponseDTO getPostById(String username, long projectId);

    ApiResponseDTO updatePost(long postId, PostRequestDTO postRequest);

    ApiResponseDTO deletePost(long postId);

    ApiResponseDTO like(long postId,String username);

    ApiResponseDTO comment(long postId, CommentRequestDTO comment, String username);
    
}
