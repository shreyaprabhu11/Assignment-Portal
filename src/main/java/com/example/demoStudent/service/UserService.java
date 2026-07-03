package com.example.demoStudent.service;

import com.example.demoStudent.model.Assignment;
import com.example.demoStudent.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // ---------- User Management ----------
    void registerUser(User user);

    Optional<User> findByUserId(String userId);

    List<User> getAllUsers();

    void deleteUser(String username);

    List<User> searchByUsername(String keyword);

    void updateUser(User user);

    void saveUser(User user);


    // ---------- Assignment Management ----------
    void postAssignment(Assignment assignment); // Admin posts assignment

    List<Assignment> getAllAssignments();

    List<Assignment> getAssignmentsByUser(String username);

    void submitAssignment(Long assignmentId, String username, String filePath);

    List<Assignment> getSubmittedAssignments(String username);

    List<Assignment> getPendingAssignments(String username);

    List<Assignment> getAllSubmittedAssignments();

    void deleteAssignment(Long id);


    void save(Assignment assignment);

}
