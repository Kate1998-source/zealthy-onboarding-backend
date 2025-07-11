package com.zealthy.onboarding.controller;

import com.zealthy.onboarding.dto.ConfigUpdateRequest;
import com.zealthy.onboarding.entity.OnboardingConfig;
import com.zealthy.onboarding.entity.ComponentType;
import com.zealthy.onboarding.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/config")
    public ResponseEntity<Map<Integer, List<ComponentType>>> getConfiguration() {
        Map<Integer, List<ComponentType>> config = adminService.getConfigurationByPage();
        return ResponseEntity.ok(config);
    }

    @PutMapping("/config")
    public ResponseEntity<String> updateConfiguration(@RequestBody ConfigUpdateRequest request) {
        try {
            adminService.updateConfiguration(request.getComponentPageMap());
            return ResponseEntity.ok("Configuration updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/config/page/{pageNumber}")
    public ResponseEntity<List<OnboardingConfig>> getComponentsForPage(@PathVariable Integer pageNumber) {
        List<OnboardingConfig> components = adminService.getComponentsForPage(pageNumber);
        return ResponseEntity.ok(components);
    }
}