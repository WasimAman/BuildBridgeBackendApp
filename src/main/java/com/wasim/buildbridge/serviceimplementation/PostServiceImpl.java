package com.wasim.buildbridge.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wasim.buildbridge.exception.PostNotFoundException;
import com.wasim.buildbridge.mapper.UserMapper;
import com.wasim.buildbridge.model.Comment;
import com.wasim.buildbridge.model.Like;
import com.wasim.buildbridge.model.Post;
import com.wasim.buildbridge.model.User;
import com.wasim.buildbridge.repository.CommentRepository;
import com.wasim.buildbridge.repository.LikeRepository;
import com.wasim.buildbridge.repository.PostRepository;
import com.wasim.buildbridge.repository.UserRepository;
import com.wasim.buildbridge.requestDTO.CommentRequestDTO;
import com.wasim.buildbridge.requestDTO.PostRequestDTO;
import com.wasim.buildbridge.responseDTO.ApiResponseDTO;
import com.wasim.buildbridge.responseDTO.CommentDTO;
import com.wasim.buildbridge.responseDTO.LikeDTO;
import com.wasim.buildbridge.responseDTO.PostDTO;
import com.wasim.buildbridge.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ApiResponseDTO addPost(String username, PostRequestDTO postRequest) {
        Post post = userMapper.mapToPost(postRequest, username);
        Post savedPost = postRepository.save(post);
        PostDTO postDTO = userMapper.mapToPostDTO(savedPost);

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Post has been saved successfully",
                postDTO);
        return response;
    }

    @Override
    public ApiResponseDTO getAllPost(String username) {
        try {
            User user = userRepository.findByUsernameOrEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            List<PostDTO> postDto = userMapper.mapToPostDTO(user.getPosts());

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "All post has been fetched.",
                    postDto);

            return response;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error: while fetching post");
        }
    }

    @Override
    public ApiResponseDTO getPostById(String username, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new PostNotFoundException("Post not found with id: " + postId);
        });

        if (!post.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized resource");
        }
        PostDTO postDto = userMapper.mapToPostDTO(post);

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Project has been fetched with id: " + postId,
                postDto);

        return response;
    }

    @Override
    public ApiResponseDTO updatePost(long postId, PostRequestDTO postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new PostNotFoundException("Post not found with id: " + postId);
        });

        post.setDescription(postRequest.getDescription());
        post.setImages(postRequest.getImages());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        PostDTO postDTO = userMapper.mapToPostDTO(savedPost);

        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "Post has been updated successfully",
                postDTO);

        return response;
    }

    @Override
    public ApiResponseDTO deletePost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new PostNotFoundException("Post not found with id: " + postId);
        });

        postRepository.delete(post);
        ApiResponseDTO response = new ApiResponseDTO(
                true,
                "post deleted successfully",
                null);
        return response;
    }

    @Override
    public ApiResponseDTO like(long postId, String username) {
        try {
            User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found with username: " + username);
            });

            Post post = postRepository.findById(postId).orElseThrow(() -> {
                throw new PostNotFoundException("Post not found with id: " + postId);
            });
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);

            Like savedLike = likeRepository.save(like);
            LikeDTO likeDTO = new LikeDTO(savedLike.getId(), user.getUsername(), user.getProfileImgUrl());

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "Like has been saved",
                    likeDTO);
            return response;
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (PostNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Error: while saving like");
        }
    }

    @Override
    public ApiResponseDTO comment(long postId, CommentRequestDTO commentRequest, String username) {
        try {
            User user = userRepository.findByUsernameOrEmail(username).orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found with username: " + username);
            });

            Post post = postRepository.findById(postId).orElseThrow(() -> {
                throw new PostNotFoundException("Post not found with id: " + postId);
            });

            Comment comment = new Comment();
            comment.setComment(commentRequest.getComment());
            comment.setUser(user);
            comment.setPost(post);
            comment.setCommentedAt(LocalDateTime.now());
            Comment savedComment = commentRepository.save(comment);

            CommentDTO commentDTO = new CommentDTO(savedComment.getId(), savedComment.getComment(), user.getUsername(),
                    savedComment.getCommentedAt());

            ApiResponseDTO response = new ApiResponseDTO(
                    true,
                    "Commented Successfully",
                    commentDTO);

            return response;
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (PostNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: while saving like");
        }
    }

}
