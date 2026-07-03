package com.example.demoStudent.repository;

import com.example.demoStudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    List<User> findByUsernameContainingIgnoreCase(String keyword);
    User findByEmail(String email);
    User findByResetToken(String token);
}
