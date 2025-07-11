package com.zealthy.onboarding.service;

import com.zealthy.onboarding.dto.UserRegistrationRequest;
import com.zealthy.onboarding.dto.UserStepUpdateRequest;
import com.zealthy.onboarding.dto.UserResponse;
import com.zealthy.onboarding.dto.CompleteUserRequest;
import com.zealthy.onboarding.entity.User;
import com.zealthy.onboarding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Main production method - Complete user registration
    public UserResponse registerCompleteUser(CompleteUserRequest request) {
        System.out.println("UserService: Starting complete registration for: " + request.getEmail());
        
        // Validate email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            System.err.println("UserService: Email already exists: " + request.getEmail());
            throw new RuntimeException("User with this email already exists");
        }

        try {
            // Create complete user object
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // In production, this should be hashed
            user.setAboutMe(request.getAboutMe());
            user.setStreetAddress(request.getStreetAddress());
            user.setCity(request.getCity());
            user.setState(request.getState());
            user.setZip(request.getZip());
            user.setBirthdate(request.getBirthdate());
            user.setCurrentStep(3); // Completed all steps
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            System.out.println("UserService: Saving user - Email: " + user.getEmail());
            System.out.println("UserService: Data - AboutMe: " + (user.getAboutMe() != null ? "Yes" : "No") + 
                             ", Address: " + (user.getStreetAddress() != null ? "Yes" : "No") + 
                             ", Birthdate: " + (user.getBirthdate() != null ? "Yes" : "No"));

            user = userRepository.save(user);
            
            System.out.println("UserService: User saved successfully with ID: " + user.getId());
            
            return convertToResponse(user);
            
        } catch (Exception e) {
            System.err.println("UserService: Error saving user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save user: " + e.getMessage());
        }
    }

    // Check if email exists
    public Optional<UserResponse> getUserByEmail(String email) {
        System.out.println("UserService: Checking email existence: " + email);
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                System.out.println("UserService: Email found: " + email);
                return Optional.of(convertToResponse(userOpt.get()));
            } else {
                System.out.println("UserService: Email available: " + email);
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("UserService: Error checking email: " + e.getMessage());
            throw new RuntimeException("Error checking email: " + e.getMessage());
        }
    }

    // Get all users for data table
    public List<UserResponse> getAllUsers() {
        try {
            System.out.println("UserService: Fetching all users from database");
            List<User> users = userRepository.findAll();
            System.out.println("UserService: Found " + users.size() + " users in database");
            
            List<UserResponse> responses = users.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
            
            System.out.println("UserService: Converted to " + responses.size() + " response objects");
            
            // Log sample for debugging
            if (!responses.isEmpty()) {
                UserResponse first = responses.get(0);
                System.out.println("UserService: Sample user - ID: " + first.getId() + 
                                 ", Email: " + first.getEmail() + 
                                 ", Created: " + first.getCreatedAt());
            }
            
            return responses;
            
        } catch (Exception e) {
            System.err.println("UserService: Error fetching users: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch users: " + e.getMessage());
        }
    }

    // Get user by ID
    public UserResponse getUserById(Long id) {
        System.out.println("UserService: Finding user by ID: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return convertToResponse(user);
    }

    // Legacy registration method (for backward compatibility)
    public UserResponse registerUser(UserRegistrationRequest request) {
        System.out.println("UserService: Legacy registration for: " + request.getEmail());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User(request.getEmail(), request.getPassword());
        user.setCurrentStep(1);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        
        return convertToResponse(user);
    }

    // Update user step (for legacy step-by-step flow)
    public UserResponse updateUserStep(Long userId, Integer step, UserStepUpdateRequest request) {
        System.out.println("UserService: Updating user " + userId + " to step " + step);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields based on request
        if (request.getAboutMe() != null && !request.getAboutMe().trim().isEmpty()) {
            user.setAboutMe(request.getAboutMe());
        }
        if (request.getStreetAddress() != null && !request.getStreetAddress().trim().isEmpty()) {
            user.setStreetAddress(request.getStreetAddress());
        }
        if (request.getCity() != null && !request.getCity().trim().isEmpty()) {
            user.setCity(request.getCity());
        }
        if (request.getState() != null && !request.getState().trim().isEmpty()) {
            user.setState(request.getState());
        }
        if (request.getZip() != null && !request.getZip().trim().isEmpty()) {
            user.setZip(request.getZip());
        }
        if (request.getBirthdate() != null) {
            user.setBirthdate(request.getBirthdate());
        }

        // Update step if advancing
        if (step > user.getCurrentStep()) {
            user.setCurrentStep(step);
        }

        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        
        return convertToResponse(user);
    }

    // Convert User entity to UserResponse DTO
    private UserResponse convertToResponse(User user) {
        try {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setAboutMe(user.getAboutMe());
            response.setStreetAddress(user.getStreetAddress());
            response.setCity(user.getCity());
            response.setState(user.getState());
            response.setZip(user.getZip());
            response.setBirthdate(user.getBirthdate());
            response.setCurrentStep(user.getCurrentStep());
            response.setCreatedAt(user.getCreatedAt());
            response.setUpdatedAt(user.getUpdatedAt());
            return response;
        } catch (Exception e) {
            System.err.println("UserService: Error converting user to response: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to convert user: " + e.getMessage());
        }
    }
}