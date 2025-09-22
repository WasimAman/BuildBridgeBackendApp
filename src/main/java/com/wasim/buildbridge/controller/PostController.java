package com.wasim.buildbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasim.buildbridge.requestDTO.CommentRequestDTO;
import com.wasim.buildbridge.requestDTO.PostRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.service.PostService;

@RestController
@RequestMapping("/api/v1/")
public class PostController{

    @Autowired
    private PostService postService;
    
    @PostMapping("posts")
    public ResponseEntity<ApiResponseDTO> addPost(@RequestBody PostRequestDTO postRequest,Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = postService.addPost(username,postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("posts")
    public ResponseEntity<ApiResponseDTO> getAllPost(Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = postService.getAllPost(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<ApiResponseDTO> getPostById(@PathVariable long projectId,Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = postService.getPostById(username,projectId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<ApiResponseDTO> editPost(@PathVariable("id") long postId,@RequestBody PostRequestDTO postRequest,Authentication authentication){
        ApiResponseDTO response = postService.updatePost(postId,postRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<ApiResponseDTO> deletePost(@PathVariable("id") long postId){
        ApiResponseDTO response = postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("posts/{id}/like")
    public ResponseEntity<ApiResponseDTO> likePost(@PathVariable("id") long postId,Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = postService.like(postId,username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("posts/{id}/comment")
    public ResponseEntity<ApiResponseDTO> commentPost(@PathVariable("id") long postId,@RequestBody CommentRequestDTO comment,Authentication authentication){
        String username = authentication.getPrincipal().toString();
        ApiResponseDTO response = postService.comment(postId,comment,username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
