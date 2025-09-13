package com.wasim.buildbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wasim.buildbridge.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{
    
}
