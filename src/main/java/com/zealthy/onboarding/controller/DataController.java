package com.zealthy.onboarding.controller;

import com.zealthy.onboarding.dto.UserResponse;
import com.zealthy.onboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost:3000")
public class DataController {

    @Autowired
    private UserService userService;

    // ISSUE 4 FIX: Endpoint for data table
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            System.out.println("DataController: Returning " + users.size() + " users");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.err.println("DataController error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Additional endpoint for testing
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Data controller is working!");
    }
}