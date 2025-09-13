package com.wasim.buildbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wasim.buildbridge.model.Projects;

@Repository
public interface ProjectRepository extends JpaRepository<Projects,Long>{
    
}
