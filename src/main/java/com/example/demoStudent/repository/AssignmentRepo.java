package com.example.demoStudent.repository;

import com.example.demoStudent.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


    @Repository
    public interface AssignmentRepo extends JpaRepository<Assignment,Long> {

        // Get all assignments submitted by a specific user
        List<Assignment> findByUsername(String username);

        // Get only submitted assignments by a specific user
        List<Assignment> findByUsernameAndSubmittedTrue(String username);

        // Get all pending (not submitted) assignments
        List<Assignment> findBySubmittedFalse();
    }


