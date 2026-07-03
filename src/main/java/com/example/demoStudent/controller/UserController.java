package com.example.demoStudent.controller;

import com.example.demoStudent.model.Assignment;
import com.example.demoStudent.model.User;
import com.example.demoStudent.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    @Autowired
    private UserService service;

    public UserController() throws IOException {
        Files.createDirectories(fileStorageLocation);
    }

    // ========== Registration ==========

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (service.findByUserId(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }

        if (!user.getPswrd().equals(user.getCpswrd())) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }

        user.setRole("user");  // default role
        service.registerUser(user);
        return "redirect:/?msg=success";
    }

    // ========== Login ==========

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/userLogin")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        Optional<User> foundUser = service.findByUserId(user.getUsername());

        if (foundUser.isPresent() && foundUser.get().getPswrd().equals(user.getPswrd())) {
            session.setAttribute("loggedInUser", foundUser.get()); // ✅ Set correctly

            if ("admin".equalsIgnoreCase(foundUser.get().getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/home"; // ✅ Normal user redirected here
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }




    // ========== Admin Dashboard ==========

    @GetMapping("/admin/dashboard")
    public String showDashboard(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = service.searchByUsername(keyword); // fetch matching users
        } else {
            users = service.getAllUsers(); // all users
        }

        List<Assignment> assignments = service.getAllAssignments();
        List<Assignment> submittedAssignments = service.getAllSubmittedAssignments();

        model.addAttribute("users", users);
        model.addAttribute("assignments", assignments);
        model.addAttribute("submittedAssignments", submittedAssignments);

        return "adminDashboard";
    }




    @PostMapping("/admin/postAssignment")
    public String postAssignment(@RequestParam("title") String title,
                                 @RequestParam("dueDate") String dueDateStr,
                                 @RequestParam("username") String username) {
        Assignment assignment = new Assignment();
        assignment.setTitle(title);

        LocalDate dueDate = LocalDate.parse(dueDateStr);
        assignment.setDueDate(dueDate.atStartOfDay());

        assignment.setPostedDate(LocalDateTime.now());
        assignment.setSubmitted(false);
        assignment.setUsername(username); // 🔥 Assign to specific user

        service.save(assignment);
        return "redirect:/admin/dashboard";
    }


    // ========== Admin Add/Edit/Delete User ==========

    @GetMapping("/admin/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        if (!user.getPswrd().equals(user.getCpswrd())) {
            model.addAttribute("error", "Passwords do not match.");
            return "addUser";
        }
        if (service.findByUserId(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "addUser";
        }
        service.registerUser(user);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/edit/{username}")
    public String editUser(@PathVariable String username, Model model) {
        Optional<User> user = service.findByUserId(username);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "editUser";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User updatedUser) {
        Optional<User> existingUserOpt = service.findByUserId(updatedUser.getUsername());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (updatedUser.getPswrd() == null || updatedUser.getPswrd().isEmpty()) {
                updatedUser.setPswrd(existingUser.getPswrd());
                updatedUser.setCpswrd(existingUser.getCpswrd());
            }
            service.updateUser(updatedUser);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return "redirect:/admin/dashboard";
    }

    // ========== Logout ==========

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ========== User Homepage ==========

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser"); // should match loginUser()
        if (user == null) return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("pendingAssignments", service.getPendingAssignments(user.getUsername()));
        model.addAttribute("submittedAssignments", service.getSubmittedAssignments(user.getUsername()));
        return "home";
    }




    // ========== Assignment Upload ==========
    @PostMapping("/assignments/upload")
    public String uploadAssignment(@RequestParam("file") MultipartFile file,
                                   @RequestParam("username") String username,
                                   @RequestParam("assignmentId") Long assignmentId) {
        try {
            String filename = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Submit specific assignment
            service.submitAssignment(assignmentId, username, filename);

        } catch (IOException e) {
            e.printStackTrace();
            // Optional: Log the error or show a user-friendly error page
            return "redirect:/home?error=upload_failed";
        }

        return "redirect:/home";
    }



    @GetMapping("/assignments/download/{id}")
    public ResponseEntity<Resource> downloadAssignment(@PathVariable Long id) throws MalformedURLException {
        Optional<Assignment> assignmentOpt = service.getAllAssignments().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();

        if (assignmentOpt.isPresent()) {
            Assignment assignment = assignmentOpt.get();
            Path filePath = fileStorageLocation.resolve(assignment.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + assignment.getFilePath() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                        .body(resource);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/deleteAssignment/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        service.deleteAssignment(id);
        return "redirect:/admin/dashboard";
    }


}
