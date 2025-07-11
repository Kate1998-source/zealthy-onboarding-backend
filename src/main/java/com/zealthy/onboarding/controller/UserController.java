package com.zealthy.onboarding.controller;

import com.zealthy.onboarding.dto.UserRegistrationRequest;
import com.zealthy.onboarding.dto.UserStepUpdateRequest;
import com.zealthy.onboarding.dto.UserResponse;
import com.zealthy.onboarding.dto.CompleteUserRequest;
import com.zealthy.onboarding.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            UserResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            System.err.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ISSUE 2 FIX: Complete registration endpoint
    @PostMapping("/register-complete")
    public ResponseEntity<UserResponse> registerCompleteUser(@Valid @RequestBody CompleteUserRequest request) {
        try {
            System.out.println("Received complete registration request for email: " + request.getEmail());
            UserResponse response = userService.registerCompleteUser(request);
            System.out.println("Registration completed successfully! User ID: " + response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            System.err.println("Complete registration error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/step/{step}")
    public ResponseEntity<UserResponse> updateUserStep(
            @PathVariable Long id,
            @PathVariable Integer step,
            @RequestBody UserStepUpdateRequest request) {
        try {
            UserResponse response = userService.updateUserStep(id, step, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("Update step error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        try {
            UserResponse response = userService.getUserById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ISSUE 1 FIX: Check if email exists endpoint
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        try {
            System.out.println("Checking if email exists: " + email);
            Optional<UserResponse> response = userService.getUserByEmail(email);
            if (response.isPresent()) {
                System.out.println("Email found in database: " + email);
                return ResponseEntity.ok(response.get());
            } else {
                System.out.println("Email not found in database: " + email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Email check error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ISSUE 4 FIX: Get all users for data table
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            System.out.println("UserController: Returning " + users.size() + " users");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.err.println("Get all users error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User controller is working!");
    }
}