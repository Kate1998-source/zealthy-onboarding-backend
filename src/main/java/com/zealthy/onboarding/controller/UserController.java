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
@CrossOrigin(origins = {"http://localhost:3000", "https://*.vercel.app", "https://*.netlify.app"})
public class UserController {

    @Autowired
    private UserService userService;

    // Health check endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User controller is working! " + java.time.LocalDateTime.now());
    }

    // Complete user registration - Main production endpoint
    @PostMapping("/register-complete")
    public ResponseEntity<UserResponse> registerCompleteUser(@Valid @RequestBody CompleteUserRequest request) {
        try {
            System.out.println("UserController: Complete registration request for: " + request.getEmail());
            UserResponse response = userService.registerCompleteUser(request);
            System.out.println("UserController: Registration successful, ID: " + response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            System.err.println("UserController: Registration failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            System.err.println("UserController: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Check email availability
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        try {
            System.out.println("UserController: Checking email: " + email);
            Optional<UserResponse> response = userService.getUserByEmail(email);
            if (response.isPresent()) {
                System.out.println("UserController: Email exists: " + email);
                return ResponseEntity.ok(response.get());
            } else {
                System.out.println("UserController: Email available: " + email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("UserController: Email check error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get all users for data table
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try {
            System.out.println("UserController: Fetching all users");
            List<UserResponse> users = userService.getAllUsers();
            System.out.println("UserController: Returning " + users.size() + " users");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.err.println("UserController: Get all users error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        try {
            System.out.println("UserController: Getting user by ID: " + id);
            UserResponse response = userService.getUserById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("UserController: User not found: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.err.println("UserController: Error getting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Legacy registration endpoint (for backward compatibility)
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            System.out.println("UserController: Legacy registration for: " + request.getEmail());
            UserResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            System.err.println("UserController: Legacy registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            System.err.println("UserController: Legacy registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update user step (for legacy step-by-step flow)
    @PutMapping("/{id}/step/{step}")
    public ResponseEntity<UserResponse> updateUserStep(
            @PathVariable Long id,
            @PathVariable Integer step,
            @RequestBody UserStepUpdateRequest request) {
        try {
            System.out.println("UserController: Updating user " + id + " to step " + step);
            UserResponse response = userService.updateUserStep(id, step, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("UserController: Update step failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.err.println("UserController: Update step error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}