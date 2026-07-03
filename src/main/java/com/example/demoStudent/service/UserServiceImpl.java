package com.example.demoStudent.service;

import com.example.demoStudent.model.Assignment;
import com.example.demoStudent.model.User;
import com.example.demoStudent.repository.AssignmentRepo;
import com.example.demoStudent.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    // ----------------- User Management -----------------

    @Override
    public void registerUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(String username) {
        userRepo.deleteById(username);
    }

    @Override
    public List<User> searchByUsername(String keyword) {
        return userRepo.findByUsernameContainingIgnoreCase(keyword);
    }

    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    // ----------------- Assignment Management -----------------

    @Override
    public void postAssignment(Assignment assignment) {
        assignment.setPostedDate(LocalDateTime.now());
        assignment.setSubmitted(false);
        assignmentRepo.save(assignment);
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepo.findAll();
    }

    @Override
    public List<Assignment> getAssignmentsByUser(String username) {
        return assignmentRepo.findByUsername(username);
    }

    @Override
    public void submitAssignment(Long assignmentId, String username, String filePath) {
        Optional<Assignment> optional = assignmentRepo.findById(assignmentId);
        if (optional.isPresent()) {
            Assignment assignment = optional.get();
            assignment.setSubmitted(true);
            assignment.setUsername(username);
            assignment.setSubmissionDate(LocalDateTime.now());
            assignment.setFilePath(filePath);
            assignment.setStatus("Submitted");
            assignmentRepo.save(assignment);
        }
    }


    @Override
    public List<Assignment> getSubmittedAssignments(String username) {
        return assignmentRepo.findByUsernameAndSubmittedTrue(username);
    }

    @Override
    public List<Assignment> getPendingAssignments(String username) {
        return assignmentRepo.findByUsername(username).stream()
                .filter(a -> !a.isSubmitted())
                .collect(Collectors.toList());
    }


    @Override
    public void save(Assignment assignment) {
        assignmentRepo.save(assignment);
    }

    @Override
    public List<Assignment> getAllSubmittedAssignments() {
        return assignmentRepo.findAll().stream()
                .filter(Assignment::isSubmitted)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentRepo.deleteById(id);
    }

}
